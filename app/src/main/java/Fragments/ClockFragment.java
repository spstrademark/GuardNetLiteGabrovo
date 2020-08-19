package Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.guardnet_lite_gabrovo.R;

import java.sql.Time;

import Common.SettingsUtils;

public class ClockFragment extends Fragment {
    SettingsUtils settings;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.clock_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settings = SettingsUtils.getInstance();
        long now = System.currentTimeMillis();
        Time sqlTime = new Time(now);
        String time_now = sqlTime.toString();

        TextView start = (TextView)view.findViewById(R.id.clockStart);
        start.setText(time_now);
        TextView end = (TextView)view.findViewById(R.id.clockEnd);
        end.setText(time_now);
    }

    public static Fragment getInstance() {

        return new ClockFragment();
    }

}