package com.apkbus.mobile.apis;


import com.apkbus.mobile.bean.BeanWrapper;
import com.apkbus.mobile.bean.Bean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by liyiheng on 16/6/24.
 */
public interface TopicService {

    //int WEEKLY_POPULAR = 30;
    int WEEKLY_POPULAR = 43;
    //int AWSOME_SOURCE = 31;
    int AWSOME_SOURCE = 46;
    //int DEMOS = 35;
    int DEMOS = 42;
/*



热门博文 33
最新博文 32
精品源码 31
一周热点 http://www.apkbus.com/api.php?mod=js&bid=30&type=json  43
实例教程 35,42


 */
    /**
     * @param bid Should be one of {@link #WEEKLY_POPULAR,#AWSOME_SOURCE,#DEMOS}
     */
    @GET("api.php?mod=js&type=json")
    Observable<BeanWrapper<Bean>> getArticles(@Query("bid") int bid);

    String DOMAIN = "http://www.apkbus.com/";


    //int BLOG_LATEST = 32;
    int BLOG_LATEST = 44;
    //int BLOG_POPULAR = 33;
    int BLOG_POPULAR = 45;

    /**
     * @param bid Should be one of {@link #BLOG_LATEST,#BLOG_POPULAR}
     */
    @GET("api.php?mod=js&type=json")
    Observable<BeanWrapper<Bean>> getBlogs(@Query("bid") int bid);
}
