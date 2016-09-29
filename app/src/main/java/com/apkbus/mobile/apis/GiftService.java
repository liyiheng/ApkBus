package com.apkbus.mobile.apis;

import com.apkbus.mobile.bean.GiftWrapper;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by liyiheng on 16/9/29.
 */

public interface GiftService {
    @GET("data/福利/{count}/{page}")
    Observable<GiftWrapper> getGifts(@Path("count") int count, @Path("page") int page);
}
