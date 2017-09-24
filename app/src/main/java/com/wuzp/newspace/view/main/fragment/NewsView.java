package com.wuzp.newspace.view.main.fragment;

import com.wuzp.newspace.base.BaseView;
import com.wuzp.newspace.network.entity.news.NewsChannelsBean;

import java.util.List;

/**
 * Created by wuzp on 2017/9/24.
 */
public interface NewsView extends BaseView {

    public void setNewsChannelData(List<NewsChannelsBean.ChannelBean> data);
}
