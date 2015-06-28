package com.reto.chacao.login.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.reto.chacao.R;
import com.reto.chacao.abstractcomponents.IntroToolBarActivity;
import com.reto.chacao.util.AppUtil;

/**
 * Created by ULISES HARRIS on 15/06/2015.
 */
public class TermsWebviewActivity extends IntroToolBarActivity {

    private static final String TAG = "TermsWebView-Activity";
    private WebView myWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setHolderView(R.layout.activity_terms_webview);

        super.onCreate(savedInstanceState);

        final ProgressDialog pd = ProgressDialog.show(this, "", "Please wait...", true);

        myWebView = (WebView) findViewById(R.id.webView);
        hideNextButton();
        String url =
                getIntent().getDataString().replace("myscheme://", "https://");
        Log.d(TAG, url);
        myWebView.getSettings().setJavaScriptEnabled(true); // enable javascript

        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pd.dismiss();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                AppUtil.showAToast(description);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                Log.d(TAG, url);
                if (url.equals("mailto:support@clamour.net")) {
                    AppUtil.sendIntentEmail(TermsWebviewActivity.this, "Question to Clamour");
                }
            }
        });
        // do something with this
        myWebView.loadUrl(url);

    }

    @Override
    protected void nextButtonAction() {

    }

    @Override
    protected void backButtonAction() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
