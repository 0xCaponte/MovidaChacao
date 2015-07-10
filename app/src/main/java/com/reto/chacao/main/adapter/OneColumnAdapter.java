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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    SimpleDateFormat mDateFormatter;

    public OneColumnAdapter(Context context, PostViewClickListener listener, ArrayList<Post> posts) {
        this.mContext = context;
        this.mListener = listener;
        this.mPosts = posts;
        mDateFormatter = new SimpleDateFormat("dd MMM.");
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
        holder.mPostDate.setText(DateUtil.getTimeText(post.getCreated()));
        if ( post.getCreated().equals(new Date("01/01/1999")) ) {
            holder.mPostConditionItem.setText("Fijo");
        } else {
            holder.mPostConditionItem.setText(mDateFormatter.format(post.getCreated()));
        }
        holder.mPostFirstImage.setImageResource(Integer.parseInt(post.getMainImageUrl()));



        if (comments != null) {
            comment = comments.get(0);
            String commentUserName = comment.getCommenterFirstName() + " " + comment.getCommenterLastName();
            holder.mPostCommentUserName.setText(commentUserName);
            holder.mPostCommentBody.setText(comment.getBody());
            holder.mPostCommentDate.setText(DateUtil.getTimeText(comment.getCreated()));
            Resources res = mContext.getResources();
            holder.mPostCommentUserImage.setImageResource(Integer.parseInt(comment.getImage()));
            String numberComments;
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
        if (mPosts != null)
            return mPosts.size();
        return 0;
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public LinearLayout mItemLayout;
        public ImageView mPostUserImage;
        public TextView mPostUserName;
        public TextView mPostTitle;
        public TextView mPostDescription;
        public TextView mPostDate;
        public TextView mPostConditionItem;
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
            mItemLayout = (LinearLayout) itemLayoutView.findViewById(R.id.item_layout);
            mPostUserImage = (ImageView) itemLayoutView.findViewById(R.id.user_image);
            mPostUserName = (TextView) itemLayoutView.findViewById(R.id.user_name);
            mPostTitle = (TextView) itemLayoutView.findViewById(R.id.post_title);
            mPostDescription = (TextView) itemLayoutView.findViewById(R.id.post_description);
            mPostDate = (TextView) itemLayoutView.findViewById(R.id.post_time);
            mPostConditionItem = (TextView) itemLayoutView.findViewById(R.id.post_condition);
            mPostSold = (View) itemLayoutView.findViewById(R.id.sold_view2);
            mPostFirstImage = (ImageView) itemLayoutView.findViewById(R.id.post_main_image);
            mPostCommentUserName = (TextView) itemLayoutView.findViewById(R.id.user_name_comment);
            mPostCommentBody = (TextView) itemLayoutView.findViewById(R.id.comment_text);
            mPostCommentDate = (TextView) itemLayoutView.findViewById(R.id.comment_time);
            mPostCommentNumber = (TextView) itemLayoutView.findViewById(R.id.text_more_comments);
            mPostComment = (RelativeLayout) itemLayoutView.findViewById(R.id
                    .btn_post_more_comments);
            mItemLayout.setOnClickListener(this);
            mPostComment.setOnClickListener(this);
            mPostFirstImage.setOnClickListener(this);
            mPostCommentUserImage = (ImageView) itemLayoutView.findViewById(R.id.user_image_comment);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_layout:
                    mListener.postViewListClicked(v, getAdapterPosition());
                    break;
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
