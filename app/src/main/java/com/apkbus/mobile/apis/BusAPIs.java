package com.apkbus.mobile.apis;


public interface BusAPIs {

    String DOMAIN = "http://www.apkbus.com/";
    /*

      @GET("api.php?mod=js&bid=35&type=json")
    Observable<BeanWrapper<FirstBean>> getDemos();


     */
    int WEEKLY_POPULAR = 30;
    int AWSOME_SOURCE = 31;

    int DEMOS = 35;


    /**
     * //should use a new bean
     * Different from others !!!!!!!!!!!!!!!
     */
    int LATEST_ARTICLES = 32;
    int POPULAR_ARTICLES = 33;

}
