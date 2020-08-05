package Fragments;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.guardnet_lite_gabrovo.MainActivity;
import com.example.guardnet_lite_gabrovo.R;

import AddTools.Credentials;
import Common.FragmentsEnum;
import Common.Settings;

public class AddFragment extends Fragment {
    View myView;
    Settings settings;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        settings = new Settings(getContext(), FragmentsEnum.ADD.ordinal());
        super.onViewCreated(view, savedInstanceState);
        myView = view;
        ButtonEvents(view);
    }

    private void ButtonEvents(@NonNull View view)
    {
        view.findViewById(R.id.authSwitch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((Switch)v).isChecked();
                myView.findViewById(R.id.newUsername).setEnabled(checked);
                myView.findViewById(R.id.newPassword).setEnabled(checked);
                myView.findViewById(R.id.showPassBUtton).setEnabled(checked);
            }
        });

        view.findViewById(R.id.showPassBUtton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox)v).isChecked();
                EditText edtPassword = (EditText)myView.findViewById(R.id.newPassword);//new EditText(myView.getContext());
                if(checked){
                    edtPassword.setTransformationMethod(null);
                }else{
                    edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        view.findViewById(R.id.addDevice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    String content = EditText.getText().toString();
            AddNew();
            myView.setVisibility(View.INVISIBLE);
            MainActivity activity = (MainActivity) getActivity();
            activity.ToggleFrontLayerVisibility(View.VISIBLE);
             NavHostFragment.findNavController(AddFragment.this)
                       .navigate(R.id.action_AddFragment_to_GalleryFragment);
            }
        });
    }

    private boolean AddNew()
    {
        EditText  text;
        Switch  _switch;
        String url = "";
        String name = "";
        String username = "";
        String password = "";
        boolean auth_check = false;
        text    =   (EditText)myView.findViewById(R.id.userURL);
        url     =   text.getText().toString().trim();
        text    =   (EditText)myView.findViewById(R.id.userCamName);
        name    =   text.getText().toString().trim();

        _switch =       (Switch)myView.findViewById(R.id.authSwitch);
        auth_check =    ((Switch)_switch).isChecked();

//        if(auth_check){
            text    =   (EditText)myView.findViewById(R.id.newUsername);
            username     =   text.getText().toString().trim();
            text    =   (EditText)myView.findViewById(R.id.newPassword);
            password    =   text.getText().toString().trim();
//        }

        Credentials credentials = new Credentials();
        return credentials.Add(url,name,auth_check,username,password,settings);
    }

}