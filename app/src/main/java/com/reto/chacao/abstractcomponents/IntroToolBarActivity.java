package com.reto.chacao.abstractcomponents;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.reto.chacao.R;

/**
 * Created by Eduardo Luttinger on 21/05/2015.
 */
public abstract class IntroToolBarActivity extends AppCompatActivity {

    private ImageView mBackButton;
    private TextView mTitle;
    private TextView mNextButton;
    private Integer layout;
    protected ProgressDialog mProgressDialog;

    protected abstract void nextButtonAction();

    protected abstract void backButtonAction();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(this.layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        mBackButton = (ImageView) findViewById(R.id.toolbar_btn_back_navigation);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mNextButton = (TextView) findViewById(R.id.toolbar_next_button);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.progress_dialog_message));
        manageComponentsEvent();
    }

    /**
     * handle events of all the componets available on the activity
     */
    private void manageComponentsEvent() {

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonAction();
            }
        });
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextButtonAction();
            }
        });
    }

    protected void setToolbarTitle(String title) {
        mTitle.setText(title);
    }

    protected void setHolderView(Integer layout) {
        this.layout = layout;
    }

    protected void hideNextButton() {
        mNextButton.setVisibility(View.INVISIBLE);
    }

    protected void setNextButtonText(String text) {
        mNextButton.setText(text);
    }

    protected void setProgressDialogMessage(String message) {
        mProgressDialog.setMessage(message);
    }

}
