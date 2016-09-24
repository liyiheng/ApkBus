package com.apkbus.mobile.presenter;

import com.apkbus.mobile.apis.MobError;
import com.apkbus.mobile.constract.ArticleContract;
import com.apkbus.mobile.apis.LSubscriber;
import com.apkbus.mobile.apis.RxAPI;
import com.apkbus.mobile.bean.BeanWrapper;
import com.apkbus.mobile.bean.Blog;
import com.apkbus.mobile.bean.FirstBean;
import com.apkbus.mobile.utils.ACache;
import com.apkbus.mobile.utils.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
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

    private ArticleContract.View mView;
    private int SECTION_INDEX;
    private CompositeSubscription mSubscriptions;

    public ArticlePresenter(ArticleContract.View mView, int sectionIndex, CompositeSubscription s) {
        this.mView = mView;
        this.SECTION_INDEX = sectionIndex;
        mSubscriptions = s;
        aCache = ACache.get(mView.getContext().getApplicationContext());
    }

    private boolean firstTime = true;

    @Override
    public void initData() {
        final Gson gson = new Gson();
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

        // Load data from cache at the first time.
        if (firstTime) {
            firstTime = false;
            JSONObject jsonObject = aCache.getAsJSONObject("DATA" + SECTION_INDEX);
            if (jsonObject != null) {
                if (blogObserver != null) {
                    BeanWrapper<Blog> data = gson.fromJson(jsonObject.toString(), new TypeToken<BeanWrapper<Blog>>() {
                    }.getType());
                    mView.updateData2(data.getRes());
                } else if (commonObserver != null) {
                    BeanWrapper<FirstBean> data = gson.fromJson(jsonObject.toString(), new TypeToken<BeanWrapper<FirstBean>>() {
                    }.getType());
                    mView.updateData(data.getRes());
                }
            }
            // Stop SwipeRefreshLayout refreshing.
            mView.updateData2(null);
            mView.updateData(null);

            boolean autoRenew = SharedPreferencesHelper.getInstance(mView.getContext()).needAutoRenew();
            if (!autoRenew) return;
        }


        // Request data from server.
        if (blogObserver != null) {
            Subscription subscribe = blogObserver
                    .observeOn(Schedulers.io())
                    .doOnNext((BeanWrapper<Blog> blogBeanWrapper) ->
                            // Cache data in IO-thread.
                            aCache.put("DATA" + SECTION_INDEX, gson.toJson(blogBeanWrapper)))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new LSubscriber<BeanWrapper<Blog>>() {


                        @Override
                        protected void onError(int httpStatusCode, MobError error) {
                            mView.showMsg(error.getMsg());
                        }

                        @Override
                        public void onNext(BeanWrapper<Blog> data) {
                            mView.updateData2(data.getRes());
                        }
                    });
            mSubscriptions.add(subscribe);
        } else if (commonObserver != null) {
            Subscription subscribe = commonObserver
                    .observeOn(Schedulers.io())
                    .doOnNext((BeanWrapper<FirstBean> firstBeanBeanWrapper) ->
                            aCache.put("DATA" + SECTION_INDEX, gson.toJson(firstBeanBeanWrapper)))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new LSubscriber<BeanWrapper<FirstBean>>() {

                        @Override
                        protected void onError(int httpStatusCode, MobError error) {
                            mView.showMsg(error.getMsg());
                        }

                        @Override
                        public void onNext(BeanWrapper<FirstBean> data) {
                            mView.updateData(data.getRes());
                        }
                    });
            mSubscriptions.add(subscribe);
        } else {
            // setRefreshing(false)
            mView.updateData2(null);
        }
    }
}
