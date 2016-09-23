package com.apkbus.mobile.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.apkbus.mobile.BasePresenter;
import com.apkbus.mobile.R;
import com.apkbus.mobile.apis.LSubscriber;
import com.apkbus.mobile.apis.UserAPI;
import com.apkbus.mobile.bean.MobWrapper;
import com.apkbus.mobile.utils.LToast;
import com.apkbus.mobile.utils.MD5Util;
import com.apkbus.mobile.utils.RegexUtil;

import rx.Observable;
import rx.Subscription;

public class RegisterActivity extends BaseActivity {

    private EditText mEditTextEmail;
    private EditText mEditTextPwd;
    private EditText mEditTextUsername;
    private View confirm;
    private LSubscriber<MobWrapper> subscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    @Override
    BasePresenter getPresenter() {
        return null;
    }

    private void initView() {
        mEditTextEmail = (EditText) findViewById(R.id.register_email);
        mEditTextPwd = ((EditText) findViewById(R.id.register_password));
        mEditTextUsername = ((EditText) findViewById(R.id.register_username));
        confirm = findViewById(R.id.register_ok);
        confirm.setOnClickListener(v -> regist());
        findViewById(R.id.register_back).setOnClickListener(v -> finish());
        subscriber = new LSubscriber<MobWrapper>() {
            @Override
            protected void onError(int httpStatusCode, int code) {
                loadingDialog.dismiss();
            }

            @Override
            public void onNext(MobWrapper mobWrapper) {
                loadingDialog.dismiss();
                LToast.show(mContext, "注册成功!");
                finish();
            }
        };
    }

    private void regist() {
        // Store values at the time of the login attempt.
        String email = mEditTextEmail.getText().toString();
        String password = mEditTextPwd.getText().toString();
        String username = mEditTextUsername.getText().toString();

        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(password)) {
            cancel = true;
            focusView = mEditTextPwd;
            mEditTextPwd.setError("请输入密码");
        }
        if (password.length() < 6) {
            cancel = true;
            focusView = mEditTextPwd;
            mEditTextPwd.setError("密码至少6位");
        }
        if (TextUtils.isEmpty(username)) {
            cancel = true;
            focusView = mEditTextUsername;
            mEditTextUsername.setError("请输入用户名");
        }
        if (!RegexUtil.isEmail(email)) {
            cancel = true;
            focusView = mEditTextEmail;
            mEditTextEmail.setError("邮箱格式不正确");
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            loadingDialog.show();
            password = MD5Util.getMD5(password);
            Subscription subscribe = UserAPI
                    .getInstance()
                    .register(username, password, email)
                    .subscribe(subscriber);
            compositeSubscription.add(subscribe);
        }
    }
}
