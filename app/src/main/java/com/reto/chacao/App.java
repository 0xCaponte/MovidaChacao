package com.reto.chacao;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;

import com.reto.chacao.util.NukeSSLCerts;

/**
 * Created by Eduardo Luttinger on 19/05/2015.
 */
public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        FacebookSdk.sdkInitialize(getApplicationContext());

        NukeSSLCerts.nuke();

    }

    public static Context getContext() {
        return mContext;
    }




}
