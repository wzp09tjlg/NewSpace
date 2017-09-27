package com.wuzp.newspace.utils.read;

import android.database.Cursor;

import com.wuzp.newspace.database.table.BookTable;
import com.wuzp.newspace.network.entity.read.Book;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wuzp on 2017/9/27.
 */
public class StringConvertUtil {
    /**
     * 将string转化为标准的json  针对章节列表同步
     */
    public static String string2StandardJsonForCollectlist(String string) {
        Pattern pattern = Pattern.compile("\\{\\\"[0-9]*\\\":");
        Matcher matcher = pattern.matcher(string);
        String s = matcher.replaceAll("[");
        Pattern pattern2 = Pattern.compile("\\},\\\"status\\\"");
        Matcher matcher2 = pattern2.matcher(s);
        String k = matcher2.replaceAll("\\],\\\"status\\\"");
        Pattern pattern3 = Pattern.compile("\\[\\]");
        Matcher matcher3 = pattern3.matcher(k);
        String j = matcher3.replaceAll("{\"chapter_id\":\"\",\"title\":\"\",\"is_vip\":\"\"}");
        Pattern pattern4 = Pattern.compile("\\\"[0-9]*\\\":");
        Matcher matcher4 = pattern4.matcher(j);
        return matcher4.replaceAll("");
    }

    /**
     * 将string转化为分享文案样式
     */
    public static String string2ShareText(String intro, String title) {
        int introNum = intro.length();
        int titleNum = title.length();
        String newString;
        if (introNum + titleNum > 119) {
            newString = intro.substring(0, 115 - titleNum) + "...";
        } else {
            newString = intro;
        }
        return "#网兜小说#" + newString + "#" + title + "#";
    }

    public static Map<String, String> getMapFromBook(Book book) {
        Map<String, String> map = new HashMap<>();
        map.put(BookTable.BOOK_ID, book.getBook_id());
        map.put(BookTable.FILE_PATH, book.getFilePath());
        map.put(BookTable.IMAGE_URL,  book.getImg());
        map.put(BookTable.INTRO, book.getIntro());
        map.put(BookTable.TOTAL_PAGE, book.getDownloadstatus() ? "0" : "-1");
        map.put(BookTable.DOWNLOAD_STATE,  "0");
        map.put(BookTable.IS_ONLINE_BOOK,  book.getIsOnlineBook() ? "0" : "1");
        map.put(BookTable.TITLE, book.getTitle());
        map.put("type", "");
        return map;
    }

    public static String getStringFromCursor(Cursor cursor, String field) {
        int count = cursor.getColumnIndexOrThrow(field);
        String value = cursor.getString(count);
        return StringConvertUtil.changeNullToEmpty(value);
    }

    /**
     * 将null的字符串替换为""
     */
    public static String changeNullToEmpty(String str) {
        if (str == null || str.trim().equalsIgnoreCase("")) {
            return "";
        }
        return str;
    }

    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }
}