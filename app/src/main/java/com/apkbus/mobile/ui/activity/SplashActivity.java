package com.apkbus.mobile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.apkbus.mobile.BasePresenter;
import com.apkbus.mobile.R;
import com.apkbus.mobile.bean.LoginInfo;
import com.apkbus.mobile.utils.SharedPreferencesHelper;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        LoginInfo token = SharedPreferencesHelper.getInstance(mContext).getToken();
        if (token == null || TextUtils.isEmpty(token.getUid())) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }

    @Override
    BasePresenter getPresenter() {
        return null;
    }
}
