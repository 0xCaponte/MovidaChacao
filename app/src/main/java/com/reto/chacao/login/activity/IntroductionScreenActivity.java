package com.reto.chacao.login.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridView;

import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import com.reto.chacao.R;
import com.reto.chacao.login.adapter.IntroductionAdapter;
import com.reto.chacao.login.fragment.FacebookSingUpScreenFragment;
import com.reto.chacao.util.AppUtil;
import com.reto.chacao.util.UserUtil;

/**
 * Created by ULISES HARRIS on 19/05/2015.
 */
public class IntroductionScreenActivity extends FragmentActivity implements View.OnClickListener {

    private GridView mLeftGridView;
    private GridView mRightGridView;

    private IntroductionAdapter mLeftAdapter;
    private IntroductionAdapter mRightAdapter;

    private Button mSignUpEmail;
    private LoginButton mSignUpFacebook;

    private boolean mLeftScrollUp = false;
    private boolean mRightScrollUp = true;

    private static final int TIMER_SCROLL = 60000;
    private static final String TAG = "IntroductionScreen";

    private Integer[] mLeftImages = {
            R.drawable.item1, R.drawable.item2,
            R.drawable.item3, R.drawable.item4,
            R.drawable.item5, R.drawable.item6,
            R.drawable.item7, R.drawable.item8,
            R.drawable.item9, R.drawable.item10
    };

    private Integer[] mRightImages = {
            R.drawable.item11, R.drawable.item12,
            R.drawable.item13, R.drawable.item14,
            R.drawable.item15, R.drawable.item16,
            R.drawable.item17, R.drawable.item18,
            R.drawable.item19, R.drawable.item20
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_introduction);

        setButtons();
        setGridViews();

        //Usuario deja de ser nuevo
        UserUtil.setRegister(this, true);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.singup_fragment_container, new FacebookSingUpScreenFragment()).commit();

    }


    private void setGridViews() {
        mLeftGridView = (GridView) findViewById(R.id.left_grid_view);
        mLeftAdapter = new IntroductionAdapter(this, mLeftImages);
        mLeftGridView.setAdapter(mLeftAdapter);

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLeftGridView.setSelection(mLeftImages.length - 1);
            }
        });

        mRightGridView = (GridView) findViewById(R.id.right_grid_view);
        mRightAdapter = new IntroductionAdapter(this, mRightImages);
        mRightGridView.setAdapter(mRightAdapter);

        setScrollingUp(mLeftGridView);
        setScrollingDown(mRightGridView);
        mLeftGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (mLeftScrollUp) {
                        setScrollingUp(mLeftGridView);
                        mLeftScrollUp = false;
                    } else {
                        setScrollingDown(mLeftGridView);
                        mLeftScrollUp = true;
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        mRightGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (mRightScrollUp) {
                        setScrollingUp(mRightGridView);
                        mRightScrollUp = false;
                    } else {
                        setScrollingDown(mRightGridView);
                        mRightScrollUp = true;
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void setButtons() {
        mSignUpEmail = (Button) findViewById(R.id.btn_sign_up_email);
        mSignUpEmail.setOnClickListener(this);
    }

    private void setScrollingUp(final GridView gridView) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                gridView.smoothScrollToPositionFromTop(0, 20, TIMER_SCROLL);
            }
        });


    }

    private void setScrollingDown(final GridView gridView) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                gridView.smoothScrollToPositionFromTop(9, 20, TIMER_SCROLL);
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_up_email:
                AppUtil.runActivity(TermsAndConditionActivity.class, this);
                break;
        }

    }

    private void runActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    private void facebookLogOut() {
        Log.i(TAG, "session de facebook finalizada");
        LoginManager.getInstance().logOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log Out From Facebook
        facebookLogOut();
    }
}
