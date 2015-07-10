package com.reto.chacao.main.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.reto.chacao.R;
import com.reto.chacao.beans.Comment;
import com.reto.chacao.beans.Post;

import java.util.ArrayList;

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
        holder.mPostConditionItem.setText(post.getCondition().getName());
        holder.mPostFirstImage.setImageResource(Integer.parseInt(post.getMainImageUrl()));
        if (position % 2 == 0) {
            holder.mPostSold.setBackgroundColor(mContext.getResources().getColor(R.color
                    .happening));
        }

        if (comments != null) {
            comment = comments.get(0);
            String commentUserName = comment.getCommenterFirstName() + " " + comment.getCommenterLastName();
            Resources res = mContext.getResources();
            String numberComments ;
            if ( comments.size() > 1 ) {
                numberComments = String.format(res.getString(R.string.home_post_item_comments), comments.size());
            } else {
                numberComments = String.format(res.getString(R.string.home_post_item_comment), comments.size());
            }
            holder.mPostCommentNumber.setText(numberComments);
        } else {
            holder.mPostComment.setVisibility(View.INVISIBLE);
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

        public LinearLayout mItemLayout;
        public ImageView mPostUserImage;
        public TextView mPostTitle;
        public TextView mPostDescription;
        public ImageView mPostFirstImage;
        public TextView mPostCommentNumber;
        public RelativeLayout mPostComment;
        public TextView mPostConditionItem;
        public View mPostSold;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mItemLayout = (LinearLayout) itemLayoutView.findViewById(R.id.item_layout);
            mPostUserImage = (ImageView) itemLayoutView.findViewById(R.id.user_image);
            mPostTitle = (TextView) itemLayoutView.findViewById(R.id.post_title);
            mPostDescription = (TextView) itemLayoutView.findViewById(R.id.post_description);
            mPostConditionItem = (TextView) itemLayoutView.findViewById(R.id.post_condition);
            mPostSold = (View) itemLayoutView.findViewById(R.id.sold_view2);
            mPostFirstImage = (ImageView) itemLayoutView.findViewById(R.id.post_main_image);
            mPostCommentNumber = (TextView) itemLayoutView.findViewById(R.id.text_more_comments);
            mPostComment = (RelativeLayout) itemLayoutView.findViewById(R.id.btn_post_more_comments);
            mItemLayout.setOnClickListener(this);
            mPostFirstImage.setOnClickListener(this);
            mPostComment.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_layout:
                    mListener.postTwoColumnViewListClicked(v, getAdapterPosition());
                    break;
                case R.id.post_main_image:
                    mListener.postTwoColumnViewListClicked(v, getAdapterPosition());
                    break;
                case R.id.btn_post_more_comments:
                    mListener.postTwoMoreCommentClick(v, getAdapterPosition());
                    break;
            }

        }
    }
}
