package com.wuzp.newspace.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.wuzp.newspace.base.BaseApp;

/**
 * Created by wuzp on 2017/9/26.
 * sharepreference 的工具类
 */
public class PreferenceUtil {
    /*-----------------------------------------------------*/
    /*--------------------- 保存值 ------------------------*/
    /*-----------------------------------------------------*/
    public static String COMMON_FIRST_OPEN = "common_first_open";  //是否是第一次打开应用

    public static String BOOK_READ_THEME = "book_read_theme";    //阅读主题
    public static String BOOK_DAY_MODEL = "book_day_model";      //白天模式
    public static String BOOK_CACHE_COUNT = "book_cache_count";  // 阅读页图书缓存的章节
    public static String BOOK_CONTENT_TEXT_SIZE   = "book_content_text_size";//阅读页内容的字体大小


    /*-----------------------------------------------------*/
   /*-----------------------------------------------------*/
   /*-----------------------------------------------------*/
   /*-----------------------------------------------------*/
    private static SharedPreferences preferences;
    private static String SP_NAME = "new_space";

    private void PreferenceUtil() {
        if (preferences == null) {
            preferences = BaseApp.gContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
    }

    public static void init(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
    }

    public static void putInt(String name, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(name, value);
        editor.commit();
    }

    public static void putFloat(String name, float value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(name, value);
        editor.commit();
    }

    public static void putString(String name, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name, value);
        editor.commit();
    }

    public static void putBoolean(String name, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    public static int getInt(String name, int defaultValue) {
        return preferences.getInt(name, defaultValue);
    }

    public static float getFloat(String name, float defaultValue) {
        return preferences.getFloat(name, defaultValue);
    }

    public static String getString(String name, String defaultValue) {
        return preferences.getString(name, defaultValue);
    }

    public static boolean getBoolean(String name, boolean defaultValue) {
        return preferences.getBoolean(name, defaultValue);
    }
}
