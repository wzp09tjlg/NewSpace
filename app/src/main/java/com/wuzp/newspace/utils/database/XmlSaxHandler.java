package com.wuzp.newspace.utils.database;


import com.wuzp.newspace.database.service.DBService;
import com.wuzp.newspace.database.table.BookTable;
import com.wuzp.newspace.network.entity.read.Chapter;
import com.wuzp.newspace.network.entity.read.ChapterList;
import com.wuzp.newspace.network.entity.read.StatusBean;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于书城下载书籍的sax解析
 * Created by zyb on 2016/10/31.
 */
public class XmlSaxHandler extends DefaultHandler {
    private ChapterList chapterList;
    private StatusBean statusBean;
    private List<Chapter> chapters;
    private String element;
    private Chapter chapter;
    private long startPos;
    String c_id = null;

    public XmlSaxHandler() {
        startPos = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>".length();
    }

    /* 此方法有三个参数
       arg0是传回来的字符数组，其包含元素内容
       arg1和arg2分别是数组的开始位置和结束位置 */
    @Override
    public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
        String content = new String(arg0, arg1, arg2);
        switch (element) {
            case "code":
                statusBean.setCode(Integer.parseInt(content));
                break;
            case "msg":
                statusBean.setMsg(content);
                break;
            case "book_id":
                chapterList.setBook_id(content);
                break;
            case "serial_num":
                chapter.setS_num(Integer.parseInt(content));
                break;
            case "chapter_id":
                if (c_id == null) {
                    c_id = content;
                    chapter.setC_id(c_id);
                } else {
                    c_id += content;
                    chapter.setC_id(c_id);
                }
                break;
            case "title":
                chapter.setTitle(content);
                break;
            case "is_vip":
                chapter.setVip(content);
                break;
            default:
                break;
        }
        if ("&".equals(content)) {
            content = "&amp;";
        }
        startPos += content.getBytes().length;
        super.characters(arg0, arg1, arg2);
    }

    @Override
    public void endDocument() throws SAXException {
        if (chapterList.getStatus().getCode() == 0) {
            DBService.resetAllChapterInfo(chapterList.getBook_id());
            DBService.saveChapterInfo(chapterList.getChapters());
            DBService.updateBooksByBookid(new String[]{BookTable.DOWNLOAD_STATE}, new String[]{"0"}, chapterList.getBook_id());
        }
        if (chapterList.getStatus().getCode() == 35 || chapterList.getStatus().getCode() == 34) {
            DBService.updateBooksByBookid(new String[]{BookTable.TAG, BookTable.BOOK_CONTENT_TYPE}, new String[]{"N", "N"}, chapterList.getBook_id());
        }
        super.endDocument();
    }

    /* arg0是名称空间
       arg1是包含名称空间的标签，如果没有名称空间，则为空
       arg2是不包含名称空间的标签 */
    @Override
    public void endElement(String arg0, String arg1, String arg2)
            throws SAXException {
        element = arg2;
        String offset = "</" + element + ">";
        switch (element) {
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
            case "content":
                chapter.setLength(startPos - chapter.getStartPos());
                break;
            default:
                break;
        }
        startPos += offset.getBytes().length;
        super.endElement(arg0, arg1, arg2);
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    /*arg0是名称空间
      arg1是包含名称空间的标签，如果没有名称空间，则为空
      arg2是不包含名称空间的标签
      arg3很明显是属性的集合 */
    @Override
    public void startElement(String arg0, String arg1, String arg2,
                             Attributes arg3) throws SAXException {
        element = arg2;
        String offset = "<" + element + ">";
        startPos += offset.getBytes().length;
        switch (element) {
            case "root":
                chapterList = new ChapterList();
                break;
            case "status":
                statusBean = new StatusBean();
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
                break;
            default:
                break;
        }
        super.startElement(arg0, arg1, arg2, arg3);
    }
}
