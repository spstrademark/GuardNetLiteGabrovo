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

import com.example.guardnet_lite_gabrovo.MainActivity;
import com.example.guardnet_lite_gabrovo.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Arrays;
import java.util.List;

import Common.FragmentsEnum;
import Common.Settings;

public class LanguageFragment extends Fragment {
    Settings settings;
    int currentLan = 0;
    boolean init = false;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.language_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        settings = new Settings(getContext(), FragmentsEnum.LANGUAGE.ordinal());
        currentLan =  settings.GetLanguage();
        super.onViewCreated(view, savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        activity.SetActiveView(LanguageFragment.this);
        activity.setTitle(R.string.Language);
        InitLanguagesDropdownList(view);
    }

    void InitLanguagesDropdownList(@NonNull View view)
    {
//        Spinner dropdown = view.findViewById(R.id.languages);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.Languages, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        dropdown.setAdapter(adapter);
//        dropdown.setSelection(currentLan);
//
//        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int arg2, long arg3) {
//                if(init==false){
//                    init = true;
//                    return;
//                }
//                if(currentLan!=arg2){
//                    settings.SetLanguage(arg2);
//                  //  onConfigurationChanged(settings.SaveLanguageValue(arg2));
//                    getView().requestLayout();
//             //       getActivity().recreate();
//               //     setContentView(R.layout.newdesign);
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//                // TODO Auto-generated method stub
//      //          PublicCameras Cameras = new PublicCameras();
//            }
//        });






        MaterialSpinner dropdown = view.findViewById(R.id.languages);//= dropdown.findViewById(); // values
        List<String> Languages = Arrays.asList(getResources().getStringArray(R.array.Languages));
        dropdown.setItems(Languages);
        dropdown.setSelectedIndex(currentLan);

        dropdown.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                if(init==false){
//                    init = true;
//                    return;
//                }
            //    if(currentLan!=position)
                {
                    settings.SetLanguage(position);
                    getActivity().recreate();
                }

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

//    @Override
//    public void onConfigurationChanged(Configuration newConfig)
//    {
//
//        super.onConfigurationChanged(newConfig);
//
//    }

}