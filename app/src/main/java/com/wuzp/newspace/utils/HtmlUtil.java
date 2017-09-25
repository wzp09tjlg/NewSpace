package com.wuzp.newspace.utils;

import android.text.TextUtils;

/**
 * Created by wuzp on 2017/9/25.
 */
public class HtmlUtil {

    public static String convert(String html){
        if(TextUtils.isEmpty(html)) return "";
        try {//从后台返回的数据有些就是不规整 这里做简单的替换。但是在有些html中 会存在很多的换行符 用空格替换吧
            html = html.replace("\n"," ");
            html = html.replace("<br>","");
            html = html.replace("<br/>","");
            html = html.replace("<br />","");
            html = html.replace("<p>","");
            html = html.replace("</p>","");
        }catch (Exception e){}
        return html.trim();
    }
}
