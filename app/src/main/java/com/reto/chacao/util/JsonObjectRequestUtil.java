package com.reto.chacao.util;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

import com.reto.chacao.statics.ClamourApiValues;

/**
 * Created by Eduardo Luttinger on 20/05/2015.
 */
public class JsonObjectRequestUtil extends JsonObjectRequest {

    public JsonObjectRequestUtil(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public JsonObjectRequestUtil(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return ClamourApiValues.API_AUTH_MAP;
    }
}
