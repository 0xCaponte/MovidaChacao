package com.reto.chacao.abstractcomponents;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Eduardo Luttinger on 05/06/2015.
 */
public class MainFragmentActivity extends Activity {


    interface Listener {

        public void passDataToMyActivity(Bundle data);


    }

    private Listener mListener;

    public void setListener(Listener mListener){
        mListener = mListener;

    }


}
