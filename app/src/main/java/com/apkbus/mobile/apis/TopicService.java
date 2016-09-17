package com.apkbus.mobile.apis;


import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by liyiheng on 16/6/24.
 */
public interface TopicService {
    @GET("topics/popular")
    Observable<TempResult<TempBean>> getPopularTopics(@Query("page") int page);

    @GET("topics")
    Observable<TempResult<TempBean>> getLatestTopics(@Query("page") int page);

    @GET("users/follows/topics")
    Observable<TempResult<TempBean>> getFollowedTopics(@Query("page") int page, @Header("Authorization") String base64);
}
