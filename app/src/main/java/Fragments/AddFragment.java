package Fragments;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guardnet_lite_gabrovo.R;

public class AddFragment extends Fragment {
    View myView;
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

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddFragment.this)
                        .navigate(R.id.action_AddFragment_to_ViewFragment);
            }
        });
    }

}