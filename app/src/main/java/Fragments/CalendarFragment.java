package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.guardnet_lite_gabrovo.R;

import java.util.Arrays;
import java.util.List;

import Settings.Settings;

public class CalendarFragment extends Fragment {
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calendar, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        //   super.onCreate(savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
        FragmentInit();
        view.findViewById(R.id.language).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CalendarFragment.this)
                        .navigate(R.id.action_SettingsFragment_to_LanguageFragment);
            }
        });

    }

    public void FragmentInit()
    {
        getActivity().setTitle(R.string.Settings);
    }
}
