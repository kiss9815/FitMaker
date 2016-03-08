package com.juntcompany.fitmaker;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;

/**
 * Created by EOM on 2016-02-22.
 */
public class FitMaker extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(this);
        context = this;
    }

    public static Context getContext(){
        return context;
    }
}
