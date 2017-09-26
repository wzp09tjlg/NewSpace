package com.wuzp.newspace.database.table;

/**
 * 书籍表
 */
public class BookTable {
    /**
     * 表名
     */
    public static final String TABLE_NAME = "Book";

    public static final String ID = "_id";

    @Deprecated
    public static final String NO = "no"; // useless
    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
    public static final String NUM = "num";
    public static final String INTRO = "intro";
    public static final String IMAGE_URL = "imageUrl";
    public static final String FILE_PATH = "filePath";
    public static final String FILE_SIZE = "fileSize";
    @Deprecated
    public static final String END = "end"; // useless
    public static final String FLAG = "flag";
    public static final String PROGRESS = "progress";
    public static final String DOWNLOAD_STATE = "downLoadState";
    public static final String TOTAL_PAGE = "totalPage";
    public static final String LAST_POS = "lastPos";
    public static final String LAST_FONTSIZE = "lastFontSize";
    public static final String BOOK_ID = "bookId";
    public static final String UPDATE_CHAPTER_NUM = "updatedChapterNum";
    public static final String TAG = "tag";
    public static final String LAST_PAGE = "lastPage";
    public static final String PAY_TYPE = "payType";
    public static final String PRICE = "price";
    public static final String LAST_READ_TIME = "lastReadTime";
    public static final String ORIGINAL_FILE_PATH = "originalFilePath";
    public static final String STATUS_INFO = "statusInfo";
    public static final String LAST_UPDATE_TIME = "lastUpdateTime";
    public static final String LAST_READ_PERCENT = "lastReadPercent";
    public static final String DOWNLOAD_TIME = "downloadTime";
    public static final String VDISK_DOWNLOAD_URL = "vdiskDownloadUrl";
    public static final String VDISK_FILE_PATH = "vdiskFilePath";
    public static final String UID = "uid";
    public static final String SINA_ID = "sid";

    @Deprecated
    public static final String IS_PARISE = "isParise";
    public static final String BOOK_CONTENT_TYPE = "contentType";
    public static final String SUITE_ID = "suiteId";
    public static final String ORIGIN_SUITE_ID = "originSuiteId";
    public static final String SUITE_NAME = "suiteName";
    public static final String AUTO_BUY = "autoBuy";
    public static final String IS_ONLINE_BOOK = "isOnlineBook";
    public static final String OWNER_UID = "ownerUid";
    public static final String ONLINE_READ_CHAPTER_ID = "onlineReadChapterId";
    public static final String IS_REMIND = "isRemind";

    public static final String LAST_READ_JSON = "lastReadJsonString";

    public static final String IS_UPDATE_CHAPTER_LIST = "isUpdateList";//3.1.0  书籍库版本号18  判断是否进行过全量更新   0 已更新过  -1未更新

    public static final String NET_READ_TIME = "netReadTime";
    /**
     * 书籍表列名称数组<br>
     * 注意：顺序不能变
     */
    public static final String[] COLUMNS = {ID, NO, TITLE, AUTHOR, NUM, INTRO, IMAGE_URL, FILE_PATH, FILE_SIZE, END,
            FLAG, PROGRESS, DOWNLOAD_STATE, TOTAL_PAGE, LAST_POS, LAST_FONTSIZE, BOOK_ID, UPDATE_CHAPTER_NUM, TAG,
            LAST_PAGE, PAY_TYPE, PRICE, LAST_READ_TIME, ORIGINAL_FILE_PATH, STATUS_INFO, LAST_UPDATE_TIME,
            LAST_READ_PERCENT, DOWNLOAD_TIME, VDISK_DOWNLOAD_URL, VDISK_FILE_PATH, UID, SINA_ID, IS_PARISE,
            BOOK_CONTENT_TYPE, SUITE_ID, ORIGIN_SUITE_ID, SUITE_NAME, AUTO_BUY, IS_ONLINE_BOOK, OWNER_UID,
            ONLINE_READ_CHAPTER_ID, IS_REMIND, LAST_READ_JSON, IS_UPDATE_CHAPTER_LIST,NET_READ_TIME};
    public static final String[] COLUMNS_TEST = {BOOK_ID,UPDATE_CHAPTER_NUM,IS_ONLINE_BOOK,IMAGE_URL,FILE_PATH,TITLE,NUM,TAG,INTRO,TOTAL_PAGE,DOWNLOAD_STATE};

    /**
     * 获取建表SQL语句
     */
    public static String getCreateSQL() {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME);
        builder.append(" (");

        builder.append(ID).append(" integer primary key autoincrement, ");
        builder.append(NO).append(" varchar(20), ");
        builder.append(TITLE).append(" varchar(256), ");
        builder.append(AUTHOR).append(" varchar(20), ");
        builder.append(NUM).append(" varchar(20), ");
        builder.append(INTRO).append(" text, ");
        builder.append(IMAGE_URL).append(" varchar(256), ");
        builder.append(FILE_PATH).append(" varchar(256), ");
        builder.append(FILE_SIZE).append(" varchar(20), ");
        builder.append(END).append(" varchar(20), ");
        builder.append(FLAG).append(" varchar(20), ");
        builder.append(PROGRESS).append(" varchar(20), ");
        builder.append(DOWNLOAD_STATE).append(" varchar(20), ");
        builder.append(TOTAL_PAGE).append(" integer DEFAULT (0), ");
        builder.append(LAST_POS).append(" integer DEFAULT (0),");
        builder.append(LAST_FONTSIZE).append(" integer DEFAULT (0), ");
        builder.append(BOOK_ID).append(" varchar(20), ");
        builder.append(UPDATE_CHAPTER_NUM).append(" integer DEFAULT (0), ");
        builder.append(TAG).append(" integer DEFAULT (0), ");
        builder.append(LAST_PAGE).append(" integer DEFAULT (0), ");
        builder.append(PAY_TYPE).append(" integer DEFAULT (0), ");
        builder.append(PRICE).append(" integer DEFAULT (0), ");
        builder.append(LAST_READ_TIME).append(" largeint(256), ");
        builder.append(ORIGINAL_FILE_PATH).append(" varchar(256) , ");
        builder.append(STATUS_INFO).append(" varchar(20), ");
        builder.append(LAST_UPDATE_TIME).append(" largeint(256) DEFAULT (0), ");
        builder.append(LAST_READ_PERCENT).append(" float(20) DEFAULT(0), ");
        builder.append(DOWNLOAD_TIME).append(" largeint(256), ");
        builder.append(VDISK_DOWNLOAD_URL).append(" varchar(256), ");
        builder.append(VDISK_FILE_PATH).append(" varchar(256), ");
        builder.append(UID).append(" varchar(256), ");
        builder.append(SINA_ID).append(" varchar(50), ");
        builder.append(IS_PARISE).append(" integer DEFAULT (0), ");
        builder.append(BOOK_CONTENT_TYPE).append(" integer DEFAULT (0), ");
        builder.append(SUITE_ID).append(" integer DEFAULT (0), ");
        builder.append(ORIGIN_SUITE_ID).append(" integer DEFAULT (0), ");
        builder.append(SUITE_NAME).append(" varchar(80), ");
        builder.append(AUTO_BUY).append(" integer DEFAULT (0), ");
        builder.append(IS_ONLINE_BOOK).append(" integer DEFAULT (0), ");
        builder.append(OWNER_UID).append(" varchar(256), ");
        builder.append(ONLINE_READ_CHAPTER_ID).append(" integer DEFAULT (0), ");
        builder.append(IS_REMIND).append(" integer DEFAULT (1), ");
        builder.append(LAST_READ_JSON).append(" text, ");
        builder.append(IS_UPDATE_CHAPTER_LIST).append(" integer DEFAULT (0), ");
        builder.append(NET_READ_TIME).append(" largeint(256) DEFAULT (-1) ");

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
