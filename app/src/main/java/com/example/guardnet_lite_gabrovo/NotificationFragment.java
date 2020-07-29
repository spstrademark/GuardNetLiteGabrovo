package com.example.guardnet_lite_gabrovo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import Camera.PublicCameras;
import Settings.Settings;



public class NotificationFragment extends Fragment {
    Settings settings;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        return inflater.inflate(R.layout.notification_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        //   super.onCreate(savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
        // settings = new Settings(getActivity());
        settings = new Settings(getContext());
      //  InitCameraDropdownList(view);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(NotificationFragment.this)
                        .navigate(R.id.action_ViewFragment_to_AddFragment);
            }
        });
    }

}