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

import Common.SettingsUtils;


public class NotificationFragment extends Fragment {

    private SettingsUtils settings;
    int selected = 0;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        return inflater.inflate(R.layout.notification_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        settings = SettingsUtils.getInstance();
        //   view.setVisibility(View.GONE);
        MainActivity activity = (MainActivity) getActivity();
        activity.SetActiveView(NotificationFragment.this);
        InitCameraDropdownList(view);
//        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(NotificationFragment.this)
//                        .navigate(R.id.action_ViewFragment_to_AddFragment);
//            }
//        });
    }

    void InitCameraDropdownList(@NonNull View view) {
        Spinner dropdown = view.findViewById(R.id.notificationIntervals);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.NotificationTrigger, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        selected = settings.getNotificationsEventTrigger();
        dropdown.setSelection(selected);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                settings.setNotificationsEventTrigger(arg2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

    }

}