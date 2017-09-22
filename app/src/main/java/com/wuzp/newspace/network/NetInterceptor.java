package com.wuzp.newspace.network;

import com.wuzp.newspace.utils.AppUtils;
import com.wuzp.newspace.utils.LogUtil;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wuzp on 2017/9/17.
 */
public class NetInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request oldRequest = chain.request();
        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter("from_client", "android")//拦截器添加公参
                .addEncodedQueryParameter("app_channel", AppUtils.getChannel())
                .addEncodedQueryParameter("version", AppUtils.getVersionName())
                .addEncodedQueryParameter("phone_imei", AppUtils.getIMEI())
                .addEncodedQueryParameter("showapi_appid", ApiFinal.APP_ID)
                .addEncodedQueryParameter("showapi_sign", ApiFinal.APP_SEACRET);
        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .addHeader("Connection", "close")
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();
        LogUtil.i("url:", newRequest.url().toString());
        Response response = chain.proceed(newRequest);
        LogUtil.i("url:", " retrofit response:" + response.toString());
        if (response.isSuccessful()) {
            return response;
        } else {
            throw new WifiFailException();
        }
    }
}