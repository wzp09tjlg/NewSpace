package com.wuzp.newspace.view.main.fragment;

import com.wuzp.newspace.base.BasePresenter;
import com.wuzp.newspace.base.BaseView;
import com.wuzp.newspace.network.ApiCallback;
import com.wuzp.newspace.network.ApiError;
import com.wuzp.newspace.network.entity.news.NewsChannelsBean;

/**
 * Created by wuzp on 2017/9/24.
 */

public class NewsPresenter extends BasePresenter<BaseView> {
    public NewsPresenter(BaseView view){
        super(view);
    }

    public void start(){
        addSubscription(apiService.getHomeNewsChannel(), new ApiCallback<NewsChannelsBean>() {
            @Override
            public void onSuccess(NewsChannelsBean model) {
                ((NewsView)mvpView).setNewsChannelData(model.getChannelList());
            }

            @Override
            public void onFailure(ApiError error) {

            }
        });
    }
}
