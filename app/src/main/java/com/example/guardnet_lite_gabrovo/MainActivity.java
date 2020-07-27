package com.example.guardnet_lite_gabrovo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Locale;

import Camera.PublicCameras;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        Configuration conf = getResources().getConfiguration();
//        conf.locale = new Locale("fr"); //french language locale
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        Resources resources = new Resources(getAssets(), metrics, conf);
//        /* get localized string */
//        String str = resources.getString(R.string.hello_world);


//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

     //   Spinner dropdown = findViewById(R.id.cameraList);
      //  String[]Cameras = savedInstanceState.getStringArray(String.valueOf(R.id.cameraList));

//        if (savedInstanceState != null) {
//
//
//        } else {
//            // This is the case when you are openning this Activity for the for the first time
////            PublicCameras Cameras = new PublicCameras();
////            // Cameras.
////            Spinner dropdown = findViewById(R.id.cameraList); // values
////            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Cameras.GetNamesEn());
////            dropdown.setAdapter(adapter);
//        }

        // MY_PREFS_NAME - a static String variable like:
//public static final String MY_PREFS_NAME = "MyPrefsFile";
        // load default values first time
//        PreferenceManager.setDefaultValues(this, R.xml.default_config, false);
//        verifySettings();
    }
    private void verifySettings() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        if (TextUtils.isEmpty(prefs.getString(SettingsActivity.PREF_IPCAM_URL, ""))) {
//            buttonDefault.setEnabled(false);
//        }
//
//        // TODO disabled
//        buttonNative.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

//        PublicCameras Cameras = new PublicCameras();
//        // Cameras.
//        Spinner dropdown = findViewById(R.id.cameraList); // values
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.PublicCameras, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        dropdown.setAdapter(adapter);


        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        PublicCameras Cameras = new PublicCameras();
//        outState.putStringArray( "cameraList",Cameras.GetNamesEn());//chnage this with you're object name
//    }
//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        PublicCameras Cameras = new PublicCameras();
//        savedInstanceState.putStringArray(String.valueOf(R.id.cameraList), Cameras.GetNamesEn());
//        super.onSaveInstanceState(savedInstanceState);
//    }


}