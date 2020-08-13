package com.example.guardnet_lite_gabrovo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
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

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Common.FragmentsEnum;
import Common.Settings;
import Device.Device;
import Device.DeviceHandler;

public class CameraFrontFragment extends Fragment {

    private Settings settings;
    private WebView webView;
    private MaterialSpinner dropdown;
    private FloatingActionButton addCameraButton;
    private ImageView hideBackdropButton;
    private ViewPager2 viewPager;

    List<Device> UserDevices;

    List<String> CamerasURL = new ArrayList<>();
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera_front, container, false);
        setHasOptionsMenu(true);
        HomeActivityInit();
        webView = view.findViewById(R.id.frontLayerWebView);
        dropdown = view.findViewById(R.id.cameraListSpinner);
        addCameraButton = view.findViewById(R.id.button_add);
        hideBackdropButton = view.findViewById(R.id.hideBackdropButton);
        viewPager = view.findViewById(R.id.viewPager);
      //  settings = new Settings(getContext(), FragmentsEnum.ADD.ordinal());
        DeviceHandler devhandler = new DeviceHandler(settings);
        UserDevices =  devhandler.GetAllDevices();
        InitCamerasURL();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setIcon(R.drawable.ic_photo_library_24px);
                    break;
                case 1:
                    tab.setIcon(R.drawable.ic_insert_invitation_24px);
                    break;
                case 2:
                    tab.setIcon(R.drawable.ic_schedule_24px);
                    break;
            }

        }).attach();

        // Set up the tool bar
        setUpBackdropButton(view);
        setupCameraSpinner();
        initWebView();
        viewerStart(1);
        setupFAB();

        return view;
    }

    private void HomeActivityInit()
    {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        getActivity().setTitle("");
       // getContext().t("");

        settings = new Settings(getContext(), FragmentsEnum.MAIN_ACTIVITY.ordinal());
        settings.InitAppFolder(getResources().getString(R.string.app_name));
        settings.GetLanguage();
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
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
;
        List<String> userDevices = new ArrayList<>();
        if(UserDevices !=null){
            for (Device device : UserDevices) {
                if(device.GetName()!=null){
                    userDevices.add(device.GetName());
                }else{
                    userDevices.add(device.GetURL());
                }
            }
        }

        List<String> cameraList = Arrays.asList(getResources().getStringArray(R.array.PublicCameras));
        userDevices.addAll(cameraList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item, userDevices);


        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<String>) (view, position, id, item) -> viewerStart(position));
    }

    private void InitCamerasURL()
    {
        if(this.UserDevices !=null){
            for (Device device : this.UserDevices) {
                CamerasURL.add(device.GetURL());
            }
        }
        List<String> PublicURL = Arrays.asList(getResources().getStringArray(R.array.PublicCamerasEmbed));
        this.CamerasURL.addAll(PublicURL);
    }

    @SuppressLint("ClickableViewAccessibility")
    void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setOnTouchListener((v, event) -> (event.getAction() == MotionEvent.ACTION_MOVE));
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
//        List<String> camerasURL = Arrays.asList(getResources().getStringArray(R.array.PublicCamerasEmbed));
//        return camerasURL.get(idx);
        return this.CamerasURL.get(idx);
    }

    private void setupFAB() {
        addCameraButton.setOnClickListener(v -> NavHostFragment.findNavController(CameraFrontFragment.this)
                .navigate(R.id.action_CameraFragment_to_AddFragment));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settingsFragment) {
            NavigationUI.onNavDestinationSelected(item, NavHostFragment.findNavController(this));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}