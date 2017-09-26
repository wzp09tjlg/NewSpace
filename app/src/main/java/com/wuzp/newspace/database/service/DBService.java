package com.wuzp.newspace.database.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.wuzp.newspace.database.helper.DBOpenHelper;
import com.wuzp.newspace.database.table.BookTable;
import com.wuzp.newspace.database.table.ChapterTable;
import com.wuzp.newspace.network.entity.read.Book;
import com.wuzp.newspace.network.entity.read.Chapter;
import com.wuzp.newspace.network.model.read.BookModel;
import com.wuzp.newspace.network.model.read.ChapterModel;
import com.wuzp.newspace.utils.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装所有数据库操作.
 */
@SuppressWarnings("all")
public class DBService {
    private static final String TAG = "DBService";
    private final static byte[] _writeLock = new byte[0];//写锁

    /**
     * 确保它的单例存在<br>
     * 只在程序启动时做一次创建
     */
    public static DBOpenHelper sDbOpenHelper;

    /**
     * 初始化DBService<br>
     * 初始化单例的sDbOpenHelper<br>
     * SQLiteDatabase会在sDbOpenHelper中缓存<br>
     * 确认开始时调用<br>
     */
    public synchronized static void init(Context context) {
        sDbOpenHelper = new DBOpenHelper(context);
        try {
            sDbOpenHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放DBService<br>
     * 确认程序退出时释放<br>
     */
    public synchronized static void close() {
        try {
            if (sDbOpenHelper != null) {
                sDbOpenHelper.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除所有表的数据
     */
    public synchronized static void clear() {
        SQLiteDatabase db = null;
        try {
            db = sDbOpenHelper.getWritableDatabase();
            if (db == null) {
                return;
            }
            db.beginTransaction();
            db.execSQL("delete from " + BookTable.TABLE_NAME);
            db.execSQL("delete from " + ChapterTable.TABLE_NAME);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
        } finally {
            try {
                if (db != null && db.inTransaction()) {
                    db.endTransaction();
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * update
     */
    public static void updateData(String tableName, String[] keys, String[] values, String where, String[] args) {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                ContentValues content = new ContentValues();
                for (int i = 0; i < keys.length; i++) {
                    content.put(keys[i], values[i]);
                }
                db.update(tableName, content, where, args);
                db.setTransactionSuccessful();
                LogUtil.e("更新数据 成功");
            } catch (Exception e) {
                LogUtil.e(e.getMessage());
                LogUtil.e("更新数据 出现错误");
            } finally {
                try {
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public static void updateData(String tableName, String keys, String values, String where, String[] args) {
        updateData(tableName, new String[]{keys}, new String[]{values}, where, args);
    }

    /***********************************
     * Book相关Method开始
     ************************************/


    public static void updateBooksByFilePath(String[] field, String[] value, String filepath) {
        updateData(BookTable.TABLE_NAME, field, value, BookTable.FILE_PATH + " = ?  ", new String[]{filepath});
    }

    /**
     * 更新多本书籍通过bookid
     *
     * @param field  键
     * @param value  值
     * @param Bookid bookid
     */
    public static void updateBooksByBookid(String[] field, String[] value, String Bookid) {
        updateData(BookTable.TABLE_NAME, field, value, BookTable.BOOK_ID + " = ?", new String[]{Bookid});
    }

    /**
     * 更新单本书籍
     *
     * @param field  键
     * @param value  值
     * @param Bookid bookid
     */
    public static void updateBookByBookid(String field, String value, String Bookid) {
        updateBooksByBookid(new String[]{field}, new String[]{value}, Bookid);
    }

    public static void updateBookUidByBookid(String bookid) {
        updateData(BookTable.TABLE_NAME, BookTable.UID, "", BookTable.BOOK_ID + " = ? ", new String[]{bookid});
    }

    public static void updateBookUidByFilepath(String filepath) {
        updateData(BookTable.TABLE_NAME, BookTable.UID, "", BookTable.FILE_PATH + " = ? ", new String[]{filepath});
    }


    public static void deteleBookByUidWithBookid(String bookid) {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                db.delete(BookTable.TABLE_NAME, BookTable.BOOK_ID + " = ?", new String[]{bookid});
                db.setTransactionSuccessful();
            } catch (Exception e) {
                LogUtil.e(e.getMessage());
            } finally {
                try {
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
        }
    }


    public static void deteleBookByUidWithFilepath(String filepath) {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                db.delete(BookTable.TABLE_NAME, BookTable.FILE_PATH + " = ? ", new String[]{filepath});
                db.setTransactionSuccessful();
            } catch (Exception e) {
                LogUtil.i(e.getMessage());
            } finally {
                try {
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public static List<Book> queryAllBooks(boolean containAddfail) {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            List<Book> list = new ArrayList<>();
            try {
                db = sDbOpenHelper.getReadableDatabase();
                if (db == null) {
                    return null;
                }
                db.beginTransaction();
                String flag = containAddfail ? " in ('normal','addfail')" : " = 'normal' ";
                cursor = db.query(BookTable.TABLE_NAME, null, BookTable.FLAG + flag, null, null, null, null);
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    Book book = BookModel.getBookByCursor(cursor);
                    list.add(book);
                    cursor.moveToNext();
                }
                db.setTransactionSuccessful();
                return list;
            } catch (Exception e) {
                
                LogUtil.e(e.getMessage());
            } finally {
                try {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }
    }


    public static List<Book> queryEmptyUidBook() {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            List<Book> list = new ArrayList<>();
            try {
                db = sDbOpenHelper.getReadableDatabase();
                if (db == null) {
                    return list;
                }
                db.beginTransaction();
                cursor = db.query(BookTable.TABLE_NAME, null, null, null, null, null, null);
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    Book book = BookModel.getBookByCursor(cursor);
                    list.add(book);
                    cursor.moveToNext();
                }
                db.setTransactionSuccessful();
                return list;
            } catch (Exception e) {
                
                LogUtil.e(e.getMessage());
            } finally {
                try {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
            return list;
        }
    }

    /**
     * 通过属性字段查询书籍信息
     */
    public static List<Book> queryInfo(String tableName, String key, String value) {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            List<Book> books = new ArrayList<>();
            try {
                db = sDbOpenHelper.getReadableDatabase();
                if (db == null) {
                    return null;
                }
                db.beginTransaction();
                cursor = db.query(tableName, null, key + " = ?  ", new String[]{value}, null, null, null);
                if (cursor.getCount() == 0) {
                    return null;
                } else {
                    cursor.moveToFirst();
                    for (int i = 0; i < cursor.getCount(); i++) {
                        Book book = BookModel.getBookByCursor(cursor);
                        books.add(book);
                        cursor.moveToNext();
                    }
                }
                db.setTransactionSuccessful();
                return books;
            } catch (Exception e) {
                
                LogUtil.e(e.getMessage());
            } finally {
                try {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }
    }

    /**
     * 通过bookid查询book信息
     */
    public static Book queryBooksInfoBybookid(String bookid) {
        List<Book> books = queryInfo(BookTable.TABLE_NAME, BookTable.BOOK_ID, bookid);
        if (books != null && books.size() > 0) {
            return books.get(0);
        } else {
            return null;
        }
    }

    /**
     * 通过bookid查询book信息 并且考虑flag
     */
    public static Book queryBooksInfoBybookidAndFlag(String bookid) {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            List<Book> books = new ArrayList<>();
            try {
                db = sDbOpenHelper.getReadableDatabase();
                if (db == null) {
                    return null;
                }
                db.beginTransaction();
                cursor = db.query(BookTable.TABLE_NAME, null, BookTable.BOOK_ID + " = ?  and " + BookTable.FLAG + " in ('normal','addfail')", new String[]{bookid}, null, null, null);
                if (cursor.getCount() == 0) {
                    return null;
                } else {
                    cursor.moveToFirst();
                    for (int i = 0; i < cursor.getCount(); i++) {
                        Book book = BookModel.getBookByCursor(cursor);
                        books.add(book);
                        cursor.moveToNext();
                    }
                }
                db.setTransactionSuccessful();
                return books.get(0);
            } catch (Exception e) {
                
                LogUtil.e(e.getMessage());
            } finally {
                try {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }
    }

    /**
     * 通过filepath查询book信息
     */
    public static Book qureyBookInfoByFilepath(String filepath) {
        List<Book> books = queryInfo(BookTable.TABLE_NAME, BookTable.FILE_PATH, filepath);
        if (books != null && books.size() > 0) {
            return books.get(0);
        } else {
            return null;
        }
    }

    /**
     * 通过bookid删除多本书籍
     */
    public static void deleteBooksByBookid(List<String> list) {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                for (int i = 0; i < list.size(); i++) {
                    db.delete(BookTable.TABLE_NAME, BookTable.BOOK_ID + " = ? ", new String[]{list.get(i)});
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
                 
                LogUtil.e(e.getMessage());
            } finally {
                try {
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 通过filepath删除书籍
     */
    public static void deleteBookByFilePath(String filePath) {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                db.delete(BookTable.TABLE_NAME, BookTable.FILE_PATH + " = ?", new String[]{filePath});
                db.setTransactionSuccessful();
            } catch (Exception e) {
                LogUtil.e(e.getMessage());
            } finally {
                try {
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 通过bookid删除书籍
     *
     * @param bookid bookid
     */
    public static void deleteBookByBookid(String bookid) {
        List<String> list = new ArrayList<>();
        list.add(bookid);
        deleteBooksByBookid(list);
    }

    public static int saveLocalBook(File file) {
        synchronized (_writeLock) {
            Book book = new Book();
            book.setTitle(file.getName());
            book.setIsOnlineBook(false);
            book.setFilePath(file.getAbsolutePath());
            book.setFileSize("" + file.length());
            book.setDownloadstatus(true);
            book.setLastreadtime(System.currentTimeMillis());
            book.setBook_id("-1");//本地导入书籍bookid设置为-1
            SQLiteDatabase db = null;
            Cursor cursor = null;
            int isSuccess = -1;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return isSuccess;
                }
                db.beginTransaction();
                ContentValues values = BookModel.setBookAllValues(book, false);
                cursor = db.query(BookTable.TABLE_NAME, null, BookTable.FILE_PATH + " = ? ", new String[]{file.getAbsolutePath()}, null, null, null);
                int count = cursor.getCount();
                if (count == 0) {
                    db.insert(BookTable.TABLE_NAME, null, values);
                    isSuccess = 0;//添加成功
                } else {
                    isSuccess = 1;//添加失败
                }
                db.setTransactionSuccessful();
                return isSuccess;
            } catch (Exception e) {
                
                LogUtil.e(e.getMessage());
            } finally {
                try {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
            return -1;
        }
    }

    /**
     * 向数据库中保存一条数据
     *
     * @param insertBooks insertBooks
     */
    public static void saveBook(Book insertBooks, boolean isInlayBook) {
        List<Book> list = new ArrayList<>();
        list.add(insertBooks);
        saveBooksChange(list, isInlayBook);
    }

    /**
     * 向数据库中保存多条数据
     * 向数据库中保存多条数据 已存在的进行更新
     *
     * @param insertBooks books
     */
    public static void saveBooksChange(List<Book> insertBooks, boolean isInlayBook) {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            List<String> bookids = new ArrayList<>();
            List<String> netReadTimes = new ArrayList<>();
            List<String> downloadTimes = new ArrayList<>();
            try {
                if (insertBooks == null || insertBooks.size() == 0) {
                    return;
                }
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                cursor = db.query(BookTable.TABLE_NAME, new String[]{BookTable.BOOK_ID, BookTable.NET_READ_TIME, BookTable.DOWNLOAD_TIME},null, null, null, null, null);
                if (cursor.getCount() > 0) {
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);
                        bookids.add(cursor.getString(0));
                        netReadTimes.add(cursor.getString(1));
                        downloadTimes.add(cursor.getString(2));
                    }
                }
                for (int i = 0; i < insertBooks.size(); i++) {
                    if (bookids.contains(insertBooks.get(i).getBook_id())) {
                        int index = bookids.indexOf(insertBooks.get(i).getBook_id());
                        db.update(BookTable.TABLE_NAME, BookModel.setBookNetValues(insertBooks.get(i), Long.valueOf(netReadTimes.get(index)) < insertBooks.get(i).getNetReadTime(), Long.valueOf(downloadTimes.get(index)) < insertBooks.get(i).getDownloadTime()), BookTable.BOOK_ID + " = ? ", new String[]{insertBooks.get(i).getBook_id(), ""});
                    } else {
                        db.insert(BookTable.TABLE_NAME, null, BookModel.setBookAllValues(insertBooks.get(i), isInlayBook));
                    }
                }
                LogUtil.e("save book success" );
                db.setTransactionSuccessful();
            } catch (Exception e) {
                LogUtil.e("save book failure" + e.getMessage());
                LogUtil.e(e.getMessage());
            } finally {
                try {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
        }
    }


    /***********************************
     * ChapterTable开始
     * **********************************
     * <p>
     * <p>
     * <p>
     * /**
     *
     * @param chapters    章节列表
     * @param allowUpadte 是否允许更新
     */
    public static long saveChapterInfo(List<Chapter> chapters, boolean allowUpadte) {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            List<String> chapterIds = new ArrayList<>();
            try {
                if (chapters == null || chapters.size() == 0) {
                    return 0;
                }
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return 0;
                }
                db.beginTransaction();
                cursor = db.query(ChapterTable.TABLE_NAME, new String[]{ChapterTable.CHAPTER_ID}, ChapterTable.TAG + " = ? and " + ChapterTable.CHAPTER_FLAGS + " = ?",
                        new String[]{chapters.get(0).getTag(), ""}, null, null, null);
                if (cursor.getCount() > 0) {
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);
                        chapterIds.add(cursor.getString(0));
                    }
                }
                for (int i = 0; i < chapters.size(); i++) {
                    if (chapterIds.contains(chapters.get(i).getC_id())) {
                        if (allowUpadte) {
                            db.update(ChapterTable.TABLE_NAME, ChapterModel.setChapterValues(chapters.get(i)),
                                    ChapterTable.TAG + " = ? and " + ChapterTable.CHAPTER_ID + " = ? and " + ChapterTable.CHAPTER_FLAGS + " = ? ",
                                    new String[]{chapters.get(i).getTag(), chapters.get(i).getC_id(), ""});
                        }
                    } else {
                        db.insert(ChapterTable.TABLE_NAME, null, ChapterModel.setChapterValues(chapters.get(i)));
                    }
                }
                db.setTransactionSuccessful();
                return chapters.size();
            } catch (Exception e) {
                
                LogUtil.e(e.getMessage());
            } finally {
                try {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
            return 0;
        }
    }

    public static long saveChapterInfo(List<Chapter> chapters) {
        return saveChapterInfo(chapters, true);
    }

    public static long saveChapterInfo(Chapter chapter) {
        return saveChapterInfo(chapter, false);
    }

    public static long saveChapterInfo(Chapter chapter, boolean update) {
        List<Chapter> chapters = new ArrayList<>();
        chapters.add(chapter);
        return saveChapterInfo(chapters, update);
    }

    public static long saveNewChapterInfo(List<Chapter> chapters) {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                db.beginTransaction();
                for (int i = 0; i < chapters.size(); i++) {
                    String insertSQL = "insert into " + ChapterTable.TABLE_NAME + "(" + ChapterTable.BOOK_ID + "," + ChapterTable.CHAPTER_ID
                            + "," + ChapterTable.IS_VIP + "," + ChapterTable.SERIAL_NUMBER + ","
                            + ChapterTable.LENGTH + "," + ChapterTable.START_POS + "," + ChapterTable.TITLE
                            + "," + ChapterTable.TAG + "," + ChapterTable.CHAPTER_FLAGS + ") values " + "(?,?,?,?,?,?,?,?,?)";
                    SQLiteStatement stat = db.compileStatement(insertSQL);
                    stat.bindString(1, chapters.get(i).getBook_id());
                    stat.bindString(2, chapters.get(i).getC_id());
                    stat.bindString(3, chapters.get(i).getVip());
                    stat.bindLong(4, chapters.get(i).getS_num());
                    stat.bindLong(5, chapters.get(i).getLength());
                    stat.bindLong(6, chapters.get(i).getStartPos());
                    stat.bindString(7, chapters.get(i).getTitle());
                    stat.bindString(8, chapters.get(i).getTag());
                    stat.bindString(9, "");
                    stat.executeInsert();
                }
                db.setTransactionSuccessful();
                return chapters.size();
            } catch (Exception e) {
                
                LogUtil.e(e.getMessage());
            } finally {
                try {
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
            return 0;
        }
    }


    public static void setChapterUid(String tag) {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                ContentValues values = new ContentValues();
                values.put(ChapterTable.CHAPTER_FLAGS, "");
                db.update(ChapterTable.TABLE_NAME, values, ChapterTable.CHAPTER_FLAGS + " = ? and " + ChapterTable.TAG + " = ? ", new String[]{"", tag});
                db.setTransactionSuccessful();
            } catch (Exception e) {
                
                LogUtil.e(e.getMessage());
            } finally {
                try {
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public static void deteleChapterByTagwithNulluid(String tag) {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                db.delete(ChapterTable.TABLE_NAME, ChapterTable.TAG + " = ? and " + ChapterTable.CHAPTER_FLAGS + " = ?", new String[]{tag, ""});
                db.setTransactionSuccessful();
            } catch (Exception e) {
                
                LogUtil.e(e.getMessage());
            } finally {
                try {
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public static void resetAllChapterInfo(String bookid) {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                db.beginTransaction();
                ContentValues content = new ContentValues();
                content.put(ChapterTable.START_POS, 0);
                content.put(ChapterTable.LENGTH, 0);
                db.update(ChapterTable.TABLE_NAME, content,
                        ChapterTable.BOOK_ID + " = ? and " + ChapterTable.CHAPTER_FLAGS + " = ?", new String[]{
                                bookid, ""});
                db.setTransactionSuccessful();
            } catch (Exception e) {
                
                LogUtil.e(e.getMessage());
            } finally {
                try {
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
        }
    }


    /**
     * 查询章节信息通过bookid和snum（针对在线书籍）
     *
     * @param chapterid cid
     * @return 章节对象
     */
    public static Chapter queryChapterInfoByTagWithCid(String tag, String chapterid) {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            Chapter chapter;
            try {
                if (chapterid == null) {
                    return null;
                }
                db = sDbOpenHelper.getReadableDatabase();
                db.beginTransaction();
                cursor = db.query(ChapterTable.TABLE_NAME, null,
                        ChapterTable.TAG + " = ? and " + ChapterTable.CHAPTER_ID + " = ? and " + ChapterTable.CHAPTER_FLAGS + " = ? ",
                        new String[]{tag, chapterid, ""}, null, null, ChapterTable.SERIAL_NUMBER + " ASC");
                if (cursor.getCount() == 0) {
                    return null;
                }
                cursor.moveToFirst();
                chapter = ChapterModel.getChapterByCursor(cursor);
                db.setTransactionSuccessful();
                return chapter;
            } catch (Exception e) {
                
                LogUtil.e(e.getMessage());
            } finally {
                try {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }
    }

    /**
     * 通过bookid删除书籍
     */
    public static void deleteChapterByTag(String tag) {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            try {
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return;
                }
                db.beginTransaction();
                db.delete(ChapterTable.TABLE_NAME, ChapterTable.TAG + " = ? and " + ChapterTable.CHAPTER_FLAGS + " = ?", new String[]{tag, ""});
                db.setTransactionSuccessful();
            } catch (Exception e) {
                
                LogUtil.e(e.getMessage());
            } finally {
                try {
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 查询所有章节
     *
     * @param tag tag
     * @return 章节列表
     */
    public static List<Chapter> queryChapterByTag(String tag) {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = sDbOpenHelper.getReadableDatabase();
                db.beginTransaction();
                cursor = db.query(ChapterTable.TABLE_NAME, new String[]{ChapterTable.BOOK_ID,ChapterTable.START_POS,ChapterTable.LENGTH,
                                ChapterTable.TITLE,ChapterTable.CHAPTER_ID,ChapterTable.SERIAL_NUMBER,ChapterTable.IS_VIP,ChapterTable.TAG},
                        ChapterTable.TAG + "= ? and " + ChapterTable.CHAPTER_FLAGS + " = ?", new String[]{tag, ""}, null, null, ChapterTable.SERIAL_NUMBER + " ASC");
                db.setTransactionSuccessful();
                return ChapterModel.getChaptersByCursor(cursor);
            } catch (Exception e) {
                LogUtil.i(e.getMessage());
            } finally {
                try {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }
    }

    /**
     * Update new chapter. 数据库是阻塞的，更新时应该做尽量少的更新操作
     *
     * @param newChapters the chapters
     */
    public static boolean updateNewChapter(List<Chapter> newChapters) {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            boolean isUpdateBook = false;
            List<String> chapterIds = new ArrayList<>();
            try {
                if (newChapters == null || newChapters.size() == 0) {
                    return isUpdateBook;
                }
                db = sDbOpenHelper.getWritableDatabase();
                if (db == null) {
                    return isUpdateBook;
                }
                db.beginTransaction();
                cursor = db.query(ChapterTable.TABLE_NAME, new String[]{ChapterTable.CHAPTER_ID}, ChapterTable.TAG + " = ? and " + ChapterTable.CHAPTER_FLAGS + " = ?",
                        new String[]{newChapters.get(0).getTag(), ""}, null, null, null);
                if (cursor.getCount() > 0) {
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);
                        chapterIds.add(cursor.getString(0));
                    }
                }
                for (int i = 0; i < newChapters.size(); i++) {
                    if (chapterIds.contains(newChapters.get(i).getC_id())) {
                        db.update(ChapterTable.TABLE_NAME, ChapterModel.updateChapterValues(newChapters.get(i)),
                                ChapterTable.TAG + " = ? and " + ChapterTable.CHAPTER_ID + " = ? and " + ChapterTable.CHAPTER_FLAGS + " = ? ",
                                new String[]{newChapters.get(i).getTag(), newChapters.get(i).getC_id(), ""});
                    } else {
                        db.insert(ChapterTable.TABLE_NAME, null, ChapterModel.setChapterValues(newChapters.get(i)));
                        isUpdateBook = true;
                    }
                }
                db.setTransactionSuccessful();
                return isUpdateBook;
            } catch (Exception e) {
                LogUtil.i(e.getMessage());
            } finally {
                try {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
            return isUpdateBook;
        }
    }

    /**
     * 查询章节信息，通过bookid和pos
     *
     * @param tag 在线书籍tag= bookid  本地书籍tag= filepath   章节列表字段tag已赋值
     * @param Pos 字节pos
     * @return 章节对象
     */
    public static Chapter queryChapterInfoByTagWithPos(String tag, int Pos) {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            Chapter chapter;
            try {
                db = sDbOpenHelper.getReadableDatabase();
                db.beginTransaction();
                cursor = db.query(ChapterTable.TABLE_NAME, null, ChapterTable.TAG + "= ? and " + ChapterTable.CHAPTER_FLAGS + " = ?", new String[]{tag, ""}, null, null, ChapterTable.SERIAL_NUMBER + " ASC");
                if (cursor.getCount() == 0) {
                    return null;
                }
                chapter = ChapterModel.getChapterInfoByCursorWithPos(cursor, Pos);
                db.setTransactionSuccessful();
                return chapter;
            } catch (Exception e) {
                LogUtil.i(e.getMessage());
            } finally {
                try {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }
    }

    public static String queryNextChapterIdByTagWithChapterId(String tag, String chapterid, boolean next) {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            String nextChapterId;
            try {
                db = sDbOpenHelper.getReadableDatabase();
                db.beginTransaction();
                cursor = db.query(ChapterTable.TABLE_NAME, new String[]{ChapterTable.ID, ChapterTable.CHAPTER_ID}, ChapterTable.TAG + " = ? and " + ChapterTable.CHAPTER_FLAGS + " = ?", new String[]{tag, ""}, null, null, ChapterTable.SERIAL_NUMBER + " ASC");
                if (cursor.getCount() == 0) {
                    return null;
                }
                if (chapterid.equals("-1") && next) {
                    cursor.moveToFirst();
                    return cursor.getString(1);
                }
                if (chapterid.equals("-1") && !next) {
                    return null;
                }
                nextChapterId = ChapterModel.getNextChapterIdByCursorWithChapterId(cursor, chapterid, next);
                db.setTransactionSuccessful();
                return nextChapterId;
            } catch (Exception e) {
                LogUtil.e(e.getMessage());
            } finally {
                try {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }
    }

    public static int queryChapterPosByTagWithChapterId(String tag, String chapterid) {
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            int chapterPos;
            try {
                db = sDbOpenHelper.getReadableDatabase();
                db.beginTransaction();
                cursor = db.query(ChapterTable.TABLE_NAME, new String[]{ChapterTable.CHAPTER_ID}, ChapterTable.TAG + "= ? and " + ChapterTable.CHAPTER_FLAGS + " = ?", new String[]{tag, ""}, null, null, ChapterTable.SERIAL_NUMBER + " ASC");
                if (cursor.getCount() == 0) {
                    return -1;
                }
                if (chapterid.equals("-1")) {
                    return 0;
                }
                chapterPos = ChapterModel.getChapterPosByCursorWithChapterId(cursor, chapterid);
                db.setTransactionSuccessful();
                return chapterPos;
            } catch (Exception e) {
                
                LogUtil.e(e.getMessage());
            } finally {
                try {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (db != null && db.inTransaction()) {
                        db.endTransaction();
                    }
                } catch (Exception e) {
                }
            }
            return 0;
        }
    }
}