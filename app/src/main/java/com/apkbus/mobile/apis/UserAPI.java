package com.apkbus.mobile.apis;

import com.apkbus.mobile.BusApp;
import com.apkbus.mobile.bean.LoginInfo;
import com.apkbus.mobile.bean.MobWrapper;
import com.apkbus.mobile.utils.SharedPreferencesHelper;

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
 * Created by liyiheng on 16/9/23.
 */

public class UserAPI {
    String DOMAIN = "http://apicloud.mob.com/user/";
    private final Retrofit retrofit;
    private final UserService userService;
    private static UserAPI api;

    public static UserAPI getInstance() {
        if (api == null) {
            synchronized (RxAPI.class) {
                if (api == null) {
                    api = new UserAPI();
                }
            }
        }
        return api;
    }

    private UserAPI() {
        new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                return null;
            }
        });
        retrofit = new Retrofit.Builder()
                .baseUrl(DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //.client()
                .build();
        userService = retrofit.create(UserService.class);
    }

    /**
     * MobWrapper.getUid()
     */
    public Observable<MobWrapper> register(String username, String password, String email) {
        return userService.register(Constants.MOBAPI_KEY, username, password, email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MobWrapper<LoginInfo>> login(String username, String password) {
        return userService.login(Constants.MOBAPI_KEY, username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<MobWrapper<String>> getProfileItem(String uid, String itemName) {
        return userService.getProfileItem(Constants.MOBAPI_KEY, uid, itemName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<MobWrapper> setProfile(String uid,String token, String itemName,String itemValue) {
        return userService.setProfile(Constants.MOBAPI_KEY,uid,token,itemName,itemValue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
