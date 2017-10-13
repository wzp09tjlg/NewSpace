package com.wuzp.newspace.view.upload;

import com.wuzp.newspace.base.BasePresenter;
import com.wuzp.newspace.network.ApiCallback;
import com.wuzp.newspace.network.ApiError;
import com.wuzp.newspace.network.entity.upload.UploadBean;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by wuzp on 2017/10/3.
 */
public class UploadPresenter extends BasePresenter<UploadView> {

    public UploadPresenter(UploadView view){
        super(view);
    }

    public void upload(File uploadFile){
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), uploadFile);
        MultipartBody.Part part = MultipartBody.Part.createFormData("avatar_file", uploadFile.getName(), requestBody);

        addSubscription(apiService.uploadAvatar("", "loadFile", part), new ApiCallback<UploadBean>() {
            @Override
            public void onSuccess(UploadBean model) {
               mvpView.setData(model.getUrl());
            }

            @Override
            public void onFailure(ApiError error) {
                mvpView.error(error.getErrorCode(),error.getMessage());
            }
        });
    }
}
