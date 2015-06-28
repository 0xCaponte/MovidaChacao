package com.reto.chacao.login.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.reto.chacao.R;
import com.reto.chacao.beans.Group;
import com.reto.chacao.beans.UserProfile;

/**
 * Created by ULISES HARRIS on 25/05/2015.
 */
public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder> {

    public static final String TAG = "Groups-Adapter";

    private ArrayList<Group> mGroupList;
    private Context mContext;
    private UserProfile mUserProfile;
    private static GroupViewClickListener mGroupViewClickListener;

    private Integer[] Image = {
            R.drawable.group_1, R.drawable.group_2,
            R.drawable.group_3
    };

    public GroupsAdapter(Context context, GroupViewClickListener groupViewClickListener,
                         UserProfile userProfile, ArrayList<Group> groups) {
        this.mGroupList = groups;
        this.mContext = context;
        this.mUserProfile = userProfile;
        this.mGroupViewClickListener = groupViewClickListener;
    }


    public interface GroupViewClickListener {

        public void groupViewListClicked(View v, int position);
    }

    @Override
    public GroupsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_list_item, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Group group = mGroupList.get(position);

        holder.mGroupName.setText(group.getGroup_name());
        holder.mGroupImage.setImageResource(Image[position]);
//        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//
//                if (jsonObject.optString("code").equals(ClamourApiValues.SUCCESS_CODE)) {
//
//                    ImageLoader mImageLoader = MySingletonUtil.getInstance(mContext).getImageLoader();
//                    String imageUrl = AppUtil.getValuesFromApiMessageObject("url", jsonObject);
//
//                    holder.mGroupImage.setImageUrl(imageUrl, mImageLoader);
//
//                } else {
//                    AppUtil.showAToast(jsonObject.optString("message"));
//
//                }
//
//            }
//        };
//
//        Response.ErrorListener responseError = new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Log.e(TAG, "Error trying to log", volleyError);
//                volleyError.printStackTrace();
//
//            }
//        };
//
//        String url = ClamourApiValues.createUrl(String.valueOf(group.getGroup_id()),
//                ClamourApiValues.IMAGE_URL, ClamourApiValues.TYPE_GROUP);
//
//        Log.i(TAG, url);
//
//        ClamourApiValues.API_AUTH_MAP.put(ClamourApiValues.API_TOKEN, mUserProfile.getApiToken());
//
//        JsonObjectRequestUtil req = new JsonObjectRequestUtil(Request.Method.GET, url,
//                mUserProfile.toJsonObject(), responseListener, responseError);
//
//        MySingletonUtil.getInstance(mContext).addToRequestQueue(req);


    }

    @Override
    public int getItemCount() {
        if (mGroupList != null)
            return mGroupList.size();
        return 0;
    }

    public void setGroups(ArrayList<Group> groups) {
        if (groups != null) {
            mGroupList = groups;
            notifyDataSetChanged();
        }
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mGroupName;
        public CheckBox mGroupCheckbox;
        public ImageView mGroupImage;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mGroupName = (TextView) itemLayoutView.findViewById(R.id.group_name);
            mGroupImage = (ImageView) itemLayoutView.findViewById(R.id.group_image);
            mGroupCheckbox = (CheckBox) itemLayoutView.findViewById(R.id.group_checkbox);
            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mGroupCheckbox.isChecked())
                mGroupCheckbox.setChecked(false);
            else
                mGroupCheckbox.setChecked(true);
            mGroupViewClickListener.groupViewListClicked(v, this.getAdapterPosition());
        }
    }
}
