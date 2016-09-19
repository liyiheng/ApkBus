package com.apkbus.mobile.apis;


import com.apkbus.mobile.bean.BeanWrapper;
import com.apkbus.mobile.bean.Blog;
import com.apkbus.mobile.bean.FirstBean;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
        new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                return null;
            }
        });
        retrofit = new Retrofit.Builder()
                .baseUrl(TopicService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //.client()
                .build();
        topicService = retrofit.create(TopicService.class);
    }

    public Observable<BeanWrapper<Blog>> getPopularArticles() {
        return topicService.getBlogs(TopicService.BLOG_POPULAR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BeanWrapper<Blog>> getLatestArticles() {
        return topicService.getBlogs(TopicService.BLOG_LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BeanWrapper<FirstBean>> getDemos() {
        return topicService.getArticles(TopicService.DEMOS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BeanWrapper<FirstBean>> getAwsomeSource() {
        return topicService.getArticles(TopicService.AWSOME_SOURCE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<BeanWrapper<FirstBean>> getWeeklyPopular() {
        return topicService.getArticles(TopicService.WEEKLY_POPULAR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
