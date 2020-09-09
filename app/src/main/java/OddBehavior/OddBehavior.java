package OddBehavior;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.tensorflow.lite.examples.posenet.lib.HumanObjects;
import org.tensorflow.lite.examples.posenet.lib.KeyPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Ai.Coords;
import Ai.TFLiteDetector;
import Common.SettingsUtils;
import kotlin.jvm.JvmStatic;

public class OddBehavior {

    private static OddBehavior sInstance;
    private static List<List<Coords>> positioning;
    private long startTime = 0;

    private double THRESHOLD = 50.0; // 20 percent
    private long oddBehaviorInFrame = 0;
    private static final Object LOCK = new Object();

    public synchronized static void createInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new OddBehavior();
                    positioning = new ArrayList<List<Coords>>();
                }
            }
        }
    }

    public static OddBehavior getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Preference instance not initialized");
        }
        return sInstance;
    }

//    public boolean isBehaviorOdd(List<List<TFLiteDetector.Coords>> coords)
//    {
//        return false;
//    }

//    public boolean isBehaviorOdd(List<List<Coords>> kp) {
//
//        return false;
//    }

    private float calculatePercentage(float current, float maximum)
    {
        return 100-((current / maximum) * 100);
    }

    private int timePassed()
    {
        long endTime     = SystemClock.elapsedRealtimeNanos() - startTime;
        return (int)(1.0f * endTime / 1000000);
    }

//    public boolean isBehaviorOdd(@NotNull List<? extends Map<String, Object>> kp) {
//        int size = kp.size();
//        if((positioning.size()==0)){  //  if( (positioning.size()==0) || (size != positioning.size())){
//
////            if(size != positioning.size()){
////                positioning = new ArrayList<List<Coords>>();
////
////            }
//            startTime   = SystemClock.elapsedRealtimeNanos();
//            for(int i =0; i <size;i++) {
//
//                List<Coords> keyPos = new ArrayList<>();
//                HashMap<Integer,Object> keys =  (HashMap<Integer,Object>)kp.get(i).get("keypoints");
//                int key_size = keys.size();
//                for(int j=0; j < key_size;j++)
//                {
//                    Coords coords = new Coords();
//                    HashMap<String,Float> item = (HashMap<String,Float>)keys.get(i);
//                    coords.x = item.get("x");
//                    coords.y = item.get("y");
//                    keyPos.add(coords);
//                    Log.i("odd", String.format("Keys %d",keyPos.size()));
//                }
//                positioning.add(keyPos);
//            }
//            Log.i("odd", String.format("Start! %d",positioning.size()));
//        }else{
//     //       Log.i("posenet", String.format("Thread took %.2f ms", 1.0f * endTime / 1000000))
//            Log.i("odd", "we are here!");
//            for(int i =0; i <size;i++) {
//
//                HashMap<Integer,Object> keys =  (HashMap<Integer,Object>)kp.get(i).get("keypoints");
//                List<Coords>  personKeys = positioning.get(i);
//                int key_size = keys.size();
//                for(int j=0; j < key_size;j++)
//                {
//                    Coords coords = new Coords();
//                    HashMap<String,Float> item = (HashMap<String,Float>)keys.get(j);
//                    coords.x = item.get("x");
//                    coords.y = item.get("y");
//
//                    Coords posKey = personKeys.get(j);
//
//                    float resultX = 0;
//                    float resultY = 0;
//
//                    if(coords.x >=posKey.x){
//                        resultX = calculatePercentage(posKey.x,coords.x);
//                    }else{
//                        resultX =  calculatePercentage(coords.x,posKey.x);
//                    }
//
//                    if(coords.y >=posKey.y){
//                        resultY = calculatePercentage(posKey.y,coords.y);
//                    }else{
//                        resultY =  calculatePercentage(coords.y,posKey.y);
//                    }
//
//                    if( (resultX >= THRESHOLD) || (resultY >= THRESHOLD) ){
//                        oddBehaviorInFrame++;
//                    }else{
//                        oddBehaviorInFrame = 0;
//                    }
//
//                }
//
//            }
//
//            positioning = new ArrayList<List<Coords>>();
//
//
//            for(int i =0; i <size;i++) {
//
//                List<Coords> keyPos = new ArrayList<>();
//                HashMap<Integer,Object> keys =  (HashMap<Integer,Object>)kp.get(i).get("keypoints");
//                int key_size = keys.size();
//                for(int j=0; j < key_size;j++)
//                {
//                    Coords coords = new Coords();
//                    HashMap<String,Float> item = (HashMap<String,Float>)keys.get(i);
//                    coords.x = item.get("x");
//                    coords.y = item.get("y");
//
//                    keyPos.add(coords);
//                }
//
//                positioning.add(keyPos);
//            }
//
//            if(timePassed() >=400 && oddBehaviorInFrame>0){
//                oddBehaviorInFrame = 0;
//                startTime = 0;
//
//                return true;
//            }
//
//
//        }
//
//        return false;
//
//    }

//    public boolean isBehaviorOdd(@NotNull List<? extends List<Coords>> bodyPos, int interval) {
//
//        if(bodyPos==null) return false;
//        int size = bodyPos.size();
//        Log.i("odd", String.format("FIX"));
//        if( (positioning.size()==0) || (size != positioning.size())){
//
//            if(size != positioning.size()){
//                positioning = new ArrayList<List<Coords>>();
//            }
//
//            startTime   = SystemClock.elapsedRealtimeNanos();
//            for(int i =0; i <size;i++) {
//
//                List<Coords> keyPos = new ArrayList<>();
//                List<Coords> keys =  bodyPos.get(i);
//                int key_size = keys.size();
//                for(int j=0; j < key_size;j++)
//                {
//                    keyPos.add( keys.get(i));
//                    Log.i("odd", String.format("Keys %d",keyPos.size()));
//                }
//                positioning.add(keyPos);
//            }
//            Log.i("odd", String.format("START"));
//        }else{
//            Log.i("odd", String.format("OK"));
//            for(int i =0; i <size;i++) {
//
//                List<Coords> keys =  bodyPos.get(i);
//                List<Coords>  personKeys = positioning.get(i);
//                int key_size = keys.size();
//
//                for(int j=0; j < key_size;j++)
//                {
//
//                    Coords inputKey = keys.get(j);
//                    Coords posKey = personKeys.get(j);
//
//                    float resultX = 0;
//                    float resultY = 0;
//
//                    if(inputKey.x >=posKey.x){
//                        resultX = calculatePercentage(posKey.x,inputKey.x);
//                    }else{
//                        resultX =  calculatePercentage(inputKey.x,posKey.x);
//                    }
//
//                    if(inputKey.y >=posKey.y){
//                        resultY = calculatePercentage(posKey.y,inputKey.y);
//                    }else{
//                        resultY =  calculatePercentage(inputKey.y,posKey.y);
//                    }
//
//                    if( (resultX >= THRESHOLD) || (resultY >= THRESHOLD) ){
//                        oddBehaviorInFrame++;
//                    }else{
//                        oddBehaviorInFrame = 0;
//                    }
//
//                }
//
//            }
//
//            positioning = new ArrayList<List<Coords>>();
//
//            for(int i =0; i <size;i++) {
//
//                List<Coords> keyPos = new ArrayList<>();
//                List<Coords> keys =  bodyPos.get(i);
//                int key_size = keys.size();
//                for(int j=0; j < key_size;j++)
//                {
//                    keyPos.add( keys.get(i));
//                }
//                positioning.add(keyPos);
//            }
//
//            if(timePassed() >=interval && oddBehaviorInFrame>0){
//                oddBehaviorInFrame = 0;
//                startTime = 0;
//
//                return true;
//            }
//
//        }
//
//        return false;
//    }

    public boolean isBehaviorOdd(@NotNull List<? extends Map<String, Object>> kp, int time) {
        int size = kp.size();
        if((positioning.size()==0)){  //  if( (positioning.size()==0) || (size != positioning.size())){

            if(size != positioning.size()){
                positioning = new ArrayList<List<Coords>>();
                Log.i("odd", "ERROR");

            }
            startTime   = SystemClock.elapsedRealtimeNanos();
            for(int i =0; i <size;i++) {

                List<Coords> keyPos = new ArrayList<>();
                HashMap<Integer,Object> keys =  (HashMap<Integer,Object>)kp.get(i).get("keypoints");
                int key_size = keys.size();
                for(int j=0; j < key_size;j++)
                {
                    Coords coords = new Coords();
                    HashMap<String,Float> item = (HashMap<String,Float>)keys.get(i);
                    coords.x = item.get("x");
                    coords.y = item.get("y");
                    keyPos.add(coords);
                    Log.i("odd", String.format("Keys %d",keyPos.size()));
                }
                positioning.add(keyPos);
            }
            Log.i("odd", String.format("Start! %d",positioning.size()));
        }else{
     //       Log.i("posenet", String.format("Thread took %.2f ms", 1.0f * endTime / 1000000))
            Log.i("odd", "we are here!");
            for(int i =0; i <size;i++) {

                HashMap<Integer,Object> keys =  (HashMap<Integer,Object>)kp.get(i).get("keypoints");
                List<Coords>  personKeys = positioning.get(i);
                int key_size = keys.size();
                for(int j=0; j < key_size;j++)
                {
                    Coords coords = new Coords();
                    HashMap<String,Float> item = (HashMap<String,Float>)keys.get(j);
                    coords.x = item.get("x");
                    coords.y = item.get("y");

                    Coords posKey = personKeys.get(j);

                    float resultX = 0;
                    float resultY = 0;

                    if(coords.x >=posKey.x){
                        resultX = calculatePercentage(posKey.x,coords.x);
                    }else{
                        resultX =  calculatePercentage(coords.x,posKey.x);
                    }

                    if(coords.y >=posKey.y){
                        resultY = calculatePercentage(posKey.y,coords.y);
                    }else{
                        resultY =  calculatePercentage(coords.y,posKey.y);
                    }

                    if( (resultX >= THRESHOLD) || (resultY >= THRESHOLD) ){
                        oddBehaviorInFrame++;
                    }else{
                        oddBehaviorInFrame = 0;
                    }

                }

            }

            positioning = new ArrayList<List<Coords>>();


            for(int i =0; i <size;i++) {

                List<Coords> keyPos = new ArrayList<>();
                HashMap<Integer,Object> keys =  (HashMap<Integer,Object>)kp.get(i).get("keypoints");
                int key_size = keys.size();
                for(int j=0; j < key_size;j++)
                {
                    Coords coords = new Coords();
                    HashMap<String,Float> item = (HashMap<String,Float>)keys.get(i);
                    coords.x = item.get("x");
                    coords.y = item.get("y");

                    keyPos.add(coords);
                }

                positioning.add(keyPos);
            }

            if(timePassed() >=400 && oddBehaviorInFrame>0){
                oddBehaviorInFrame = 0;
                startTime = 0;

                return true;
            }


        }

        return false;

    }

    public boolean isBehaviorOdd(@NotNull HumanObjects kp, int Interval) {

        int size = kp.getKeyPoints().size();
        if((positioning.size()==0)){
    //    if( (positioning.size()==0) || (size != positioning.size())){

//            if(size != positioning.size()){
//                positioning = new ArrayList<List<Coords>>();
//                Log.i("odd", "ERROR");
//
//            }
            startTime   = SystemClock.elapsedRealtimeNanos();
            positionAdd(kp,size,positioning);
            Log.i("odd", String.format("Start! %d",positioning.size()));
        }else{
            //       Log.i("posenet", String.format("Thread took %.2f ms", 1.0f * endTime / 1000000))
            Log.i("odd", "we are here!");
            List<List<Coords>> positioningNew = new ArrayList<List<Coords>>();
            positionAdd(kp,size,positioningNew);
            if(positioningNew.size() != positioning.size()){
                positioning = new ArrayList<List<Coords>>();
                positionAdd(kp,size,positioning);
                startTime   = SystemClock.elapsedRealtimeNanos();
                return false;
            }else{
                int len = positioningNew.size();
                for(int i=0; i < len;i++){
                    List<Coords>  personKeys1 =   positioning.get(i);
                    List<Coords>  personKeys2 =   positioningNew.get(i);

                    if(personKeys1.size()==personKeys2.size()){
                        for(int j=0; j < personKeys1.size();j++){
                            Coords coords1 = personKeys1.get(j);
                            Coords coords2 = personKeys2.get(j);

                            float resultX = 0;
                            float resultY = 0;

                            if(coords2.x >=coords1.x){
                                resultX = calculatePercentage(coords1.x,coords2.x);
                            }else{
                                resultX =  calculatePercentage(coords2.x,coords1.x);
                            }

                            if(coords2.y >=coords1.y){
                                resultY = calculatePercentage(coords1.y,coords2.y);
                            }else{
                                resultY =  calculatePercentage(coords2.y,coords1.y);
                            }

                            Log.i("odd", String.format("X: %.2f Y: %.2f", resultX,resultY));

                            if( (resultX >= THRESHOLD) || (resultY >= THRESHOLD) ){
                                oddBehaviorInFrame++;
                            }else{
                                oddBehaviorInFrame = 0;
                            }

                        }
                    }

                }
                Log.i("odd", "OK!");
            }
            positioning = new ArrayList<List<Coords>>();
            positionAdd(kp,size,positioning);
            if(timePassed() >=Interval ){
                long oddEvents =  oddBehaviorInFrame;
                oddBehaviorInFrame = 0;
                startTime = 0;

                if(oddEvents>=Interval/1000){
                    Log.i("odd", String.format("oddEvents: %d,Interval: %d", oddEvents,Interval/1000));
                    return true;
                }

            }
//
//
        }


        return false;
    }

    public void positionAdd(@NotNull HumanObjects kp,int size,List<List<Coords>> pos)
    {
        for(int i =0; i <size;i++) {
            List<Coords> keyPos = new ArrayList<>();
            KeyPoint keys =  kp.getKeyPoints().get(i);
            Coords coords = new Coords();
            coords.x = keys.getPosition().getX();
            coords.y =  keys.getPosition().getY();
            keyPos.add(coords);
            pos.add(keyPos);
        }
    }

}
