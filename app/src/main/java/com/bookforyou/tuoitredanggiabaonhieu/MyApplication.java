package com.bookforyou.tuoitredanggiabaonhieu;

import android.app.Application;

import com.bookforyou.tuoitredanggiabaonhieu.utils.PrefManager;
import com.facebook.stetho.Stetho;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PrefManager.initialize(this);
        Stetho.initializeWithDefaults(this);
    }
}
