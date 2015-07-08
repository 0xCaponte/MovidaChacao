package com.reto.chacao.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.reto.chacao.R;
import com.reto.chacao.abstractcomponents.IntroToolBarActivity;
import com.reto.chacao.beans.UserProfile;
import com.reto.chacao.statics.ClamourApiValues;
import com.reto.chacao.util.AppUtil;
import com.reto.chacao.util.JsonObjectRequestUtil;
import com.reto.chacao.util.MySingletonUtil;
import com.reto.chacao.util.UserUtil;

import org.json.JSONObject;

/**
 * Created by Eduardo Luttinger on 21/05/2015.
 */
public class SingUpScreenActivity extends IntroToolBarActivity {


    private static final String TITLE = "Sign up with email";
    private static final String TAG = "SingUpScreenActivity";
    private UserProfile mUserProfile;
    private EditText mFirstNameText;
    private EditText mLastNameText;
    private EditText mEmailText;
    private EditText mPasswordText;
    private String mName;
    private String mLastName;
    private String mEmail;
    private String mPassword;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setHolderView(R.layout.activity_sign_up_with_email);
        super.onCreate(savedInstanceState);
        setToolbarTitle(TITLE);

        mFirstNameText = (EditText) findViewById(R.id.singup_form_fisrt_name);
        mLastNameText = (EditText) findViewById(R.id.singup_form_last_name);
        mEmailText = (EditText) findViewById(R.id.singup_form_email);
        mPasswordText = (EditText) findViewById(R.id.singup_form_password);

        mPasswordText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    nextButtonAction();
                }
                return false;
            }
        });

        setEvents();

    }


    private Boolean fieldVerification() {
        mName = mFirstNameText.getText().toString().trim();
        mLastName = mLastNameText.getText().toString().trim();
        mEmail = mEmailText.getText().toString().trim();
        mPassword = mPasswordText.getText().toString().trim();

        boolean validFields = Boolean.TRUE;
        if ("".equals(mName)) {
            mFirstNameText.setError(getString(R.string.field_required));
            validFields = Boolean.FALSE;
        } else if ("".equals(mLastName)) {
            mLastNameText.setError(getString(R.string.field_required));
            validFields = Boolean.FALSE;
//        } else if (!StringUtil.isValidEmail(mEmail)) {
//            mEmailText.setError(getString(R.string.email_error));
//            validFields = Boolean.FALSE;
//        }
        } else if ("".equals(mEmail)) {
            mEmailText.setError(getString(R.string.field_required));
            validFields = Boolean.FALSE;
        } else if ("".equals(mPassword)) {
            mPasswordText.setError(getString(R.string.field_required));
            validFields = Boolean.FALSE;
        }
        return validFields;
    }

    private void setEvents() {
        mPasswordText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    nextButtonAction();
                }
                return false;
            }
        });
    }


    private void signUp() {

        mProgressDialog.show();

        mUserProfile = new UserProfile();
        mUserProfile.setFamilyName(mLastName);
        mUserProfile.setFirstName(mName);
        mUserProfile.setMiddleName("");
        mUserProfile.setUsername(mEmail);
        //SUCCESS RESPONSE MANAGEMENT
        Response.Listener<JSONObject> saltResponseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.i(TAG, jsonObject.toString());
                if (jsonObject.optString("code").equals(ClamourApiValues.SUCCESS_CODE)) {

                    mUserProfile.setSalt(AppUtil.getValuesFromApiMessageObject("salt", jsonObject));
                    if (!"".equals(mUserProfile.getSalt())) {

                        Log.i(TAG, "Codigo SALT encontrado : " + mUserProfile.getSalt());

                        singUpByEmail();
                    }

                } else {
                    mProgressDialog.dismiss();

                    AppUtil.showAToast(jsonObject.optString("message"));

                }
            }
        };
        //ERROR RESPONSE MANAGEMENT
        Response.ErrorListener saltResponseError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mProgressDialog.dismiss();
                Log.e(TAG, "Error trying get salt", volleyError);
                volleyError.printStackTrace();
            }
        };
        //JSON REQUEST LOADER
        JsonObjectRequestUtil req = new JsonObjectRequestUtil(Request.Method.GET,
                ClamourApiValues.GET_SALT_URL,
                mUserProfile.toJsonObject(), saltResponseListener, saltResponseError);

//        AppUtil.runActivity(VerifyCodeScreenActivity.class, SingUpScreenActivity.this, MovidaValues.VERIFICATION_CODE_INTENT_EXTRA, mUserProfile);
        //JSON REQUEST CALL
        MySingletonUtil.getInstance(this).addToRequestQueue(req);

    }


    private void singUpByEmail() {

        Log.i(TAG, "Registrando con email...");
        mUserProfile.setFamilyName(mLastName);
        mUserProfile.setFirstName(mName);
        mUserProfile.setMiddleName("");
        mUserProfile.setEmail(mEmail);
        mUserProfile.setUsername(mEmail);
        mUserProfile.setPassword(AppUtil.getEncryptedPassword(mPassword, mUserProfile.getSalt()));

        Log.i(TAG, "Password Cifrado: " + mUserProfile.getPassword());

        //SUCCESS RESPONSE MANAGEMENT
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                Log.i(TAG, jsonObject.toString());

                if (jsonObject.optString("code").equals(ClamourApiValues.SUCCESS_CODE)) {

                    mProgressDialog.dismiss();

                    Log.i(TAG, "Registro exitoso con correo");

                    mUserProfile.setApiToken(AppUtil.getValuesFromApiMessageObject("apiToken", jsonObject));
                    mUserProfile.setConfirmationToken(AppUtil.getValuesFromApiMessageObject("confirmationToken", jsonObject));
                    mUserProfile.setUserId(AppUtil.getValuesFromApiMessageObjectInt("userId", jsonObject));

                    UserUtil.saveUserRegister(SingUpScreenActivity.this, mUserProfile);
                    UserUtil.setRegister(SingUpScreenActivity.this, true);

                    Log.i(TAG, "Corriendo pantalla de verificacion de cuenta creada");
               //     AppUtil.runActivity(VerifyCodeScreenActivity.class, SingUpScreenActivity.this, MovidaValues.VERIFICATION_CODE_INTENT_EXTRA, mUserProfile);


                } else {

                    mProgressDialog.dismiss();
                    AppUtil.showAToast(jsonObject.optString("message"));

                }
            }
        };
        //ERROR RESPONSE MANAGEMENT
        Response.ErrorListener responseError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mProgressDialog.dismiss();
                Log.e(TAG, "Error trying to log", volleyError);
                volleyError.printStackTrace();
            }
        };

        //JSON REQUEST LOADER
        JsonObjectRequestUtil req = new JsonObjectRequestUtil(Request.Method.POST,
                ClamourApiValues.REGISTER_WITH_EMAIL,
                mUserProfile.toJsonObject(), responseListener, responseError);

        //JSON REQUEST CALL
        MySingletonUtil.getInstance(this).addToRequestQueue(req);
    }

    @Override
    protected void nextButtonAction() {
        if (fieldVerification()) {
            Log.i(TAG, "Llamada al servicio de registro con email");
            signUp();
        }
    }


    @Override
    protected void backButtonAction() {
        onBackPressed();
    }


}
