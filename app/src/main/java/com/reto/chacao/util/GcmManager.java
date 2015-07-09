package com.reto.chacao.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONObject;

import java.io.IOException;

import com.reto.chacao.R;
import com.reto.chacao.beans.Device;
import com.reto.chacao.statics.ClamourApiValues;
import com.reto.chacao.statics.MovidaValues;

/**
 * Created by Eduardo Luttinger on 25/05/2015.
 */
public class GcmManager {

    private static final String TAG = "GcmManager";

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    /**
     *
     */
    private GoogleCloudMessaging mGoogleCloudMessaging;

    /**
     *
     */
    private String registerID;

    /**
     *
     */
    private Activity mActivity;

    /**
     *
     */
    private Context mContext;

    /**
     *
     */
    private Device mDevice;

    public GcmManager(Activity activity) {
        this.mActivity = activity;
        this.mContext = mActivity;
    }


    public void registerGCM() {

        if (checkPlayServices()) {

            registerID = getRegistrationId();

            Log.i(TAG,"RegistroID: "+registerID);

            if (registerID.isEmpty()) {

                registerInBackground();

            }

        }
    }


    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mActivity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, mActivity, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                AppUtil.showAToast(mActivity.getString(R.string.app_device_not_supported));
                Log.i(TAG, mActivity.getString(R.string.app_device_not_supported));
                mActivity.finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Gets the current registration ID for application on GCM service.
     * <p/>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     * registration ID.
     */
    private String getRegistrationId() {
        String registrationId = SharedPreferenceUtil.getString(mContext, MovidaValues.PROPERTY_REG_ID);
        registrationId = registrationId == null ? "" : registrationId;
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
        } else {
            // Check if app was updated; if so, it must clear the registration ID
            // since the existing registration ID is not guaranteed to work with
            // the new app version.
            int registeredVersion = SharedPreferenceUtil.getInt(mContext, MovidaValues.PROPERTY_APP_VERSION);

            int currentVersion = getAppVersion();
            if (registeredVersion != currentVersion) {
                Log.i(TAG, "App version changed.");
                registrationId = "";
            }
        }
        return registrationId;
    }


    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private int getAppVersion() {
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p/>
     * Stores the registration ID and app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {

                String message = "";

                try {

                    if (mGoogleCloudMessaging == null) {
                        mGoogleCloudMessaging = GoogleCloudMessaging.getInstance(mContext);
                    }

                    registerID = mGoogleCloudMessaging.register(MovidaValues.CLAMOUR_GOOGLE_API_NUMBER);

                    message = "device registered, registration id = " + registerID;

                    //sendRegistrationIdToBackend();

                    storeRegistrationId();

                } catch (IOException ioe) {
                    Log.e(TAG, "", ioe);
                }
                return message;
            }

            @Override
            protected void onPostExecute(String message) {
                Log.i(TAG, message);
            }
        }.execute();

    }


    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP
     * or CCS to send messages to your app.
     */
    private void sendRegistrationIdToBackend() {

        mDevice = new Device();
        mDevice.setDeviceModel(AppUtil.getDeviceName());
        mDevice.setOsVersion(System.getProperty("os.version"));
        mDevice.setDeviceType(mContext.getString(R.string.device_type_smartphone));
        if (mContext.getResources().getBoolean(R.bool.isATabled)) {
            mDevice.setDeviceType(mContext.getString(R.string.device_type_tablet));
        }

        // Your implementation here.
        Log.i(TAG, "sending the device id to the backend api service");

        //SUCCESS RESPONSE MANAGEMENT
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.i(TAG, jsonObject.toString());
                if (jsonObject.optString("code").equals(ClamourApiValues.SUCCESS_CODE)) {

                } else {
                    AppUtil.showAToast(jsonObject.optString("message"));
                }
            }
        };
        //ERROR RESPONSE MANAGEMENT
        Response.ErrorListener responseError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error traying to log on facebook", volleyError);

            }
        };
        //JSON REQUEST LOADER
        JsonObjectRequestUtil req = new JsonObjectRequestUtil(Request.Method.POST,
                ClamourApiValues.DEVICE_REGISTER_URL,
                mDevice.toJsonObject(), responseListener, responseError);

        //JSON REQUEST CALL
        MySingletonUtil.getInstance(mContext).addToRequestQueue(req);

    }


    /**
     * Stores the registration ID and app versionCode in the application's
     * {@code SharedPreferences}.
     */
    private void storeRegistrationId() {
        int appVersion = getAppVersion();
        Log.i(TAG, "Saving rewgisterId on app version " + appVersion);
        SharedPreferenceUtil.saveString(mContext, MovidaValues.PROPERTY_REG_ID, registerID);
        SharedPreferenceUtil.saveInt(mContext, MovidaValues.PROPERTY_APP_VERSION, appVersion);
    }

}
