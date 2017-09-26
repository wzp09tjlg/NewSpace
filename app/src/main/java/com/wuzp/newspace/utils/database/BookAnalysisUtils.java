package com.wuzp.newspace.utils.database;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import com.wuzp.newspace.database.service.DBService;
import com.wuzp.newspace.database.table.BookTable;
import com.wuzp.newspace.network.entity.read.Chapter;
import com.wuzp.newspace.network.entity.read.ChapterList;
import com.wuzp.newspace.network.entity.read.StatusBean;
import com.wuzp.newspace.utils.FileUtils;
import com.wuzp.newspace.utils.LogUtil;
import com.wuzp.newspace.utils.listener.ScheduleListener;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 */
public class BookAnalysisUtils {

    public static void analysisDownloadBook(final File file, final Context context, final String bookid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser parser = factory.newSAXParser();
                    XmlSaxHandler dh = new XmlSaxHandler();//解析结束后存入数据库
                    parser.parse(file, dh);
                    DBService.updateBooksByBookid(new String[]{BookTable.FILE_PATH},
                            new String[]{String.valueOf(file.getAbsolutePath())}, bookid);
                    context.getContentResolver().notifyChange(Uri.parse("content://" + "com.wuzp.book/" + BookTable.TABLE_NAME), null);
                    LogUtil.e("book  parse success bookid:" + bookid);
                } catch (Exception e) {
                    LogUtil.e(e.toString());
                    LogUtil.e("book parse error" + e.getMessage());
                }
            }
        }).start();
    }

    public static void analysisDownloadBookByPull(final File file, final Context context, final String bookid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ChapterList chapterList = null;
                    StatusBean statusBean = null;
                    List<Chapter> chapters = null;
                    Chapter chapter = null;
                    long startPos = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes().length;
                    String c_id = null;
                    XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
                    FileInputStream is = new FileInputStream(file);
                    parser.setInput(is, "utf-8");
                    int eventType = parser.getEventType();
                    boolean succ = true;
                    while (succ && eventType != XmlPullParser.END_DOCUMENT) {
                        switch (eventType) {
                            case XmlPullParser.START_TAG:
                                String content = null;
                                String startTagName = parser.getName();
                                String offset = "<" + startTagName + ">";
                                LogUtil.e(offset);
                                startPos += offset.getBytes().length;
                                switch (startTagName) {
                                    case "root":
                                        chapterList = new ChapterList();
                                        break;
                                    case "code":
                                        content = parser.nextText();
                                        statusBean.setCode(Integer.parseInt(content));
                                        startPos += ("</" + startTagName + ">").getBytes().length;
                                        break;
                                    case "status":
                                        statusBean = new StatusBean();
                                        break;
                                    case "msg":
                                        content = parser.nextText();
                                        statusBean.setMsg(content);
                                        startPos += ("</" + startTagName + ">").getBytes().length;
                                        break;
                                    case "book_id":
                                        content = parser.nextText();
                                        chapterList.setBook_id(content);
                                        startPos += ("</" + startTagName + ">").getBytes().length;
                                        break;
                                    case "serial_num":
                                        content = parser.nextText();
                                        chapter.setS_num(Integer.parseInt(content));
                                        startPos += ("</" + startTagName + ">").getBytes().length;
                                        break;
                                    case "chapter":
                                        chapters = new ArrayList<>();
                                        break;
                                    case "item":
                                        chapter = new Chapter();
                                        chapter.setBook_id(chapterList.getBook_id());
                                        chapter.setTag(chapterList.getBook_id());
                                        break;
                                    case "content":
                                        chapter.setStartPos(startPos);
                                        content = parser.nextText();
                                        chapter.setLength(content.getBytes().length);
                                        startPos += ("</" + startTagName + ">").getBytes().length;
                                        break;
                                    case "chapter_id":
                                        content = parser.nextText();
                                        if (c_id == null) {
                                            c_id = content;
                                            chapter.setC_id(c_id);
                                        } else {
                                            c_id += content;
                                            chapter.setC_id(c_id);
                                            startPos -= ("<" + startTagName + ">").getBytes().length;
                                            startPos -= ("</" + startTagName + ">").getBytes().length;
                                        }
                                        startPos += ("</" + startTagName + ">").getBytes().length;
                                        break;
                                    case "title":
                                        content = parser.nextText();
                                        chapter.setTitle(content);
                                        startPos += ("</" + startTagName + ">").getBytes().length;
                                        break;
                                    case "is_vip":
                                        content = parser.nextText();
                                        chapter.setVip(content);
                                        startPos += ("</" + startTagName + ">").getBytes().length;
                                        break;
                                    default:
                                        break;
                                }
                                if (content != null) {
                                    if ("&".equals(content)) {
                                        content = "&amp;";
                                    }
                                    startPos += content.getBytes().length;
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                String endTagName = parser.getName();
                                String offsetend = "</" + endTagName + ">";
                                switch (endTagName) {
                                    case "status":
                                        chapterList.setStatus(statusBean);
                                        break;
                                    case "chapter":
                                        chapterList.setChapters(chapters);
                                        break;
                                    case "item":
                                        chapters.add(chapter);
                                        c_id = null;
                                        break;
                                    case "root":
                                        succ = false;
                                        break;
                                    default:
                                        break;
                                }
                                LogUtil.e(offsetend);
                                startPos += offsetend.getBytes().length;
                                break;
                        }
                        if (succ){
                            eventType = parser.next();
                        }
                    }
                    if (chapterList.getStatus().getCode() == 0) {
                        DBService.resetAllChapterInfo(chapterList.getBook_id());
                        DBService.saveChapterInfo(chapterList.getChapters());
                        DBService.updateBooksByBookid(new String[]{BookTable.DOWNLOAD_STATE}, new String[]{"0"}, chapterList.getBook_id());
                    }
                    if (chapterList.getStatus().getCode() == 35 || chapterList.getStatus().getCode() == 34) {
                        DBService.updateBooksByBookid(new String[]{BookTable.TAG, BookTable.BOOK_CONTENT_TYPE}, new String[]{"N", "N"}, chapterList.getBook_id());
                    }
                    DBService.updateBooksByBookid(new String[]{BookTable.FILE_PATH},
                            new String[]{String.valueOf(file.getAbsolutePath())}, bookid);
                    context.getContentResolver().notifyChange(Uri.parse("content://" + "com.wuzp.book/" + BookTable.TABLE_NAME), null);
                } catch (Exception e) {
                    LogUtil.e( e.toString());
                    LogUtil.e("download book parse error:" + e.getMessage());
                }
            }
        }).start();
    }

    public static void analysisLocalBook(final String filepath, final ScheduleListener listener) {
        final File file = new File(filepath);
        final List<Chapter> chapterList = new ArrayList<>();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (listener != null) {
                    switch (msg.what) {
                        case 0:
                            listener.start();
                            break;
                        case 100:
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    DBService.saveNewChapterInfo(chapterList);
                                    listener.end(true);
                                }
                            }).start();
                            break;
                        case 200:
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    DBService.saveNewChapterInfo(chapterList);
                                    listener.end(false);
                                }
                            }).start();
                            break;
                        default:
                            listener.progress(msg.what);
                            break;
                    }
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    handler.sendEmptyMessage(0);
                    long startPos = 0;
                    int s_num = 0;
                    String content;
                    String encode;
                    encode = FileUtils.getFileIncode(file);
                    if (encode == null) {
                        encode = "UTF-8";
                    }
                    List<Long> startList = new ArrayList<>();
                    FileInputStream fis = new FileInputStream(file);
                    BufferedReader dr = new BufferedReader(new InputStreamReader(fis, encode));
                    Chapter chapter = null;
                    long chapterLength = 0;
                    long line = 0;
                    long contentLong;
                    boolean isfirstpage = true;
                    boolean isSuccess = true;
                    while ((content = dr.readLine()) != null) {
                        int progress = (int) (startPos * 100 / file.length());
                        if (progress != 100) {
                            handler.sendEmptyMessage(progress);
                        }
                        line++;
                        if (line == 200) {
                            startList.add(startPos);
                            line = 0;
                        }
                        contentLong = content.getBytes(encode).length + "\n\t".getBytes(encode).length;
                        if (!content.trim().matches("(.{0,20})([第].{1,7}[章])(.{0,20})")) {
                            startPos += contentLong;
                            chapterLength += contentLong;
                            if (startPos == file.length() && chapter != null) {
                                chapter.setLength(chapterLength);
                                chapterList.add(chapter);
                            }
                        } else {
                            startPos += contentLong;
                            if (s_num != 0) {
                                if (chapterLength < 10) {
                                    chapterLength += contentLong;
                                    continue;
                                }
                                chapter.setLength(chapterLength);
                                chapterList.add(chapter);
                                if (chapterList.size() >= 10 && isfirstpage) {
                                    DBService.saveChapterInfo(chapterList);
                                    chapterList.clear();
                                    isfirstpage = false;
                                }
                            } else {
                                if (chapterLength > 300) {
                                    Chapter chapter1 = new Chapter();
                                    chapter1.setBook_id("-1");
                                    chapter1.setTag(file.getAbsolutePath());
                                    chapter1.setTitle("");
                                    chapter1.setVip("N");
                                    chapter1.setStartPos(0);
                                    chapter1.setLength(startPos - contentLong);
                                    chapter1.setS_num(0);
                                    chapter1.setC_id(String.valueOf(0));
                                    chapterList.add(chapter1);
                                }
                            }
                            chapterLength = 0;
                            chapter = new Chapter();
                            chapter.setBook_id("-1");
                            chapter.setTag(file.getAbsolutePath());
                            chapter.setTitle(content);
                            chapter.setVip("N");
                            chapter.setStartPos(startPos);
                            s_num++;
                            chapter.setS_num(s_num);
                            chapter.setC_id(String.valueOf(s_num));
                        }
                    }
                    if (chapterList.size() == 0) {
                        if (startList.size() != 0) {
                            for (int i = 0; i < startList.size(); i++) {
                                Chapter chapter1 = new Chapter();
                                chapter1.setBook_id("-1");
                                chapter1.setVip("N");
                                chapter1.setTag(file.getAbsolutePath());
                                chapter1.setTitle("" + i);
                                if (i == 0) {
                                    chapter1.setStartPos(0);
                                    chapter1.setLength(startList.get(i));
                                } else if (i == (startList.size() - 1)) {
                                    chapter1.setStartPos(startList.get(i));
                                    chapter1.setLength(file.length() - 1 - startList.get(i));
                                } else {
                                    chapter1.setStartPos(startList.get(i - 1));
                                    chapter1.setLength(startList.get(i) - startList.get(i - 1));
                                }
                                chapter1.setS_num(i);
                                chapter1.setC_id(String.valueOf(i));
                                chapterList.add(chapter1);
                            }
                        } else {
                            Chapter chapter1 = new Chapter();
                            chapter1.setBook_id("-1");
                            chapter1.setTag(file.getAbsolutePath());
                            chapter1.setTitle("0");
                            chapter1.setVip("N");
                            chapter1.setStartPos(0);
                            chapter1.setLength(file.length() - 1);
                            chapter1.setS_num(0);
                            chapter1.setC_id(String.valueOf(0));
                            chapterList.add(chapter1);
                        }
                        isSuccess = false;
                    }
                    if (isSuccess) {
                        handler.sendEmptyMessage(100);
                    } else {
                        handler.sendEmptyMessage(200);
                    }
                } catch (Exception e) {
                    LogUtil.i(e.getMessage());
                }
            }
        }).start();
    }
}
