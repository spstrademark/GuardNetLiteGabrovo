package Fragments;

//import android.content.SharedPreferences;
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

import com.example.guardnet_lite_gabrovo.R;

import Camera.PublicCameras;
import Settings.Settings;


public class ViewFragment extends Fragment {
    Settings settings;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        return inflater.inflate(R.layout.view_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
     //   super.onCreate(savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
       // settings = new Settings(getActivity());
        settings = new Settings(getContext());
        InitCameraDropdownList(view);

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
        dropdown.setSelection(settings.RestoreCameraValue());
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                settings.SaveCameraValue(arg2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                PublicCameras Cameras = new PublicCameras();
            }
        });

    }



}