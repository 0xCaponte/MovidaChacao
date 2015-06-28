package com.reto.chacao.profile.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.reto.chacao.R;
import com.reto.chacao.abstractcomponents.AppFragment;
import com.reto.chacao.main.activity.MovidaMainActivity;
import com.reto.chacao.settings.fragment.SettingsFragment;

/**
 * Created by Eduardo Luttinger on 08/06/2015.
 */
public class UserProfileScreenFragment extends AppFragment implements View.OnClickListener {


    private static final String TAG = "UserProfileScreen-Fragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(Boolean.TRUE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View userProfileView = inflater.inflate(R.layout.fragment_user_profile_screen, container, false);
        ImageView settingsButton = (ImageView) userProfileView.findViewById(R.id.user_profile_settings);
        settingsButton.setOnClickListener(this);
        return userProfileView;
    }

    @Override
    public void onClick(View view) {
        switch ( view.getId() ) {
            case R.id.user_profile_settings:
                getFragmentListener().goToFragment(new SettingsFragment());
        }

    }


    @Override
    public String getMyTag() {
        return TAG;
    }

    @Override
    protected AppFragmentListener getFragmentListener() {
        return (MovidaMainActivity) getActivity();
    }

    @Override
    protected boolean onBackPressed() {
        return true;
    }
}
