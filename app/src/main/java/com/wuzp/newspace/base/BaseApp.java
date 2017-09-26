package com.wuzp.newspace.base;

import android.app.Application;
import android.content.Context;

import com.wuzp.newspace.database.service.DBService;
import com.wuzp.newspace.utils.LogReportManager;
import com.wuzp.newspace.utils.PreferenceUtil;

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

        PreferenceUtil.init(gContext); //初始化Sp
        DBService.init(gContext);//初始化数据库

        LogReportManager.init(gContext);//开启本地记录崩溃日志
    }
}
