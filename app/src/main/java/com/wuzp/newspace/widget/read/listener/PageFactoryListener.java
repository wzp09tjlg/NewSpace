package com.wuzp.newspace.widget.read.listener;

public interface PageFactoryListener {
    void newAddChapter(Object o);

    void changePage(int totalpage, int curpage);

    void preChapter();//上一章

    void nextChapter();//下一章

    void isShowMark(boolean isContainMark);//本页书签

    void getChapterIdInfo(String current, String pre, String next, String existLast, int position);//pagefactory中数据源信息

    void contentLoadSuccsee();//当加载一章时提示初始化控件

    void changeTheme();//改变主题
}