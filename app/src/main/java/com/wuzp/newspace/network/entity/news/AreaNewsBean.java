package com.wuzp.newspace.network.entity.news;

import java.util.List;

/**
 * Created by wuzp on 2017/9/24.
 */
public class AreaNewsBean {
    private int ret_code;
    private PageBean pagebean;

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public PageBean getPagebean() {
        return pagebean;
    }

    public void setPagebean(PageBean pagebean) {
        this.pagebean = pagebean;
    }

    @Override
    public String toString() {
        return "{ret_code:" + ret_code + ",pagebean:" + pagebean + "}";
    }

    public static class PageBean {
        private int allPages;
        private List<ContentBean> contentlist;
        private int currentPage;
        private int allNum;
        private int maxResult;

        @Override
        public String toString() {
            return "{}";
        }
    }

    public static class ContentBean {
        private List<String> allList;
        private String pubDate;
        private String title;
        private String channelName;
        private List<String> imageurls;
        private String desc;
        private String channelId;
        private String nid;
        private String link;

        public List<String> getAllList() {
            return allList;
        }

        public void setAllList(List<String> allList) {
            this.allList = allList;
        }

        public String getPubDate() {
            return pubDate;
        }

        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public List<String> getImageurls() {
            return imageurls;
        }

        public void setImageurls(List<String> imageurls) {
            this.imageurls = imageurls;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getNid() {
            return nid;
        }

        public void setNid(String nid) {
            this.nid = nid;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        @Override
        public String toString() {
            return "{}";
        }
    }
}
