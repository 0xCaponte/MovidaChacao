package com.reto.chacao.abstractcomponents;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.List;

import com.reto.chacao.R;

/**
 * Created by Eduardo Luttinger on 05/06/2015.
 */
abstract public class MainToolbarActivity extends AppCompatActivity {

    //To share with my sons :)
    protected ProgressDialog mProgressDialog;
    protected static final String TAG = "MAIN_ACTIVITY";

    //Not for share :P
    private Integer myLayout;
    private Integer myToolbar;
    private Integer myFragmentContainer;
    private Toolbar mToolbar;
    private FragmentManager mFragmentManager;


    public MainToolbarActivity(Integer myLayout, Integer myToolbar,Integer myFragmentContainer) {
        this.myLayout = myLayout;
        this.myToolbar = myToolbar;
        this.myFragmentContainer = myFragmentContainer;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(myLayout);
        mToolbar = (Toolbar) findViewById(myToolbar);
        mToolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(mToolbar);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.progress_dialog_message));
        mFragmentManager = getSupportFragmentManager();


        AppFragment fragmentToRun = getAppFragment();

        if(fragmentToRun != null){
            runMyFragment(fragmentToRun,Boolean.TRUE);
        }


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            //Restore the fragment's instance
            AppFragment mContent = (AppFragment) getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
            runMyFragment(mContent, false);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        AppFragment fragment = getLastFragment();
        getSupportFragmentManager().putFragment(outState, "mContent", fragment);
    }

    public void runMyFragment(AppFragment fragment, Boolean isAReplace) {
        if(myFragmentContainer != null){
            if (!isAReplace) {
                mFragmentManager.beginTransaction().add(myFragmentContainer, fragment).addToBackStack(fragment.getMyTag()).commit();
            } else {
                mFragmentManager.beginTransaction().replace(myFragmentContainer, fragment).addToBackStack(fragment.getMyTag()).commit();
            }
        }else{
            Log.e(TAG,"it is not an activity with fragments, cannot run a fragment here");
        }
    }


    @Override
    public void onBackPressed() {
        AppFragment fragment = getLastFragment();
        if(fragment != null){
            if(fragment.onBackPressed()){
                int count = getSupportFragmentManager().getBackStackEntryCount();
                if (count == 1) {
                    finish();
                } else{
                    super.onBackPressed();
                }
            }
        }else{
            super.onBackPressed();
        }
    }

    public AppFragment getLastFragment() {
        FragmentManager fm = getSupportFragmentManager();
        AppFragment fragment = (AppFragment) null;
        if (fm != null) {
            List<Fragment> fragments = fm.getFragments();
            for (int i = fragments.size() - 1; i >= 0; i--) {
                fragment = (AppFragment) fragments.get(i);
                if (fragment != null) {
                    break;
                }
            }
        }
        return fragment;
    }

    abstract protected AppFragment getAppFragment();


}
