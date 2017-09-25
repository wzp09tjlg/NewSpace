package com.wuzp.newspace.view.main.fragment;

import com.wuzp.newspace.base.BasePresenter;
import com.wuzp.newspace.network.ApiCallback;
import com.wuzp.newspace.network.ApiError;
import com.wuzp.newspace.network.entity.main.InfosBean;

/**
 * Created by wuzp on 2017/9/23.
 */

public class InfoPresenter extends BasePresenter<InfoView> {

    public InfoPresenter(InfoView view){
        super(view);
    }

    public void start(){
        addSubscription(apiService.getHomeInfo(), new ApiCallback<InfosBean>() {
            @Override
            public void onSuccess(InfosBean model) {
                ((InfoView)mvpView).setData(model.getList());
            }

            @Override
            public void onFailure(ApiError error) {
               mvpView.error(error.getErrorCode(),error.getMessage());
            }
        });
    }
}
