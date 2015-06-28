package com.reto.chacao.login.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import com.reto.chacao.R;
import com.reto.chacao.abstractcomponents.IntroToolBarActivity;
import com.reto.chacao.beans.Group;
import com.reto.chacao.beans.UserProfile;
import com.reto.chacao.login.adapter.GroupsAdapter;
import com.reto.chacao.main.activity.ClamourMainActivity;
import com.reto.chacao.statics.ClamourApiValues;
import com.reto.chacao.util.AppUtil;
import com.reto.chacao.util.UserUtil;

/**
 * Created by ULISES HARRIS on 25/05/2015.
 */
public class GroupScreenActivity extends IntroToolBarActivity implements View.OnClickListener,
        GroupsAdapter.GroupViewClickListener {

    public static final String TAG = "Groups-Activity";

    private RelativeLayout mGroupListLayout;
    private LinearLayout mGroupEmptyView;

    private RecyclerView mGroupList;
    private GroupsAdapter mGroupsAdapter;

    private TextView mGroupsTitle;

    private Button mGroupButtonRequest;
    private Button mGroupButtonOkay;
    private Button mGroupButtonStartGroup;

    private UserProfile mUserProfile;
    private ArrayList<Group> mGroups;
    private ArrayList<Group> mGroupsTest = new ArrayList<Group>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setHolderView(R.layout.activity_init_groups);

        super.onCreate(savedInstanceState);

        mUserProfile = UserUtil.getUserRegister(this);

        setToolbarTitle(getString(R.string.groups_init_toolbar_text));
        hideNextButton();

        setViews();
        setButtons();
        setGroups();
        getUserGroups();


    }

    private void setGroups() {
        Group group = new Group();
        group.setGroup_id(1);
        group.setGroup_name("Pinecrest Marketplace");
        mGroupsTest.add(group);

        group = new Group();
        group.setGroup_id(2);
        group.setGroup_name("Boston Market");
        mGroupsTest.add(group);

        group = new Group();
        group.setGroup_id(3);
        group.setGroup_name("I love New York");
        mGroupsTest.add(group);
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

    private void setButtons() {
        mGroupButtonRequest = (Button) findViewById(R.id.group_init_request_invite);
        mGroupButtonRequest.setOnClickListener(this);

        mGroupButtonOkay = (Button) findViewById(R.id.group_init_okay_btn);
        mGroupButtonOkay.setOnClickListener(this);

        mGroupButtonStartGroup = (Button) findViewById(R.id.group_init_start_btn);
        mGroupButtonStartGroup.setOnClickListener(this);

    }

    private void setViews() {
        mGroupListLayout = (RelativeLayout) findViewById(R.id.group_init_list);
        mGroupEmptyView = (LinearLayout) findViewById(R.id.group_init_empty_view);

        mGroupsTitle = (TextView) findViewById(R.id.group_init_title);

        mGroupList = (RecyclerView) findViewById(R.id.groups_list);
        mGroupList.setHasFixedSize(true);
        mGroupList.setLayoutManager(new LinearLayoutManager(this));
        mGroupsAdapter = new GroupsAdapter(this, this, mUserProfile, null);
        mGroupList.setAdapter(mGroupsAdapter);
        mGroupList.setItemAnimator(new DefaultItemAnimator());
    }

    private void getUserGroups() {
//        startDialog(getString(R.string.groups_init_dialog));
        mGroupsAdapter.setGroups(mGroupsTest);
        Resources res = getResources();
        String text = String.format(res.getString(R.string.groups_init_title), mGroupsTest.size());
        mGroupsTitle.setText(text);
        mGroupListLayout.setVisibility(View.VISIBLE);

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.i(TAG, jsonObject.toString());
                if (jsonObject.optString("code").equals(ClamourApiValues.SUCCESS_CODE)) {
                    Log.i(TAG, "Success");
                    Gson gson = new Gson();
                    mGroups = gson.fromJson(jsonObject.optString("message"),
                            new TypeToken<ArrayList<Group>>() {
                            }.getType());
                    if (mGroups.size() != 0) {
                        mGroupsAdapter.setGroups(mGroups);
                        Resources res = getResources();
                        String text = String.format(res.getString(R.string.groups_init_title), mGroups.size());
                        mGroupsTitle.setText(text);
                        mGroupEmptyView.setVisibility(View.GONE);
                        mGroupListLayout.setVisibility(View.VISIBLE);
                    } else {
                        mGroupListLayout.setVisibility(View.GONE);
                        mGroupEmptyView.setVisibility(View.VISIBLE);
                    }

                    dismissDialog();
                } else {
                    mGroupsAdapter.setGroups(mGroupsTest);
                    Resources res = getResources();
                    String text = String.format(res.getString(R.string.groups_init_title), mGroupsTest.size());
                    mGroupsTitle.setText(text);
                    mGroupListLayout.setVisibility(View.VISIBLE);
                    dismissDialog();
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

//        String url = ClamourApiValues.createUrl(String.valueOf(mUserProfile.getUserId()),
//                ClamourApiValues.GROUPS_URL, ClamourApiValues.TYPE_USER);
////        Log.i(TAG, url);
//
//        ClamourApiValues.API_AUTH_MAP.put(ClamourApiValues.API_TOKEN, mUserProfile.getApiToken());
//
//
//        JsonObjectRequestUtil request = new JsonObjectRequestUtil(Request.Method.GET, url, null, responseListener, errorListener);
//
//        MySingletonUtil.getInstance(this).addToRequestQueue(request);
    }

    @Override
    protected void nextButtonAction() {

    }

    @Override
    protected void backButtonAction() {
        onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.group_init_request_invite:
                onGroupRequestInvite();
                break;
            case R.id.group_init_okay_btn:
                break;
            case R.id.group_init_start_btn:
                AppUtil.sendIntentEmail(this, getString(R.string.clamour_create_group));
                break;
        }

    }

    private void onGroupRequestInvite() {
//        startDialog(getString(R.string.groups_init_dialog_request));
        AppUtil.runActivityClearTop(ClamourMainActivity.class, GroupScreenActivity.this);
        finish();
        final AtomicInteger counter = new AtomicInteger();
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.i(TAG, jsonObject.toString());
                counter.decrementAndGet();
                if (counter.get() == 0) {
                    dismissDialog();
                    AppUtil.runActivityClearTop(ClamourMainActivity.class, GroupScreenActivity.this);
                    finish();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                dismissDialog();
            }
        };

//        ClamourApiValues.API_AUTH_MAP.put(ClamourApiValues.API_TOKEN, mUserProfile.getApiToken());
//
//        for (Group group : mGroupsTest) {
//            String url = ClamourApiValues.createUrl(String.valueOf(group.getGroup_id()),
//                    ClamourApiValues.GROUP_REQUEST_JOIN_URL, ClamourApiValues.TYPE_GROUP);
//
//            Log.i(TAG, url);
//
//            if (group.isChecked()) {
//                JsonObjectRequestUtil req = new JsonObjectRequestUtil(Request.Method.POST, url,
//                        mUserProfile.toJsonObject(), responseListener, errorListener);
//
//                MySingletonUtil.getInstance(this).addToRequestQueue(req);
//                counter.incrementAndGet();
//            }
//        }
    }

    @Override
    public void groupViewListClicked(View v, int position) {
        if (mGroupsTest.get(position).isChecked())
            mGroupsTest.get(position).setChecked(false);
        else
            mGroupsTest.get(position).setChecked(true);
//        mGroups.get(position).setChecked(true);
    }
}
