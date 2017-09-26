package com.wuzp.newspace.network.entity.read;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/***/
public class Book {

    private String uid = "";

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @SerializedName("book_id")
    private String book_id;
    @SerializedName("is_vip")
    private String is_vip;
    @SerializedName("paytype")
    private String paytype;
    @SerializedName("price")
    private double price;
    @SerializedName("intro")
    private String intro;
    @SerializedName("title")
    private String title;
    @SerializedName("updatetime")
    private long updatetime;
    @SerializedName("s_bid")
    private String s_bid;
    @SerializedName("img")
    private String img;
    @SerializedName("status")
    private String status;
    @SerializedName("src")
    private String src;
    @SerializedName("sina_id")
    private String sina_id;
    /**
     * chapter_id : 10278266
     * title : 第871章　哭货的电话
     * is_vip : Y
     */

    private LastChapterBean last_chapter;
    @SerializedName("cate_name")
    private String cate_name;
    @SerializedName("cate_id")
    private int cate_id;
    @SerializedName("chapter_num")
    private int chapter_num = 0;
    @SerializedName("chapter_amount")
    private int chapter_amount;
    @SerializedName("author")
    private String author;
    private String bid;
    private String cid;
    private String createtime;
    private String ios_app;
    @SerializedName("isbuy")
    private String isbuy;
    @SerializedName("buy_type")
    private int buy_type;
    private String suite_id;
    private int is_suite;
    private String suite_name;
    private String kind;
    private int index;
    private String checked;
    private String show_status;
    /*
    *
    * */
    private boolean autobuy = false;
    private String filePath;
    private String fileSize;
    private boolean is_online_book = true;
    private boolean downloadstatus = false;
    private long lastreadtime;
    @SerializedName("bookreadtime")
    private long netReadTime = -1;
    @SerializedName("bookshelftime")
    private long downloadTime = -1;
    private int id;
    private String onlineReadChapterId = "-1";
    private int isUpdateChapterList = 0;
    private int lastPos = 0;
    private int fontsize = 20;
    private int lastPage = 0;

    @SerializedName("chapters")
    private List<Chapter> chapters;
    @SerializedName("check_status")
    private String checkStatus;

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getShow_status() {
        return show_status;
    }

    public void setShow_status(String show_status) {
        this.show_status = show_status;
    }

    /**
     * 更新的章节数.
     */
    private int updateChaptersNum = 0;

    public boolean isAutobuy() {
        return autobuy;
    }

    public void setAutobuy(boolean autobuy) {
        this.autobuy = autobuy;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getFontsize() {
        return fontsize;
    }

    public void setFontsize(int fontsize) {
        this.fontsize = fontsize;
    }

    public int getLastPos() {
        return lastPos;
    }

    public void setLastPos(int lastPos) {
        this.lastPos = lastPos;
    }

    public int getIsUpdateChapterList() {
        return isUpdateChapterList;
    }

    public void setIsUpdateChapterList(int isUpdateChapterList) {
        this.isUpdateChapterList = isUpdateChapterList;
    }

    public String getOnlineReadChapterId() {
        return onlineReadChapterId;
    }

    public void setOnlineReadChapterId(String onlineReadChapterId) {
        this.onlineReadChapterId = onlineReadChapterId;
    }

    /**
     * 连载中状态.
     */
    public static final String STATUS_SERIAL_EN = "SERIES";

    /**
     * 选载状态.
     */
    public static final String STATUS_PAUSE_EN = "PAUSE";

    /**
     * 完结状态.
     */
    public static final String STATUS_FINISH_EN = "FINISH";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLastreadtime() {
        return lastreadtime;
    }

    public void setLastreadtime(long lastreadtime) {
        this.lastreadtime = lastreadtime;
    }

    public long getNetReadTime() {
        return netReadTime;
    }

    public void setNetReadTime(long netReadTime) {
        this.netReadTime = netReadTime;
    }

    public boolean getDownloadstatus() {
        return downloadstatus;
    }

    public void setDownloadstatus(boolean downloadstatus) {
        this.downloadstatus = downloadstatus;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public boolean getIsOnlineBook() {
        return is_online_book;
    }

    public void setIsOnlineBook(boolean is_online_book) {
        this.is_online_book = is_online_book;
    }

    /*
    *
    *
    * */
    public void setPrice(double price) {
        this.price = price;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(String is_vip) {
        this.is_vip = is_vip;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public double getPrice() {
        return price;
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

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

    public String getS_bid() {
        return s_bid;
    }

    public void setS_bid(String s_bid) {
        this.s_bid = s_bid;
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

    public String getSina_id() {
        return sina_id;
    }

    public void setSina_id(String sina_id) {
        this.sina_id = sina_id;
    }

    public LastChapterBean getLast_chapter() {
        return last_chapter;
    }

    public void setLast_chapter(LastChapterBean last_chapter) {
        this.last_chapter = last_chapter;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public int getCate_id() {
        return cate_id;
    }

    public void setCate_id(int cate_id) {
        this.cate_id = cate_id;
    }

    public int getChapter_num() {
        return chapter_num;
    }

    public void setChapter_num(int chapter_num) {
        this.chapter_num = chapter_num;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public int getChapter_amount() {
        return chapter_amount;
    }

    public void setChapter_amount(int chapter_amount) {
        this.chapter_amount = chapter_amount;
    }

    public long getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(long downloadTime) {
        this.downloadTime = downloadTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getIos_app() {
        return ios_app;
    }

    public void setIos_app(String ios_app) {
        this.ios_app = ios_app;
    }

    public String getIsbuy() {
        return isbuy;
    }

    public void setIsbuy(String isbuy) {
        this.isbuy = isbuy;
    }

    public int getBuy_type() {
        return buy_type;
    }

    public void setBuy_type(int buy_type) {
        this.buy_type = buy_type;
    }

    public String getSuite_id() {
        return suite_id;
    }

    public void setSuite_id(String suite_id) {
        this.suite_id = suite_id;
    }

    public int getIs_suite() {
        return is_suite;
    }

    public void setIs_suite(int is_suite) {
        this.is_suite = is_suite;
    }

    public String getSuite_name() {
        return suite_name;
    }

    public void setSuite_name(String suite_name) {
        this.suite_name = suite_name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public int getUpdateChaptersNum() {
        return updateChaptersNum;
    }

    public void setUpdateChaptersNum(int updateChaptersNum) {
        this.updateChaptersNum = updateChaptersNum;
    }

    public class LastChapterBean {
        private String chapter_id;
        private String title;
        private String is_vip;

        public String getChapter_id() {
            return chapter_id;
        }

        public void setChapter_id(String chapter_id) {
            this.chapter_id = chapter_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(String is_vip) {
            this.is_vip = is_vip;
        }

    }

    @Override
    public String toString() {
        return "Book{" +
                "book_id='" + book_id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
