package com.example.guardnet_lite_gabrovo;

import android.app.Application;

import Common.SettingsUtils;

public class GuardNetApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SettingsUtils.createInstance(getApplicationContext());
    }
}
