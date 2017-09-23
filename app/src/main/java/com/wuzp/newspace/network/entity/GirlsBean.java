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

    public static class GirlBean  {
        private String title;
        private String picUrl;
        private String description;
        private String ctime;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "{title:" +title +
                    "picUrl:" + picUrl +
                    "description:"+description +
                    "ctime:" + ctime +
                    "url:" + url +"}";
        }
    }
}
