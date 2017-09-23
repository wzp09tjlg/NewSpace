package com.wuzp.newspace.network;

import com.wuzp.newspace.network.entity.main.GirlsBean;
import com.wuzp.newspace.network.entity.base.HttpBase;
import com.wuzp.newspace.network.entity.main.InfosBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wuzp on 2017/9/17.
 * 定义Retrofit的请求地址
 */
public interface ApiService {

    /*******************************************/
    // 首页模块的地址
    /*******************************************/
    //资讯
    @GET(ApiFinal.URL_HOME_INFO)
    Flowable<HttpBase<InfosBean>> getHomeInfo();

    //新闻
    //娱乐
    //读书
    //我的
    @GET(ApiFinal.URL_HOME)
    Flowable<HttpBase<GirlsBean>> getHome(@Query("num") String num,
                                          @Query("page") String page, @Query("rand")String rand);

    /*******************************************/
    // 娱乐模块的地址
    /*******************************************/

    /*******************************************/
    // 咨询模块的地址
    /*******************************************/

    /*******************************************/
    // 个人中心模块的地址
    /*******************************************/
}
