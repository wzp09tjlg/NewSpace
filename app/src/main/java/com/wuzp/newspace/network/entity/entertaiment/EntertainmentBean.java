package com.wuzp.newspace.network.entity.entertaiment;

import java.util.List;

/**
 * Created by wuzp on 2017/9/25.
 */
public class EntertainmentBean {
    private int allPages;
    private int ret_code;
    private int currentPage;
    private int allNum;
    private int maxResult;
    private List<ContentBean> contentlist;

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public List<ContentBean> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<ContentBean> contentlist) {
        this.contentlist = contentlist;
    }

    @Override
    public String toString() {
        return "{}";
    }
    public static class ContentBean {
        private String text;
        private String title;
        private String img;
        private String ct;
        private int type;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCt() {
            return ct;
        }

        public void setCt(String ct) {
            this.ct = ct;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        @Override
        public String toString() {
            return "{}";
        }
    }

}
