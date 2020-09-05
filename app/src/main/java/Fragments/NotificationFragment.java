package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.guardnet_lite_gabrovo.HomeActivity;
import com.example.guardnet_lite_gabrovo.MainActivity;
import com.example.guardnet_lite_gabrovo.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

import Common.SettingsUtils;
import Device.DevicePushResultTypes;
import NotificationSettings.NotificationsUtils;


public class NotificationFragment extends Fragment {

    Spinner dropdown;
    private SettingsUtils settings;
    private Button saveNotifications;
    private Switch notificationShow;
    private Switch notificationSend;
    private EditText email0;
    private EditText email1;
    private EditText email2;

    private NotificationsUtils notificationsUtils;

    int selected = 0;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        return inflater.inflate(R.layout.notification_fragment, container, false);
    }

    private void initOptions(@NonNull View view)
    {
        saveNotifications = view.findViewById(R.id.saveNotifications);
        notificationShow = (Switch) view.findViewById(R.id.notificationShow);
        notificationSend = (Switch) view.findViewById(R.id.notificationSend);
        email0 = view.findViewById(R.id.addEmail0);
        email1 = view.findViewById(R.id.addEmail1);
        email2 = view.findViewById(R.id.addEmail2);
        settings = SettingsUtils.getInstance();
        notificationsUtils = NotificationsUtils.getInstance();//new NotificationsUtils(settings);

        notificationShow.setChecked(notificationsUtils.notificationsNotifyGet());
        notificationSend.setChecked(notificationsUtils.notificationsSendGet());


        String[] values = notificationsUtils.notificationsGetMailFields();

        if(values==null) return;
        if(values[0]!=null)
            email0.setText(values[0]);
        if(values[1]!=null)
            email1.setText(values[1]);
        if(values[2]!=null)
            email2.setText(values[2]);


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        settings = SettingsUtils.getInstance();
        //   view.setVisibility(View.GONE);
        HomeActivity activity = (HomeActivity) getActivity();
        //    activity.SetActiveView(NotificationFragment.this);
        initOptions(view);
        InitCameraDropdownList(view);

        saveNotifications.setOnClickListener(view1 -> {
            //    String content = EditText.getText().toString();
            boolean result = setNotifications();

            if (result) {
                NavHostFragment.findNavController(NotificationFragment.this)
                        .navigate(R.id.action_NotificationFragment_to_SettingsFragment);
            }
        });

//        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(NotificationFragment.this)
//                        .navigate(R.id.action_ViewFragment_to_AddFragment);
//            }
//        });
    }

    private boolean setNotifications()
    {
        String e1 = email0.getText().toString().trim();
        String e2 = email1.getText().toString().trim();
        String e3 = email1.getText().toString().trim();

        notificationsUtils.notificationsNotifySet(notificationShow.isChecked());
        notificationsUtils.notificationsSendSet(notificationSend.isChecked());
        notificationsUtils.notificationsSetMails(e1,e2,e3);
        notificationsUtils.notificationSecondsTriggerSet(dropdown.getSelectedItem().toString());

        return true;
    }

    void InitCameraDropdownList(@NonNull View view) {
        dropdown = view.findViewById(R.id.notificationIntervals);
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

    public static Fragment getInstance() {
        return new NotificationFragment();
    }



}