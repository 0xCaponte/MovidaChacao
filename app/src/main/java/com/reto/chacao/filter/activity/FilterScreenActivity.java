package com.reto.chacao.filter.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import com.reto.chacao.R;
import com.reto.chacao.abstractcomponents.IntroToolBarActivity;
import com.reto.chacao.beans.Category;
import com.reto.chacao.beans.ItemCondition;
import com.reto.chacao.filter.adapter.FilterAdapter;
import com.reto.chacao.statics.ClamourApiValues;
import com.reto.chacao.statics.ClamourValues;
import com.reto.chacao.util.AppUtil;
import com.reto.chacao.util.JsonObjectRequestUtil;
import com.reto.chacao.util.MySingletonUtil;

/**
 * Created by ULISES HARRIS on 28/05/2015.
 */
public class FilterScreenActivity extends IntroToolBarActivity implements FilterAdapter.FilterClickListener {

    private RecyclerView mFilterRecyclerView;

    private FilterAdapter mFilterAdapter;
    private int mGroupId;
    private ArrayList<Category> mCategories;
    private ArrayList<ItemCondition> mItemConditions;
    private ProgressDialog mProgressDialog;
    public static final String TAG = "FilterScreen-Activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setHolderView(R.layout.activity_filter_screen);

        super.onCreate(savedInstanceState);

        setToolbarTitle(getString(R.string.filter_toolbar_title));
        setNextButtonText(getString(R.string.filter_toolbar_btn_text));

        Bundle bundle = getIntent().getExtras();
        mGroupId = bundle.getInt(ClamourValues.GROUP_ID);

        setViews();
        getCategories();
    }

    private void startDialog(String message) {
        if (mProgressDialog != null) {
            mProgressDialog.setMessage(message);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }
    }

    private void dismissDialog() {
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    private void getCategories() {

        startDialog("Getting categories...");
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.i(TAG, jsonObject.toString());
                if (jsonObject.optString("code").equals(ClamourApiValues.SUCCESS_CODE)) {
                    Log.i(TAG, "Success");
                    Gson gson = new Gson();
                    JSONObject message = jsonObject.optJSONObject("message");
                    mCategories = gson.fromJson(message.optString("categories"),
                            new TypeToken<ArrayList<Category>>() {
                            }.getType());
                    mItemConditions = ItemCondition.setItemCondition();
                    mFilterAdapter.setItems(mCategories, mItemConditions);
                    dismissDialog();
                } else {
                    dismissDialog();
                    AppUtil.showAToast(jsonObject.optString("message"));

                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error trying to log", volleyError);
                volleyError.printStackTrace();
                dismissDialog();
            }
        };

        HashMap<String, String> parameters = new HashMap<String, String>();

        parameters.put(ClamourApiValues.GROUP_ID, String.valueOf(mGroupId));

        String url = ClamourApiValues.createStaticUrlGet(ClamourApiValues.TYPE_CATEGORY, parameters);

        Log.i(TAG, url);

        JsonObjectRequestUtil request = new JsonObjectRequestUtil(Request.Method.GET, url, null, responseListener, errorListener);

        MySingletonUtil.getInstance(this).addToRequestQueue(request);

    }

    private void setViews() {
        mFilterRecyclerView = (RecyclerView) findViewById(R.id.filter_list_view);
        mFilterRecyclerView.setHasFixedSize(true);
        mFilterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFilterAdapter = new FilterAdapter(this, null, null, this);
        mFilterRecyclerView.setAdapter(mFilterAdapter);
        mFilterRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mProgressDialog = new ProgressDialog(this);
    }


    @Override
    protected void nextButtonAction() {

    }

    @Override
    protected void backButtonAction() {
        onBackPressed();
    }

    @Override
    public void conditionsListClicked(View v, int position) {
        mFilterAdapter.notifyDataSetChanged();
        for (int i = 0; i < mItemConditions.size(); i++) {
            if (mItemConditions.get(i).isChecked()) {
                if (i != position) {
                    mItemConditions.get(i).setChecked(false);
                    break;
                }
            }
        }

        mItemConditions.get(position).setChecked(true);
    }

    @Override
    public void categoriesListClicked(View v, int position) {
        if (mCategories.get(position).isChecked())
            mCategories.get(position).setChecked(false);
        else
            mCategories.get(position).setChecked(true);
    }
}
