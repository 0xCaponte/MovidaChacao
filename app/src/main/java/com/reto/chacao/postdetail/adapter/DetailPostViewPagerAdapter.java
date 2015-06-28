package com.reto.chacao.postdetail.adapter;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.reto.chacao.R;
import com.reto.chacao.postdetail.dialog.ZoomableImageDialog;
import com.reto.chacao.statics.ClamourValues;

/**
 * Created by Eduardo Luttinger on 28/05/2015.
 */
public class DetailPostViewPagerAdapter extends PagerAdapter implements View.OnClickListener {

    private static final String TAG = "PagerAdapter";
    int[] mResources = new int[]{
            R.drawable.item1,
            R.drawable.item12,
            R.drawable.item13
    };
    private Context mContext;
    private Activity mActivity;
    private Fragment mFragment;
    private View mContainer;
    private LayoutInflater mLayoutInflater;

    public DetailPostViewPagerAdapter(Context context) {

        mContext = context;

        mActivity = (Activity) context;

        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {

        mContainer = mLayoutInflater.inflate(R.layout.fragment_post_detail_image_view, container, false);

        ImageView imageView = (ImageView) mContainer.findViewById(R.id.detailPostImage);

        imageView.setOnClickListener(this);

        imageView.setImageResource(mResources[position]);

        imageView.setTag(position);

        container.addView(mContainer);

        return mContainer;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.detailPostImage:
                int zoomImage = mResources[(Integer) view.getTag()];
                Bundle params = new Bundle();
                params.putInt(ClamourValues.IMAGE_TO_ZOOM_ID, zoomImage);
                ZoomableImageDialog dialog = new ZoomableImageDialog();
                dialog.setArguments(params);
                dialog.show(mActivity.getFragmentManager(),"ZoomableImage");
        }
    }


}
