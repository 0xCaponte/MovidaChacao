package com.reto.chacao.login.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.reto.chacao.R;

/**
 * Created by ULISES HARRIS on 19/05/2015.
 */
public class IntroductionAdapter extends BaseAdapter {

    Context mContext;
    Integer[] mImages;
    private LayoutInflater mInflater = null;

    public IntroductionAdapter(Context context, Integer[] images) {
        mContext = context;
        mImages = images;
        mInflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        if (mImages != null)
            return mImages.length;
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Holder holder;
        if (convertView == null) {

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            v = mInflater.inflate(R.layout.introduction_image_item, parent, false);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new Holder();
            holder.mImage = (ImageView) v.findViewById(R.id.image);

            /************  Set holder with LayoutInflater ************/
            v.setTag(holder);
        } else
            holder = (Holder) v.getTag();

        holder.mImage.setImageResource(mImages[position]);
        return v;
    }

    /**
     * ****** Create a holder Class to contain inflated xml file elements ********
     */
    public static class Holder {
        public ImageView mImage;

    }
}
