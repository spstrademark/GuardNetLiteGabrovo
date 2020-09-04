package com.example.guardnet_lite_gabrovo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import Fragments.CalendarFragment;
import Fragments.ClockFragment;
import Fragments.GalleryFragment;
import Fragments.NotificationFragment;
import Notifications.Notifications;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return NotificationFragment.getInstance();
            case 2:
                return GalleryFragment.getInstance();
            case 0:
            default:
                return CalendarFragment.getInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
