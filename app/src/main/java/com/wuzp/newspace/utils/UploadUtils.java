package com.wuzp.newspace.utils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by wuzp on 2017/8/19.
 * 上传文件的工具类
 */
public class UploadUtils {

    public static void upload(String token,String fileName,File uploadFile,final UploadCallback callback){
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), uploadFile);
        MultipartBody.Part part = MultipartBody.Part.createFormData("avatar_file", uploadFile.getName(), requestBody);
        //网络接口上传数据
    }

    public interface UploadCallback{
        void onUploadSuccess(String url);
        void onUploadFailure(String msg);
        void mustRun();
    }
}
