package com.wuzp.newspace.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.wuzp.newspace.base.BaseApp;

/**
 * Created by wuzp on 2017/9/17.
 * 这个工具类管控全局的配置参数
 */
public class AppUtils {
    public static boolean isDebug = true;//是否为调试模式

    public static String channel = "";
    public static String imei = "";
    public static String version = "";

    public static String getVersionName() {
        if (!TextUtils.isEmpty(version)) {
            return version;
        }
        try{
            version = getPackageInfo(BaseApp.gContext).versionName;
        }catch (Exception e){
            version = "3.0";
        }
        return version;
    }

    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        if (context != null) {
            String packName = context.getPackageName();
            PackageManager pm = context.getPackageManager();
            try {
                info = pm.getPackageInfo(packName, PackageManager.GET_PERMISSIONS);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return info;
    }

    public static String getChannel() {
        if (!TextUtils.isEmpty(channel)) {
            return channel;
        }
        try {
            channel = getMetaData(BaseApp.gContext, "CHANNEL");
            if (!TextUtils.isEmpty(channel)) {
                return channel;
            } else {
                LogUtil.e(LogUtil.getTag() + "！！！！！！！！！channel为空");
            }
        } catch (Exception e) {
            LogUtil.e(LogUtil.getTag());
        }
        return "10000";
    }

    private static String getMetaData(Context context, String key) throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        if (null != pm) {
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai.metaData) {
                Object value = ai.metaData.get(key);
                if (value != null) {
                    return value.toString();
                }
            }
        }
        return null;
    }

    public static String getIMEI() {
        if (!TextUtils.isEmpty(imei)) {
            return imei;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) BaseApp.gContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager.getDeviceId() == null || telephonyManager.getDeviceId().equals("")) {
                if (Build.VERSION.SDK_INT >= 23) {
                    imei = telephonyManager.getDeviceId(0);
                }else{
                    imei = "000000000000000";
                }
            } else {
                imei = telephonyManager.getDeviceId();
            }
        } catch (Exception e) {
            imei = "000000000000000";
        }finally {
            return imei;
        }
    }
}
