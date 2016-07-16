package com.m520it.blacknews;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.m520it.blacknews.bean.Action;

/**
 * @author michael
 * @time 2016/6/23  21:42
 * @desc ${TODD}
 */
public class WebViewActivity extends Activity {
    private WebView mWebView;
    public static final String ACTION_NAME = "action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        //拿main传过来的url
        Action action = (Action) getIntent().getSerializableExtra(ACTION_NAME);
        String link_url = action.getLink_url();
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(link_url);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
