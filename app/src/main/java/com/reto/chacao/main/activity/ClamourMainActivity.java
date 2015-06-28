package com.reto.chacao.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.reto.chacao.R;
import com.reto.chacao.abstractcomponents.AppFragment;
import com.reto.chacao.abstractcomponents.MainToolbarActivity;
import com.reto.chacao.main.dialog.AddPostDialog;
import com.reto.chacao.main.fragment.HomeScreenFragment;
import com.reto.chacao.profile.fragment.UserProfileScreenFragment;
import com.reto.chacao.statics.ClamourValues;

/**
 * Created by Eduardo Luttinger on 05/06/2015.
 */
public class ClamourMainActivity extends MainToolbarActivity implements AppFragment.AppFragmentListener, View.OnClickListener {


    private ImageView mHomeButton;
    private ImageView mProfileButton;
    private ImageView mAddPostButton;

    public ClamourMainActivity() {
        super(R.layout.activity_main_home_screen, R.id.bottomToolbar,R.id.fragmentContainer);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ClamourValues.setAppFragmentListener(this);
        setBottomToolbar();
    }

    @Override
    public void goToFragment(AppFragment fragment) {
        runMyFragment(fragment, Boolean.TRUE);
    }

    private void setBottomToolbar() {
        mHomeButton = (ImageView) findViewById(R.id.toolbar_home_button);
        mHomeButton.setOnClickListener(this);
        mProfileButton = (ImageView) findViewById(R.id.toolbar_go_to_profile_button);
        mProfileButton.setOnClickListener(this);
        mAddPostButton = (ImageView) findViewById(R.id.toolbar_add_post_button);
        mAddPostButton.setOnClickListener(this);
    }

    private void showAddPostPopUp() {
        AddPostDialog dialog = new AddPostDialog(this, R.style.DialogTheme);
        dialog.setContentView(R.layout.popup_window_add_post);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.y = getResources().getDimensionPixelSize(R.dimen.home_bottom_toolbar_height);
        window.setAttributes(wlp);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_home_button:
                runMyFragment(new HomeScreenFragment(), true);
                break;
            case R.id.toolbar_go_to_profile_button:
                runMyFragment(new UserProfileScreenFragment(), true);
                break;
            case R.id.toolbar_add_post_button:
                showAddPostPopUp();
                break;
        }
    }


    @Override
    protected AppFragment getAppFragment() {
        AppFragment fragment = (AppFragment) getIntent().getSerializableExtra(ClamourValues.SERIALIZABLE_FRAGMENT);
        if(fragment == null){
            fragment = new HomeScreenFragment();
        }else{
            Bundle bundle = new Bundle();
            bundle.putString("POST_DETAIL",getIntent().getStringExtra(ClamourValues.FRAGMENT_BUNDLE));
            fragment.setArguments(bundle);
        }
        return fragment;
    }
}
