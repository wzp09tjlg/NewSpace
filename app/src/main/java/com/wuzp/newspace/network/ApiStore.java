package com.wuzp.newspace.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wuzp.newspace.base.BaseApp;
import com.wuzp.newspace.utils.AppUtils;
import com.wuzp.newspace.utils.FileUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wuzp on 2017/9/17.
 */
public class ApiStore {
    private static final long DEFAULT_JSON_CACHE_SIZE = 50 * 1024 * 1204;
    private static final int S_DEFAULT_TIMEOUT = 15;

    private static ApiService mApiStores = null;
    private static Retrofit retrofit = null;
    private static Gson mGson = null;

    private ApiStore(){}

    public static ApiService getApiService(){
        if(mApiStores == null){
            OkHttpClient client = new OkHttpClient.Builder()
                    .cache(new Cache(FileUtils.getJsonCache(BaseApp.gContext), DEFAULT_JSON_CACHE_SIZE))
                    .addInterceptor(new NetInterceptor())//处理公共参数，并处理因为wifi导致的重定向
                    .retryOnConnectionFailure(true)//允许失败重试
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .followRedirects(false)//禁止重定向
                    .build();
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .client(client)
                        .baseUrl(getHostUrl())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(getGson()))
                        .build();
            }
            mApiStores = retrofit.create(ApiService.class);
        }
        return mApiStores;
    }

    private static Gson getGson(){
      if(mGson == null){
          mGson = new GsonBuilder()
                  .serializeNulls()//允许序列化反序列化为null
                  .setLenient()
                  .create();
      }
      return mGson;
    }

    private static String getHostUrl(){
      if (AppUtils.isDebug){
         return ApiFinal.URL_HOST_DEBUG;
      }else{
         return ApiFinal.URL_HOST;
      }
    }
}
