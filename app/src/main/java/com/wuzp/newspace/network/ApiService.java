package com.wuzp.newspace.network;

import com.wuzp.newspace.network.entity.base.HttpBase;
import com.wuzp.newspace.network.entity.entertaiment.EntertainmentBean;
import com.wuzp.newspace.network.entity.main.InfosBean;
import com.wuzp.newspace.network.entity.news.NewsChannelsBean;
import com.wuzp.newspace.network.entity.upload.UploadBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    //地域新闻
    @GET(ApiFinal.URL_HOME_NEW_CHANNEL)
    Flowable<HttpBase<NewsChannelsBean>> getHomeNewsChannel();

    //娱乐
    //获取笑话
    @GET(ApiFinal.URL_HOME_JOKE_TEXT)
    Flowable<HttpBase<EntertainmentBean>> getHomeJokeText();
    //获取趣图
    @GET(ApiFinal.URL_HOME_FUNNY_PIC)
    Flowable<HttpBase<EntertainmentBean>> getHomeFunnyPic();

    //读书
    //我的

    //针对上传文件的封装(假设上传文件的name是loadFile)
    @Multipart
    @POST(ApiFinal.URL_UPLOAD_AVATAR)
    Flowable<HttpBase<UploadBean>> uploadAvatar(@Query("access_token") String token, @Query("avatar_file") String fileName, @Part()okhttp3.MultipartBody.Part part);

}
