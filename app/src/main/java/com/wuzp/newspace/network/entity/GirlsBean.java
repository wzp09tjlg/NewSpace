package com.wuzp.newspace.network.entity;

import java.util.List;

/**
 * Created by wuzp on 2017/9/22.
 */
public class GirlsBean{

    private List<GirlBean> newslist;

    public List<GirlBean> getData() {
        return newslist;
    }

    public void setData(List<GirlBean> data) {
        this.newslist = data;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(GirlBean bean : newslist){
            builder.append(bean.toString() + " \n");
        }
        return builder.toString();
    }
}
