package com.wuzp.newspace.view.main;

import com.wuzp.newspace.base.BaseView;
import com.wuzp.newspace.network.entity.GirlBean;

import java.util.List;

/**
 * Created by wuzp on 2017/9/19.
 */

public interface MainView extends BaseView {
   public void setData(List<GirlBean> data);
}
