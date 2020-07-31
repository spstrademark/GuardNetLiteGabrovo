package com.example.guardnet_lite_gabrovo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import Fragments.ViewFragment;
import Settings.Settings;

public class MainActivity extends AppCompatActivity {
    Settings settings;
    int activeID = 0;
    int doublePress = 0;

    private static final int MENU_ADD = Menu.FIRST;
//    private static final int MENU_LIST = MENU.FIRST + 1;
//    private static final int MENU_REFRESH = MENU.FIRST + 2;
//    private static final int MENU_LOGIN = MENU.FIRST + 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainAcivityInit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//        toolbar.setNavigationOnClickListener(new View.OnClickListener()){
//
//        }
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

//    public void OpenSettings(MenuItem item) {
//        // does something very interesting
//        Fragment currentFragment = getVisibleFragment();
//    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        menu.clear();
//
//
//       // menu.add(0, MENU_ADD, Menu.NONE, R.string.Settings).setIcon(R.drawable.ic_launcher_background);
//
//        return super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    //    invalidateOptionsMenu();

        Fragment currentFragment = getVisibleFragment();
        String tag = (String)currentFragment.getTag();
        if(currentFragment !=null)
        {

        }
     //   boolean tag = currentFragment.();
        int id = item.getItemId();
//      //  if(id!=activeID){
//            activeID = id;
//
//            if(doublePress > 0){
//                doublePress--;
//                super.onBackPressed();
//            }
//            //noinspection SimplifiableIfStatement
            if (id == R.id.buttonSettings) {
                doublePress++;
           //     SetMenuItemsVisibility(View.INVISIBLE);
                NavHostFragment.findNavController(currentFragment)
                        .navigate(R.id.action_ViewFragment_to_SettingsFragment);
                return true;
            }


//        }else{
//            super.onBackPressed();
//            SetMenuItemsVisibility(View.VISIBLE);
//            activeID = 0;
//
//        }
//


      return super.onOptionsItemSelected(item);
    }



    private Fragment getVisibleFragment() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<androidx.fragment.app.Fragment> fragments = fragmentManager.getFragments();
        for (androidx.fragment.app.Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
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
        settings = new Settings(this);
        settings.InitAppFolder(getResources().getString(R.string.app_name));
    }


}