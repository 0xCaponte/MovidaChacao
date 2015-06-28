package com.reto.chacao.postdetail.holder;

import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import com.reto.chacao.R;
import com.reto.chacao.view.MainHolder;

/**
 * Created by Eduardo Luttinger on 27/05/2015.
 */
public class DetailPostCommentsHolder extends MainHolder {


    private NetworkImageView mPostBuyerProfileImage;

    private TextView mPostBuyerNameText;

    private TextView mPostBuyerCommentText;


    public DetailPostCommentsHolder(View itemView) {
        super(itemView);
        setView(itemView);
    }

    private void setView(View itemView) {

        mPostBuyerProfileImage = (NetworkImageView) itemView.findViewById(R.id.commentBuyerImage);
        mPostBuyerNameText = (TextView) itemView.findViewById(R.id.commentBuyerName);
        mPostBuyerCommentText = (TextView) itemView.findViewById(R.id.commentBuyerText);

    }

    public NetworkImageView getPostBuyerProfileImage() {
        return mPostBuyerProfileImage;
    }

    public void setPostBuyerProfileImage(NetworkImageView postBuyerProfileImage) {
        mPostBuyerProfileImage = postBuyerProfileImage;
    }

    public TextView getPostBuyerNameText() {
        return mPostBuyerNameText;
    }

    public void setPostBuyerNameText(TextView postBuyerNameText) {
        mPostBuyerNameText = postBuyerNameText;
    }

    public TextView getPostBuyerCommentText() {
        return mPostBuyerCommentText;
    }

    public void setPostBuyerCommentText(TextView postBuyerCommentText) {
        mPostBuyerCommentText = postBuyerCommentText;
    }
}
