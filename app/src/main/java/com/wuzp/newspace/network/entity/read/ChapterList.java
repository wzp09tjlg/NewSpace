package com.wuzp.newspace.network.entity.read;

import java.util.List;

public class ChapterList{
    /**
     * code : 0
     * msg : 成功
     */
    /**
     * status : {"code":0,"msg":"成功"}
     * book_id : 5365725
     * chapter_num : 732
     * total_page : 74
     * chapters : [{"s_num":1,"c_id":10300479,"title":"第1章 为了一个承诺","vip":"N","volume":"正文"},{"s_num":2,"c_id":10300480,"title":"第2章 校花冠军","vip":"N"},{"s_num":3,"c_id":10300481,"title":"第3章 做我的未婚夫","vip":"N"},{"s_num":4,"c_id":10300482,"title":"第4章 学狗叫我听听","vip":"N"},{"s_num":5,"c_id":10300483,"title":"第5章 真是个小富婆","vip":"N"},{"s_num":6,"c_id":10300484,"title":"第6章 叶皓辰的原则","vip":"N"},{"s_num":7,"c_id":10300485,"title":"第7章 这下彻底完了","vip":"N"},{"s_num":8,"c_id":10300486,"title":"第8章 利刃出鞘","vip":"N"},{"s_num":9,"c_id":10300487,"title":"第9章 清水出芙蓉","vip":"N"},{"s_num":10,"c_id":10300488,"title":"第10章 美救英雄","vip":"N"}]
     */

    private StatusBean status;

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean statusBean) {
        this.status = statusBean;
    }

    private String book_id;
    private int chapter_num;
    private int total_page;
    /**
     * s_num : 1
     * c_id : 10300479
     * title : 第1章 为了一个承诺
     * vip : N
     * volume : 正文
     */

    private List<Chapter> chapters;

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public int getChapter_num() {
        return chapter_num;
    }

    public void setChapter_num(int chapter_num) {
        this.chapter_num = chapter_num;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    @Override
    public String toString() {
        return "ChapterList{" +
                ", book_id=" + book_id +
                ", chapter_num=" + chapter_num +
                ", total_page=" + total_page +
                ", chapters=" + chapters +
                '}';
    }
}
