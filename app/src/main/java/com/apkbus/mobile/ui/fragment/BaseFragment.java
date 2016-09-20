package com.apkbus.mobile.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by liyiheng on 16/9/19.
 */
public class BaseFragment extends Fragment {
    protected View layout;
    protected CompositeSubscription mSubscriptions;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscriptions!=null){
            mSubscriptions.unsubscribe();
        }
    }
}
