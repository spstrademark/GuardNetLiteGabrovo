package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.guardnet_lite_gabrovo.R;

import Common.FragmentsEnum;
import Common.SettingsUtils;


public class SettingsFragment extends Fragment {

    private SettingsUtils settings;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = SettingsUtils.getInstance();
        super.onViewCreated(view, savedInstanceState);
        FragmentInit();
//        view.findViewById(R.id.language).setOnClickListener(view1 -> NavHostFragment.findNavController(SettingsFragment.this)
//                .navigate(R.id.action_SettingsFragment_to_LanguageFragment));
//        view.findViewById(R.id.gallery).setOnClickListener(view1 -> NavHostFragment.findNavController(SettingsFragment.this)
//                .navigate(R.id.action_SettingsFragment_to_GalleryFragment));
        view.findViewById(R.id.notifications).setOnClickListener(view1 -> NavHostFragment.findNavController(SettingsFragment.this)
                .navigate(R.id.action_SettingsFragment_to_NotificationFragment));
    }

    public void FragmentInit() {
        getActivity().setTitle(R.string.Settings);
    }

}