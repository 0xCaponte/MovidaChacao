package com.reto.chacao.login.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;

import com.facebook.appevents.AppEventsLogger;
import com.reto.chacao.R;
import com.reto.chacao.beans.MapProfile;
import com.reto.chacao.beans.UserProfile;
import com.reto.chacao.database.DataBaseHelper;
import com.reto.chacao.main.activity.MovidaMainActivity;
import com.reto.chacao.util.AppUtil;
import com.reto.chacao.util.GcmManager;
import com.reto.chacao.util.MapUtil;
import com.reto.chacao.util.UserUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Eduardo Luttinger on 19/05/2015.
 */
public class SplashScreenActivity extends Activity {


    private static final String TAG = "SplashScreenActivity";
    private static final String KEY_INTRODUCTION = "com.reto.chacao.IntroductionKey";

    private static final long SPLASH_SCREEN_DELAY = 2500;

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

        DataBaseHelper dbHelper = new DataBaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            dbHelper.onUpgrade(db,2,3);
        }catch (Exception err){
            err.printStackTrace();
        }

        mGcmManager = new GcmManager(this);

        mGcmManager.registerGCM();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {


                // Rutina de ejecucion de la proxima actividad
                mUserProfile = UserUtil.getUserRegister(SplashScreenActivity.this);

                // Usuarios nuevo -> Introducción
                if (!UserUtil.getRegister(SplashScreenActivity.this)) {

                    //Filtro inicial de los mapas
                    MapProfile map = new MapProfile();
                    MapUtil.saveMapFilters(SplashScreenActivity.this, map);

                    AppUtil.runActivity(IntroductionScreenActivity.class, SplashScreenActivity.this);

                // Usuarios Viejos -> Home
                } else  {

                    AppUtil.runActivityClearTop(MovidaMainActivity.class, SplashScreenActivity.this);
                }

                //Se finaliza la actividad para que el usuario no pueda volver con el back button
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }



}
