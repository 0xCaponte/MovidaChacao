package com.reto.chacao.login.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

import com.reto.chacao.R;
import com.reto.chacao.beans.UserProfile;
import com.reto.chacao.statics.ClamourValues;
import com.reto.chacao.util.AppUtil;
import com.reto.chacao.util.GcmManager;
import com.reto.chacao.util.UserUtil;

/**
 * Created by Eduardo Luttinger on 19/05/2015.
 */
public class SplashScreenActivity extends Activity {


    private static final String TAG = "SplashScreenActivity";

    private static final long SPLASH_SCREEN_DELAY = 3000;

    private GcmManager mGcmManager;

    private UserProfile mUserProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Presentacion Vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //se esconde el title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash_screen);

        mGcmManager = new GcmManager(this);

        mGcmManager.registerGCM();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {


                // Rutina de ejecucion de la proxima actividad
                mUserProfile = UserUtil.getUserRegister(SplashScreenActivity.this);

                if (!UserUtil.getRegister(SplashScreenActivity.this)) {

                    AppUtil.runActivity(IntroductionScreenActivity.class, SplashScreenActivity.this);

                } else if (!UserUtil.getEmailVerification(SplashScreenActivity.this)) {

                    AppUtil.runActivity(VerifyCodeScreenActivity.class, SplashScreenActivity.this, ClamourValues.VERIFICATION_CODE_INTENT_EXTRA, mUserProfile);

                } else if (!UserUtil.getGeolocation(SplashScreenActivity.this)) {

                    AppUtil.runActivity(GeolocationScreenActivity.class, SplashScreenActivity.this, ClamourValues.GEOLOCATION_SCREEN_INTENT_EXTRA, mUserProfile);
                } else if (!UserUtil.getGroupsStatus(SplashScreenActivity.this)) {

                    AppUtil.runActivity(GroupScreenActivity.class, SplashScreenActivity.this, ClamourValues.GROUP_SCREEN_INTENT_EXTRA, mUserProfile);
                }


                //Se finaliza la actividad para que el usuario no pueda volver con el back button
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }


}
