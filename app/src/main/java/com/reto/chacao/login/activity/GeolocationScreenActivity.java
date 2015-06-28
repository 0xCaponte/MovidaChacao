package com.reto.chacao.login.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import com.reto.chacao.R;
import com.reto.chacao.abstractcomponents.IntroToolBarActivity;
import com.reto.chacao.beans.UserProfile;
import com.reto.chacao.statics.ClamourApiValues;
import com.reto.chacao.statics.ClamourValues;
import com.reto.chacao.util.AppUtil;
import com.reto.chacao.util.JsonObjectRequestUtil;
import com.reto.chacao.util.MySingletonUtil;
import com.reto.chacao.util.UserUtil;

public class GeolocationScreenActivity extends IntroToolBarActivity implements View.OnClickListener {

    public static final String TAG = "Geolocation-Activity";

    Button mBtnGeolocation;
    EditText mManuallyZipCode;
    ProgressDialog mDialog;

    String mZipCode;
    LocationManager mLocationManager;
    Location mLocation;
    String provider;
    final double[] gps = new double[2];

    UserProfile mUserProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setHolderView(R.layout.activity_geolocation_screen);

        super.onCreate(savedInstanceState);

        setToolbarTitle(getString(R.string.geolocation_toolbar_text));

        setViews();

        mUserProfile = (UserProfile) getIntent().getSerializableExtra(ClamourValues.GEOLOCATION_SCREEN_INTENT_EXTRA);
    }

    private void setViews() {


        mDialog = new ProgressDialog(this);
        mDialog.setMessage(getString(R.string.progress_dialog_message));
        mDialog.setCancelable(false);

        mBtnGeolocation = (Button) findViewById(R.id.btn_current_location);
        mBtnGeolocation.setOnClickListener(this);

        mManuallyZipCode = (EditText) findViewById(R.id.edit_text_zip_code);
        mManuallyZipCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    AppUtil.hideKeyboard(GeolocationScreenActivity.this, mManuallyZipCode);
                    sendZipCode();
                }
                return false;
            }
        });

    }

    public void startDialog() {
        mDialog.show();
    }

    public void dismissDialog() {
        if (mDialog != null)
            if (mDialog.isShowing())
                mDialog.dismiss();
    }

    public void getZipCode() {
        startDialog();
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location loc) {
                // Called when a new location is found by the network location provider.
                Log.i("NOTIFICATION", "onLocationChenged Triggered");
                gps[0] = loc.getLatitude();
                gps[1] = loc.getLongitude();

                Geocoder geocoder = new Geocoder(GeolocationScreenActivity.this, Locale.getDefault());

                try {
                    List<Address> addresses = geocoder.getFromLocation(gps[0], gps[1], 5);
                    Log.i(TAG, "Address number: " + addresses.size());
                    if (addresses != null && addresses.size() > 0) {
                        mZipCode = addresses.get(0).getPostalCode();
                    }
                    if (mZipCode != null) {
                        mManuallyZipCode.setText(mZipCode);
                        mUserProfile.setZipCode(mZipCode);
                    }
                    dismissDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                    dismissDialog();
                    Toast msg = Toast.makeText(GeolocationScreenActivity.this,
                            "An error has occurred. Please, try again", Toast.LENGTH_SHORT);
                    msg.show();
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Register the listener with the Location Manager to receive location updates
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_current_location:
                getZipCode();
                break;
        }

    }

    @Override
    protected void nextButtonAction() {
        zipCodeAlert();
    }


    @Override
    protected void backButtonAction() {
        onBackPressed();
    }

    private void zipCodeAlert() {
        final AlertDialog.Builder zipCodeAlert = new AlertDialog.Builder(this);

        Resources res = getResources();
        String text = String.format(res.getString(R.string.geolocation_alert_message),
                mManuallyZipCode.getText().toString().trim());

        zipCodeAlert.setMessage(text);
        zipCodeAlert.setCancelable(false);
        zipCodeAlert.setPositiveButton(R.string.geolocation_alert_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendZipCode();
                dismissDialog();
            }
        });
        zipCodeAlert.setNegativeButton(R.string.geolocation_alert_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismissDialog();
            }
        });

        zipCodeAlert.show();

    }


    private void sendZipCode() {
        startDialog();
        UserProfile user = UserUtil.getUserRegister(this);
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.i(TAG, jsonObject.toString());
                if (jsonObject.optString("code").equals(ClamourApiValues.SUCCESS_CODE)) {
                    Log.i(TAG, "Success");
                    UserUtil.setGeolocation(GeolocationScreenActivity.this, true);
                    AppUtil.runActivity(GroupScreenActivity.class, GeolocationScreenActivity.this);
                    dismissDialog();
                } else {
                    dismissDialog();
                    AppUtil.showAToast(jsonObject.optString("message"));
                }
            }
        };
        Response.ErrorListener responseError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error trying to log", volleyError);
                volleyError.printStackTrace();
                dismissDialog();
            }
        };

        mZipCode = mManuallyZipCode.getText().toString().trim();
        mUserProfile.setZipCode(mZipCode);
        UserUtil.saveUserRegister(GeolocationScreenActivity.this, mUserProfile);

        String url = ClamourApiValues.createUrl(String.valueOf(mUserProfile.getUserId()),
                ClamourApiValues.ZIP_CODE_URL, ClamourApiValues.TYPE_USER);

//        Log.i(TAG, url);

        ClamourApiValues.API_AUTH_MAP.put(ClamourApiValues.API_TOKEN, mUserProfile.getApiToken());

        JsonObjectRequestUtil req = new JsonObjectRequestUtil(Request.Method.PUT, url,
                mUserProfile.toJsonObject(), responseListener, responseError);

        MySingletonUtil.getInstance(this).addToRequestQueue(req);
    }
}
