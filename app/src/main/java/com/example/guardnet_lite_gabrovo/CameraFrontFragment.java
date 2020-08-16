package com.example.guardnet_lite_gabrovo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Common.FragmentsEnum;
import Common.SettingsUtils;
import Device.Device;
import Device.DeviceHandler;

public class CameraFrontFragment extends Fragment {

    private SettingsUtils settings;
    private WebView webView;
    private MaterialSpinner dropdown;
    private FloatingActionButton addCameraButton;
    private ImageView hideBackdropButton;
    private ViewPager2 viewPager;

    List<Device> userDevicesList;
    List<String> camerasURLList = new ArrayList<>();
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera_front, container, false);
        setHasOptionsMenu(true);
        homeActivityInit();
        webView = view.findViewById(R.id.frontLayerWebView);
        dropdown = view.findViewById(R.id.cameraListSpinner);
        addCameraButton = view.findViewById(R.id.button_add);
        hideBackdropButton = view.findViewById(R.id.hideBackdropButton);
        viewPager = view.findViewById(R.id.viewPager);
      //  settings = new Settings(getContext(), FragmentsEnum.ADD.ordinal());
        DeviceHandler devhandler = new DeviceHandler(settings);
        userDevicesList =  devhandler.GetAllDevices();
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
        setupAddDeviceButton();

        executor.scheduleAtFixedRate(aiTask , 0, 500, TimeUnit.MILLISECONDS );

        return view;
    }

    private void homeActivityInit()
    {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        getActivity().setTitle("");
       // getContext().t("");

        settings = SettingsUtils.getInstance();
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
        if(userDevicesList.size() != 0){
            for (Device device : userDevicesList) {
                if (device != null)
                {
                    if(device.GetName()!=null){
                        userDevices.add(device.GetName());
                    }else{
                        userDevices.add(device.GetURL());
                    }
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
        if(userDevicesList.size() != 0){
            for (Device device : this.userDevicesList) {
                if(device==null) continue;
                camerasURLList.add(device.GetURL());
            }
        }
        List<String> PublicURL = Arrays.asList(getResources().getStringArray(R.array.PublicCamerasEmbed));
        this.camerasURLList.addAll(PublicURL);
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
        return this.camerasURLList.get(idx);
    }

    private void setupAddDeviceButton() {
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

    private Runnable aiTask = new Runnable() {
        public void run() {

            try {
//                long inferenceStartTimeNanos = SystemClock.elapsedRealtimeNanos();
//                total = SystemClock.elapsedRealtimeNanos();
//                DoStuff();
//
//                long lastInferenceTimeNanos = SystemClock.elapsedRealtimeNanos() - inferenceStartTimeNanos;
//
//                Log.e("Runnable:","Runnable:" + ( 1.0f * lastInferenceTimeNanos / 1_000_000) + " ms");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = eventgetCurrentFocus(); // NOT RESOLVED
//            if ( v instanceof EditText) {
//                Rect outRect = new Rect();
//                v.getGlobalVisibleRect(outRect);
//                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
//                    v.clearFocus();
//                    InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE); // NOT RESOLVED
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                }
//            }
//        }
//        return super.dispatchTouchEvent(event);
//    }

    private Bitmap getBitmap()
    {
        final Bitmap[] bm = {null};
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                webView.measure(View.MeasureSpec.makeMeasureSpec(
                        View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                webView.layout(0, 0, webView.getMeasuredWidth(),
                        webView.getMeasuredHeight());
                webView.setDrawingCacheEnabled(true);
                webView.buildDrawingCache();
                bm[0] = Bitmap.createBitmap(webView.getMeasuredWidth(),
                        webView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            }
        });

        return bm[0];
    }

    private void saveBitmap(Bitmap bm)
    {
        if (bm != null) {
            try {
                String path = String.format("%s%s%s",
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                        File.separator,
                        getResources().getString(R.string.app_name));


//                String path = Environment.getExternalStorageDirectory()
//                        .toString();
          //      OutputStream fOut = null;
                File file = new File(path, "/aaaa.png");
                OutputStream fOut = new FileOutputStream(file);

                bm.compress(Bitmap.CompressFormat.PNG, 50, fOut);
                fOut.flush();
                fOut.close();
                bm.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}