package Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.guardnet_lite_gabrovo.R;

public class CalendarFragment extends Fragment {
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calendar_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentInit();

        CalendarView calendarView=(CalendarView) view.findViewById(R.id.calendarView);

        calendarView.setMinDate(System.currentTimeMillis() - 1000); // disable past dates
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                NavHostFragment.findNavController(CalendarFragment.this)
                        .navigate(R.id.action_CalendarFragment_to_ClockFragment);
            }
        });

//        view.findViewById(R.id.language).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(CalendarFragment.this)
//                        .navigate(R.id.action_SettingsFragment_to_LanguageFragment);
//            }
//        });

    }

    public void FragmentInit()
    {
        getActivity().setTitle(R.string.Settings);
    }
}
