package com.wuzp.newspace.widget.read.help;

import com.wuzp.newspace.utils.PreferenceUtil;

/**
 * Created by wuzp on 2017/9/27.
 */
public class ThemeManager {
    public static boolean isDayOrNight() {
        return !PreferenceUtil.getString(PreferenceUtil.BOOK_READ_THEME, ColorManager.FFe6dbc8).equals(ColorManager.FF3e3e3e);
    }

    public static String getTheme() {
        return isDayOrNight() ? PreferenceUtil.getString(PreferenceUtil.BOOK_DAY_MODEL, ColorManager.FFe6dbc8) : ColorManager.FF3e3e3e;
    }

    public static void changeThemeGroup(){
        if (isDayOrNight()){
            PreferenceUtil.putString(PreferenceUtil.BOOK_READ_THEME,ColorManager.FF3e3e3e);
        }else {
            PreferenceUtil.putString(PreferenceUtil.BOOK_READ_THEME,ColorManager.FFe6dbc8);
        }
    }
    public static void setTheme(String theme){
        PreferenceUtil.putString(PreferenceUtil.BOOK_READ_THEME,theme);
        if (isDayOrNight()){
            PreferenceUtil.putString(PreferenceUtil.BOOK_DAY_MODEL,theme);
        }
    }
}
