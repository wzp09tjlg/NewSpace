package com.wuzp.newspace.database.table;

/**
 * 章节表
 */
public class ChapterTable {
    /**
     * 表名
     */
    public static final String TABLE_NAME = "Chapter";

    public static final String ID = "_id";

    public static final String BOOK_ID = "bookId";
    public static final String CHAPTER_ID = "chapterId";
    public static final String TITLE = "title";
    public static final String START_POS = "startPos";
    public static final String LENGTH = "length";
    public static final String IS_VIP = "isVip";
    public static final String GLOBAL_CHAPTER_ID = "globalChapterId";
    public static final String TAG = "tag";
    public static final String CHAPTER_FLAGS = "chapterFlags";
    public static final String SERIAL_NUMBER = "serialNumber";

    /**
     * 章节表列名称数组<br>
     * 注意：顺序不能变
     */
    public static final String[] COLUMNS = {ID, BOOK_ID, CHAPTER_ID, TITLE,
            START_POS, LENGTH, IS_VIP, GLOBAL_CHAPTER_ID, TAG, CHAPTER_FLAGS, SERIAL_NUMBER};

    /**
     * 获取建表SQL语句
     */
    public static String getCreateSQL() {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME);
        builder.append(" (");

        builder.append(ID).append(" integer primary key autoincrement, ");
        builder.append(BOOK_ID).append(" integer not null, ");
        builder.append(CHAPTER_ID).append(" integer, ");
        builder.append(TITLE).append(" varchar(512), ");
        builder.append(START_POS).append(" integer, ");
        builder.append(LENGTH).append(" integer, ");
        builder.append(IS_VIP).append(" varchar(20), ");
        builder.append(GLOBAL_CHAPTER_ID).append(" integer, ");
        builder.append(TAG).append(" integer, ");
        builder.append(CHAPTER_FLAGS).append(" integer DEFAULT (0), ");
        builder.append(SERIAL_NUMBER).append(" integer DEFAULT (-1)");

        builder.append(")");
        return builder.toString();
    }

    /**
     * 获取删表SQL语句
     */
    public static String getDeleteSQL() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
