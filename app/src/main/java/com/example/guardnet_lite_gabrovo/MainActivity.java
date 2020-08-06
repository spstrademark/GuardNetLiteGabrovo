//package com.example.guardnet_lite_gabrovo;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.content.res.Configuration;
//import android.graphics.Rect;
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.navigation.fragment.NavHostFragment;
//
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.animation.LinearInterpolator;
//import android.view.inputmethod.InputMethodManager;
//import android.webkit.WebView;
//import android.widget.EditText;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;
//
//import java.util.List;
//import android.os.Handler;
//import Common.FragmentsEnum;
//import Common.Settings;
//
//import com.evolve.backdroplibrary.BackdropContainer;
//public class MainActivity extends AppCompatActivity {
//    Settings settings;
//    private Toolbar toolbar;
//    private Handler handler = new Handler();
////    private static final int DUMMY1 = Menu.FIRST;
////    private static final int DUMMY2 = DUMMY1 + 1;
////    private static final int RETURN = DUMMY2 + 1;
//    private BackdropContainer backdropContainer;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        MainAcivityInit();
//        backdropContainer =(BackdropContainer)findViewById(R.id.backdropcontainer);
//
//
//        int height= 150;//this.getResources().getDimensionPixelSize(R.dimen.sneek_height);
//        backdropContainer.attachToolbar(toolbar)
//                .dropInterpolator(new LinearInterpolator())
//                .dropHeight(height)
//                .build();
//
////        handler.post(AIThread);
////        FloatingActionButton fab = findViewById(R.id.fab);
////        fab.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
////            }
////        });
//
//
////        toolbar.setNavigationOnClickListener(new View.OnClickListener()){
////
////        }
//       // getSupportActionBar().setDisplayShowHomeEnabled(true);
////        Configuration conf = getResources().getConfiguration();
////        conf.locale = new Locale("fr"); //french language locale
////        DisplayMetrics metrics = new DisplayMetrics();
////        getWindowManager().getDefaultDisplay().getMetrics(metrics);
////        Resources resources = new Resources(getAssets(), metrics, conf);
////        /* get localized string */
////        String str = resources.getString(R.string.hello_world);
//
//
////        FloatingActionButton fab = findViewById(R.id.fab);
////        fab.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
////            }
////        });
//
//    }
//
//
//
//    // Define the code block to be executed
//    private Runnable AIThread = new Runnable() {
//        @Override
//        public void run() {
//            // Insert custom code here
//           // TODO AI
//            // Repeat every 2 seconds
//            handler.postDelayed(AIThread, 2000);
//        }
//    };
//
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        // refresh your views here
//        super.onConfigurationChanged(newConfig);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//
//        return true;
//    }
//
////    public void OpenSettings(MenuItem item) {
////        // does something very interesting
////        Fragment currentFragment = getVisibleFragment();
////    }
//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//       // menu.clear();
//
//
//        //and to remove a element just remove it with id
////        menu.removeItem(R.id.gallery);
////        menu.removeItem(R.id.calendar_fragment);
////        menu.removeItem(R.id.clock);
////
////       menu.add(1, DUMMY1,1, "R.string.").setIcon(R.drawable.ic_add_24px);
//////        menu.add(0, DUMMY1, Menu.ITEM, "R.string.").setIcon(R.drawable.ic_launcher_background);
//////        menu.add(1, DUMMY2, Menu.NONE, "R.string.").setIcon(R.drawable.ic_more_vert_24px);
////        menu.add(9, RETURN, Menu.NONE, "R.string.").setIcon(R.drawable.ic_add_24px);
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//    //    invalidateOptionsMenu();
//
//        Fragment currentFragment = getVisibleFragment();
//        int view =  settings.GetView();
//
//        switch(item.getItemId())
//        {
////                case R.id.buttonSettings:
////                    if(view==FragmentsEnum.VIEW.ordinal()){
////                        NavHostFragment.findNavController(currentFragment)
////                                .navigate(R.id.action_ViewFragment_to_SettingsFragment);
////                    }
////                    if(view==FragmentsEnum.GALLERY.ordinal()){
////                        NavHostFragment.findNavController(currentFragment)
////                                .navigate(R.id.action_GalleryFragment_to_SettingsFragment);
////                    }
////                    if(view==FragmentsEnum.CALENDAR.ordinal()){
////                        NavHostFragment.findNavController(currentFragment)
////                                .navigate(R.id.action_CalendarFragment_to_SettingsFragment);
////                    }
////
////                    return true;
////            case R.id.quick_gallery:
////                if(view==FragmentsEnum.VIEW.ordinal()) {
////                    NavHostFragment.findNavController(currentFragment)
////                            .navigate(R.id.action_ViewFragment_to_GalleryFragment);
////
////                }else if(view==FragmentsEnum.CALENDAR.ordinal()){
////                    NavHostFragment.findNavController(currentFragment)
////                            .navigate(R.id.action_CalendarFragment_to_GalleryFragment);
////                }
////                else if(view==FragmentsEnum.NOTIFICATIONS.ordinal()){
////                    NavHostFragment.findNavController(currentFragment)
////                            .navigate(R.id.action_NotificationFragment_to_GalleryFragment);
////                }
////                else if(view==FragmentsEnum.SETTINGS.ordinal()){
////                    NavHostFragment.findNavController(currentFragment)
////                            .navigate(R.id.action_SettingsFragment_to_GalleryFragment);
////                }
////                return true;
////            case R.id.quick_calendar:
////                if(view==FragmentsEnum.VIEW.ordinal()) {
////                    NavHostFragment.findNavController(currentFragment)
////                            .navigate(R.id.action_ViewFragment_to_calendarFragment);
////                }else if(view==FragmentsEnum.GALLERY.ordinal()){
////                    NavHostFragment.findNavController(currentFragment)
////                            .navigate(R.id.action_GalleryFragment_to_CalendarFragment);
////                }else if(view==FragmentsEnum.NOTIFICATIONS.ordinal()){
////                    NavHostFragment.findNavController(currentFragment)
////                            .navigate(R.id.action_NotificationFragment_to_CalendarFragment);
////                }
////
////                return true;
////            case R.id.quick_notification:
////                if(view==FragmentsEnum.VIEW.ordinal()) {
////                    NavHostFragment.findNavController(currentFragment)
////                            .navigate(R.id.action_ViewFragment_to_NotificationFragment);
////                }else if(view==FragmentsEnum.GALLERY.ordinal()){
////                    NavHostFragment.findNavController(currentFragment)
////                            .navigate(R.id.action_GalleryFragment_to_NotificationFragment);
////                }else if(view==FragmentsEnum.CALENDAR.ordinal()){
////                    NavHostFragment.findNavController(currentFragment)
////                            .navigate(R.id.action_CalendarFragment_to_NotificationFragment);
////                }
////                return true;
//
//            default: return super.onOptionsItemSelected(item);
//
//        }
//
//    }
//
//
//
//    private Fragment getVisibleFragment() {
//        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
//        List<androidx.fragment.app.Fragment> fragments = fragmentManager.getFragments();
//        for (androidx.fragment.app.Fragment fragment : fragments) {
//            if (fragment != null && fragment.isVisible())
//                return fragment;
//        }
//        return null;
//    }
//
//    private void MainAcivityInit()
//    {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//        }
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        }
//
//
//        this.setTitle("");
//        settings = new Settings(this, FragmentsEnum.MAIN_ACTIVITY.ordinal());
//        settings.InitAppFolder(getResources().getString(R.string.app_name));
//        settings.RestoreLanguage();
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if ( v instanceof EditText) {
//                Rect outRect = new Rect();
//                v.getGlobalVisibleRect(outRect);
//                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
//                    v.clearFocus();
//                    InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                }
//            }
//        }
//        return super.dispatchTouchEvent(event);
//    }
//
//
//
//}


package com.example.guardnet_lite_gabrovo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.evolve.backdroplibrary.BackdropContainer;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Arrays;
import java.util.List;
import android.os.Handler;
import android.widget.Spinner;

import Device.DeviceHandler;
import Common.FragmentsEnum;
import Common.Settings;

public class MainActivity extends AppCompatActivity {
    Settings settings;
    private Handler handler = new Handler();
    private BackdropContainer backdropContainer;
    private static int BackdropHeight   = 400;
    private static int DefaultDelay     = 500;
    private Fragment Activefragment;
    private WebView Viewer;
    private int selected = 0;
    private int quickMenuActive = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainAcivityInit();
        InitBackDrop();
        InitCameraDropdownList();
        InitViewer();
        ViewerStart(selected);
        handler.post(AIThread);
      //  settings = new Settings(this, FragmentsEnum.ADD.ordinal());
 //      DeviceHandler device = new DeviceHandler();
//        device.Add("url","name",true,"username","password",settings);
  //      List<String> tst =     device.GetAllDeviceNames();
        findViewById(R.id.hideBD_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backdropContainer.closeBackview();
                v.setVisibility(View.INVISIBLE);
                Activefragment.getView().setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int view =  settings.GetView();

                if(view==FragmentsEnum.GALLERY.ordinal()){
                    NavHostFragment.findNavController(Activefragment)
                            .navigate(R.id.action_GalleryFragment_to_AddFragment);
                }else if(view==FragmentsEnum.CALENDAR.ordinal()){
                    NavHostFragment.findNavController(Activefragment)
                            .navigate(R.id.action_CalendarFragment_to_AddFragment);
                }else if(view==FragmentsEnum.NOTIFICATIONS.ordinal()){
                    NavHostFragment.findNavController(Activefragment)
                            .navigate(R.id.action_NotificationFragment_to_AddFragment);
                }
                ToggleFrontLayerVisibility(View.INVISIBLE);

            }
        });



//        findViewById(R.id.addDevice).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Fragment currentFragment = getVisibleFragment();
////                ToggleFrontLayerVisibility(View.VISIBLE);
////                NavHostFragment.findNavController(currentFragment)
////                        .navigate(R.id.action_AddFragment_to_GalleryFragment);
//            }
//        });


    }





    // Define the code block to be executed
    private Runnable AIThread = new Runnable() {
        @Override
        public void run() {
            // TODO AI
            ExtractView();
            handler.postDelayed(AIThread, 2000);
        }
    };


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // refresh your views here
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_settings_24px);// set drawable icon // FIX AND REMOVE FROM HERE
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //  ab.setDisplayHomeAsUpEnabled(true);
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // menu.clear();


        //and to remove a element just remove it with id
//        menu.removeItem(R.id.gallery);
//        menu.removeItem(R.id.calendar_fragment);
//        menu.removeItem(R.id.clock);
//
//       menu.add(1, DUMMY1,1, "R.string.").setIcon(R.drawable.ic_add_24px);
////        menu.add(0, DUMMY1, Menu.ITEM, "R.string.").setIcon(R.drawable.ic_launcher_background);
////        menu.add(1, DUMMY2, Menu.NONE, "R.string.").setIcon(R.drawable.ic_more_vert_24px);
//        menu.add(9, RETURN, Menu.NONE, "R.string.").setIcon(R.drawable.ic_add_24px);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //    invalidateOptionsMenu();
        boolean navigation_set = false;
        boolean valid_nav = false;
        //    Fragment currentFragment = getVisibleFragment();
        // String tag = currentFragment.getTag();
        int view =  settings.GetView();

        switch(item.getItemId())
        {
            case android.R.id.home:
                int i = 0;
                break;
            case R.id.buttonSettings:

                if(view==FragmentsEnum.VIEW.ordinal()){
//                    NavHostFragment.findNavController(currentFragment)
//                            .navigate(R.id.action_ViewFragment_to_SettingsFragment);
                    valid_nav = true;
                }
                if(view==FragmentsEnum.GALLERY.ordinal()){
                    NavHostFragment.findNavController(Activefragment)
                            .navigate(R.id.action_GalleryFragment_to_SettingsFragment);
                    valid_nav = true;
                }
                if(view==FragmentsEnum.CALENDAR.ordinal()){
                    NavHostFragment.findNavController(Activefragment)
                            .navigate(R.id.action_CalendarFragment_to_SettingsFragment);
                    valid_nav = true;
                }
//                if(view==FragmentsEnum.ADD.ordinal()){
//                    NavHostFragment.findNavController(currentFragment)
//                            .navigate(R.id.action_CalendarFragment_to_SettingsFragment);
//                    valid_nav = true;
//                }
                navigation_set = true;
                break;
            case R.id.quick_gallery:

                if(quickMenuActive==0){
                    quickMenuActive = FragmentsEnum.GALLERY.ordinal();
//                    ConstraintLayout layout = (ConstraintLayout) this.findViewById(R.id.Friggy);
//
//                    layout.setVisibility(View.VISIBLE);
                }else{
                    if(quickMenuActive==FragmentsEnum.GALLERY.ordinal()){
                        backdropContainer.closeBackview();
                    }
                    quickMenuActive = 0;
                }

                if(view==FragmentsEnum.VIEW.ordinal()) {
//                    NavHostFragment.findNavController(currentFragment)
//                            .navigate(R.id.action_ViewFragment_to_GalleryFragment);
                    valid_nav = true;
                }else if(view==FragmentsEnum.CALENDAR.ordinal()){
                    NavHostFragment.findNavController(Activefragment)
                            .navigate(R.id.action_CalendarFragment_to_GalleryFragment);
                    valid_nav = true;
                }
                else if(view==FragmentsEnum.NOTIFICATIONS.ordinal()){
                    NavHostFragment.findNavController(Activefragment)
                            .navigate(R.id.action_NotificationFragment_to_GalleryFragment);
                    valid_nav = true;
                }
                else if(view==FragmentsEnum.SETTINGS.ordinal()){
                    NavHostFragment.findNavController(Activefragment)
                            .navigate(R.id.action_SettingsFragment_to_GalleryFragment);
                    valid_nav = true;
                }
                navigation_set = valid_nav;
                break;
            case R.id.quick_calendar:

                if(quickMenuActive==0){
                    quickMenuActive = FragmentsEnum.CALENDAR.ordinal();
                }else{
                    if(quickMenuActive==FragmentsEnum.CALENDAR.ordinal()){
                        backdropContainer.closeBackview();
                    }
                    quickMenuActive = 0;
                }
                if(view==FragmentsEnum.VIEW.ordinal()) {
//                    NavHostFragment.findNavController(currentFragment)
//                            .navigate(R.id.action_ViewFragment_to_calendarFragment);
                    valid_nav = true;
                }else if(view==FragmentsEnum.GALLERY.ordinal()){
                    NavHostFragment.findNavController(Activefragment)
                            .navigate(R.id.action_GalleryFragment_to_CalendarFragment);
                    valid_nav = true;
                }else if(view==FragmentsEnum.NOTIFICATIONS.ordinal()){
                    NavHostFragment.findNavController(Activefragment)
                            .navigate(R.id.action_NotificationFragment_to_CalendarFragment);
                    valid_nav = true;
                }

                navigation_set = valid_nav;
                break;
            case R.id.quick_notification:
                if(quickMenuActive==0){
                    quickMenuActive = FragmentsEnum.NOTIFICATIONS.ordinal();
                }else{
                    if(quickMenuActive==FragmentsEnum.NOTIFICATIONS.ordinal()){
                        backdropContainer.closeBackview();
                    }
                    quickMenuActive = 0;
                }
                if(view==FragmentsEnum.VIEW.ordinal()) {
//                    NavHostFragment.findNavController(currentFragment)
//                            .navigate(R.id.action_ViewFragment_to_NotificationFragment);
                    valid_nav = true;
                }else if(view==FragmentsEnum.GALLERY.ordinal()){
                    NavHostFragment.findNavController(Activefragment)
                            .navigate(R.id.action_GalleryFragment_to_NotificationFragment);
                    valid_nav = true;
                }else if(view==FragmentsEnum.CALENDAR.ordinal()){
                    NavHostFragment.findNavController(Activefragment)
                            .navigate(R.id.action_CalendarFragment_to_NotificationFragment);
                    valid_nav = true;
                }
                navigation_set = valid_nav;
                break;

        }

        if(navigation_set){
            backdropContainer.showBackview();
            return true;
        }else{

            if(quickMenuActive !=0){
                Activefragment.getView().setVisibility(View.VISIBLE);
                backdropContainer.showBackview();
                findViewById(R.id.hideBD_button).postDelayed(new Runnable() {
                    public void run() {
                        findViewById(R.id.hideBD_button).setVisibility(View.VISIBLE);
                    }
                }, DefaultDelay);
            }else{
                findViewById(R.id.hideBD_button).setVisibility(View.INVISIBLE);
                Activefragment.getView().setVisibility(View.INVISIBLE);
            }

        }

        return super.onOptionsItemSelected(item);


    }

    private void MainAcivityInit()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }


        this.setTitle("");

        settings = new Settings(this, FragmentsEnum.MAIN_ACTIVITY.ordinal());
        settings.InitAppFolder(getResources().getString(R.string.app_name));
        settings.GetLanguage();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void InitBackDrop()
    {
        backdropContainer = (BackdropContainer) findViewById(R.id.backdropcontainer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        backdropContainer.attachToolbar(toolbar)
                .dropInterpolator(new LinearInterpolator())
                .dropHeight(BackdropHeight)
                .build();
    }



    void InitCameraDropdownList()
    {
        Spinner testspin = findViewById(R.id.spinner2);//= dropdown.findViewById(); // values
       // ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, R.array.PublicCameras);
        List<String> CamerasURL = Arrays.asList(getResources().getStringArray(R.array.PublicCameras));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,CamerasURL);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        testspin.setAdapter(adapter);

        MaterialSpinner dropdown = findViewById(R.id.cameraList);//= dropdown.findViewById(); // values
        List<String> Cameras = Arrays.asList(getResources().getStringArray(R.array.PublicCameras));
        dropdown.setItems(Cameras);
        selected = settings.GetCamera();
        dropdown.setSelectedIndex(selected);

        dropdown.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                settings.SetCamera(position);
                ViewerStart(position);
          //      Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }

//            @Override public void onNothingSelected(MaterialSpinner spinner) {
//                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
//            }
        });
        dropdown.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {
       //         Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    void InitViewer()
    {
        Viewer = (WebView) findViewById( R.id.viewer );
        WebSettings webSettings = Viewer.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        Viewer.setHorizontalScrollBarEnabled(false);
        Viewer.setVerticalScrollBarEnabled(false);
        Viewer.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });
    }

    void ViewerStart( int camera)
    {
        String webContent =    "<!DOCTYPE html>" +
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



        String playVideo= String.format(webContent, GetCameraURL(camera));
        Viewer.loadData(playVideo, "text/html", "utf-8");


//        Viewer.setWebViewClient(new WebViewClient() {
//// IN A NEW THREAT !
////            public void onPageFinished(WebView view, String url) {
////                // do your stuff here
////                Viewer.measure(View.MeasureSpec.makeMeasureSpec(
////                        View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
////                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
////                Viewer.layout(0, 0, Viewer.getMeasuredWidth(),
////                        Viewer.getMeasuredHeight());
////                Viewer.setDrawingCacheEnabled(true);
////                Viewer.buildDrawingCache();
////                Bitmap bm = Bitmap.createBitmap(Viewer.getMeasuredWidth(),
////                        Viewer.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
////
////                Canvas bigcanvas = new Canvas(bm);
////                Paint paint = new Paint();
////                int iHeight = bm.getHeight();
////                bigcanvas.drawBitmap(bm, 0, iHeight, paint);
////                Viewer.draw(bigcanvas);
////                System.out.println("1111111111111111111111="
////                        + bigcanvas.getWidth());
////                System.out.println("22222222222222222222222="
////                        + bigcanvas.getHeight());
////
////                if (bm != null) {
////                    try {
////                        String path = Environment.getExternalStorageDirectory()
////                                .toString();
////                        OutputStream fOut = null;
////                        File file = new File(path, "/aaaa.png");
////                        fOut = new FileOutputStream(file);
////
////                        bm.compress(Bitmap.CompressFormat.PNG, 50, fOut);
////                        fOut.flush();
////                        fOut.close();
////                        bm.recycle();
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
////                }
////            }
//        });
    }


    private String GetCameraURL(int idx)
    {
        List<String> CamerasURL = Arrays.asList(getResources().getStringArray(R.array.PublicCamerasEmbed));
        return CamerasURL.get(idx);
    }

    private void ExtractView()
    {
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

    public void ToggleFrontLayerVisibility(int visibility)
    {
        Viewer.setVisibility(visibility);
        findViewById(R.id.cameraViwer).setVisibility(visibility);
        findViewById(R.id.cameraList).setVisibility(visibility);
        findViewById(R.id.button_add).setVisibility(visibility);
    }

    public void SetActiveView(@NonNull  Fragment frag)
    {
        this.Activefragment = frag;
        //  ActiveView = view;
    }

    public void UpdateLanguage(int current_fragment)
    {
        setContentView(current_fragment);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }



}