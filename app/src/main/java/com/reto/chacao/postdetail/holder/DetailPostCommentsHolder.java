package com.reto.chacao.postdetail.holder;

import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import com.reto.chacao.R;
import com.reto.chacao.database.DataBaseHelper;
import com.reto.chacao.model.Comment;
import com.reto.chacao.view.MainHolder;

import java.util.List;

/**
 * Created by Eduardo Luttinger on 27/05/2015.
 */
public class DetailPostCommentsHolder extends MainHolder {


    private ImageView mPostBuyerProfileImage;

    private TextView mPostBuyerNameText;

    private TextView mPostBuyerCommentText;


    public DetailPostCommentsHolder(View itemView) {
        super(itemView);
        setView(itemView);
    }

    public DetailPostCommentsHolder(View itemView,List<Comment> cm) {
        super(itemView);
        setView(itemView);
        setComment(cm);
    }

    private void setComment(List<Comment> cm){
        for(Comment comme : cm){
            mPostBuyerProfileImage.setImageResource(comme.getId());
            mPostBuyerNameText.setText(comme.getFirstname()+" "+comme.getLastname());
            mPostBuyerCommentText.setText(comme.getText());

        }

    }
//    setImageResource(Integer.parseInt(post.getMainImageUrl()));

    private void setView(View itemView) {

        mPostBuyerProfileImage = (ImageView) itemView.findViewById(R.id.commentBuyerImage);
        mPostBuyerNameText = (TextView) itemView.findViewById(R.id.commentBuyerName);
        mPostBuyerCommentText = (TextView) itemView.findViewById(R.id.commentBuyerText);

    }

    public ImageView getPostBuyerProfileImage() {
        return mPostBuyerProfileImage;
    }

    public void setPostBuyerProfileImage(ImageView postBuyerProfileImage) {
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
