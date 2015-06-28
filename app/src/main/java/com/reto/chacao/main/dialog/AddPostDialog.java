package com.reto.chacao.main.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.reto.chacao.R;
import com.reto.chacao.main.activity.AddPostScreenActivity;
import com.reto.chacao.main.activity.SearchPostScreenActivity;
import com.reto.chacao.util.AppUtil;

/**
 * Created by ULISES HARRIS on 29/05/2015.
 */
public class AddPostDialog extends Dialog implements View.OnClickListener {

    private Button mAddPostButton;
    private Button mSearchPostButton;

    public AddPostDialog(Context context, int theme) {
        super(context, theme);
    }


    public AddPostDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window_add_post);

        setButtons();

    }

    private void setButtons() {
        mAddPostButton = (Button) findViewById(R.id.btn_add_post);
        mAddPostButton.setOnClickListener(this);

        mSearchPostButton = (Button) findViewById(R.id.btn_search_post);
        mSearchPostButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_post:
                AppUtil.runActivity(AddPostScreenActivity.class, getContext());
                dismiss();
                break;
            case R.id.btn_search_post:
                AppUtil.runActivity(SearchPostScreenActivity.class, getContext());
                dismiss();
                break;

        }

    }
}
