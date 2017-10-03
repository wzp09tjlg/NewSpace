package com.wuzp.newspace.utils.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wuzp on 2017/10/2.
 * 这个工具类就是专门处理线程池的
 * 这里会提供四种线程池  分别
 */
public class Executors {

    //CacheThreadPool //没有核心线程，都是非核心线程。
    //FixedThreadPool // 线程数固定
    //ScheduledThreadPool // 可以延迟的线程池，核心线程固定，非核心线程不固定
    //SingleExecutor  //可以理解成固定线程数只有1个的FixedThreadPool

    public static ExecutorService newCacheThreadPool(){
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit. SECONDS, new SynchronousQueue<Runnable>());
    }
    //使用
    //Executors.newCacheThreadPool().execute(r);

    public static ExecutorService newFixThreadPool(int nThreads){
        return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }
    //使用
    //Executors.newFixThreadPool(5).execute(r);

    /*public static ExecutorService newSingleThreadPool (int nThreads){
        return new FinalizableDelegatedExecutorService ( new ThreadPoolExecutor (1, 1, 0, TimeUnit. MILLISECONDS, new LinkedBlockingQueue<Runnable>()) );
    }*/
    //使用
    //Executors.newSingleThreadPool ().execute(r);

    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize){
        return new ScheduledThreadPoolExecutor(corePoolSize);
    }
   //使用，延迟1秒执行，每隔2秒执行一次Runnable r
   //Executors. newScheduledThreadPool (5).scheduleAtFixedRate(r, 1000, 2000, TimeUnit.MILLISECONDS);
}
