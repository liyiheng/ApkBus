package com.apkbus.mobile.apis;


import com.apkbus.mobile.bean.Bean;
import com.apkbus.mobile.bean.BeanWrapper;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liyiheng on 16/6/24.
 */
public class RxAPI {
    private final Retrofit retrofit;
    private final TopicService topicService;
    private static RxAPI api;

    public static RxAPI getInstance() {
        if (api == null) {
            synchronized (RxAPI.class) {
                if (api == null) {
                    api = new RxAPI();
                }
            }
        }
        return api;
    }

    private RxAPI() {
        new OkHttpClient.Builder().addInterceptor(chain -> {
            //Request request = chain.request();

            return null;
        });
        retrofit = new Retrofit.Builder()
                .baseUrl(TopicService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //.client()
                .build();
        topicService = retrofit.create(TopicService.class);
    }

    public Observable<BeanWrapper<Bean>> getPopularArticles() {
        return topicService.getBlogs(TopicService.BLOG_POPULAR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BeanWrapper<Bean>> getLatestArticles() {
        return topicService.getBlogs(TopicService.BLOG_LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BeanWrapper<Bean>> getDemos() {
        return topicService.getArticles(TopicService.DEMOS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BeanWrapper<Bean>> getAwesomeSource() {
        return topicService.getArticles(TopicService.AWESOME_SOURCE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BeanWrapper<Bean>> getWeeklyPopular() {
        return topicService.getArticles(TopicService.WEEKLY_POPULAR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
