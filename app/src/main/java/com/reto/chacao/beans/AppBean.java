package com.reto.chacao.beans;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

import com.reto.chacao.App;
import com.reto.chacao.util.MySingletonUtil;

/**
 * Created by Eduardo Luttinger on 20/05/2015.
 */
public class AppBean implements Serializable {

    private static final String TAG = "AppBean";


    public JSONObject toJsonObject() {
        JSONObject jsonObject = (JSONObject) null;
        try {
            String json = MySingletonUtil.getInstance(App.getContext().getApplicationContext()).getGSON().toJson(this);
            jsonObject = new JSONObject(json);
            Log.i(TAG,"Json Request: "+jsonObject.toString());
        } catch (Exception   e) {
            Log.e(TAG, " ", e);
        }
        return jsonObject;
    }

}
