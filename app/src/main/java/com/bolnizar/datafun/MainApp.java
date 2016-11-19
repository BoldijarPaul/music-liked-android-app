package com.bolnizar.datafun;

import android.app.Application;

import com.bolnizar.datafun.persistance.CleanSqlHelper;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by BoldijarPaul on 19/11/2016.
 */
public class MainApp extends Application {

    private static CleanSqlHelper sSqlHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        sSqlHelper = new CleanSqlHelper(this);
    }

    public static CleanSqlHelper getSqlHelper() {
        return sSqlHelper;
    }
}
