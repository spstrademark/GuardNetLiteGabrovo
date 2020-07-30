package Fragments;


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

import com.example.guardnet_lite_gabrovo.R;

import Camera.PublicCameras;
import Settings.Settings;

public class LanguageFragment extends Fragment {
    Settings settings;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.language_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settings = new Settings(getContext());
        getActivity().setTitle(R.string.Language);
        InitLanguagesDropdownList(view);
    }

    void InitLanguagesDropdownList(@NonNull View view)
    {
        Spinner dropdown = view.findViewById(R.id.languages);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.Languages, android.R.layout.simple_spinner_item);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    dropdown.setAdapter(adapter);
        dropdown.setSelection(0);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
          //      settings.SaveCameraValue(arg2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
      //          PublicCameras Cameras = new PublicCameras();
            }
        });

    }

}