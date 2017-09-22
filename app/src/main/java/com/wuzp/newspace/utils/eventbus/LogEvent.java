package com.wuzp.newspace.utils.eventbus;

/**
 * Created by wuzp on 2017/9/22.
 */
public class LogEvent {
    public int id;
    public String msg;

    public LogEvent(){}

    public LogEvent(int id,String msg){
        this.id = id;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "{id:" + id + ";msg:" + msg + "}";
    }
}
