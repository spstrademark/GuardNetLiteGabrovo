package com.example.guardnet_lite_gabrovo;

//import android.content.SharedPreferences;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import Camera.PublicCameras;


public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        if (savedInstanceState != null) {
            String[]Cameras = savedInstanceState.getStringArray(String.valueOf(R.id.cameraList));
        } else {

//         //    This is the case when you are openning this Activity for the for the first time
//            PublicCameras Cameras = new PublicCameras();
//            // Cameras.
//        //    Spinner dropdown = findViewById(R.id.cameraList); // values
//            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Cameras.GetNamesEn());
//            dropdown.setAdapter(adapter);
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onViewCreated(view, savedInstanceState);

        Spinner dropdown = view.findViewById(R.id.cameraList);;//= dropdown.findViewById(); // values
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.PublicCameras, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                PublicCameras Cameras = new PublicCameras();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                PublicCameras Cameras = new PublicCameras();
            }
        });

//        SharedPreferences.Editor editor = this.getContext().getSharedPreferences(MY_PREFS_NAME, this.getContext().MODE_PRIVATE).edit();
//        editor.putString("name", "Elena");
//        editor.putInt("idName", 12);
//        editor.apply();


        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
      //  PublicCameras Cameras = new PublicCameras();
      //  String key = String.valueOf(R.id.cameraList);
   //     savedInstanceState.putStringArray(key, Cameras.GetNamesEn());
        super.onSaveInstanceState(savedInstanceState);
    }
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        PublicCameras Cameras = new PublicCameras();
//        outState.putStringArray( R.id.cameraList,Cameras.GetNamesEn());//chnage this with you're object name
//    }

}