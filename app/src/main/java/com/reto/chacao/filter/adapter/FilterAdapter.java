package com.reto.chacao.filter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import com.reto.chacao.R;
import com.reto.chacao.beans.Category;
import com.reto.chacao.beans.ItemCondition;

/**
 * Created by ULISES HARRIS on 28/05/2015.
 */
public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MainViewHolder> {

    private static FilterClickListener mListener;
    Context mContext;
    ArrayList<Category> mCategories;
    ArrayList<ItemCondition> mItemConditions;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CONDITION = 1;
    private static final int TYPE_CATEGORY = 2;

    static int selectedPosition = 0;

    public FilterAdapter(Context context, ArrayList<Category> categories,
                         ArrayList<ItemCondition> itemConditions,
                         FilterClickListener listener) {
        mContext = context;
        mCategories = categories;
        mItemConditions = itemConditions;
        mListener = listener;
    }

    public interface FilterClickListener {

        public void conditionsListClicked(View v, int position);

        public void categoriesListClicked(View v, int position);
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == 5) {
            return TYPE_HEADER;
        } else if (position > 0 & position < 5) {
            return TYPE_CONDITION;
        } else {
            return TYPE_CATEGORY;
        }
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType) {

            case TYPE_HEADER:
                ViewGroup vImage = (ViewGroup) mInflater.inflate(R.layout.filter_header_item, viewGroup, false);
                HeaderViewHolder headerViewHolder = new HeaderViewHolder(vImage);
                return headerViewHolder;
            case TYPE_CONDITION:
                ViewGroup vGroup = (ViewGroup) mInflater.inflate(R.layout.filter_condition_item, viewGroup, false);
                ConditionViewHolder conditionViewHolder = new ConditionViewHolder(vGroup);
                return conditionViewHolder;
            case TYPE_CATEGORY:
                ViewGroup vGroup0 = (ViewGroup) mInflater.inflate(R.layout.filter_category_item, viewGroup, false);
                CategoryViewHolder categoryViewHolder = new CategoryViewHolder(vGroup0);
                return categoryViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MainViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {

            case TYPE_HEADER:
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;

                if (position == 0)
                    headerViewHolder.mHeaderTitle.setText(mContext.getString(R.string.filter_item_condition_header));
                else
                    headerViewHolder.mHeaderTitle.setText(mContext.getString(R.string.filter_category_header));
                break;
            case TYPE_CONDITION:
                ConditionViewHolder conditionViewHolder = (ConditionViewHolder) viewHolder;

                ItemCondition condition = mItemConditions.get(position - 1);
                conditionViewHolder.mConditionName.setText(condition.getName());
                conditionViewHolder.mConditionCheckBox.setChecked(position == selectedPosition);
                conditionViewHolder.mConditionCheckBox.setTag(position);
                break;
            case TYPE_CATEGORY:
                CategoryViewHolder categoryViewHolder = (CategoryViewHolder) viewHolder;

                Category category = mCategories.get(position - mItemConditions.size() - 2);
                categoryViewHolder.mCategoryName.setText(category.getName());
                categoryViewHolder.mCategoryCheckbox.setChecked(category.isChecked());
                break;

        }

    }

    @Override
    public int getItemCount() {
        if (mItemConditions != null && mCategories != null)
            return mItemConditions.size() + mCategories.size() + 2;
        return 0;
    }

    public void setItems(ArrayList<Category> categories, ArrayList<ItemCondition>
            itemConditions) {
        mCategories = categories;
        mItemConditions = itemConditions;
        notifyDataSetChanged();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {
        public MainViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class HeaderViewHolder extends MainViewHolder {

        TextView mHeaderTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mHeaderTitle = (TextView) itemView.findViewById(R.id.filter_header_title);
        }
    }

    public static class CategoryViewHolder extends MainViewHolder implements View.OnClickListener {

        CheckBox mCategoryCheckbox;
        TextView mCategoryName;

        public CategoryViewHolder(View itemView) {
            super(itemView);

            mCategoryCheckbox = (CheckBox) itemView.findViewById(R.id.category_checkbox);
            mCategoryName = (TextView) itemView.findViewById(R.id.category_name);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mCategoryCheckbox.isChecked())
                mCategoryCheckbox.setChecked(false);
            else
                mCategoryCheckbox.setChecked(true);
            mListener.categoriesListClicked(v, getAdapterPosition() - 6);
        }
    }

    public static class ConditionViewHolder extends MainViewHolder implements View.OnClickListener {

        CheckBox mConditionCheckBox;
        TextView mConditionName;

        public ConditionViewHolder(View itemView) {
            super(itemView);
            mConditionCheckBox = (CheckBox) itemView.findViewById(R.id
                    .condition_radio_button);
            mConditionName = (TextView) itemView.findViewById(R.id.condition_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            selectedPosition = (Integer) mConditionCheckBox.getTag();
            mListener.conditionsListClicked(v, getAdapterPosition() - 1);
        }
    }

}
