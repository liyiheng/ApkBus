package com.apkbus.mobile.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.apkbus.mobile.BasePresenter;
import com.apkbus.mobile.R;

import net.youmi.android.AdManager;
import net.youmi.android.listener.Interface_ActivityListener;
import net.youmi.android.listener.OffersWallDialogListener;

public class SplashActivity extends BaseActivity implements Interface_ActivityListener, OffersWallDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AdManager.getInstance(this).init("db0d1ec3bdc4a2ac", "aa68f62fc2d2f8f6", false, false);
        //OffersManager.getInstance(this).showOffersWall(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onResume() {
        super.onResume();
//        LoginInfo token = SharedPreferencesHelper.getInstance(mContext).getToken();
//        if (token == null || TextUtils.isEmpty(token.getUid())) {
//            startActivity(new Intent(this, LoginActivity.class));
//        } else {
        startActivity(new Intent(this, MainActivity.class));
//        }

    }

    @Override
    BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onActivityDestroy(Context context) {

    }

    @Override
    public void onDialogClose() {

    }
}
