package com.apkbus.mobile.ui.activity;

import android.os.Bundle;

import com.apkbus.mobile.BasePresenter;
import com.apkbus.mobile.R;

public class ForgetPasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
    }

    @Override
    BasePresenter getPresenter() {
        return null;
    }
}
