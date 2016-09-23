package com.apkbus.mobile.presenter;

import android.util.Base64;

import com.apkbus.mobile.apis.LSubscriber;
import com.apkbus.mobile.apis.RxAPI;
import com.apkbus.mobile.apis.UserAPI;
import com.apkbus.mobile.bean.LoginInfo;
import com.apkbus.mobile.bean.MobWrapper;
import com.apkbus.mobile.bean.User;
import com.apkbus.mobile.bean.UserProfile;
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
        if (token == null) {
            mView.bindData(null);
            return;
        }
//        Subscription s = UserAPI.getInstance().getProfileItem(token.getUid(), User.ITEM_NAME)
        Subscription s = UserAPI.getInstance().getProfileItem(token.getUid(), Base64.encodeToString("username".getBytes(),Base64.DEFAULT))
                .subscribe(new LSubscriber<MobWrapper<String>>() {


                    @Override
                    protected void onError(int httpStatusCode, int code) {
                        mView.bindData(null);
                    }

                    @Override
                    public void onNext(MobWrapper<String> stringMobWrapper) {
                        User user = new User();
                        user.setNickname(stringMobWrapper.getResult());
                        mView.bindData(user);
                    }
                });
        mSubscriptions.add(s);
    }

    @Override
    public void setUserProfile(UserProfile item, String value) {
        LoginInfo token = SharedPreferencesHelper.getInstance(mView.getContext()).getToken();
        Subscription subscribe = UserAPI.getInstance().setProfile(token.getUid(), token.getToken(), Base64.encodeToString(item.getValue().getBytes(),Base64.DEFAULT), value)
                .subscribe(new LSubscriber<MobWrapper>() {
                    @Override
                    protected void onError(int httpStatusCode, int code) {
                        mView.bindData(null);
                    }

                    @Override
                    public void onNext(MobWrapper stringMobWrapper) {
                        if ("200".equals(stringMobWrapper.getRetCode())) {
                            initData();
                        }
                    }
                });
        mSubscriptions.add(subscribe);
    }


}
