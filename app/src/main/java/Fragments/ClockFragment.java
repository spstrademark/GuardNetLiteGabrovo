package Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.guardnet_lite_gabrovo.R;

import Common.FragmentsEnum;
import Common.Settings;

public class ClockFragment extends Fragment {
    Settings settings;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.clock_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        settings = new Settings(getContext(), FragmentsEnum.CLOCK.ordinal());
        super.onViewCreated(view, savedInstanceState);

    }



}