package com.apkbus.mobile.presenter;

import com.apkbus.mobile.apis.LSubscriber;
import com.apkbus.mobile.apis.UserAPI;
import com.apkbus.mobile.bean.LoginInfo;
import com.apkbus.mobile.bean.MobWrapper;
import com.apkbus.mobile.bean.User;
import com.apkbus.mobile.constract.MainContract;
import com.apkbus.mobile.utils.SharedPreferencesHelper;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by liyiheng on 16/9/23.
 */

public class MainPresenter implements MainContract.Presenter {
    private final CompositeSubscription mSubscriptions;
    private MainContract.View mView;

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscriptions.unsubscribe();
    }

    public MainPresenter(MainContract.View view) {
        this.mView = view;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void initData() {
        LoginInfo token = SharedPreferencesHelper.getInstance(mView.getContext()).getToken();
        if (token == null) return;
        Subscription s = UserAPI.getInstance().getProfileItem(token.getUid(), "username")
                .subscribe(new LSubscriber<MobWrapper<String>>() {


                    @Override
                    protected void onError(int httpStatusCode, int code) {

                    }

                    @Override
                    public void onNext(MobWrapper<String> stringMobWrapper) {
                            User user = new User();
                            user.setUsername(stringMobWrapper.getResult());
                            mView.bindData(user);
                    }
                });
        mSubscriptions.add(s);
    }
}
