package com.wuzp.newspace.network.model.read;

import android.content.ContentValues;
import android.database.Cursor;

import com.wuzp.newspace.database.service.DBService;
import com.wuzp.newspace.database.table.BookTable;
import com.wuzp.newspace.network.entity.read.Book;
import com.wuzp.newspace.network.entity.read.BookInfo;
import com.wuzp.newspace.network.entity.read.Chapter;

import java.util.List;

import static com.wuzp.newspace.utils.database.StringConvertUtil.getStringFromCursor;

/**
 * Created by zyb on 2016/11/1.
 */
public class BookModel {
    public static Book getBookByCursor(Cursor cursor) {
        Book book = new Book();
        book.setBook_id(getStringFromCursor(cursor, BookTable.BOOK_ID));
        book.setAuthor(getStringFromCursor(cursor, BookTable.AUTHOR));
        book.setBid(getStringFromCursor(cursor, BookTable.BOOK_ID));
        book.setChapter_num(Integer.valueOf(getStringFromCursor(cursor, BookTable.NUM)));
        book.setFilePath(getStringFromCursor(cursor, BookTable.FILE_PATH));

        book.setIntro(getStringFromCursor(cursor, BookTable.INTRO));
        book.setImg(getStringFromCursor(cursor, BookTable.IMAGE_URL));
        book.setDownloadstatus(getStringFromCursor(cursor, BookTable.DOWNLOAD_STATE).equals("0"));
        book.setPaytype(getStringFromCursor(cursor, BookTable.PAY_TYPE));
        book.setPrice(Float.valueOf(getStringFromCursor(cursor, BookTable.PRICE)));
        book.setLastreadtime(Long.valueOf(getStringFromCursor(cursor, BookTable.LAST_READ_TIME)));
        book.setUpdatetime(Long.valueOf(getStringFromCursor(cursor, BookTable.LAST_UPDATE_TIME)));
//        book.setChapter_total(Integer.valueOf(StringConvertUtil.getStringFromCursor(cursor, BookTable.TOTAL_PAGE)));
        book.setSuite_id(getStringFromCursor(cursor, BookTable.SUITE_ID));
        book.setSuite_name(getStringFromCursor(cursor, BookTable.SUITE_NAME));
        book.setIsOnlineBook(getStringFromCursor(cursor, BookTable.IS_ONLINE_BOOK).equals("0"));
        book.setStatus(getStringFromCursor(cursor, BookTable.STATUS_INFO));
        book.setTitle(getStringFromCursor(cursor, BookTable.TITLE));

        book.setLastPos(Integer.valueOf(getStringFromCursor(cursor, BookTable.LAST_POS)));
        book.setLastPage(Integer.valueOf(getStringFromCursor(cursor, BookTable.LAST_PAGE)));
        book.setFontsize(Integer.valueOf(getStringFromCursor(cursor, BookTable.LAST_FONTSIZE)));
        book.setOnlineReadChapterId(getStringFromCursor(cursor, BookTable.ONLINE_READ_CHAPTER_ID));
        book.setAutobuy(Boolean.valueOf(getStringFromCursor(cursor, BookTable.AUTO_BUY)));
        book.setShow_status(String.valueOf(getStringFromCursor(cursor, BookTable.BOOK_CONTENT_TYPE)));//内容类型 ：版权判断
        book.setChecked(String.valueOf(getStringFromCursor(cursor, BookTable.TAG)));//内容类型:下架判断
        book.setUid(getStringFromCursor(cursor, BookTable.UID));
        book.setIsUpdateChapterList(Integer.valueOf(getStringFromCursor(cursor, BookTable.IS_UPDATE_CHAPTER_LIST)));
        book.setNetReadTime(Long.valueOf(getStringFromCursor(cursor, BookTable.NET_READ_TIME)));
        return book;
    }

    public synchronized static ContentValues setBookAllValues(Book book, boolean isInlayBook) {
        ContentValues values = new ContentValues();

        values.put(BookTable.TITLE, book.getTitle());
        values.put(BookTable.AUTHOR, book.getAuthor());
        values.put(BookTable.NUM, book.getChapter_num());
        values.put(BookTable.INTRO, book.getIntro());
        values.put(BookTable.IMAGE_URL, book.getImg());
        values.put(BookTable.FILE_PATH, book.getFilePath());
        values.put(BookTable.FILE_SIZE, "");
        values.put(BookTable.FLAG, "normal");
        values.put(BookTable.PROGRESS, "");
        values.put(BookTable.DOWNLOAD_STATE, book.getDownloadstatus() ? 0 : -1);//-1未下载，0已下载,1正在下载
        values.put(BookTable.LAST_POS, book.getLastPos());
        values.put(BookTable.BOOK_ID, book.getBook_id());
        values.put(BookTable.UPDATE_CHAPTER_NUM, "");
        values.put(BookTable.TAG, book.getChecked());
        values.put(BookTable.PAY_TYPE, book.getPaytype());
        values.put(BookTable.PRICE, book.getPrice());
        values.put(BookTable.LAST_READ_TIME, isInlayBook ? -1 : System.currentTimeMillis());
        values.put(BookTable.NET_READ_TIME, book.getNetReadTime() != -1 ? book.getNetReadTime() : System.currentTimeMillis());
        values.put(BookTable.ORIGINAL_FILE_PATH, "");
        values.put(BookTable.STATUS_INFO, book.getStatus());
        values.put(BookTable.LAST_UPDATE_TIME, System.currentTimeMillis());
        values.put(BookTable.LAST_READ_PERCENT, "");

        values.put(BookTable.DOWNLOAD_TIME, book.getDownloadTime() != -1 ? book.getDownloadTime() : System.currentTimeMillis());
        values.put(BookTable.VDISK_DOWNLOAD_URL, "");
        values.put(BookTable.VDISK_FILE_PATH, "");
        values.put(BookTable.UID, "UserUtils.getUid()");
        values.put(BookTable.SINA_ID, book.getSina_id());
        values.put(BookTable.BOOK_CONTENT_TYPE, book.getShow_status());
        values.put(BookTable.SUITE_ID, book.getSuite_id());
        values.put(BookTable.ORIGIN_SUITE_ID, "");
        values.put(BookTable.SUITE_NAME, book.getSuite_name());
        values.put(BookTable.LAST_FONTSIZE, book.getFontsize());
//        values.put(BookTable.TOTAL_PAGE, book.getChapter_total());
        values.put(BookTable.LAST_PAGE, book.getLastPage());
        values.put(BookTable.AUTO_BUY, book.isAutobuy());
        values.put(BookTable.IS_ONLINE_BOOK, book.getIsOnlineBook() ? 0 : 1);//在线书籍，1本地书籍
        values.put(BookTable.OWNER_UID, "");
        values.put(BookTable.ONLINE_READ_CHAPTER_ID, book.getOnlineReadChapterId());
        values.put(BookTable.IS_REMIND, "");
        values.put(BookTable.LAST_READ_JSON, "");

        return values;
    }

    public synchronized static ContentValues setBookNetValues(Book book,boolean updateNetTime,boolean updateDownTime) {
        ContentValues values = new ContentValues();
        values.put(BookTable.TITLE, book.getTitle());
        values.put(BookTable.AUTHOR, book.getAuthor());
        values.put(BookTable.INTRO, book.getIntro());
        values.put(BookTable.IMAGE_URL, book.getImg());
        values.put(BookTable.BOOK_ID, book.getBook_id());
        values.put(BookTable.PRICE, book.getPrice());
        values.put(BookTable.PAY_TYPE, book.getPaytype());
        values.put(BookTable.STATUS_INFO, book.getStatus());
        values.put(BookTable.BOOK_CONTENT_TYPE, book.getShow_status());
        values.put(BookTable.TAG, book.getChecked());
        if (updateNetTime) {
            values.put(BookTable.NET_READ_TIME, book.getNetReadTime());
        }
        if (updateDownTime) {
            values.put(BookTable.DOWNLOAD_TIME, book.getDownloadTime());
        }
        return values;
    }

    public static Book getBookByBookinfo(BookInfo info) {
        Book book = new Book();
        book.setBook_id(info.getBooks().getBookId());
        book.setTitle(info.getBooks().getTitle());
        book.setAuthor(info.getBooks().getAuthor());
        book.setBid(info.getBooks().getSBid());
        book.setChapter_num(info.getBooks().getChapterNum());
        book.setIntro(info.getBooks().getIntro());
        book.setImg(info.getBooks().getImg());
        book.setPaytype(info.getBooks().getPaytype());
        book.setPrice(info.getBooks().getPrice());
//        book.setChapter_total(info.getBooks().getChapterTotal());
        book.setIsOnlineBook(true);
        book.setStatus(info.getBooks().getStatus());
        book.setDownloadstatus(true);
        book.setAutobuy(false);
        return book;
    }

    /**
     * 从数据库查出章节信息.
     */
    public static List<Chapter> parseChapter(Book book) {
        List<Chapter> chapters = book.getChapters();
        if (chapters == null || chapters.isEmpty()) {
            chapters = DBService.queryChapterByTag(book.getBook_id());
        }
        return chapters;
    }
}
