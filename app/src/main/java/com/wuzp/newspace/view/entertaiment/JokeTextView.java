package com.wuzp.newspace.view.entertaiment;

import com.wuzp.newspace.base.BaseView;
import com.wuzp.newspace.network.entity.entertaiment.EntertainmentBean;

import java.util.List;

/**
 * Created by wuzp on 2017/9/24.
 */
public interface JokeTextView extends BaseView {

    public void setJokeTextData(List<EntertainmentBean.ContentBean> data);
}
