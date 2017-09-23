package com.wuzp.newspace.view.main.fragment;

import com.wuzp.newspace.base.BaseView;
import com.wuzp.newspace.network.entity.main.InfosBean;

import java.util.List;

/**
 * Created by wuzp on 2017/9/23.
 */
public interface InfoView extends BaseView {
    public void setData(List<InfosBean.InfoBean> data);
}
