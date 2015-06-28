package com.reto.chacao.login.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import org.json.JSONObject;

import java.util.Arrays;

import com.reto.chacao.R;
import com.reto.chacao.beans.UserProfile;
import com.reto.chacao.statics.ClamourApiValues;
import com.reto.chacao.util.AppUtil;
import com.reto.chacao.util.JsonObjectRequestUtil;
import com.reto.chacao.util.MySingletonUtil;

/**
 * Created by Eduardo Luttinger on 19/05/2015.
 */
public class FacebookLoginScreenFragment extends Fragment {


    private static final String TAG = "FacebookLoginScreen";
    private LoginButton mLoginButton;
    private CallbackManager mCallbackManager;
    private UserProfile mUserProfile;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        mCallbackManager = CallbackManager.Factory.create();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View loginView = inflater.inflate(R.layout.fragment_facebook_login_button, container, false);

        //Facebook Button init
        mLoginButton = (LoginButton) loginView.findViewById(R.id.loginFacebookButton);
        mLoginButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        mLoginButton.setReadPermissions(Arrays.asList("public_profile, email"));
        mLoginButton.setFragment(this);

        //Login on Facebook routine
        loginWithFacebookManagement();

        return loginView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void loginWithFacebookManagement() {

        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                final String accessToken = loginResult.getAccessToken().getToken();

                Bundle requestParameters = new Bundle();

                requestParameters.putString("fields", "id");

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject me, GraphResponse response) {

                        if (response.getError() != null) {


                        } else {
                            //Json Object Loader
                            mUserProfile = new UserProfile();
                            mUserProfile.setFbId(me.optString("id"));

                            //SUCCESS RESPONSE MANAGEMENT
                            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    Log.i(TAG, jsonObject.toString());
                                    if(jsonObject.optString("code").equals(ClamourApiValues.SUCCESS_CODE)){



                                    }else{
                                        AppUtil.showAToast(jsonObject.optString("message"));
                                    }
                                }
                            };
                            //ERROR RESPONSE MANAGEMENT
                            Response.ErrorListener responseError = new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Log.e(TAG, "Error iniciando session con facebook", volleyError);
                                    AppUtil.showAToast(getString(R.string.facebook_login_error));
                                }
                            };

                            //JSON REQUEST LOADER
                            JsonObjectRequestUtil req = new JsonObjectRequestUtil(Request.Method.POST,
                                    ClamourApiValues.LOGIN_WITH_FACEBOOK,
                                    mUserProfile.toJsonObject(), responseListener, responseError);

                            //JSON REQUEST CALL
                            MySingletonUtil.getInstance(getActivity()).addToRequestQueue(req);
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


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

