package com.apkbus.mobile.presenter;

import android.support.annotation.IntDef;

import com.apkbus.mobile.apis.MobError;
import com.apkbus.mobile.bean.event.ScrollSignal;
import com.apkbus.mobile.constract.ArticleContract;
import com.apkbus.mobile.apis.LSubscriber;
import com.apkbus.mobile.apis.RxAPI;
import com.apkbus.mobile.bean.BeanWrapper;
import com.apkbus.mobile.bean.Bean;
import com.apkbus.mobile.utils.ACache;
import com.apkbus.mobile.utils.RxBus;
import com.apkbus.mobile.utils.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by liyiheng on 16/9/19.
 */
public class ArticlePresenter implements ArticleContract.Presenter {
    private final ACache aCache;

    @Override
    public void subscribe() {
        // todo
    }

    @Override
    public void unSubscribe() {
        // todo
        //mView = null;

    }

    /*
    switch (position) {
                case 0:
                    return "热门博文";
                case 1:
                    return "最新博文";
                case 2:
                    return "精品源码";
                case 3:
                    return "一周热点";
                case 4:
                    return "实例教程";
            }
     */
    public static final int TYPE_BLOG_HOT = 0;
    public static final int TYPE_BLOG_NEW = 1;
    public static final int TYPE_AWSOME_CODE = 2;
    public static final int TYPE_WEEKLY_HOT = 3;
    public static final int TYPE_DEMOS = 4;

    @IntDef(value = {TYPE_AWSOME_CODE, TYPE_BLOG_HOT, TYPE_BLOG_NEW, TYPE_WEEKLY_HOT, TYPE_DEMOS})
    public @interface ListType {
    }

    private ArticleContract.View mView;
    private int mType;
    private CompositeSubscription mSubscriptions;

    public ArticlePresenter(ArticleContract.View view, @ListType int type, CompositeSubscription s) {
        this.mView = view;
        this.mType = type;
        mSubscriptions = s;
        aCache = ACache.get(mView.getContext().getApplicationContext());
        Subscription subscription = RxBus
                .getInstance()
                .toSubscription(ScrollSignal.class, (ScrollSignal scrollSignal)
                        -> {
                    if (scrollSignal.tabPosition - 1 == mType) {
                        mView.scroll2Top();
                    }
                });
        mSubscriptions.add(subscription);
    }

    private boolean firstTime = true;

    @Override
    public void initData() {
        final Gson gson = new Gson();
        RxAPI api = RxAPI.getInstance();
        Observable<BeanWrapper<Bean>> observer = null;
        switch (mType) {
            case TYPE_BLOG_HOT:
                observer = api.getPopularArticles();
                break;
            case TYPE_BLOG_NEW:
                observer = api.getLatestArticles();
                break;
            case TYPE_AWSOME_CODE:
                observer = api.getAwsomeSource();
                break;
            case TYPE_WEEKLY_HOT:
                observer = api.getWeeklyPopular();
                break;
            case TYPE_DEMOS:
                observer = api.getDemos();
                break;
        }

        // Load data from cache at the first time.
        if (firstTime) {
            firstTime = false;
            JSONObject jsonObject = aCache.getAsJSONObject("DATA" + mType);
            if (jsonObject != null && observer != null) {
                BeanWrapper<Bean> data = gson.fromJson(jsonObject.toString(), new TypeToken<BeanWrapper<Bean>>() {
                }.getType());
                mView.updateData(data.getRes());
            } else {
                // Stop SwipeRefreshLayout refreshing.
                mView.updateData(null);
            }
            boolean autoRenew = SharedPreferencesHelper.getInstance(mView.getContext()).needAutoRenew();
            if (!autoRenew) return;
        }


        // Request data from server.
        if (observer != null) {
            Subscription subscribe = observer
                    .observeOn(Schedulers.io())
                    .doOnNext((BeanWrapper<Bean> blogBeanWrapper) ->
                            // Cache data in IO-thread.
                            aCache.put("DATA" + mType, gson.toJson(blogBeanWrapper)))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new LSubscriber<BeanWrapper<Bean>>() {

                        @Override
                        protected void onError(int httpStatusCode, MobError error) {
                            mView.showMsg(error.getMsg());
                        }

                        @Override
                        public void onNext(BeanWrapper<Bean> data) {
                            mView.updateData(data.getRes());
                        }
                    });
            mSubscriptions.add(subscribe);
        } else {
            // setRefreshing(false)
            mView.updateData(null);
        }
    }
}
