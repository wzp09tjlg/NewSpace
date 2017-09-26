package com.wuzp.newspace.network.entity.read;

import com.google.gson.annotations.SerializedName;

public class BookInfo {
    /**
     * book_id : 178971
     * is_vip : Y
     * paytype : 3
     * price : 0
     * intro : 杀伐果断，热血沸腾至极！执掌诸天轮回，管他什么诸神，管他什么妖魔，就算是三千真灵法相齐出，我也能轰杀一切，铸就一曲掌控天下的妖孽传说。
     * title : 弑杀诸神毁灭九天：执掌轮回
     * updatetime : 2012-09-25 06:01:24
     * s_bid : 178971
     * img : http://vipbook.sinaedge.com/bookcover/pics/27/cover_11ab5544d8b4865f2b3f5503084e4f8d.jpg
     * status : 完结
     * src : websina
     * sina_id :
     * last_chapter : []
     * cate_name : 鐜勫够灏忚?
     * cate_id : 3
     * chapter_total : 887
     * chapter_num : 887
     * chapter_amount : 887
     * author : 荒野之鸿
     * buy_type : 0
     * isbuy : U
     * status_flag : FINISH
     * status_info : 完结
     * praise_num : 10
     * is_praise : U
     * pic_card : []
     * is_forbidden : N
     * cmread_card : {"script_name":"和阅读","is_cmread":"N"}
     * is_epub : 1
     * kind : 1
     */

    @SerializedName("books")
    private BooksBean books;
    /**
     * price_tip : []
     * status : {"code":0,"msg":"成功"}
     * books : {"book_id":"178971","is_vip":"Y","paytype":"3","price":0,"intro":"杀伐果断，热血沸腾至极！执掌诸天轮回，管他什么诸神，管他什么妖魔，就算是三千真灵法相齐出，我也能轰杀一切，铸就一曲掌控天下的妖孽传说。","title":"弑杀诸神毁灭九天：执掌轮回","updatetime":"2012-09-25 06:01:24","s_bid":"178971","img":"http://vipbook.sinaedge.com/bookcover/pics/27/cover_11ab5544d8b4865f2b3f5503084e4f8d.jpg","status":"完结","src":"websina","sina_id":"","last_chapter":[],"cate_name":"鐜勫够灏忚?","cate_id":3,"chapter_total":887,"chapter_num":887,"chapter_amount":887,"author":"荒野之鸿","buy_type":0,"isbuy":"U","status_flag":"FINISH","status_info":"完结","praise_num":10,"is_praise":"U","pic_card":[],"is_forbidden":"N","cmread_card":{"script_name":"和阅读","is_cmread":"N"},"is_epub":1,"kind":"1"}
     * suite_id :
     * is_suite : 1
     * suite_name :
     */

    @SerializedName("suite_id")
    private String suiteId;
    @SerializedName("is_suite")
    private int isSuite;
    @SerializedName("suite_name")
    private String suiteName;

    public BooksBean getBooks() {
        return books;
    }

    public void setBooks(BooksBean books) {
        this.books = books;
    }

    public String getSuiteId() {
        return suiteId;
    }

    public void setSuiteId(String suiteId) {
        this.suiteId = suiteId;
    }

    public int getIsSuite() {
        return isSuite;
    }

    public void setIsSuite(int isSuite) {
        this.isSuite = isSuite;
    }

    public String getSuiteName() {
        return suiteName;
    }

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    public static class BooksBean {
        @SerializedName("book_id")
        private String bookId;
        @SerializedName("is_vip")
        private String isVip;
        @SerializedName("paytype")
        private String paytype;
        @SerializedName("price")
        private float price;
        @SerializedName("intro")
        private String intro;
        @SerializedName("title")
        private String title;
        @SerializedName("updatetime")
        private String updatetime;
        @SerializedName("s_bid")
        private String sBid;
        @SerializedName("img")
        private String img;
        @SerializedName("status")
        private String status;
        @SerializedName("src")
        private String src;
        @SerializedName("sina_id")
        private String sinaId;
        @SerializedName("cate_name")
        private String cateName;
        @SerializedName("cate_id")
        private int cateId;
        @SerializedName("chapter_total")
        private int chapterTotal;
        @SerializedName("chapter_num")
        private int chapterNum;
        @SerializedName("chapter_amount")
        private int chapterAmount;
        @SerializedName("author")
        private String author;
        @SerializedName("buy_type")
        private int buyType;
        @SerializedName("isbuy")
        private String isbuy;
        @SerializedName("status_flag")
        private String statusFlag;
        @SerializedName("status_info")
        private String statusInfo;
        @SerializedName("praise_num")
        private int praiseNum;
        @SerializedName("is_praise")
        private String isPraise;
        @SerializedName("is_forbidden")
        private String isForbidden;
        /**
         * script_name : 和阅读
         * is_cmread : N
         */

        @SerializedName("cmread_card")
        private CmreadCardBean cmreadCard;
        @SerializedName("is_epub")
        private int isEpub;
        @SerializedName("kind")
        private String kind;

        public String getBookId() {
            return bookId;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }

        public String getIsVip() {
            return isVip;
        }

        public void setIsVip(String isVip) {
            this.isVip = isVip;
        }

        public String getPaytype() {
            return paytype;
        }

        public void setPaytype(String paytype) {
            this.paytype = paytype;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getSBid() {
            return sBid;
        }

        public void setSBid(String sBid) {
            this.sBid = sBid;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getSinaId() {
            return sinaId;
        }

        public void setSinaId(String sinaId) {
            this.sinaId = sinaId;
        }

        public String getCateName() {
            return cateName;
        }

        public void setCateName(String cateName) {
            this.cateName = cateName;
        }

        public int getCateId() {
            return cateId;
        }

        public void setCateId(int cateId) {
            this.cateId = cateId;
        }

        public int getChapterTotal() {
            return chapterTotal;
        }

        public void setChapterTotal(int chapterTotal) {
            this.chapterTotal = chapterTotal;
        }

        public int getChapterNum() {
            return chapterNum;
        }

        public void setChapterNum(int chapterNum) {
            this.chapterNum = chapterNum;
        }

        public int getChapterAmount() {
            return chapterAmount;
        }

        public void setChapterAmount(int chapterAmount) {
            this.chapterAmount = chapterAmount;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getBuyType() {
            return buyType;
        }

        public void setBuyType(int buyType) {
            this.buyType = buyType;
        }

        public String getIsbuy() {
            return isbuy;
        }

        public void setIsbuy(String isbuy) {
            this.isbuy = isbuy;
        }

        public String getStatusFlag() {
            return statusFlag;
        }

        public void setStatusFlag(String statusFlag) {
            this.statusFlag = statusFlag;
        }

        public String getStatusInfo() {
            return statusInfo;
        }

        public void setStatusInfo(String statusInfo) {
            this.statusInfo = statusInfo;
        }

        public int getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(int praiseNum) {
            this.praiseNum = praiseNum;
        }

        public String getIsPraise() {
            return isPraise;
        }

        public void setIsPraise(String isPraise) {
            this.isPraise = isPraise;
        }

        public String getIsForbidden() {
            return isForbidden;
        }

        public void setIsForbidden(String isForbidden) {
            this.isForbidden = isForbidden;
        }

        public CmreadCardBean getCmreadCard() {
            return cmreadCard;
        }

        public void setCmreadCard(CmreadCardBean cmreadCard) {
            this.cmreadCard = cmreadCard;
        }

        public int getIsEpub() {
            return isEpub;
        }

        public void setIsEpub(int isEpub) {
            this.isEpub = isEpub;
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public static class CmreadCardBean {
            @SerializedName("script_name")
            private String scriptName;
            @SerializedName("is_cmread")
            private String isCmread;

            public String getScriptName() {
                return scriptName;
            }

            public void setScriptName(String scriptName) {
                this.scriptName = scriptName;
            }

            public String getIsCmread() {
                return isCmread;
            }

            public void setIsCmread(String isCmread) {
                this.isCmread = isCmread;
            }
        }
    }
}
