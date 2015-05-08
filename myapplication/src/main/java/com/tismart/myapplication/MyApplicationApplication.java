package com.tismart.myapplication;

import android.app.Application;

import com.tismart.tsmlibrary.database.DatabaseInstance;

/**
 * Created by luis.burgos on 06/05/2015.
 *
 */
public class MyApplicationApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseInstance.initialize(this, "MyApplication", "MyApplication.sqlite", 1, true);
    }

}
