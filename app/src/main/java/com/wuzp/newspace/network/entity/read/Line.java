package com.wuzp.newspace.network.entity.read;

/**
 * Created by wuzp on 2017/9/27.
 */
public class Line {
    private String content;
    private int firstpos;
    private int lastpos;

    public float getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(float coordinateY) {
        this.coordinateY = coordinateY;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFirstpos() {
        return firstpos;
    }

    public void setFirstpos(int firstpos) {
        this.firstpos = firstpos;
    }

    public int getLastpos() {
        return lastpos;
    }

    public void setLastpos(int lastpos) {
        this.lastpos = lastpos;
    }

    private float coordinateY;


    @Override
    public String toString() {
        return "Line{" +
                "content='" + content + '\'' +
                ", firstpos=" + firstpos +
                ", lastpos=" + lastpos +
                ", coordinateY=" + coordinateY +
                '}';
    }
}