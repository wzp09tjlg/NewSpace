package com.wuzp.newspace.network;

import com.wuzp.newspace.network.entity.base.HttpBase;
import com.wuzp.newspace.network.entity.main.InfosBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;

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

    //地域新闻
    @GET(ApiFinal.URL_HOME_NEW_AREA)
    Flowable<HttpBase<InfosBean>> getHomeAreaNews();

    //实时新闻
    @GET(ApiFinal.URL_HOME_NEW_DYNAMIC)
    Flowable<HttpBase<InfosBean>> getHomeDynamicNews();

    //娱乐
    //读书
    //我的

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
