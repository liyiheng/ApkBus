package com.apkbus.mobile.presenter;

import com.apkbus.mobile.apis.GiftAPI;
import com.apkbus.mobile.apis.LSubscriber;
import com.apkbus.mobile.apis.MobError;
import com.apkbus.mobile.bean.GiftWrapper;
import com.apkbus.mobile.constract.GiftContract;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by liyiheng on 16/9/29.
 */

public class GiftPresenter implements GiftContract.Presenter {
    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscriptions.unsubscribe();
    }

    private GiftContract.View mView;
    private CompositeSubscription mSubscriptions;

    public GiftPresenter(GiftContract.View view) {
        mView = view;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void initData() {
        currentPage = 0;
        Subscription subscribe = GiftAPI.getInstance().getGifts(currentPage).subscribe(new LSubscriber<GiftWrapper>() {
            @Override
            protected void onError(int httpStatusCode, MobError error) {
                mView.updateData(null);
            }

            @Override
            public void onNext(GiftWrapper giftWrapper) {
                mView.updateData(giftWrapper == null ? null : giftWrapper.getResult());
            }
        });
        mSubscriptions.add(subscribe);

    }

    private int currentPage;

    @Override
    public void loadMore() {
        Subscription subscribe = GiftAPI.getInstance().getGifts(++currentPage).subscribe(new LSubscriber<GiftWrapper>() {
            @Override
            protected void onError(int httpStatusCode, MobError error) {
                mView.addData(null);
            }

            @Override
            public void onNext(GiftWrapper giftWrapper) {
                mView.addData(giftWrapper == null ? null : giftWrapper.getResult());
            }
        });
        mSubscriptions.add(subscribe);
    }
}
