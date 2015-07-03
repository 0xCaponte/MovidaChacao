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
import com.reto.chacao.util.DateUtil;

/**
 * Created by ULISES HARRIS on 27/05/2015.
 */
public class OneColumnAdapter extends RecyclerView.Adapter<OneColumnAdapter.ViewHolder> {

    public static final String TAG = "OneColumn-Adapter";

    private Context mContext;
    private static PostViewClickListener mListener;
    private ArrayList<Post> mPosts;

    public OneColumnAdapter(Context context, PostViewClickListener listener, ArrayList<Post> posts) {
        this.mContext = context;
        this.mListener = listener;
        this.mPosts = posts;
    }

    public interface PostViewClickListener {

        public void postViewListClicked(View v, int position);

        public void postCommentLikeClick(View v, int position);

        public void postMoreCommentClick(View v, int position);

    }

    @Override
    public OneColumnAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.one_column_item, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OneColumnAdapter.ViewHolder holder, int position) {
        Post post = mPosts.get(position);
        ArrayList<Comment> comments = post.getComments();
        Comment comment;

        String postUserName = post.getUser().getFirstName() + " " + post.getUser().getFamilyName();
        holder.mPostUserName.setText(postUserName);
        holder.mPostTitle.setText(post.getTitle());
        holder.mPostDescription.setText(post.getDescription());
        holder.mPostPrice.setText(post.getPrice());
        holder.mPostDate.setText(DateUtil.getTimeText(post.getCreated()));
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
            holder.mPostCommentUserName.setText(commentUserName);
            holder.mPostCommentBody.setText(comment.getBody());
            holder.mPostCommentDate.setText(DateUtil.getTimeText(comment.getCreated()));
            Resources res = mContext.getResources();
            String numberComments = String.format(res.getString(R.string.home_post_item_comments), comments.size());
            holder.mPostCommentNumber.setText(numberComments);
        }

    }

    @Override
    public int getItemCount() {
        if (mPosts != null)
            return mPosts.size();
        return 0;
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mPostUserImage;
        public TextView mPostUserName;
        public TextView mPostTitle;
        public TextView mPostDescription;
        public TextView mPostDate;
        public TextView mPostPrice;
        public TextView mPostConditionItem;
        public TextView mPostStatus;
        public View mPostSold;
        public ImageView mPostFirstImage;
        public ImageView mPostCommentUserImage;
        public TextView mPostCommentUserName;
        public TextView mPostCommentBody;
        public TextView mPostCommentDate;
        public TextView mPostCommentNumber;
        public RelativeLayout mPostComment;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mPostUserImage = (ImageView) itemLayoutView.findViewById(R.id.user_image);
            mPostUserName = (TextView) itemLayoutView.findViewById(R.id.user_name);
            mPostTitle = (TextView) itemLayoutView.findViewById(R.id.post_title);
            mPostDescription = (TextView) itemLayoutView.findViewById(R.id.post_description);
            mPostPrice = (TextView) itemLayoutView.findViewById(R.id.post_price);
            mPostDate = (TextView) itemLayoutView.findViewById(R.id.post_time);
            mPostConditionItem = (TextView) itemLayoutView.findViewById(R.id.post_condition);
            mPostStatus = (TextView) itemLayoutView.findViewById(R.id.post_status);
            mPostSold = (View) itemLayoutView.findViewById(R.id.sold_view);
            mPostFirstImage = (ImageView) itemLayoutView.findViewById(R.id.post_main_image);
            mPostCommentUserName = (TextView) itemLayoutView.findViewById(R.id.user_name_comment);
            mPostCommentBody = (TextView) itemLayoutView.findViewById(R.id.comment_text);
            mPostCommentDate = (TextView) itemLayoutView.findViewById(R.id.comment_time);
            mPostCommentNumber = (TextView) itemLayoutView.findViewById(R.id.text_more_comments);
            mPostComment = (RelativeLayout) itemLayoutView.findViewById(R.id
                    .btn_post_more_comments);
            mPostComment.setOnClickListener(this);
            mPostFirstImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.post_main_image:
                    mListener.postViewListClicked(v, getAdapterPosition());
                    break;
                case R.id.btn_post_more_comments:
                    mListener.postMoreCommentClick(v, getAdapterPosition());
                    break;
            }
        }
    }
}
