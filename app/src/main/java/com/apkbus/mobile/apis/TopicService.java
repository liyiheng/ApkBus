package com.apkbus.mobile.apis;


import com.apkbus.mobile.bean.BeanWrapper;
import com.apkbus.mobile.bean.Blog;
import com.apkbus.mobile.bean.FirstBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by liyiheng on 16/6/24.
 */
public interface TopicService {

    int WEEKLY_POPULAR = 30;
    int AWSOME_SOURCE = 31;
    int DEMOS = 35;

    /**
     * @param bid Should be one of {@link #WEEKLY_POPULAR,#AWSOME_SOURCE,#DEMOS}
     */
    @GET("api.php?mod=js&type=json")
    Observable<BeanWrapper<FirstBean>> getArticles(@Query("bid") int bid);

    String DOMAIN = "http://www.apkbus.com/";


    int BLOG_LATEST = 32;
    int BLOG_POPULAR = 33;

    /**
     * @param bid Should be one of {@link #BLOG_LATEST,#BLOG_POPULAR}
     */
    @GET("api.php?mod=js&type=json")
    Observable<BeanWrapper<Blog>> getBlogs(@Query("bid") int bid);
}
