package com.reto.chacao.abstractcomponents;

import android.support.v4.app.Fragment;

import java.io.Serializable;

/**
 * Created by Eduardo Luttinger on 09/06/2015.
 */
abstract public class AppFragment extends Fragment implements Serializable {

    public interface AppFragmentListener {
        public void goToFragment(AppFragment fragment);
    }

    private AppFragmentListener mFragmentListener;


    protected AppFragment() {
        mFragmentListener = getFragmentListener();
    }

    public abstract String getMyTag();

    protected abstract AppFragmentListener getFragmentListener();

    protected abstract boolean onBackPressed();

}
