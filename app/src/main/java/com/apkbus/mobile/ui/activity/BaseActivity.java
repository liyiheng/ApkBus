package com.apkbus.mobile.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.apkbus.mobile.BasePresenter;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by liyiheng on 16/9/17.
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    /**
     * Managing all the Subscriptions
     */
    protected CompositeSubscription compositeSubscription;
    protected BaseActivity mContext;
    protected MaterialDialog loadingDialog;
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getPresenter();
        compositeSubscription = new CompositeSubscription();
        mContext = this;
        loadingDialog = new MaterialDialog.Builder(this)
                .progress(true, 50)
                .content("请稍候...")
                .cancelable(false)
                .progressIndeterminateStyle(false).build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //To prevent memory leaks
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }

    abstract T getPresenter();
}
