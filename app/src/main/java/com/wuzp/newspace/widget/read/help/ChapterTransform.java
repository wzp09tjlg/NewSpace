package com.wuzp.newspace.widget.read.help;

import com.sina.weibo.sdk.utils.Base64;
import com.wuzp.newspace.database.service.DBService;
import com.wuzp.newspace.network.entity.read.Book;
import com.wuzp.newspace.network.entity.read.Chapter;
import com.wuzp.newspace.network.entity.read.ChapterForReader;
import com.wuzp.newspace.network.entity.read.ChapterSingle;
import com.wuzp.newspace.utils.FileUtils;
import com.wuzp.newspace.utils.LogUtil;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzp on 2017/9/27.
 */
public class ChapterTransform {
    /**
     * 将在线下载书籍转化为阅读器适配模式
     * 暂时缺省书签和划线信息
     *
     * @param bookid bookid
     * @return ChapterForReader
     */
    public static ChapterForReader getChapterByDownloadBook(String bookid, String chapterid) {
        ChapterForReader chapterForReader = new ChapterForReader();
        Book book = DBService.queryBooksInfoBybookid(bookid);
        File file = null;
        if (book != null){
            file = new File(book.getFilePath());
        }
        Chapter customChapter = DBService.queryChapterInfoByTagWithCid(bookid, chapterid);
        if (customChapter != null) {
            chapterForReader.setTitle(customChapter.getTitle());
            FileChannel channel;
            MappedByteBuffer buffer;
            try {
                RandomAccessFile readfile = new RandomAccessFile(file, "r");
                channel = readfile.getChannel();
                buffer = channel.map(FileChannel.MapMode.READ_ONLY, customChapter.getStartPos(), (long) customChapter.getLength());
                String stringValue = byteBufferToString(buffer, "utf-8");
                if (stringValue != null) {
                    stringValue = stringValue.replaceAll("\n", "\n" + "　　");
                }
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("\n" + customChapter.getTitle() + "\n");
                stringBuffer.append(stringValue);

                chapterForReader.setContent(StringEscapeUtils.unescapeHtml3(StringEscapeUtils.unescapeHtml3(stringBuffer.toString())));
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.e( e.getMessage());
                customChapter.setLength(0);
                List<Chapter> chapters = new ArrayList<>();
                chapters.add(customChapter);
                DBService.saveChapterInfo(chapters);
            }
        }
        chapterForReader.setChapterId(chapterid);
        chapterForReader.setBookid(bookid);
        chapterForReader.setTag(bookid);
        return chapterForReader;
    }

    /**
     * 将在线阅读书籍转化为阅读器适配章节
     * 缺省书签及划线信息
     *
     * @param chapterSingle chapterSingle
     * @return ChapterForReader
     */
    public static ChapterForReader getChapterByOnlineRead(final ChapterSingle chapterSingle) {
        final ChapterForReader chapterForReader = new ChapterForReader();
        chapterForReader.setTitle(chapterSingle.getTitle());
        chapterForReader.setTag(chapterSingle.getBook_id());
        String content = new String(Base64.decode(chapterSingle.getContent().getBytes()));
        content = content.replaceAll("\n", "\n" + "　　");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\n" + chapterSingle.getTitle() + "\n");
        stringBuffer.append(content);
        chapterForReader.setContent(StringEscapeUtils.unescapeHtml3(stringBuffer.toString()));
        chapterForReader.setChapterId(chapterSingle.getChapter_id());
        chapterForReader.setBookid(chapterSingle.getBook_id());
        return chapterForReader;
    }

    public static ChapterForReader getChapterByLocal(String filepath, String chapterid) {
        ChapterForReader chapterForReader = new ChapterForReader();
        File file = new File(filepath);
        Chapter customChapter = DBService.queryChapterInfoByTagWithCid(filepath, chapterid);
        if (customChapter != null) {
            chapterForReader.setTitle(customChapter.getTitle());
            FileChannel channel = null;
            MappedByteBuffer buffer;
            try {
                RandomAccessFile readfile = new RandomAccessFile(file, "r");
                channel = readfile.getChannel();
                buffer = channel.map(FileChannel.MapMode.READ_ONLY, customChapter.getStartPos(), (long) customChapter.getLength());
                String stringValue = byteBufferToString(buffer, FileUtils.getFileIncode(file));
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("\n" + customChapter.getTitle() + "\n");
                stringBuffer.append(stringValue);
                chapterForReader.setContent(stringBuffer.toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (channel != null) {
                        channel.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        chapterForReader.setTag(filepath);
        chapterForReader.setChapterId(chapterid);
        return chapterForReader;
    }

    public static String byteBufferToString(ByteBuffer buffer, String encode) {
        CharBuffer charBuffer;
        try {
            Charset charset = Charset.forName(encode);
            charBuffer = charset.decode(buffer);
            buffer.flip();
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


}

