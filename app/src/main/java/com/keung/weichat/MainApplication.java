package com.keung.weichat;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        context = base;
        super.attachBaseContext(base);
    }
}
