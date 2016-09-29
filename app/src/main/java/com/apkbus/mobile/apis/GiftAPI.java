package com.apkbus.mobile.apis;

import com.apkbus.mobile.bean.GiftWrapper;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liyiheng on 16/9/29.
 */

public class GiftAPI {
    private final Retrofit retrofit;
    private final GiftService mService;
    private static GiftAPI api;

    public static GiftAPI getInstance() {
        if (api == null) {
            synchronized (RxAPI.class) {
                if (api == null) {
                    api = new GiftAPI();
                }
            }
        }
        return api;
    }

    private GiftAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //.client()
                .build();
        mService = retrofit.create(GiftService.class);
    }

    public Observable<GiftWrapper> getGifts(int count, int page) {
        return mService.getGifts(count, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<GiftWrapper> getGifts(int page) {
        return mService.getGifts(30, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
