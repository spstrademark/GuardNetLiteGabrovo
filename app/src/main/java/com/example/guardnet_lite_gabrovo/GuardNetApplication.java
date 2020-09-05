package com.example.guardnet_lite_gabrovo;

import android.app.Application;

import Common.SettingsUtils;
import NotificationSettings.NotificationsUtils;
import OddBehavior.OddBehavior;

public class GuardNetApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SettingsUtils.createInstance(getApplicationContext());
        NotificationsUtils.createInstance(SettingsUtils.getInstance());
        OddBehavior.createInstance();
    }
}
