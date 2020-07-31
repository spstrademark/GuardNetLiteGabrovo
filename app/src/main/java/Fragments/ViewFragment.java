package Fragments;

//import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.guardnet_lite_gabrovo.R;

import java.util.Arrays;
import java.util.List;
import Settings.Settings;


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
        settings = new Settings(getContext());
        settings.RestoreLanguage();
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
    }

    public String GetCameraURL(int idx)
    {
        List<String> CamerasURL = Arrays.asList(getResources().getStringArray(R.array.PublicCamerasEmbed));
        return CamerasURL.get(idx);
    }

}