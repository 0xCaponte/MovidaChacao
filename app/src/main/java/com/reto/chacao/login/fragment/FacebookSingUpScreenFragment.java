package com.reto.chacao.login.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.reto.chacao.R;
import com.reto.chacao.abstractcomponents.AppFragment;
import com.reto.chacao.beans.UserProfile;
import com.reto.chacao.main.activity.MovidaMainActivity;
import com.reto.chacao.statics.ClamourApiValues;
import com.reto.chacao.statics.MovidaValues;
import com.reto.chacao.util.AppUtil;
import com.reto.chacao.util.JsonObjectRequestUtil;
import com.reto.chacao.util.MySingletonUtil;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by Eduardo Luttinger on 19/05/2015.
 */
public class FacebookSingUpScreenFragment extends AppFragment {


    private static final String TAG = "FacebookSingUpScreen";
    private LoginButton mLoginButton;
    private CallbackManager mCallbackManager;
    private UserProfile mUserProfile;
    private ProgressDialog mProgressDialog;
    private Integer mLayout;

    public FacebookSingUpScreenFragment() {
        this.mLayout = R.layout.fragment_facebook_sign_up_button;
    }

    public FacebookSingUpScreenFragment(Integer mLayout) {
        this.mLayout = mLayout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        mCallbackManager = CallbackManager.Factory.create();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View loginView = inflater.inflate(mLayout, container, false);

        //Facebook Button init
        mLoginButton = (LoginButton) loginView.findViewById(R.id.singUPFacebookButton);
        mLoginButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        mLoginButton.setReadPermissions(Arrays.asList("public_profile, email"));
        mLoginButton.setFragment(this);

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(R.string.progress_dialog_message));

        //Login on Facebook routine
        singUpWithFacebookManagement();


        return loginView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void singUpWithFacebookManagement() {

        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                final String accessToken = loginResult.getAccessToken().getToken();

                Bundle requestParameters = new Bundle();

                requestParameters.putString("fields", "id,email,first_name,last_name");

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject me, GraphResponse response) {

                        if (response.getError() != null) {


                        } else {
                            //Json Object Loader
                            mUserProfile = new UserProfile();
                            mUserProfile.setEmail(me.optString("email"));
                            mUserProfile.setFamilyName(me.optString("last_name"));
                            mUserProfile.setFirstName(me.optString("first_name"));
                            mUserProfile.setFbId(me.optString("id"));
                            mUserProfile.setMiddleName("");
                            mUserProfile.setFbToken(accessToken);

                            if ("".equals(mUserProfile.getEmail())) {

                           //     Intent emailRequestIntent = new Intent(getActivity(), EmailReqestScreenActivity.class);
                             //   emailRequestIntent.putExtra(MovidaValues.EMAIL_REQUEST_INTENT_EXTRA, mUserProfile);
                               // startActivity(emailRequestIntent);

                            } else {
                                registerWithFacebook();
                            }

                        }
                    }
                });
                request.setParameters(requestParameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                Log.i(TAG, "Facebook login operation was canceled by clamour application");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.i(TAG, "Application error", exception);
            }
        });

    }

    private void registerWithFacebook() {
        //SUCCESS RESPONSE MANAGEMENT

        mProgressDialog.show();

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.i(TAG, jsonObject.toString());

                if (jsonObject.optString("code").equals(ClamourApiValues.SUCCESS_CODE)) {

                    mUserProfile.setApiToken(AppUtil.getValuesFromApiMessageObject("apiToken", jsonObject));
                    mUserProfile.setConfirmationToken(AppUtil.getValuesFromApiMessageObject("confirmationToken", jsonObject));
                    AppUtil.runActivity(MovidaMainActivity.class, getActivity(), MovidaValues.VERIFICATION_CODE_INTENT_EXTRA, mUserProfile);

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
        MySingletonUtil.getInstance(getActivity()).addToRequestQueue(req);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public String getMyTag() {
        return null;
    }

    @Override
    protected AppFragmentListener getFragmentListener() {
        return null;
    }

    @Override
    protected boolean onBackPressed() {
        return false;
    }
}

