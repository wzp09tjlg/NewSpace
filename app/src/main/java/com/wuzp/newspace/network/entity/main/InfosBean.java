package com.wuzp.newspace.network.entity.main;

import java.util.List;

/**
 * Created by wuzp on 2017/9/23.
 * 资讯的类
 */
public class InfosBean {

    private int ret_code;
    private List<InfoBean> list;
    private String msg;

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public List<InfoBean> getList() {
        return list;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (InfoBean bean:list){
            builder.append(bean.toString() + ",");
        }
        builder.append("]");
        return "{ret_code:" + ret_code + ",list:" + builder.toString() + ",msg:" + msg + "}";
    }

    public void setList(List<InfoBean> list) {
        this.list = list;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class InfoBean {
        private String title;
        private String name;
        private String image;
        private String day;
        private String aid;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "{" +
                    "title:" + title +
                    ",name:" + name +
                    ",image:" + image +
                    ",day:" + day +
                    ",aid:" + aid +
                    ",url:" + url +
                    "}";
        }
    }
}
