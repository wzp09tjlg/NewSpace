package com.wuzp.newspace.view.task;

import com.wuzp.newspace.R;
import com.wuzp.newspace.base.BasePresenter;
import com.wuzp.newspace.base.BaseView;
import com.wuzp.newspace.base.NewActivity;
import com.wuzp.newspace.databinding.ActivityTaskBinding;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wuzp on 2017/10/2.
 */
public class TaskActivity extends NewActivity<ActivityTaskBinding,BasePresenter> implements BaseView {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_task;
    }

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter(this);
    }

    @Override
    public void error(int code, String msg) {

    }

    //对线程的学习和理解(四种线程池1.NewCacheThreadPool 2.newFixedThreadPool 3.ScheduledThreadPool  4.SingleExecutor)
    /**
     * 线程池分类：
     1：FixedThreadPool-线程数量固定的线程池。
        特点：当线程处于空闲状态时，它们并不会被回收，除非线程池被关闭。
              当所有线程都处于活动状态时，新任务都会处于等待状态，直到有线程空闲出来
     2：CachedThreadPool- 线程数量不定的线程池
        特点：它只有非核心线程，当线程池中的线程都处于活动状态时，线程池会创建新的线程来处理新任务，
              否则就会利用空闲的线程来处理新任务。
     3：ScheduleThreadPool - 核心线程数量是固定的
        特点：核心线程固定，非核心线程没有限制，并且当非核心线程闲置时，被立即回收
     4：SingleThreadExcutor - 线程池内部只有一个核心线程
        特点 ：它确保所有的任务都在同一个线程中按顺序执行。
     * */

   private Executor executor = new ThreadPoolExecutor(4,10,30, TimeUnit.SECONDS,null);
}
