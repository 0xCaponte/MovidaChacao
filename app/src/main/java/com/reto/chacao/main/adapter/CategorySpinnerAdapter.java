package com.reto.chacao.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.reto.chacao.R;
import com.reto.chacao.beans.Category;

/**
 * Created by ULISES HARRIS on 02/06/2015.
 */
public class CategorySpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<Category> mCategories;
    private Context mContext;

    public CategorySpinnerAdapter(Context context, ArrayList<Category> categories) {
        this.mContext = context;
        this.mCategories = categories;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (mCategories != null)
            return mCategories.size();
        return 0;
    }

    @Override
    public Category getItem(int position) {
        if (mCategories != null)
            return mCategories.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView text;
        if (convertView != null) {
            // Re-use the recycled view here!
            text = (TextView) convertView;
        } else {
            // No recycled view, inflate the "original" from the platform:
            text = (TextView) layoutInflater.inflate(
                    android.R.layout.simple_dropdown_item_1line, parent, false
            );

        }
        text.setTextColor(mContext.getResources().getColor(R.color.colorPrimary
        ));
        text.setText(mCategories.get(position).getName());
        return text;
    }
}
