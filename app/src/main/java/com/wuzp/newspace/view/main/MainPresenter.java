package com.wuzp.newspace.view.main;

import com.wuzp.newspace.base.BasePresenter;
import com.wuzp.newspace.network.ApiCallback;
import com.wuzp.newspace.network.ApiError;
import com.wuzp.newspace.network.entity.main.GirlsBean;
import com.wuzp.newspace.utils.LogUtil;

/**
 * Created by wuzp on 2017/9/19.
 */
public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(MainView view){
        super(view);
    }

    public void start(){
        addSubscription(apiService.getHome("10", "1", "1"), new ApiCallback<GirlsBean>() {
            @Override
            public void onSuccess(GirlsBean model) {
                if(mvpView != null){
                    mvpView.setData(model.getData());
                }
            }

            @Override
            public void onFailure(ApiError error) {
            LogUtil.e("wzp","err:" + error.toString());
            }
        });
    }

}
