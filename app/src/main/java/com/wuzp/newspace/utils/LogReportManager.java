package com.wuzp.newspace.utils;

import android.content.Context;

/**
 * Created by wuzp on 2017/9/23.
 */
public class LogReportManager {

    //配置logreport信息
    public static void init(Context context){
        /*if(AppUtils.isOpenLogReport){
            initCrashReport(context);
        }*/
    }

    //写日志
    public static void writeLog(String tag,String msg){
        /*if(AppUtils.isOpenLogReport && !TextUtils.isEmpty(msg)){
            LogReport.getInstance().writeLog(tag,msg);
        }*/
    }

    //上传日志
    public static void upload(Context context){
        /*if(AppUtils.isOpenLogReport){
            LogReport.getInstance().upload(context);
        }*/
    }

    //初始化奔溃日志信息的类
    private static void initCrashReport(Context context) {
        /*LogReport.getInstance()
                .setCacheSize(10 * 1024 * 1024)//支持设置缓存大小10M，超出后清空
                .setLogDir(context.getApplicationContext(),"sdcard/" + context.getString(context.getApplicationInfo().labelRes) + "/")//定义路径为：sdcard/[app name]/
                .setWifiOnly(true)//设置只在Wifi状态下上传，设置为false为Wifi和移动网络都上传
                .setLogSaver(new CrashWriter(context.getApplicationContext()))//支持自定义保存崩溃信息的样式
                //.setEncryption(new AESEncode()) //支持日志到AES加密或者DES加密，默认不开启
                .init(context.getApplicationContext());
        initEmailReporter(context);*/
    }

    /**
     * 使用EMAIL发送日志
     */
    private static void initEmailReporter(Context context) {
        /*EmailReporter email = new EmailReporter(context);
        email.setReceiver("wzp09tjlg@vread.cn");//收件人
        email.setSender("wzp16beijing@163.com");//发送人邮箱
        email.setSendPassword("Wzp19890417");//邮箱密码
        email.setSMTPHost("smtp.163.com");//SMTP地址
        email.setPort("465");//SMTP 端口
        LogReport.getInstance().setUploadType(email);*/
    }
}
