package com.wuzp.newspace.network.model.read;

import android.content.ContentValues;
import android.database.Cursor;

import com.wuzp.newspace.database.service.DBService;
import com.wuzp.newspace.database.table.ChapterTable;
import com.wuzp.newspace.network.entity.read.Book;
import com.wuzp.newspace.network.entity.read.Chapter;
import com.wuzp.newspace.utils.database.StringConvertUtil;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class ChapterModel {
    public static Chapter getChapterByCursor(Cursor cursor) {
        Chapter chapter = new Chapter();
        chapter.setBook_id(StringConvertUtil.getStringFromCursor(cursor, ChapterTable.BOOK_ID));
        chapter.setStartPos(Long.valueOf(StringConvertUtil.getStringFromCursor(cursor, ChapterTable.START_POS)));
        chapter.setLength(Long.valueOf(StringConvertUtil.getStringFromCursor(cursor, ChapterTable.LENGTH)));
        chapter.setTitle(StringConvertUtil.getStringFromCursor(cursor, ChapterTable.TITLE));
        chapter.setC_id(StringConvertUtil.getStringFromCursor(cursor, ChapterTable.CHAPTER_ID));
        chapter.setS_num(Integer.valueOf(StringConvertUtil.getStringFromCursor(cursor, ChapterTable.SERIAL_NUMBER)));
        chapter.setVip(StringConvertUtil.getStringFromCursor(cursor, ChapterTable.IS_VIP));
        chapter.setTag(StringConvertUtil.getStringFromCursor(cursor, ChapterTable.TAG));
        return chapter;
    }

    public static List<Chapter> getChaptersByCursor(Cursor cursor) {
        List<Chapter> list = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                Chapter chapter = new Chapter();
                chapter.setBook_id(StringConvertUtil.getStringFromCursor(cursor, ChapterTable.BOOK_ID));
                chapter.setStartPos(Long.valueOf(StringConvertUtil.getStringFromCursor(cursor, ChapterTable.START_POS)));
                chapter.setLength(Long.valueOf(StringConvertUtil.getStringFromCursor(cursor, ChapterTable.LENGTH)));
                chapter.setTitle(StringConvertUtil.getStringFromCursor(cursor, ChapterTable.TITLE));
                chapter.setC_id(StringConvertUtil.getStringFromCursor(cursor, ChapterTable.CHAPTER_ID));
                chapter.setS_num(Integer.valueOf(StringConvertUtil.getStringFromCursor(cursor, ChapterTable.SERIAL_NUMBER)));
                chapter.setVip(StringConvertUtil.getStringFromCursor(cursor, ChapterTable.IS_VIP));
                chapter.setTag(StringConvertUtil.getStringFromCursor(cursor, ChapterTable.TAG));
                list.add(chapter);
                cursor.moveToNext();
            }
        }
        return list;
    }

    public static String getNextChapterIdByCursorWithChapterId(Cursor cursor, String chapterId, boolean next) {
        String id;
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            if (cursor.getString(1).equals(chapterId)) {
                for (int j = 1; j < cursor.getCount(); j++) {//防止重复章节信息
                    if (!next) {
                        if (i >= j) {
                            cursor.moveToPosition(i - j);
                        } else {
                            return null;
                        }
                    } else {
                        if (i + j < cursor.getCount()) {
                            cursor.moveToPosition(i + j);
                        } else {
                            return null;
                        }
                    }
                    id = cursor.getString(1);
                    if (!id.equals(chapterId)) {
                        return id;
                    }
                }
            }
        }
        return null;
    }

    public static int getChapterPosByCursorWithChapterId(Cursor cursor, String chapterId) {
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            if (cursor.getString(0).equals(chapterId)) {
                return i;
            }
        }
        return 0;
    }

    public static Chapter getChapterInfoByCursorWithPos(Cursor cursor, int pos) {
        cursor.moveToPosition(pos);
        return getChapterByCursor(cursor);
    }

    public synchronized static ContentValues setChapterValues(Chapter chapter) {
        ContentValues values = new ContentValues();
        values.put(ChapterTable.BOOK_ID, chapter.getBook_id());
        values.put(ChapterTable.CHAPTER_ID, chapter.getC_id());
        values.put(ChapterTable.IS_VIP, chapter.getVip());
        values.put(ChapterTable.SERIAL_NUMBER, chapter.getS_num());
        values.put(ChapterTable.LENGTH, chapter.getLength());
        values.put(ChapterTable.START_POS, chapter.getStartPos());
        values.put(ChapterTable.TITLE, chapter.getTitle());
        values.put(ChapterTable.TAG, chapter.getTag());
        values.put(ChapterTable.CHAPTER_FLAGS, "UserUtils.getUid()");
        return values;
    }

    public synchronized static ContentValues updateChapterValues(Chapter chapter) {
        ContentValues values = new ContentValues();
        values.put(ChapterTable.BOOK_ID, chapter.getBook_id());
        values.put(ChapterTable.CHAPTER_ID, chapter.getC_id());
        values.put(ChapterTable.IS_VIP, chapter.getVip());
        values.put(ChapterTable.SERIAL_NUMBER, chapter.getS_num());
        values.put(ChapterTable.TITLE, chapter.getTitle());
        values.put(ChapterTable.TAG, chapter.getTag());
        values.put(ChapterTable.CHAPTER_FLAGS, "UserUtils.getUid()");
        return values;
    }

    public synchronized static ContentValues setChapterValues(Book book, Chapter chapter) {
        ContentValues values = new ContentValues();
        values.put(ChapterTable.BOOK_ID, book.getId());
        values.put(ChapterTable.CHAPTER_ID, chapter.getC_id());
        values.put(ChapterTable.TITLE, chapter.getTitle());
        values.put(ChapterTable.START_POS, chapter.getStartPos());
        values.put(ChapterTable.LENGTH, chapter.getLength());
        values.put(ChapterTable.IS_VIP, chapter.getVip());
        values.put(ChapterTable.TAG, chapter.getTag());
        values.put(ChapterTable.SERIAL_NUMBER, chapter.getS_num());
        values.put(ChapterTable.CHAPTER_FLAGS, "UserUtils.getUid()");
        return values;
    }

    public static void saveChapter(String bookid, String chapterid, String isvip, String title, String s_num) {
        Chapter chapter = new Chapter();
        chapter.setBook_id(bookid);
        chapter.setTag(bookid);
        chapter.setTitle(title);
        chapter.setC_id(chapterid);
        chapter.setS_num(Integer.valueOf(s_num));
        chapter.setVip(isvip);
        DBService.saveChapterInfo(chapter);
    }

}
