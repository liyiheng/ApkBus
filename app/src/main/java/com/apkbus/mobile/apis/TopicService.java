package com.apkbus.mobile.apis;


import com.apkbus.mobile.bean.BeanWrapper;
import com.apkbus.mobile.bean.FirstBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by liyiheng on 16/6/24.
 */
public interface TopicService {
    //
    //"http://www.apkbus.com/api.php?mod=js&bid=28&type=json";
    //  ?mod=js&bid=28&type=json
/*

最新博文http://www.apkbus.com/api.php?mod=js&bid=32&type=json

热门博文http://www.apkbus.com/api.php?mod=js&bid=33&type=json
精品源码http://www.apkbus.com/api.php?mod=js&bid=31&type=json
一周热点http://www.apkbus.com/api.php?mod=js&bid=30&type=json
 */


    @GET("api.php?mod=js&bid=35&type=json")
    Observable<BeanWrapper<FirstBean>> getDemos();

    @GET("api.php?mod=js&bid=32&type=json")
    Observable<BeanWrapper<FirstBean>> getLatestArticles();

    @GET("api.php?mod=js&bid=30&type=json")
    Observable<BeanWrapper<FirstBean>> getWeeklyPopular();

    @GET("api.php?mod=js&bid=31&type=json")
    Observable<BeanWrapper<FirstBean>> getAwsomeSrc();

    @GET("api.php?mod=js&bid=33&type=json")
    Observable<BeanWrapper<FirstBean>> getPopularArticles();

    //@GET("api.php")
    //Observable<BeanWrapper<FirstBean>> getPopularTopics(@Query("page") int page);

    /**
     * todo ----------------------------------
     * 32,33 ,should use a new bean
     * @param bid Can't be 32,33!!!!!
     * @return
     */
    @GET("api.php?mod=js&type=json")
    Observable<BeanWrapper<FirstBean>> getArticles(@Query("bid") int bid);

}
