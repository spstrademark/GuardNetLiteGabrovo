package Ai;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.Tensor;
import org.tensorflow.lite.gpu.GpuDelegate;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.util.Comparator;
import java.util.PriorityQueue;


public class TFLiteDetector implements Classifier {

    enum Device {
        CPU,
        NNAPI,
        GPU
    }


    public static int[][] CocoColors = {
            {255, 0, 0}, {255, 0, 0}, {255, 0, 0}, {255, 0, 0}, {255, 0, 0}, {255, 0, 0}, {255, 0, 0},
            {255, 0, 0}, {255, 0, 0}, {255, 0, 0}, {255, 0, 0}, {255, 0, 0}, {255, 0, 0}, {255, 0, 0},
            {255, 0, 0},{255, 0, 0} , {255, 0, 0}, {255, 0, 0}};

    String[] partNames = {
            "nose", "leftEye", "rightEye", "leftEar", "rightEar", "leftShoulder",
            "rightShoulder", "leftElbow", "rightElbow", "leftWrist", "rightWrist",
            "leftHip", "rightHip", "leftKnee", "rightKnee", "leftAnkle", "rightAnkle"
    };

    String[][] poseChain = {
            {"nose", "leftEye"}, {"leftEye", "leftEar"}, {"nose", "rightEye"},
            {"rightEye", "rightEar"}, {"nose", "leftShoulder"},
            {"leftShoulder", "leftElbow"}, {"leftElbow", "leftWrist"},
            {"leftShoulder", "leftHip"}, {"leftHip", "leftKnee"},
            {"leftKnee", "leftAnkle"}, {"nose", "rightShoulder"},
            {"rightShoulder", "rightElbow"}, {"rightElbow", "rightWrist"},
            {"rightShoulder", "rightHip"}, {"rightHip", "rightKnee"},
            {"rightKnee", "rightAnkle"}
    };

    Map<String, Integer> partsIds = new HashMap<>();
    List<Integer> parentToChildEdges = new ArrayList<>();
    List<Integer> childToParentEdges = new ArrayList<>();

    int localMaximumRadius = 1;
    int outputStride = 16;
    double threshold = 0.7;

    int numResults = 2;      // defaults to 5
    int nmsRadius = 10;

    Device device = Device.CPU;
    private  Interpreter interpreter = null;
    private  GpuDelegate gpuDelegate = null;
    private Context context = null;
    private String filename = "";
    private static int NUM_LITE_THREADS = 4;
    private static final String TAG = "tflite_detector";
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128.0f;

    private static final int BYTES_PER_CHANNEL = 4;
    private int inputSize = 0;

    private int HUMAN_RADIUS = 2;
    private TFLiteDetector() {
    }

    public static Classifier create(Context context,
                                                        String filename,
                                                        int raw_res,
                                                        int img_width,
                                                        int img_height
    ) throws IOException {
        TFLiteDetector tflite = new TFLiteDetector();

        tflite.context = context;
        tflite.filename = filename;
//        tflite.DIM_IMG_WIDTH = img_width;
//        tflite.DIM_IMG_HEIGHT = img_height;

        Interpreter.Options options = new Interpreter.Options();
        switch(tflite.device){
            case CPU:
                break;
            case GPU:
                tflite.gpuDelegate = new GpuDelegate();
                options.addDelegate(tflite.gpuDelegate);
                break;
            default:
                options = options.setUseNNAPI(true);
                break;
        }

        options.setNumThreads(NUM_LITE_THREADS);
        tflite.inputSize = img_width;
        tflite.interpreter = new Interpreter(loadModelFile(tflite.context, tflite.filename,raw_res), options);



        return tflite;
    }

    private static Matrix getTransformationMatrix(final int srcWidth,
                                                  final int srcHeight,
                                                  final int dstWidth,
                                                  final int dstHeight,
                                                  final boolean maintainAspectRatio) {
        final Matrix matrix = new Matrix();

        if (srcWidth != dstWidth || srcHeight != dstHeight) {
            final float scaleFactorX = dstWidth / (float) srcWidth;
            final float scaleFactorY = dstHeight / (float) srcHeight;

            if (maintainAspectRatio) {
                final float scaleFactor = Math.max(scaleFactorX, scaleFactorY);
                matrix.postScale(scaleFactor, scaleFactor);
            } else {
                matrix.postScale(scaleFactorX, scaleFactorY);
            }
        }

        matrix.invert(new Matrix());
        return matrix;
    }

    /** Writes Image data into a {@code ByteBuffer}. */
    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmapRaw) {

        Tensor tensor = interpreter.getInputTensor(0);
        int[] shape = tensor.shape();
        inputSize = shape[1];
        int inputChannels = shape[3];

        int bytePerChannel = tensor.dataType() == DataType.UINT8 ? 1 : BYTES_PER_CHANNEL;
        ByteBuffer imgData = ByteBuffer.allocateDirect(1 * inputSize * inputSize * inputChannels * bytePerChannel);
        imgData.order(ByteOrder.nativeOrder());

        Bitmap bitmap = bitmapRaw;
        if (bitmapRaw.getWidth() != inputSize || bitmapRaw.getHeight() != inputSize) {
            Matrix matrix = getTransformationMatrix(bitmapRaw.getWidth(), bitmapRaw.getHeight(),
                    inputSize, inputSize, false);
            bitmap = Bitmap.createBitmap(inputSize, inputSize, Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(bitmapRaw, matrix, null);
        }

        if (tensor.dataType() == DataType.FLOAT32) {
            for (int i = 0; i < inputSize; ++i) {
                for (int j = 0; j < inputSize; ++j) {
                    int pixelValue = bitmap.getPixel(j, i);
                    imgData.putFloat((((pixelValue >> 16) & 0xFF) - IMAGE_MEAN) / IMAGE_STD);
                    imgData.putFloat((((pixelValue >> 8) & 0xFF) - IMAGE_MEAN) / IMAGE_STD);
                    imgData.putFloat(((pixelValue & 0xFF) - IMAGE_MEAN) / IMAGE_STD);
                }
            }
        } else {
            for (int i = 0; i < inputSize; ++i) {
                for (int j = 0; j < inputSize; ++j) {
                    int pixelValue = bitmap.getPixel(j, i);
                    imgData.put((byte) ((pixelValue >> 16) & 0xFF));
                    imgData.put((byte) ((pixelValue >> 8) & 0xFF));
                    imgData.put((byte) (pixelValue & 0xFF));
                }
            }
        }
//feedInputTensor
        return imgData;

//        ByteBuffer buffer = ByteBuffer.allocateDirect(4 * DIM_BATCH_SIZE * this.DIM_IMG_WIDTH * this.DIM_IMG_HEIGHT * DIM_PIXEL_SIZE);
//        buffer.order(ByteOrder.nativeOrder());
//        buffer.rewind();
//
//        int[] intValues = new int[this.DIM_IMG_WIDTH * this.DIM_IMG_HEIGHT];
//        int bitmap_width = bitmap.getWidth();
//        int bitmap_height = bitmap.getHeight();
//        bitmap.getPixels(intValues, 0, bitmap_width, 0, 0, bitmap_width, bitmap_height);
//        // Convert the image to floating point.
//        for (final int val : intValues) {
//            buffer.putFloat((((val >> 16) & 0xFF) - IMAGE_MEAN) / IMAGE_STD);
//            buffer.putFloat((((val >> 8) & 0xFF) - IMAGE_MEAN) / IMAGE_STD);
//            buffer.putFloat((((val) & 0xFF) - IMAGE_MEAN) / IMAGE_STD);
//        }
//
//        return buffer;
    }

    private static AssetFileDescriptor GetFileDescriptor(Context context, String filepath, int raw_res) throws IOException {
        if(filepath!=null){ // if file is found in assets try to get it from there, if not use it as raw_res
            return context.getAssets().openFd(filepath);
        }else{
            return context.getResources().openRawResourceFd(raw_res);
        }
    }

    /** Memory-map the model file in Assets. */
    private static MappedByteBuffer loadModelFile(Context context, String filename, int raw_res) throws IOException {
        final AssetFileDescriptor fileDescriptor =  GetFileDescriptor(context,filename,raw_res);//context.getResources().openRawResourceFd(raw_res);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }




    /** Classifies a frame from the preview stream. */

    public  List<Map<String, Object>> get_positions(Bitmap bitmap) {
        if (interpreter == null) {
            Log.e(TAG, "Image classifier has not been initialized; Skipped.");
    //        return null;
        }
        ByteBuffer buffer =  convertBitmapToByteBuffer(bitmap);
        Object[] input = {buffer};


        Map<Integer, Object> outputMap = new HashMap<>();

        for (int i = 0; i < interpreter.getOutputTensorCount(); i++) {
            int[] shape = interpreter.getOutputTensor(i).shape();
            float[][][][] output = new float[shape[0]][shape[1]][shape[2]][shape[3]];
            outputMap.put(i, output);
        }

        interpreter.runForMultipleInputsOutputs(input, outputMap);
        List<Map<String, Object>> keypoint =    decodePose(outputMap);

        return keypoint;

    }


    public List<List<Coords>>  getBodyPartsPosition(Bitmap Bitmap, @NotNull List<Map<String, Object>> keypoints)
    {
        if(keypoints==null) return null;
        int size = keypoints.size();
        if(size==0) return null;

        int image_w = Bitmap.getWidth();
        int image_h = Bitmap.getHeight();

        List<List<Coords>> Positions = new ArrayList<List<Coords>>();

        for(int i =0; i <size;i++) {

            List<Coords> keyPos = new ArrayList<>();
            HashMap<Integer,Object> keys =  (HashMap<Integer,Object>)keypoints.get(i).get("keypoints");
            int key_size = keys.size();
            for(int j=0; j < key_size;j++)
            {
                Coords coords = new Coords();
                HashMap<String,Float> item = (HashMap<String,Float>)keys.get(i);
                coords.x = (item.get("x") * image_w) - 6;
                coords.y = (item.get("y") * image_h) - 6;
                keyPos.add(coords);
            }

            Positions.add(keyPos);
        }
        return Positions;
    }



    private  List<Map<String, Object>> decodePose( Map<Integer, Object> outputMap) {
        for (int i = 0; i < partNames.length; ++i)
            partsIds.put(partNames[i], i);

        for (int i = 0; i < poseChain.length; ++i) {
            parentToChildEdges.add(partsIds.get(poseChain[i][1]));
            childToParentEdges.add(partsIds.get(poseChain[i][0]));
        }

        float[][][] scores = ((float[][][][]) outputMap.get(0))[0];
        float[][][] offsets = ((float[][][][]) outputMap.get(1))[0];
        float[][][] displacementsFwd = ((float[][][][]) outputMap.get(2))[0];
        float[][][] displacementsBwd = ((float[][][][]) outputMap.get(3))[0];

        PriorityQueue<Map<String, Object>> pq = buildPartWithScoreQueue(scores, threshold, localMaximumRadius);

        int numParts = scores[0][0].length;
        int numEdges = parentToChildEdges.size();
        int sqaredNmsRadius = nmsRadius * nmsRadius;

        List<Map<String, Object>> results = new ArrayList<>();

        while (results.size() < numResults && pq.size() > 0) {
            Map<String, Object> root = pq.poll();
            float[] rootPoint = getImageCoords(root, outputStride, numParts, offsets);

            if (withinNmsRadiusOfCorrespondingPoint(
                    results, sqaredNmsRadius, rootPoint[0], rootPoint[1], (int) root.get("partId")))
                continue;

            Map<String, Object> keypoint = new HashMap<>();
            keypoint.put("score", root.get("score"));
            keypoint.put("part", partNames[(int) root.get("partId")]);
            keypoint.put("y", rootPoint[0] / inputSize);
            keypoint.put("x", rootPoint[1] / inputSize);
//            keypoint.put("y", rootPoint[0] );
//            keypoint.put("x", rootPoint[1] );
            Map<Integer, Map<String, Object>> keypoints = new HashMap<>();
            keypoints.put((int) root.get("partId"), keypoint);

            for (int edge = numEdges - 1; edge >= 0; --edge) {
                int sourceKeypointId = parentToChildEdges.get(edge);
                int targetKeypointId = childToParentEdges.get(edge);
                if (keypoints.containsKey(sourceKeypointId) && !keypoints.containsKey(targetKeypointId)) {
                    keypoint = traverseToTargetKeypoint(edge, keypoints.get(sourceKeypointId),
                            targetKeypointId, scores, offsets, outputStride, displacementsBwd);
                    if(keypoint==null) return null;
                    keypoints.put(targetKeypointId, keypoint);
                }
            }

            for (int edge = 0; edge < numEdges; ++edge) {
                int sourceKeypointId = childToParentEdges.get(edge);
                int targetKeypointId = parentToChildEdges.get(edge);
                if (keypoints.containsKey(sourceKeypointId) && !keypoints.containsKey(targetKeypointId)) {
                    keypoint = traverseToTargetKeypoint(edge, keypoints.get(sourceKeypointId),
                            targetKeypointId, scores, offsets, outputStride, displacementsFwd);
                    if(keypoint==null) return null;
                    keypoints.put(targetKeypointId, keypoint);
                }
            }

            Map<String, Object> result = new HashMap<>();
            result.put("keypoints", keypoints);
            result.put("score", getInstanceScore(keypoints, numParts));
            results.add(result);
        }
        return results;

    }

    PriorityQueue<Map<String, Object>> buildPartWithScoreQueue(float[][][] scores,
                                                               double threshold,
                                                               int localMaximumRadius) {
        PriorityQueue<Map<String, Object>> pq =
                new PriorityQueue<>(
                        1,
                        new Comparator<Map<String, Object>>() {
                            @Override
                            public int compare(Map<String, Object> lhs, Map<String, Object> rhs) {
                                return Float.compare((float) rhs.get("score"), (float) lhs.get("score"));
                            }
                        });

        for (int heatmapY = 0; heatmapY < scores.length; ++heatmapY) {
            for (int heatmapX = 0; heatmapX < scores[0].length; ++heatmapX) {
                for (int keypointId = 0; keypointId < scores[0][0].length; ++keypointId) {
                    float score = sigmoid(scores[heatmapY][heatmapX][keypointId]);
                    if (score < threshold) continue;

                    if (scoreIsMaximumInLocalWindow(
                            keypointId, score, heatmapY, heatmapX, localMaximumRadius, scores)) {
                        Map<String, Object> res = new HashMap<>();
                        res.put("score", score);
                        res.put("y", heatmapY);
                        res.put("x", heatmapX);
                        res.put("partId", keypointId);
                        pq.add(res);
                    }
                }
            }
        }

        return pq;
    }

    boolean scoreIsMaximumInLocalWindow(int keypointId,
                                        float score,
                                        int heatmapY,
                                        int heatmapX,
                                        int localMaximumRadius,
                                        float[][][] scores) {
        boolean localMaximum = true;
        int height = scores.length;
        int width = scores[0].length;

        int yStart = Math.max(heatmapY - localMaximumRadius, 0);
        int yEnd = Math.min(heatmapY + localMaximumRadius + 1, height);
        for (int yCurrent = yStart; yCurrent < yEnd; ++yCurrent) {
            int xStart = Math.max(heatmapX - localMaximumRadius, 0);
            int xEnd = Math.min(heatmapX + localMaximumRadius + 1, width);
            for (int xCurrent = xStart; xCurrent < xEnd; ++xCurrent) {
                if (sigmoid(scores[yCurrent][xCurrent][keypointId]) > score) {
                    localMaximum = false;
                    break;
                }
            }
            if (!localMaximum) {
                break;
            }
        }

        return localMaximum;
    }

    float[] getImageCoords(Map<String, Object> keypoint,
                           int outputStride,
                           int numParts,
                           float[][][] offsets) {
        int heatmapY = (int) keypoint.get("y");
        int heatmapX = (int) keypoint.get("x");
        int keypointId = (int) keypoint.get("partId");
        float offsetY = offsets[heatmapY][heatmapX][keypointId];
        float offsetX = offsets[heatmapY][heatmapX][keypointId + numParts];

        float y = heatmapY * outputStride + offsetY;
        float x = heatmapX * outputStride + offsetX;

        return new float[]{y, x};
    }

    boolean withinNmsRadiusOfCorrespondingPoint(List<Map<String, Object>> poses,
                                                float squaredNmsRadius,
                                                float y,
                                                float x,
                                                int keypointId) {
        for (Map<String, Object> pose : poses) {
            Map<Integer, Object> keypoints = (Map<Integer, Object>) pose.get("keypoints");
            Map<String, Object> correspondingKeypoint = (Map<String, Object>) keypoints.get(keypointId);
            float _x = (float) correspondingKeypoint.get("x") * inputSize - x;
            float _y = (float) correspondingKeypoint.get("y") * inputSize - y;
            float squaredDistance = _x * _x + _y * _y;
            if (squaredDistance <= squaredNmsRadius)
                return true;
        }

        return false;
    }

    Map<String, Object> traverseToTargetKeypoint(int edgeId,
                                                 Map<String, Object> sourceKeypoint,
                                                 int targetKeypointId,
                                                 float[][][] scores,
                                                 float[][][] offsets,
                                                 int outputStride,
                                                 float[][][] displacements) {
        int height = scores.length;
        int width = scores[0].length;
        int numKeypoints = scores[0][0].length;
        float sourceKeypointY = (float) sourceKeypoint.get("y") * inputSize;
        float sourceKeypointX = (float) sourceKeypoint.get("x") * inputSize;

        int[] sourceKeypointIndices = getStridedIndexNearPoint(sourceKeypointY, sourceKeypointX,
                outputStride, height, width);

        float[] displacement = getDisplacement(edgeId, sourceKeypointIndices, displacements);

        if(displacement==null) return null;

        float[] displacedPoint = new float[]{
                sourceKeypointY + displacement[0],
                sourceKeypointX + displacement[1]
        };

        float[] targetKeypoint = displacedPoint;

        final int offsetRefineStep = 2;
        for (int i = 0; i < offsetRefineStep; i++) {
            int[] targetKeypointIndices = getStridedIndexNearPoint(targetKeypoint[0], targetKeypoint[1],
                    outputStride, height, width);

            int targetKeypointY = targetKeypointIndices[0];
            int targetKeypointX = targetKeypointIndices[1];

            float offsetY = offsets[targetKeypointY][targetKeypointX][targetKeypointId];
            float offsetX = offsets[targetKeypointY][targetKeypointX][targetKeypointId + numKeypoints];

            targetKeypoint = new float[]{
                    targetKeypointY * outputStride + offsetY,
                    targetKeypointX * outputStride + offsetX
            };
        }

        int[] targetKeypointIndices = getStridedIndexNearPoint(targetKeypoint[0], targetKeypoint[1],
                outputStride, height, width);

        float score = sigmoid(scores[targetKeypointIndices[0]][targetKeypointIndices[1]][targetKeypointId]);

        Map<String, Object> keypoint = new HashMap<>();
        keypoint.put("score", score);
        keypoint.put("part", partNames[targetKeypointId]);
        keypoint.put("y", targetKeypoint[0] / inputSize);
        keypoint.put("x", targetKeypoint[1] / inputSize);

        return keypoint;
    }

    int[] getStridedIndexNearPoint(float _y, float _x, int outputStride, int height, int width) {
        int y_ = Math.round(_y / outputStride);
        int x_ = Math.round(_x / outputStride);
        int y = y_ < 0 ? 0 : y_ > height - 1 ? height - 1 : y_;
        int x = x_ < 0 ? 0 : x_ > width - 1 ? width - 1 : x_;
        return new int[]{y, x};
    }

    float[] getDisplacement(int edgeId, int[] keypoint, float[][][] displacements) {

        try{
            int numEdges = displacements[0][0].length / 2;
            int y = keypoint[0];
            int x = keypoint[1];

            return new float[]{displacements[y][x][edgeId], displacements[y][x][edgeId + numEdges]};
        }catch(Exception e){
            return null;
        }

    }

    float getInstanceScore(Map<Integer, Map<String, Object>> keypoints, int numKeypoints) {
        float scores = 0;
        for (Map.Entry<Integer, Map<String, Object>> keypoint : keypoints.entrySet())
            scores += (float) keypoint.getValue().get("score");
        return scores / numKeypoints;
    }

    private float sigmoid(final float x) {
        return (float) (1. / (1. + Math.exp(-x)));
    }




    @Override
    public void enableStatLogging(boolean debug) {

    }

    @Override
    public String getStatString() {
        return null;
    }

    @Override
    public void close() {
        if(interpreter!=null){
            interpreter.close();
            interpreter = null;
        }

        if(gpuDelegate!=null)
        {
            gpuDelegate.close();
            gpuDelegate = null;
        }

    }

    public void drawPoints(Bitmap bitmap, List<List<Coords>> coords ) {
        if(coords==null) return;
        if(coords.size()==0) return;
        int size = coords.size();
        if(size==0) return;
        Canvas canvas = new Canvas(bitmap);

        for(int i=0; i < size;i++)
        {
            List<Coords> c = coords.get(i);
            for(int j=0; j< c.size();j++)
            {
                Paint paint = new Paint();
                paint.setColor(Color.rgb(CocoColors[0][0], CocoColors[0][1], CocoColors[0][2]));
                canvas.drawCircle(c.get(j).x, c.get(j).y, HUMAN_RADIUS, paint);
            }
        }

    }

}