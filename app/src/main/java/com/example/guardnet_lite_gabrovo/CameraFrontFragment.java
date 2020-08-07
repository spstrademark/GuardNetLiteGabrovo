package com.example.guardnet_lite_gabrovo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Arrays;
import java.util.List;

public class CameraFrontFragment extends Fragment {

    private WebView webView;
    private MaterialSpinner dropdown;
    private FloatingActionButton addCameraButton;
    private ImageView hideBackdropButton;
    private View rootLayout;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera_front, container, false);
        rootLayout = view.findViewById(R.id.frameLayout);
        webView = view.findViewById(R.id.frontLayerWebView);
        dropdown = view.findViewById(R.id.cameraListSpinner);
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        addCameraButton = view.findViewById(R.id.button_add);
        hideBackdropButton = view.findViewById(R.id.hideBackdropButton);

        // Set up the tool bar
        setUpBackdropButton(view);
        setupCameraSpinner();
        initWebView();
        viewerStart(1);

        return view;
    }

    private void setUpBackdropButton(View view) {
        hideBackdropButton.setOnClickListener(new NavigationIconClickListener(
                getContext(),
                view.findViewById(R.id.front_layer),
                new AccelerateDecelerateInterpolator(),
                getContext().getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_24px), // Menu open icon
                getContext().getResources().getDrawable(R.drawable.ic_expand_more_24px))); // Menu close icon
    }

    private void setupCameraSpinner() {
        List<String> cameraList = Arrays.asList(getResources().getStringArray(R.array.PublicCameras));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item, cameraList);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                viewerStart(position);
            }
        });
        dropdown.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
            }
        });
    }

    void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });
    }

    private void viewerStart(int position) {
        String webContent = "<!DOCTYPE html>" +
                "<html> " +
                "<head> " +
                "<meta charset=\\\"UTF-8\\\"><meta name=\\\"viewport\\\" content=\\\"target-densitydpi=high-dpi\\\" /> " +
                "<meta name=\\\"viewport\\\" content=\\\"width=device-width, initial-scale=1\\\"> <link rel=\\\"stylesheet\\\" " +
                "media=\\\"screen and (-webkit-device-pixel-ratio:1.5)\\\" href=\\\"hdpi.css\\\" />" +
                "</head> " +
                "<body onload=\"ClickFrame()\" style=\"background:black;margin:0 0 0 0; padding:0 0 0 0;width:450px;height:254;\">" +
                "<iframe id=\"view\" type=type=\"text/html\" width=\"450\" height=\"254\" src=\"%s\" ></iframe>" +
                "<script type=\"text/javascript\">" +
                "function ClickFrame(){" +
                "setTimeout(function(){ document.getElementById('view').click(); }, 3000);" +
                //      "document.getElementById('view').click();" +
                "}" +
                "</script>" +
                "</body" +
                "></html>";

        String playVideo = String.format(webContent, getCameraURL(position));
        webView.loadData(playVideo, "text/html", "utf-8");
    }

    private String getCameraURL(int idx) {
        List<String> camerasURL = Arrays.asList(getResources().getStringArray(R.array.PublicCamerasEmbed));
        return camerasURL.get(idx);
    }

    // TODO
    private void setupFAB() {
        addCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(rootLayout, "Settings", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settingsFragment:
                Toast.makeText(getContext(), "Settings", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}