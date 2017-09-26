package com.wuzp.newspace.utils.listener;

public interface ScheduleListener {
    void progress(int progress);

    void start();

    void end(boolean success);
}
