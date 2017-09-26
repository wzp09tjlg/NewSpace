package com.wuzp.newspace.network.entity.read;

import com.google.gson.annotations.SerializedName;

/***/
public class Chapter {
    @SerializedName("s_num")
    private int s_num;
    @SerializedName("c_id")
    private String c_id;
    @SerializedName("title")
    private String title;
    @SerializedName("vip")
    private String vip;
    private String volume;
    private String content;
    private long startPos = 0;
    private long length = 0;
    private String book_id;
    private String tag;//做本地书籍路径存储，用于数据库
    /**
     * 单章价格接口所需属性
     */
    private String has_buy;
    private float price;
    private float buy_price;
    private String is_vip;
    private String src;
    private String sina_id;

    public String getHas_buy() {
        return has_buy;
    }

    public void setHas_buy(String has_buy) {
        this.has_buy = has_buy;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(float buy_price) {
        this.buy_price = buy_price;
    }

    public String getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(String is_vip) {
        this.is_vip = is_vip;
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

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public int getS_num() {
        return s_num;
    }

    public void setS_num(int s_num) {
        this.s_num = s_num;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getStartPos() {
        return startPos;
    }

    public void setStartPos(long startPos) {
        this.startPos = startPos;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "ChapterForReader{" +
                "s_num=" + s_num +
                ", c_id=" + c_id +
                ", title='" + title + '\'' +
                ", vip='" + vip + '\'' +
                ", volume='" + volume + '\'' +
                ", content='" + content + '\'' +
                ", startPos=" + startPos +
                ", length=" + length +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Chapter chapter = (Chapter) o;

        if (c_id != chapter.c_id)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return s_num + Integer.valueOf(c_id);
    }
}
