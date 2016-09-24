package com.apkbus.mobile.presenter;

import android.util.Log;

import com.apkbus.mobile.apis.LSubscriber;
import com.apkbus.mobile.apis.MobError;
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
    private final UserAPI mUserAPI;
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
        mUserAPI = UserAPI.getInstance();
    }

    @Override
    public void initData() {
        LoginInfo token = SharedPreferencesHelper.getInstance(mView.getContext()).getToken();
        if (token == null) {
            mView.bindData(null);
            return;
        }
        String username = mUserAPI.encodeData(UserProfile.NICKNAME.getValue());
//        Subscription s = UserAPI.getInstance().getProfileItem(token.getUid(), User.ITEM_NAME)
        Subscription s = mUserAPI.getProfileItem(token.getUid(), username)
                .subscribe(new LSubscriber<MobWrapper<String>>() {


                    @Override
                    protected void onError(int httpStatusCode, MobError error) {
                        mView.bindData(null);
                        mView.showMsg(error.getMsg());
                    }

                    @Override
                    public void onNext(MobWrapper<String> stringMobWrapper) {
                        if (!"200".equals(stringMobWrapper.getRetCode())) {
                            Log.e("MainPresenter", "UnExcepted:" + stringMobWrapper.getRetCode());
                            Log.e("MainPresenter", "Message:   " + stringMobWrapper.getMsg());
                        }
                        User user = new User();
                        user.setNickname(mUserAPI.decodeData(stringMobWrapper.getResult()));
                        mView.bindData(user);
                    }
                });
        mSubscriptions.add(s);
    }

    @Override
    public void setUserProfile(UserProfile item, String value) {
        LoginInfo token = SharedPreferencesHelper.getInstance(mView.getContext()).getToken();
        Subscription subscribe = mUserAPI
                .setProfile(token.getUid(),
                        token.getToken(),
                        mUserAPI.encodeData(item.getValue()),
                        mUserAPI.encodeData(value))
                .subscribe(new LSubscriber<MobWrapper>() {

                    @Override
                    protected void onError(int httpStatusCode, MobError error) {
                        mView.bindData(null);
                        mView.showMsg(error.getMsg());
                    }

                    @Override
                    public void onNext(MobWrapper stringMobWrapper) {
                        if ("200".equals(stringMobWrapper.getRetCode())) {
                            initData();
                            mView.showMsg("设置成功");
                        }
                    }
                });
        mSubscriptions.add(subscribe);
    }


}
