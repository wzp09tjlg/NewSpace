package com.wuzp.newspace.network;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.wuzp.newspace.network.entity.HttpBase;
import com.wuzp.newspace.utils.LogUtil;

import java.net.ConnectException;

/**
 * Created by wuzp on 2017/9/17.
 */
public abstract class ApiCallback<M> {
    public abstract void onSuccess(M model);

    public abstract void onFailure(ApiError error);

    public void onFinish() {
    }

    public void onNext(HttpBase<M> model) {
        if (model == null) {
            onFailure(new ApiError(ApiError.S_NULL_DATA, "数据异常"));
            LogUtil.i("ApiCallback -> Data empty exception");
            return;
        }
        if (model.getShowapi_res_code() == 0) {
            M m = model.getShowapi_res_body();
            if (m != null) {
                onSuccess(m);
            } else {
                LogUtil.i("Logical processing exception-> " + "数据异常");
                onFailure(new ApiError(ApiError.S_ERROR_DATA, "数据异常"));
            }
        } else if (model.getShowapi_res_code() == ApiError.S_ERROR_NEED_LOGIN) {
            onFailure(new ApiError(model.getShowapi_res_code(), model.getShowapi_res_error()));
        } else {
            onFailure(new ApiError(ApiError.S_ERROR_DATA_TIP, model.getShowapi_res_error()));
            LogUtil.i("ApiCallback -> Data exception");
        }
    }

    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            String msg = httpException.getMessage();
            onFailure(new ApiError(ApiError.S_UNKNOW_ERROR, msg));
            LogUtil.i("ApiCallback -> Network connection exception -> " + msg);
        } else if (e instanceof ConnectException) {
            ConnectException connectException = (ConnectException) e;
            int code = ApiError.S_NETWORK_UNCONNECT;
            String msg = connectException.getMessage();
            onFailure(new ApiError(ApiError.S_NETWORK_UNCONNECT, msg));
        } else {
            onFailure(new ApiError(ApiError.S_UNKNOW_ERROR));
            LogUtil.i("ApiCallback -> Unknow exception " + e);
        }
    }

    public void onComplete() {
        onFinish();
    }
}