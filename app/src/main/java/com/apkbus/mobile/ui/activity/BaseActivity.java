package com.apkbus.mobile.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by liyiheng on 16/9/17.
 */
public class BaseActivity extends AppCompatActivity {
    /**
     * Managing all the Subscriptions
     */
    protected CompositeSubscription compositeSubscription;
    protected BaseActivity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeSubscription = new CompositeSubscription();
        mContext = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //To prevent memory leaks
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
    }
}
