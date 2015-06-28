package com.reto.chacao.login.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import com.reto.chacao.R;
import com.reto.chacao.abstractcomponents.IntroToolBarActivity;
import com.reto.chacao.beans.UserProfile;
import com.reto.chacao.login.fragment.FacebookLoginScreenFragment;
import com.reto.chacao.main.activity.ClamourMainActivity;
import com.reto.chacao.statics.ClamourApiValues;
import com.reto.chacao.util.AppUtil;

/**
 * Created by Eduardo Luttinger on 19/05/2015.
 */
public class LoginScreenActivity extends IntroToolBarActivity {

    private static final String TAG = "LoginScreenActivity";
    private static final String TITLE = "Log In";

    private UserProfile mUserProfile;
    private EditText mEmailText;
    private EditText mPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setHolderView(R.layout.activity_login_screen);

        super.onCreate(savedInstanceState);

        setToolbarTitle(TITLE);

        mEmailText = (EditText) findViewById(R.id.login_form_email);
        mPasswordText = (EditText) findViewById(R.id.login_form_password);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.login_fragment_container, new FacebookLoginScreenFragment()).commit();

    }


    private void loginByEmail(String email, String password) {

        mUserProfile = new UserProfile();
        mUserProfile.setEmail(email);
        mUserProfile.setPassword(password);
        AppUtil.hideKeyboard(this);
        AppUtil.runActivityClearTop(ClamourMainActivity.class, LoginScreenActivity.this);
//        mProgressDialog.show();

        //SUCCESS RESPONSE MANAGEMENT
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.i(TAG, jsonObject.toString());
                mProgressDialog.dismiss();
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
                mProgressDialog.dismiss();
                AppUtil.showAToast(getString(R.string.app_login_error));

            }
        };
        //JSON REQUEST LOADER
//        JsonObjectRequestUtil req = new JsonObjectRequestUtil(Request.Method.POST,
//                ClamourApiValues.LOGIN_BY_EMAIL,
//                mUserProfile.toJsonObject(), responseListener, responseError);
//
//        //JSON REQUEST CALL
//        MySingletonUtil.getInstance(this).addToRequestQueue(req);

    }

    @Override
    protected void nextButtonAction() {
        Log.i(TAG, "Presiono el boton Next");
        if (fieldVerification()) {
            loginByEmail(mEmailText.getText().toString(), mPasswordText.getText().toString());
        }
    }

    @Override
    protected void backButtonAction() {
        Log.i(TAG, "Presiono el boton back");
        onBackPressed();
    }

    private Boolean fieldVerification() {
        boolean validFields = Boolean.TRUE;
        if ("".equals(mEmailText.getText().toString())) {
            mEmailText.setError("this field is required");
            validFields = Boolean.FALSE;
        } else if ("".equals(mPasswordText.getText().toString())) {
            mPasswordText.setError("this field is required");
            validFields = Boolean.FALSE;
        }
        return validFields;
    }

}
