package com.wuzp.newspace.network.entity.widget;

public class TagBean {
    public static final int TYPE_NORMAL = 0;//浅灰色
    public static final int TYPE_FINISHED = 1;//浅红
    public static final int TYPE_ALBUM = 2;//浅蓝
    public static final int TYPE_MATCH = 3;//浅橘黄
    private int type = 0;
    private String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}