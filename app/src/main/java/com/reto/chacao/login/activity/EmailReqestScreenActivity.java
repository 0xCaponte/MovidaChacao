package com.reto.chacao.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;

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

/**
 * Created by Eduardo Luttinger on 21/05/2015.
 */
public class EmailReqestScreenActivity extends IntroToolBarActivity {

    private static final String TAG = "EmailReqestScreen";
    private static final String TITLE = "Email Request";

    private EditText mEmailText;
    private UserProfile mUserProfile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setHolderView(R.layout.activity_email_request);
        super.onCreate(savedInstanceState);
        setToolbarTitle(TITLE);
        mEmailText = (EditText)findViewById(R.id.email_request_email);
        mUserProfile = (UserProfile) getIntent().getSerializableExtra(ClamourValues.EMAIL_REQUEST_INTENT_EXTRA);

    }

    private void registerWithFacebook() {

        mProgressDialog.show();
        mUserProfile.setEmail(mEmailText.getText().toString());
        mUserProfile.setUsername(mEmailText.getText().toString());

        //SUCCESS RESPONSE MANAGEMENT
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.i(TAG, jsonObject.toString());

                if (jsonObject.optString("code").equals(ClamourApiValues.SUCCESS_CODE)) {

                    mUserProfile.setApiToken(AppUtil.getValuesFromApiMessageObject("apiToken", jsonObject));
                    mUserProfile.setConfirmationToken(AppUtil.getValuesFromApiMessageObject("confirmationToken", jsonObject));
                    AppUtil.runActivity(GeolocationScreenActivity.class, EmailReqestScreenActivity.this, ClamourValues.VERIFICATION_CODE_INTENT_EXTRA, mUserProfile);

                }else{

                    AppUtil.showAToast(jsonObject.optString("message"));

                }


                mProgressDialog.dismiss();
            }
        };
        //ERROR RESPONSE MANAGEMENT
        Response.ErrorListener responseError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error traying to log on facebook", volleyError);
                mProgressDialog.dismiss();
            }
        };

        //JSON REQUEST LOADER
        JsonObjectRequestUtil req = new JsonObjectRequestUtil(Request.Method.POST,
                ClamourApiValues.REGISTER_WITH_FACEBOOK,
                mUserProfile.toJsonObject(), responseListener, responseError);

        //JSON REQUEST CALL
        MySingletonUtil.getInstance(this).addToRequestQueue(req);
    }

    private Boolean fieldVerification(){
        boolean validFields =Boolean.TRUE;
        if("".equals(mEmailText.getText().toString())){
            mEmailText.setError("this field is required");
            validFields = Boolean.FALSE;
        }
        return validFields;
    }


    @Override
    protected void backButtonAction() {
        onBackPressed();
    }

    @Override
    protected void nextButtonAction() {
        if(fieldVerification()){
            registerWithFacebook();
        }
    }

}
