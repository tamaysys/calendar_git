package com.naserb.newcalendar;

import android.app.Application;

import com.naserb.newcalendar.util.Utils;


public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        Utils.initUtils(getApplicationContext());


    }
}
