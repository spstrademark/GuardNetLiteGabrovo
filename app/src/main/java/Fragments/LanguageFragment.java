package Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.guardnet_lite_gabrovo.HomeActivity;
import com.example.guardnet_lite_gabrovo.MainActivity;
import com.example.guardnet_lite_gabrovo.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Arrays;
import java.util.List;

import Common.SettingsUtils;

public class LanguageFragment extends Fragment {

    private SettingsUtils settings;
    int currentLan = 0;
    boolean init = false;
  //  HomeActivity activity;
    View thisView;
    MaterialSpinner dropdown;

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
        settings = SettingsUtils.getInstance();
        currentLan = settings.getLanguage();

        thisView = view;
     //   activity = (HomeActivity) getActivity();
      //  activity.SetActiveView(LanguageFragment.this);
     //   activity.setTitle(R.string.Language);
        getActivity().setTitle(R.string.Language);
        InitLanguagesDropdownList(view);
    }

    private MaterialSpinner LanguageSpinnerFill(@NonNull View view) {
        MaterialSpinner dropdown = view.findViewById(R.id.languages);//= dropdown.findViewById(); // values
        List<String> Languages = Arrays.asList(getResources().getStringArray(R.array.Languages));
        dropdown.setItems(Languages);
        return dropdown;
    }

    void InitLanguagesDropdownList(@NonNull View view) {

        dropdown = LanguageSpinnerFill(view);
        dropdown.setSelectedIndex(currentLan);

        dropdown.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                if(init==false){
//                    init = true;
//                    return;
//                }
                //    if(currentLan!=position)
                {
                    settings.setLanguage(position);
//                   dropdown.setSelectedIndex(0);
                    HomeActivity activity = (HomeActivity)getActivity();
                    activity.UpdateLanguage(R.layout.language_fragment);
           //         activity.UpdateLanguage(R.layout.language_fragment);
//            //        notifyDataSetChanged
//                    dropdown = LanguageSpinnerFill(view);
                    dropdown.setSelectedIndex(position);

                    //      myView.setContentView(R.layout.activity_main);

                    //  this.setContentView(R.layout.main);
                    //  getActivity().recreate();
                }

                //      Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }

//            @Override public void onNothingSelected(MaterialSpinner spinner) {
//                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
//            }
        });
        dropdown.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                //         Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
            }
        });

    }

}