package com.wuzp.newspace.utils.database;

import android.database.Cursor;

import com.wuzp.newspace.database.table.BookTable;
import com.wuzp.newspace.network.entity.read.Book;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zyb on 2016/9/27.
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

    public static Map<String, String> strings2MapFromBook(String bookId, String filepath, String imageUrl, String intro, String num, String download, String isonlinebook, String title, String type) {
        Map<String, String> map = new HashMap<>();
        map.put(BookTable.BOOK_ID, bookId);
        map.put(BookTable.FILE_PATH, filepath);
        map.put(BookTable.IMAGE_URL, imageUrl);
        map.put(BookTable.INTRO, intro);
        map.put(BookTable.TOTAL_PAGE, num);
        map.put(BookTable.DOWNLOAD_STATE, download);
        map.put(BookTable.IS_ONLINE_BOOK, isonlinebook);
        map.put(BookTable.TITLE, title);
        map.put("type", type);
        return map;
    }

    public static String getStringFromCursor(Cursor cursor, String field) {
        int count = cursor.getColumnIndexOrThrow(field);
        String value = cursor.getString(count);
        return StringConvertUtil.changeNullToEmpty(value);
    }

    public static Map<String, String> getMapFromCursor(Cursor cursor, String... strings) {
        return strings2MapFromBook(getStringFromCursor(cursor, strings[0]), getStringFromCursor(cursor, strings[1]),
                getStringFromCursor(cursor, strings[2]), getStringFromCursor(cursor, strings[3]),
                getStringFromCursor(cursor, strings[4]), getStringFromCursor(cursor, strings[5]), getStringFromCursor(cursor, strings[6]), getStringFromCursor(cursor, strings[7]), "");
    }

    public static Book getBookFromCursor(Cursor cursor){
        Book tempBook = new Book();
        tempBook.setBook_id(cursor.getString(cursor.getColumnIndex(BookTable.BOOK_ID)));
        tempBook.setUpdateChaptersNum(cursor.getInt(cursor.getColumnIndex(BookTable.UPDATE_CHAPTER_NUM)));
        tempBook.setIsOnlineBook(0 == cursor.getInt(cursor.getColumnIndex(BookTable.IS_ONLINE_BOOK)));
        tempBook.setFilePath(cursor.getString(cursor.getColumnIndex(BookTable.FILE_PATH)));
        tempBook.setTitle(cursor.getString(cursor.getColumnIndex(BookTable.TITLE)));
        //NUM (章节数)   Book的bean中无对应
        //TAG (是否下架) Book的bean中无对应
        //BOOK_CONTENT_TYPE （是否有版权） Book的bean中无对应
        tempBook.setImg(cursor.getString(cursor.getColumnIndex(BookTable.IMAGE_URL)));
        tempBook.setIntro(cursor.getString(cursor.getColumnIndex(BookTable.INTRO)));
        //TOTAL_PAGE (多少页(是应用分页计算所得)) Book的bean中无对应
        tempBook.setDownloadstatus("Y".equals(cursor.getString(cursor.getColumnIndex(BookTable.DOWNLOAD_STATE))));
        return tempBook;
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
