package com.wuzp.newspace.utils.database;

import java.util.HashMap;
import java.util.Map;

public class InlayBookUtils {
    public static Map<String, String[]> map = new HashMap<>();
    public static final String MAN_BOOK = "MAN";
    public static final String WOMAN_BOOK = "WOMAN";
    public static final String PUBLIC_ARTICLE_BOOK = "PUBLIC_ARTICLE_BOOK";
    public static final String ALL = "ALL";

    public static String[] getStrings(String type){
        return map.get(type);
    }

    public static void addDates(String type,String[] dates){
        map.put(type,dates);
    }

}
