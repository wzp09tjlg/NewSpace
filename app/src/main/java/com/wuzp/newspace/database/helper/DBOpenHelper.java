package com.wuzp.newspace.database.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.wuzp.newspace.base.BaseApp;
import com.wuzp.newspace.database.table.BookTable;
import com.wuzp.newspace.database.table.ChapterTable;
import com.wuzp.newspace.utils.LogUtil;
import com.wuzp.newspace.utils.database.StorageUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 数据库创建
 */
public class DBOpenHelper extends SdCardSQLiteOpenHelper {
    private static final String TAG = "DBOpenHelper";
    public static final  String DB_NAME = "newreader.db";
    // TODO 数据库的版本号不能随意改动，只有在数据库中的某个表需要增加或删除某个字段时升级版本号
    private static final int VERSION = 1;
    private static final int NOW_VERSION_BOOK_COLUMNS = 45;
    private static final int NOW_VERSION_CHAPTER_COLUMNS = 11;

    public Context context;

    public DBOpenHelper(Context context) {
        super(context.getFilesDir().getPath().endsWith(File.separator) ? (context.getFilesDir().getPath()) : (context.getFilesDir().getPath() + File.separator) , DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public SQLiteDatabase onCreateDatabase(String dbPath, String dbName, CursorFactory factory) {
        LogUtil.d(TAG, "db onCreateDatabase");
        if (!StorageUtil.isSDCardExist()) {
            return null;
        }

        SQLiteDatabase db = null;
        // 创建数据库
        File dir = new File(dbPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File dbf = new File(dbPath + dbName);
        if (dbf.exists()) {
            dbf.delete();
        }

        // 拷贝本地书籍 针对低版本的数据库处理到 高版本的数据库
        //copyDataBase();// TODO: 2017/9/26  没有数据文件

        db = SQLiteDatabase.openOrCreateDatabase(dbf, null);
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtil.e(TAG, "db onCreate");
        try {
            // 创建书本表
            db.execSQL(BookTable.getCreateSQL());

            // 创建章节信息表
            db.execSQL(ChapterTable.getCreateSQL());
            LogUtil.e(TAG, "db onCreate, success");
        } catch (Exception e) {
            LogUtil.e(TAG, "db onCreate, Exception >> e:" + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int dbVersion, int newVersion) {
        LogUtil.e(TAG, "db onUpgrade oldVersion=" + dbVersion + ", newVersion=" + newVersion);
        LogUtil.e("ReadInfoLeft", "DBOpenHelper >> onUpgrade oldVersion=" + dbVersion + ", newVersion=" + newVersion);
        onCreate(db);

        LogUtil.e(TAG, "onUpgrade oldVersion : " + dbVersion);
        LogUtil.e(TAG, "onUpgrade newVersion : " + newVersion);
        LogUtil.e(TAG, "onUpgrade getVersion : " + db.getVersion());
        // 当新版本小于当前数据库版本时，不更新
        // 这样的话，我们的数据库需要向前兼容
        if (newVersion <= dbVersion) {
            return;
        }
        /*try {
            updateTableCheck(db);
        } catch (Exception e) {
        }*/
        db.setVersion(newVersion);
    }

    private void copyDataBase(String outFileName) {
        LogUtil.i( "copy数据库");
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            myInput = BaseApp.gContext.getAssets().open(DB_NAME);
            myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (myOutput != null) {
                    myOutput.flush();
                    myOutput.close();
                }
                if (myInput != null) {
                    myInput.close();
                }
            } catch (IOException e) {
                LogUtil.d(TAG, "close db file failed");
                e.printStackTrace();
            }
        }
    }

    private void copyDataBase() {
        String path = context.getFilesDir().getPath() + DB_NAME;
        copyDataBase(path);
    }

    /**
     * 删除所有表
     */
    private void deleteAllTable(SQLiteDatabase db, String tag) {
        LogUtil.d("DBOpenHelper", "deleteAllTable >> tag=" + tag);
        db.execSQL(BookTable.getDeleteSQL());
        db.execSQL(ChapterTable.getDeleteSQL());
    }

    /**
     * 管理各数据库版本变化，检查数据库的更新是否有效
     */
    private void updateTableCheck(SQLiteDatabase db) {
        Cursor cursor = null;
        try {
            String sql = null;
            int bookColumnsCount = -1;
            int chapterColumnsCount = -1;

            // 比对书本表
            sql = "SELECT * from Book LIMIT 1";
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                bookColumnsCount = cursor.getColumnCount();
            }
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            LogUtil.d(TAG, "book table: now:" + bookColumnsCount + " real:" + NOW_VERSION_BOOK_COLUMNS);
            if (bookColumnsCount != -1 && bookColumnsCount != NOW_VERSION_BOOK_COLUMNS) {
                deleteAllTable(db, "book");
                onCreate(db);
                return;
            }

            // 比对章节表
            sql = "SELECT * from ChapterForReader LIMIT 1";
            cursor = db.rawQuery(sql, null);
            if (cursor != null && cursor.moveToFirst()) {
                chapterColumnsCount = cursor.getColumnCount();
            }
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            LogUtil.d(TAG, "chapter table: now:" + chapterColumnsCount + " real:" + NOW_VERSION_CHAPTER_COLUMNS);
            if (chapterColumnsCount != -1 && chapterColumnsCount != NOW_VERSION_CHAPTER_COLUMNS) {
                deleteAllTable(db, "chapter");
                onCreate(db);
                return;
            }
        } catch (Exception e) {
            // doNothing
            LogUtil.e(e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}