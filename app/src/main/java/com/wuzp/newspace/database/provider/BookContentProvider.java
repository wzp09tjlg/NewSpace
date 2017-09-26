package com.wuzp.newspace.database.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wuzp.newspace.database.helper.DBOpenHelper;
import com.wuzp.newspace.database.table.BookTable;

/**
 * Created by zyb on 2016/9/28.
 */
public class BookContentProvider extends ContentProvider {
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private DBOpenHelper helper;

    static {
        matcher.addURI("com.wuzp.book", BookTable.TABLE_NAME, 1);
    }

    @Override
    public boolean onCreate() {
        helper = new DBOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (matcher.match(uri)) {
            case 1:
                SQLiteDatabase db;
                db = helper.getReadableDatabase();
                Cursor cursor;
                cursor = db.query(BookTable.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder, null);
                if(cursor != null){ //bugfixed wuzp 异常情况可能是空（数据库被锁之后 就会出现cursor是空,数据库为啥被锁，没找到原因，可能是多线程同时操作数据库）
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                }
                return cursor;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (matcher.match(uri)) {
            case 1:
                return BookTable.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        getContext().getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }
}
