package com.reto.chacao.postdetail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.reto.chacao.R;
import com.reto.chacao.beans.AppBean;
import com.reto.chacao.beans.DetailPost;
import com.reto.chacao.beans.PostComment;
import com.reto.chacao.postdetail.holder.DetailPostCommentsHolder;
import com.reto.chacao.postdetail.holder.DetailPostMainHolder;
import com.reto.chacao.util.AppUtil;
import com.reto.chacao.view.MainHolder;

/**
 * Created by Eduardo Luttinger on 27/05/2015.
 */
public class DetailPostAdapter extends RecyclerView.Adapter<MainHolder> {

    private static final String TAG = "DetailPostAdapter";

    private static final int TYPE_DETAIL_POST = 0;

    private static final int TYPE_DETAIL_POST_COMMENT = 1;

    private static final String POST_COMMENT = "POST_COMMENT_BEAN";

    private static final String DETAIL_POST_BEAN = "DETAIL_POST_BEAN";

    private LayoutInflater mLayoutInflater;

    private Context mContext;

    private List<DetailPost> mListOfDetailPost;

    private List<PostComment> mListOfComments;

    private List<AppBean> mListOfObjects;

    public DetailPostMainHolder mDetailPostMainHolder;

    public DetailPostCommentsHolder mDetailPostCommentsHolder;


    public DetailPostAdapter(Context context, List<DetailPost> listOfDetailPost, List<PostComment> listOfComments) {
        mContext = context;
        mListOfDetailPost = listOfDetailPost;
        mListOfComments = listOfComments;
        mListOfObjects = AppUtil.mergeCommunBeansInASingleList(mListOfDetailPost,mListOfComments);
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getItemViewType(int position) {

        int viewId = TYPE_DETAIL_POST;

        if (position != TYPE_DETAIL_POST) {

            viewId = TYPE_DETAIL_POST_COMMENT;
        }

        return viewId;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MainHolder holder = (MainHolder) null;
        View view = null;

        switch (viewType) {

            case TYPE_DETAIL_POST:
                view = mLayoutInflater.inflate(R.layout.fragment_post_detail, parent, false);
                holder = (mDetailPostMainHolder = new DetailPostMainHolder(view,
                        mListOfDetailPost.get(0).getPostImages()));
                break;

            case TYPE_DETAIL_POST_COMMENT:
                view = mLayoutInflater.inflate(R.layout.fragment_post_detail_comment, parent, false);
                holder = (mDetailPostCommentsHolder = new DetailPostCommentsHolder(view));
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {

        if (holder instanceof DetailPostCommentsHolder) {
            String comment = ((PostComment) mListOfObjects.get(position)).getComment();
            comment = comment == null ? "" : comment;
            if (!"".equals(comment)) {
                ((DetailPostCommentsHolder) holder).getPostBuyerCommentText().setText(comment);
            }


        } else if (holder instanceof DetailPostMainHolder) {
            DetailPostMainHolder mainHolder = (DetailPostMainHolder) holder;
            DetailPost mDetailPost = mListOfDetailPost.get(position);
            mainHolder.getCommentCountText().setText(mListOfComments.size()+" "+AppUtil.getString(R.string
                    .commentCount));
            mainHolder.getPostOwnerNameText().setText(mDetailPost.getPostOwnerName());
            mainHolder.getPostOwnerCommentText().setText(mDetailPost.getPostOwnerComment());
        }

    }


    @Override
    public int getItemCount() {
        return mListOfObjects.size();
    }


    public void addItemToRecyclerView(AppBean bean) {
        if(bean instanceof PostComment){
            mListOfComments.add((PostComment)bean);
        }
        mListOfObjects.add(bean);
    }


}
