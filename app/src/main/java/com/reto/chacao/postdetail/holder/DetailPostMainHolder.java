package com.reto.chacao.postdetail.holder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.reto.chacao.beans.Post;
import com.viewpagerindicator.CirclePageIndicator;

import com.reto.chacao.R;
import com.reto.chacao.postdetail.adapter.DetailPostViewPagerAdapter;
import com.reto.chacao.util.AppUtil;
import com.reto.chacao.view.MainHolder;

/**
 * Created by Eduardo Luttinger on 27/05/2015.
 */
public class DetailPostMainHolder extends MainHolder implements View.OnClickListener {

    private static final String TAG = "DetailPostImageHolder";

    private ViewPager mImageViewPager;

    private CirclePageIndicator mCirclePageIndicator;

    private DetailPostViewPagerAdapter mPostDetailImageAdapter;

    private NetworkImageView mPostOwnerProfileImage;

    private TextView mPostOwnerNameText;

    private TextView mPostOwnerCommentText;

    private TextView mCommentCountText;


    private Context mContext;


    public DetailPostMainHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        setViews(itemView);
        setBehaviors(itemView);
    }

    public DetailPostMainHolder(View itemView,Post data) {
        super(itemView);
        mContext = itemView.getContext();
        setViews(itemView,data);
        setBehaviors(itemView);
    }

    public DetailPostMainHolder(View itemView, int[] images) {
        super(itemView);
        mContext = itemView.getContext();
        setViews(itemView);
        setBehaviors(itemView, images);
    }

    private void setViews(View itemView) {
        mImageViewPager = (ViewPager) itemView.findViewById(R.id.viewPager);
        mCirclePageIndicator = (CirclePageIndicator) itemView.findViewById(R.id.viewPagerIndicator);
        mPostOwnerNameText = (TextView) itemView.findViewById(R.id.commentOwnerName);
        mPostOwnerCommentText = (TextView) itemView.findViewById(R.id.commentOwnerText);
        mCommentCountText = (TextView) itemView.findViewById(R.id.detailPostCommentCountText);

    }

    private void setViews(View itemView,Post data) {
        mImageViewPager = (ViewPager) itemView.findViewById(R.id.viewPager);
        mCirclePageIndicator = (CirclePageIndicator) itemView.findViewById(R.id.viewPagerIndicator);
        mPostOwnerNameText = (TextView) itemView.findViewById(R.id.commentOwnerName);
        mPostOwnerCommentText = (TextView) itemView.findViewById(R.id.commentOwnerText);
        mCommentCountText = (TextView) itemView.findViewById(R.id.detailPostCommentCountText);

    }

    private void setBehaviors(View itemView) {
        mPostDetailImageAdapter = new DetailPostViewPagerAdapter(itemView.getContext());
        mImageViewPager.setAdapter(mPostDetailImageAdapter);
        mCirclePageIndicator.setViewPager(mImageViewPager);
    }

    private void setBehaviors(View itemView, int[] images) {
        mPostDetailImageAdapter = new DetailPostViewPagerAdapter(itemView.getContext(), images);
        mImageViewPager.setAdapter(mPostDetailImageAdapter);
        mCirclePageIndicator.setViewPager(mImageViewPager);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }



    public NetworkImageView getPostOwnerProfileImage() {
        return mPostOwnerProfileImage;
    }

    public TextView getPostOwnerNameText() {
        return mPostOwnerNameText;
    }

    public TextView getPostOwnerCommentText() {
        return mPostOwnerCommentText;
    }

    public TextView getCommentCountText() {
        return mCommentCountText;
    }
}
