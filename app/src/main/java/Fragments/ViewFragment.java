package Fragments;

//import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;


import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.guardnet_lite_gabrovo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import Common.FragmentsEnum;
import Common.Settings;

public class ViewFragment extends Fragment {
    Settings settings;
    int selected = 0;
    WebView Viewer;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        return inflater.inflate(R.layout.view_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        settings = new Settings(getContext(), FragmentsEnum.VIEW.ordinal());
       // settings.RestoreLanguage();
        super.onViewCreated(view, savedInstanceState);
       // settings = new Settings(getActivity());


        InitCameraDropdownList(view);
        InitViewer(view);
        ViewerStart(selected);
        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ViewFragment.this)
                        .navigate(R.id.action_ViewFragment_to_AddFragment);
            }
        });
    }

    void InitCameraDropdownList(@NonNull View view)
    {
        Spinner dropdown = view.findViewById(R.id.cameraList);//= dropdown.findViewById(); // values
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.PublicCameras, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        selected = settings.RestoreCameraValue();
        dropdown.setSelection(selected);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                settings.SaveCameraValue(arg2);
                ViewerStart(arg2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

    }

    void InitViewer(@NonNull View view)
    {
        Viewer = (WebView) view.findViewById( R.id.viewer );
        Viewer.setVerticalScrollBarEnabled(false);
        Viewer.setHorizontalScrollBarEnabled(false);
        WebSettings webSettings = Viewer.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);

    }

    void ViewerStart( int camera)
    {
        String playVideo= String.format("<iframe type=\"text/html\" width=\"400\" height=\"400\" src=\"%s\" >", GetCameraURL(camera));
        Viewer.loadData(playVideo, "text/html", "utf-8");

        Viewer.setWebViewClient(new WebViewClient() {
// IN A NEW THREAT !
//            public void onPageFinished(WebView view, String url) {
//                // do your stuff here
//                Viewer.measure(View.MeasureSpec.makeMeasureSpec(
//                        View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
//                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//                Viewer.layout(0, 0, Viewer.getMeasuredWidth(),
//                        Viewer.getMeasuredHeight());
//                Viewer.setDrawingCacheEnabled(true);
//                Viewer.buildDrawingCache();
//                Bitmap bm = Bitmap.createBitmap(Viewer.getMeasuredWidth(),
//                        Viewer.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//
//                Canvas bigcanvas = new Canvas(bm);
//                Paint paint = new Paint();
//                int iHeight = bm.getHeight();
//                bigcanvas.drawBitmap(bm, 0, iHeight, paint);
//                Viewer.draw(bigcanvas);
//                System.out.println("1111111111111111111111="
//                        + bigcanvas.getWidth());
//                System.out.println("22222222222222222222222="
//                        + bigcanvas.getHeight());
//
//                if (bm != null) {
//                    try {
//                        String path = Environment.getExternalStorageDirectory()
//                                .toString();
//                        OutputStream fOut = null;
//                        File file = new File(path, "/aaaa.png");
//                        fOut = new FileOutputStream(file);
//
//                        bm.compress(Bitmap.CompressFormat.PNG, 50, fOut);
//                        fOut.flush();
//                        fOut.close();
//                        bm.recycle();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
        });
    }


    public String GetCameraURL(int idx)
    {
        List<String> CamerasURL = Arrays.asList(getResources().getStringArray(R.array.PublicCamerasEmbed));
        return CamerasURL.get(idx);
    }

}