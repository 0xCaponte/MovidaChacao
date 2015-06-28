package com.reto.chacao.main.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import com.reto.chacao.R;
import com.reto.chacao.beans.Comment;
import com.reto.chacao.beans.Post;

/**
 * Created by ULISES HARRIS on 27/05/2015.
 */
public class TwoColumnAdapter extends RecyclerView.Adapter<TwoColumnAdapter.ViewHolder> {

    public static final String TAG = "TwoColumn-Adapter";

    private Context mContext;
    private static PostTwoColumnViewClickListener mListener;
    private ArrayList<Post> mPosts;

    public TwoColumnAdapter(Context context, PostTwoColumnViewClickListener listener, ArrayList<Post> posts) {
        this.mContext = context;
        this.mListener = listener;
        this.mPosts = posts;
    }

    public interface PostTwoColumnViewClickListener {

        public void postTwoColumnViewListClicked(View v, int position);

        public void postTwoCommentLikeClick(View v, int position);

        public void postTwoMoreCommentClick(View v, int position);
    }

    @Override
    public TwoColumnAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.two_columns_item, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TwoColumnAdapter.ViewHolder holder, int position) {

        Post post = mPosts.get(position);
        ArrayList<Comment> comments = post.getComments();
        Comment comment;


        holder.mPostTitle.setText(post.getTitle());
        holder.mPostDescription.setText(post.getDescription());
        holder.mPostPrice.setText(post.getPrice());
        holder.mPostConditionItem.setText(post.getCondition().getName());
        if (position % 2 == 0) {
            holder.mPostStatus.setText("Sold");
            holder.mPostSold.setBackgroundColor(mContext.getResources().getColor(R.color
                    .item_sold));
        } else {
            holder.mPostStatus.setVisibility(View.INVISIBLE
            );

        }

        if (comments != null) {
            comment = comments.get(0);
            String commentUserName = comment.getCommenterFirstName() + " " + comment.getCommenterLastName();
            Resources res = mContext.getResources();
            String numberComments = String.format(res.getString(R.string.home_post_item_comments), comments.size());
            holder.mPostCommentNumber.setText(numberComments);
        }

    }

    @Override
    public int getItemCount() {
        if (mPosts != null) {
            return mPosts.size();
        }
        return 0;
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mPostUserImage;
        public TextView mPostTitle;
        public TextView mPostDescription;
        public ImageView mPostFirstImage;
        public TextView mPostCommentNumber;
        public RelativeLayout mPostComment;
        public ImageView mPostCommentLike;
        public TextView mPostPrice;
        public TextView mPostConditionItem;
        public TextView mPostStatus;
        public View mPostSold;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            mPostUserImage = (ImageView) itemLayoutView.findViewById(R.id.user_image);
            mPostTitle = (TextView) itemLayoutView.findViewById(R.id.post_title);
            mPostDescription = (TextView) itemLayoutView.findViewById(R.id.post_description);
            mPostPrice = (TextView) itemLayoutView.findViewById(R.id.post_price);
            mPostConditionItem = (TextView) itemLayoutView.findViewById(R.id.post_condition);
            mPostStatus = (TextView) itemLayoutView.findViewById(R.id.post_status);
            mPostSold = (View) itemLayoutView.findViewById(R.id.sold_view);
            mPostFirstImage = (ImageView) itemLayoutView.findViewById(R.id.post_main_image);
            mPostCommentNumber = (TextView) itemLayoutView.findViewById(R.id.text_more_comments);
            mPostComment = (RelativeLayout) itemLayoutView.findViewById(R.id.btn_post_more_comments);
            mPostCommentLike = (ImageButton) itemLayoutView.findViewById(R.id.btn_post_first_comment_like);
            mPostFirstImage.setOnClickListener(this);
            mPostCommentLike.setOnClickListener(this);
            mPostComment.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.post_main_image:
                    mListener.postTwoColumnViewListClicked(v, getAdapterPosition());
                    break;
                case R.id.btn_post_first_comment_like:
                    mListener.postTwoCommentLikeClick(v, getAdapterPosition());
                    break;
                case R.id.btn_post_more_comments:
                    mListener.postTwoMoreCommentClick(v, getAdapterPosition());
                    break;
            }

        }
    }
}
