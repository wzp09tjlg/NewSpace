package com.wuzp.newspace.view.entertaiment;

import com.wuzp.newspace.base.BasePresenter;
import com.wuzp.newspace.base.BaseView;
import com.wuzp.newspace.network.ApiCallback;
import com.wuzp.newspace.network.ApiError;
import com.wuzp.newspace.network.entity.entertaiment.EntertainmentBean;

/**
 * Created by wuzp on 2017/9/24.
 */
public class FunnyPicPresenter extends BasePresenter {
    public FunnyPicPresenter(BaseView view){
        super(view);
    }

    public void start(){
        addSubscription(apiService.getHomeJokeText(), new ApiCallback<EntertainmentBean>() {
            @Override
            public void onSuccess(EntertainmentBean model) {

            }

            @Override
            public void onFailure(ApiError error) {

            }
        });
    }
}
