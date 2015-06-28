package com.reto.chacao.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.reto.chacao.R;
import com.reto.chacao.abstractcomponents.IntroToolBarActivity;
import com.reto.chacao.main.activity.MovidaMainActivity;
import com.reto.chacao.util.AppUtil;

/**
 * Created by ULISES HARRIS on 15/06/2015.
 */
public class TermsAndConditionActivity extends IntroToolBarActivity implements View.OnClickListener {


    private TextView mTermsConditions;
    private Button mContinue;
    private Button mLoginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setHolderView(R.layout.activity_terms_and_conditions);
        super.onCreate(savedInstanceState);

        hideNextButton();
        setViews();
    }

    private void setViews() {
        mTermsConditions = (TextView) findViewById(R.id.terms_conditions_text);
        Spannable s = (Spannable) Html.fromHtml(getString(R.string.terms_and_conditions));
        for (URLSpan u : s.getSpans(0, s.length(), URLSpan.class)) {
            s.setSpan(new UnderlineSpan() {
                public void updateDrawState(TextPaint tp) {
                    tp.setUnderlineText(false);
                }
            }, s.getSpanStart(u), s.getSpanEnd(u), 0);
        }
        mTermsConditions.setText(s);
        mTermsConditions.setMovementMethod(LinkMovementMethod.getInstance());

        mContinue = (Button) findViewById(R.id.terms_conditions_button);
        mContinue.setOnClickListener(this);

    }

    @Override
    protected void nextButtonAction() {

    }

    @Override
    protected void backButtonAction() {
        onBackPressed();
    }

    @Override
    public void onClick(View v) {

        AppUtil.runActivity(MovidaMainActivity.class, this);

    }
}
