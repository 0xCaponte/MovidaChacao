package com.reto.chacao.main.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import com.reto.chacao.R;
import com.reto.chacao.beans.Category;
import com.reto.chacao.main.adapter.CategorySpinnerAdapter;
import com.reto.chacao.statics.ClamourApiValues;
import com.reto.chacao.util.AppUtil;
import com.reto.chacao.util.JsonObjectRequestUtil;
import com.reto.chacao.util.MySingletonUtil;
import com.reto.chacao.util.StringUtil;

/**
 * Created by ULISES HARRIS on 02/06/2015.
 */
public class SearchPostScreenActivity extends ActionBarActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    private static final String TAG = "SearchPost-Activity";

    private TextView mToolbarTitle;
    private EditText mSearchTitle;
    private Spinner mSearchCategory;
    private EditText mSearchPrice;
    private EditText mSearchLocation;
    private EditText mSearchComment;
    private ProgressDialog mProgressDialog;
    private ArrayList<Category> mCategories;
    private CategorySpinnerAdapter mCategoryAdapter;
    private Button mSearchPost;
    private ImageButton mClosePost;
    private String mTitle;
    private String mLocation;
    private String mPrice;
    private String mComment;
    private Category mCategory;
    private ImageButton mHelp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post);

        Toolbar toolbar = (Toolbar) findViewById(R.id.search_post_toolbar);
        if (toolbar != null)
            setSupportActionBar(toolbar);

        setToolBarViews();
        setViews();
        setButton();
        getCategories();
    }

    private void setButton() {
        mSearchPost = (Button) findViewById(R.id.btn_post_item);
        mSearchPost.setText(getString(R.string.search_post_button));
        mSearchPost.setOnClickListener(this);

        mClosePost = (ImageButton) findViewById(R.id.add_post_btn_close);
        mClosePost.setOnClickListener(this);

        mHelp = (ImageButton) findViewById(R.id.add_post_btn_help);
        mHelp.setOnClickListener(this);
    }

    private void setViews() {
        mSearchTitle = (EditText) findViewById(R.id.search_post_title);
        mSearchCategory = (Spinner) findViewById(R.id.search_post_category);
        mSearchPrice = (EditText) findViewById(R.id.search_post_price);
        mSearchPrice.addTextChangedListener(new CurrencyTextWatcher());
        mSearchLocation = (EditText) findViewById(R.id.search_post_location);
        mSearchComment = (EditText) findViewById(R.id.search_post_comment);

    }

    private void setToolBarViews() {
        mToolbarTitle = (TextView) findViewById(R.id.add_post_title);
        mToolbarTitle.setText(getString(R.string.search_post_toolbar_title));
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

//        startDialog("Getting categories...");
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
                    mCategoryAdapter = new CategorySpinnerAdapter(SearchPostScreenActivity.this, mCategories);
                    // apply the Adapter:
                    mSearchCategory.setAdapter(mCategoryAdapter);
//                    dismissDialog();
                } else {
//                    dismissDialog();
                    AppUtil.showAToast(jsonObject.optString("message"));

                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Error trying to log", volleyError);
                volleyError.printStackTrace();
//                dismissDialog();
            }
        };

        HashMap<String, String> parameters = new HashMap<String, String>();

        parameters.put(ClamourApiValues.GROUP_ID, "2");

        String url = ClamourApiValues.createStaticUrlGet(ClamourApiValues.TYPE_CATEGORY, parameters);

        Log.i(TAG, url);

        JsonObjectRequestUtil request = new JsonObjectRequestUtil(Request.Method.GET, url, null, responseListener, errorListener);

        MySingletonUtil.getInstance(this).addToRequestQueue(request);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_post_btn_close:
                onBackPressed();
                AppUtil.hideKeyboard(this);
                break;
            case R.id.btn_post_item:
                onSearchItem();
                break;
            case R.id.add_post_btn_help:
                AppUtil.sendIntentEmail(this, "Question to Clamour");
                break;
        }
    }

    private void onSearchItem() {
        mTitle = mSearchTitle.getText().toString().trim();
        mLocation = mSearchLocation.getText().toString().trim();
        mPrice = mSearchPrice.getText().toString().trim();
        mComment = mSearchComment.getText().toString().trim();

        if (validateFields()) {
            AppUtil.showAToast("Show Fee dialog");
        }

    }

    private boolean validateFields() {
        boolean valid = true;
        if ("".equals(mTitle)) {
            mSearchTitle.setError("This field is required");
            return false;
        } else if ("".equals(mLocation) & !StringUtil.isValidNumber(mLocation)) {
            mSearchLocation.setError("This field is required");
            return false;
//        } else if (mCategory == null) {
//            AppUtil.showAToast("You need to select a category for the item");
//            return false;
        } else if ("".equals(mPrice)) {
            mSearchPrice.setError("This field is required");
            return false;
        } else if ("".equals(mComment)) {
            mSearchComment.setError("This field is required");
            return false;
        }

        return valid;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mCategory = mCategories.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

