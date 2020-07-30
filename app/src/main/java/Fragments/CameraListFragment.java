package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.guardnet_lite_gabrovo.R;

import Settings.Settings;

public class CameraListFragment extends Fragment {
    Settings settings;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        return inflater.inflate(R.layout.view_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
        // settings = new Settings(getActivity());
        settings = new Settings(getContext());
        //  InitCameraDropdownList(view);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CameraListFragment.this)
                        .navigate(R.id.action_ViewFragment_to_AddFragment);
            }
        });
    }

}