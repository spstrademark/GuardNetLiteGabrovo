package Fragments;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.guardnet_lite_gabrovo.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;

import Common.SettingsUtils;
import Common.DevicePushResultTypes;

public class AddFragment extends Fragment {

    private SettingsUtils settings;
    private EditText newUsername;
    private EditText newPassword;
    private CheckBox showPassToggle;
    private Button addDeviceButton;
    private EditText cameraUrlEditText, userNameEditText;
    private SwitchMaterial authSwitch;
    private View addFragmentRootLayout;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settings = SettingsUtils.getInstance();
        addFragmentRootLayout = view.findViewById(R.id.addFragmentRootLayout);
        newUsername = view.findViewById(R.id.newUsername);
        newPassword = view.findViewById(R.id.newPassword);
        newPassword = view.findViewById(R.id.newPassword);
        addDeviceButton = view.findViewById(R.id.addDevice);
        cameraUrlEditText = view.findViewById(R.id.userUrlEditField);
        userNameEditText = view.findViewById(R.id.userCamName);
        authSwitch = view.findViewById(R.id.authSwitch);
        showPassToggle = view.findViewById(R.id.showPassBUtton);

        buttonEvents();
    }

    private void buttonEvents() {
        authSwitch.setOnClickListener(v -> {
            boolean checked = ((SwitchMaterial) v).isChecked();
            newUsername.setEnabled(checked);
            newPassword.setEnabled(checked);
            showPassToggle.setEnabled(checked);
        });

        showPassToggle.setOnClickListener(v -> {
            boolean checked = ((CheckBox) v).isChecked();
            if (checked) {
                newPassword.setTransformationMethod(null);
            } else {
                newPassword.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

        addDeviceButton.setOnClickListener(view1 -> {
            //    String content = EditText.getText().toString();
            DevicePushResultTypes result = addNew();

            if (result == DevicePushResultTypes.OK) {
                NavHostFragment.findNavController(AddFragment.this)
                        .navigate(R.id.action_AddFragment_to_CameraFragment);
            }
        });
    }

    private DevicePushResultTypes addNew() {
        String url;
        String name;
        String username;
        String password;
        boolean isAuthEnabled;

        url = cameraUrlEditText.getText().toString().trim();
        name = userNameEditText.getText().toString().trim();
        isAuthEnabled = authSwitch.isChecked();

        username = newUsername.getText().toString().trim();
        password = newPassword.getText().toString().trim();

        DevicePushResultTypes result = settings.Add(url, name, isAuthEnabled, username, password);
        printAddMessage(result);
        return result;
    }

    private void printAddMessage(DevicePushResultTypes result) {
        switch (result) {
            case OK:
                Snackbar.make(addFragmentRootLayout, R.string.ItemAddOк, Snackbar.LENGTH_LONG).show();
                break;
            case INVALID_CHARACTER:
                Snackbar.make(addFragmentRootLayout, R.string.ItemInvalidField, Snackbar.LENGTH_LONG).show();
                break;
            case FIELD_IS_EMPTY:
                Snackbar.make(addFragmentRootLayout, R.string.ItemFieldEmpty, Snackbar.LENGTH_LONG).show();
                break;
            case MAX_LIMIT:
                Snackbar.make(addFragmentRootLayout, R.string.ItemMaxLimit, Snackbar.LENGTH_LONG).show();
                break;
        }
    }

}