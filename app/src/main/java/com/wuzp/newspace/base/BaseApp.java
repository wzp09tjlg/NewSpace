package com.wuzp.newspace.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by wuzp on 2017/9/17.
 */
public class BaseApp extends Application {

    public static Context gContext = null;

    @Override
    public void onCreate() {
        super.onCreate();
        initGlobalVar();
    }

    private void initGlobalVar(){
        gContext = this;
    }
}