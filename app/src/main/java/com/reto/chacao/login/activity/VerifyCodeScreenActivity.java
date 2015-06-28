package com.reto.chacao.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import com.reto.chacao.R;
import com.reto.chacao.abstractcomponents.IntroToolBarActivity;
import com.reto.chacao.beans.UserProfile;
import com.reto.chacao.statics.ClamourApiValues;
import com.reto.chacao.statics.ClamourValues;
import com.reto.chacao.util.AppUtil;
import com.reto.chacao.util.JsonObjectRequestUtil;
import com.reto.chacao.util.MySingletonUtil;
import com.reto.chacao.util.StringUtil;
import com.reto.chacao.util.UserUtil;

/**
 * Created by Eduardo Luttinger on 21/05/2015.
 */
public class VerifyCodeScreenActivity extends IntroToolBarActivity implements View.OnClickListener {

    private static final String TAG = "VerifyCodeScreen";

    private static final String TITLE = "Verification";

    private UserProfile mUserProfile;

    private EditText mVerificationCodeText;

    private Button mVerificationButton;

    private Button mResendVerificationButton;
    private String mVerificationCode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setHolderView(R.layout.activity_verification_email);
        super.onCreate(savedInstanceState);
        setToolbarTitle(TITLE);
        hideNextButton();
        setButtons();
        setEvents();
        mUserProfile = (UserProfile) getIntent().getSerializableExtra(ClamourValues.VERIFICATION_CODE_INTENT_EXTRA);
    }

    private void setButtons() {
        mVerificationCodeText = (EditText) findViewById(R.id.verification_code);
        mVerificationButton = (Button) findViewById(R.id.verification_button);
        mResendVerificationButton = (Button) findViewById(R.id.btn_verify_lost);

    }

    private void setEvents() {

        mVerificationButton.setOnClickListener(this);
        mResendVerificationButton.setOnClickListener(this);
        mVerificationCodeText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    executeVerification();
                }
                return false;
            }
        });
    }


    @Override
    protected void nextButtonAction() {
        AppUtil.runActivity(GeolocationScreenActivity.class, this, ClamourValues.GEOLOCATION_SCREEN_INTENT_EXTRA, mUserProfile);
    }

    @Override
    protected void backButtonAction() {
        onBackPressed();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.verification_button:
                executeVerification();
                break;

            case R.id.btn_verify_lost:
                resendVerificationCode();
                break;

        }
    }


    private void executeVerification() {
        if (fieldVerification()) {
            verifyCode();
        }
    }

    private Boolean fieldVerification() {
        mVerificationCode = mVerificationCodeText.getText().toString().trim();

        boolean validFields = Boolean.TRUE;
        if ("".equals(mVerificationCode)) {
            mVerificationCodeText.setError(getString(R.string.field_required));
            validFields = Boolean.FALSE;
        } else if (!StringUtil.isValidNumber(mVerificationCode)) {
            mVerificationCodeText.setError(getString(R.string.number_error));
            validFields = Boolean.FALSE;
        }
        return validFields;
    }

    private void verifyCode() {

        mProgressDialog.show();
        mUserProfile.setNumericConfirmationToken(Integer.parseInt(mVerificationCode));

        //SUCCESS RESPONSE MANAGEMENT
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                Log.i(TAG, "Response from verification code service: " + jsonObject.toString());

                mProgressDialog.dismiss();

                if (jsonObject.optString("code").equals(ClamourApiValues.SUCCESS_CODE)) {

                    UserUtil.setEmailVerification(VerifyCodeScreenActivity.this, true);
                    UserUtil.saveUserRegister(VerifyCodeScreenActivity.this, mUserProfile);
                    nextButtonAction();

                } else {

                    AppUtil.showAToast(jsonObject.optString("message"));

                }
                nextButtonAction();

            }
        };
        //ERROR RESPONSE MANAGEMENT
        Response.ErrorListener responseError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error trying to verify account", volleyError);
                mProgressDialog.dismiss();
                AppUtil.showAToast(getString(R.string.app_login_error));
            }
        };
        //JSON REQUEST LOADER
        JsonObjectRequestUtil req = new JsonObjectRequestUtil(Request.Method.POST,
                ClamourApiValues.VERICIFATION_CODE_URL,
                mUserProfile.toJsonObject(), responseListener, responseError);

//        JSON REQUEST CALL
        MySingletonUtil.getInstance(this).addToRequestQueue(req);
    }

    private void resendVerificationCode() {

        mProgressDialog.show();

        //SUCCESS RESPONSE MANAGEMENT
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                Log.i(TAG, "Response from resend verification code service: " + jsonObject.toString());

                mProgressDialog.dismiss();

                if (jsonObject.optString("code").equals(ClamourApiValues.SUCCESS_CODE)) {

                    AppUtil.showAToast("Check your email again");

                } else {

                    AppUtil.showAToast(jsonObject.optString("message"));

                }
            }
        };
        //ERROR RESPONSE MANAGEMENT
        Response.ErrorListener responseError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error trying to verify account", volleyError);
                mProgressDialog.dismiss();
                AppUtil.showAToast(getString(R.string.app_login_error));
            }
        };
        //JSON REQUEST LOADER
        JsonObjectRequestUtil req = new JsonObjectRequestUtil(Request.Method.POST,
                ClamourApiValues.RECOVER_VERIFICATION_CODE_URL,
                mUserProfile.toJsonObject(), responseListener, responseError);

        //JSON REQUEST CALL
        MySingletonUtil.getInstance(this).addToRequestQueue(req);
    }


}
