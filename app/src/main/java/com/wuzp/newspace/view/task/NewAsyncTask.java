package com.wuzp.newspace.view.task;

import android.os.AsyncTask;

/**
 * Created by wuzp on 2017/10/2.
 * 这里分析一下AsyncTask 的源码
 * 在使用AsyncTask 时 会出现这样几个问题：
 * 1.当创建AsyncTask对象的activity被销毁之后，AsyncTask对象是还存在的，这样导致异步执行完之后执行post方法，
 * 页面的更新就会是空，可能crash。即使是在activity销毁之前调用了cancel 但是也没有效果。
 * 2.如果AsyncTask这个类持有了activity的应用，这就导致activity销毁时 内存不能释放，导致内存泄漏的问题
 * 3.在Activity中声明AsyncTask 子类，会持有外部内的一个应用，这样在使用AsyncTask 执行异步时，activity销毁之后 因为存在应用，内存不会被释放掉，导致内存泄漏。
 * 4.在1.6之前是串行 1.-2.3是并行的。之后又做了修改。所以在执行的时候，串行执行task.execute(); 并行执行 task.executeOnExecutor()
 * 优点：
 * 5.使用这种方式的确是可以保证执行的异步。就是在执行完dobackgroud之后 在执行postExecute
 */
public class NewAsyncTask extends AsyncTask<String,Void,String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
