package com.apkbus.mobile.presenter;

import com.apkbus.mobile.constract.ArticleContract;
import com.apkbus.mobile.apis.LSubscriber;
import com.apkbus.mobile.apis.RxAPI;
import com.apkbus.mobile.bean.BeanWrapper;
import com.apkbus.mobile.bean.Blog;
import com.apkbus.mobile.bean.FirstBean;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by liyiheng on 16/9/19.
 */
public class ArticlePresenter implements ArticleContract.Presenter {
    @Override
    public void subscribe() {
        // todo
    }

    @Override
    public void unSubscribe() {
        // todo
        //mView = null;

    }

    private ArticleContract.View mView;
    private int SECTION_INDEX;
    private CompositeSubscription mSubscriptions;

    public ArticlePresenter(ArticleContract.View mView, int sectionIndex,CompositeSubscription s) {
        this.mView = mView;
        this.SECTION_INDEX = sectionIndex;
        mSubscriptions = s;
    }

    @Override
    public void initData() {
        RxAPI api = RxAPI.getInstance();
        Observable<BeanWrapper<Blog>> blogObserver = null;
        Observable<BeanWrapper<FirstBean>> commonObserver = null;
        switch (SECTION_INDEX) {
            case 0:
                blogObserver = api.getPopularArticles();
                break;
            case 1:
                blogObserver = api.getLatestArticles();
                break;
            case 2:
                commonObserver = api.getAwsomeSource();
                break;
            case 3:
                commonObserver = api.getWeeklyPopular();
                break;
            case 4:
                commonObserver = api.getDemos();
                break;
        }
        if (blogObserver != null) {
            Subscription subscribe = blogObserver.subscribe(new LSubscriber<BeanWrapper<Blog>>() {
                @Override
                protected void onError(int httpStatusCode, int code) {

                }

                @Override
                public void onNext(BeanWrapper<Blog> data) {
                    mView.updateData2(data.getRes());
                }
            });
            mSubscriptions.add(subscribe);
        } else if (commonObserver != null) {
            Subscription subscribe = commonObserver.subscribe(new LSubscriber<BeanWrapper<FirstBean>>() {
                @Override
                protected void onError(int httpStatusCode, int code) {

                }

                @Override
                public void onNext(BeanWrapper<FirstBean> data) {
                    mView.updateData(data.getRes());
                }
            });
            mSubscriptions.add(subscribe);
        }else {
            // setRefreshing(false)
            mView.updateData2(null);
        }
    }
}
