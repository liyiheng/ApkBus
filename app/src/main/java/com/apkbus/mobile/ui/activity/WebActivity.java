package com.apkbus.mobile.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.webkit.WebView;

import com.apkbus.mobile.R;

public class WebActivity extends BaseActivity {

    public static void loadURL(Context context,String url){
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("URL",url);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        String url = getIntent().getStringExtra("URL");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (TextUtils.isEmpty(url)){
            return;
        }
        WebView webView = (WebView) findViewById(R.id.web_activity_webview);
        webView.loadUrl(url);

    }

}
