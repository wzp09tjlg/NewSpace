package com.wuzp.newspace.view.splash;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.wuzp.newspace.R;
import com.wuzp.newspace.base.BasePresenter;
import com.wuzp.newspace.base.NewActivity;
import com.wuzp.newspace.database.service.DBService;
import com.wuzp.newspace.database.table.BookTable;
import com.wuzp.newspace.databinding.ActivityGuideBinding;
import com.wuzp.newspace.network.ApiStore;
import com.wuzp.newspace.network.entity.read.BookInfo;
import com.wuzp.newspace.network.entity.read.ChapterList;
import com.wuzp.newspace.network.model.read.BookModel;
import com.wuzp.newspace.utils.ActivityUtil;
import com.wuzp.newspace.utils.FileUtils;
import com.wuzp.newspace.utils.LogUtil;
import com.wuzp.newspace.utils.PreferenceUtil;
import com.wuzp.newspace.utils.database.BookAnalysisUtils;
import com.wuzp.newspace.utils.database.InlayBookUtils;
import com.wuzp.newspace.view.main.MainActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by wuzp on 2017/9/26.
 */
public class GuideActivity extends NewActivity<ActivityGuideBinding,BasePresenter> {
    //添加的所有分类的书籍
    public static final String BOY = "boy";
    public static final String GIRL = "girl";
    public static final String PUBLIC_BOOK = "publicBook";

    public static final int TYPE_OLD_USER = 0;
    public static final int TYPE_MAN_ALL = 1;
    public static final int TYPE_WOMAN_ALL = 2;
    public static final int TYPE_MAN_ONLINE = 3;
    public static final int TYPE_WOMAN_ONLINE = 4;
    public static final int TYPE_PUBLIC_ARTICLE = 5;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        getBooksMap();
    }

    @Override
    protected void initData() {
        super.initData();
        //初次进入应用，加载书籍到数据库中 以及初始化书架
        loadBook();
    }

    private void loadBook(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                initShelf(BOY, InlayBookUtils.getStrings(InlayBookUtils.MAN_BOOK));
                initShelf(GIRL, InlayBookUtils.getStrings(InlayBookUtils.WOMAN_BOOK));
                initShelf(PUBLIC_BOOK, InlayBookUtils.getStrings(InlayBookUtils.PUBLIC_ARTICLE_BOOK));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ActivityUtil.start(mContext, MainActivity.class);
                        PreferenceUtil.putBoolean(PreferenceUtil.COMMON_FIRST_OPEN,true);
                        finish();
                    }
                });
            }
        }).start();
    }

    public void initShelf(String type, String... books) {
        for (String bookid : books) {
            /**
             * 保存书籍信息
             *    id   书名   封皮     第一次展示的时候   判断id
             * 保存章节信息
             *     根据id名做文件保存   json解析文件  保存数据库
             * 复制文件到手机
             *      文件复制  复制后更改数据库路径然后解析
             */
            BookInfo book = doReadFileForBook(mContext,"book/" + bookid + "_" + type + "_bookinfo.txt");
            LogUtil.e("book:" + book.toString());
            if (book != null) {
                DBService.saveBook(BookModel.getBookByBookinfo(book),true);
            }
            ChapterList chapterList = doReadFileForChapter(mContext,"book/" + bookid + "_chapterinfo.txt");
            if (chapterList != null) {
                for (int j = 0; j < chapterList.getChapters().size(); j++) {
                    chapterList.getChapters().get(j).setBook_id(chapterList.getBook_id());
                    chapterList.getChapters().get(j).setTag(chapterList.getBook_id());
                }
                DBService.saveNewChapterInfo(chapterList.getChapters());
                DBService.updateBookByBookid(BookTable.NUM, String.valueOf(chapterList.getChapter_num()), bookid);
            }
           File file =FileUtils.doCopy(mContext,"book/" + bookid + ".txt", bookid + "|.dat");//将书保存到包名下的文件中
            if (file != null) { //解析本地的书到数据库中
                BookAnalysisUtils.analysisDownloadBook(file,mContext,bookid);
            }
        }
    }

    private ChapterList doReadFileForChapter(Context context,  String filename) {
        InputStream fis = null;
        InputStreamReader isReader = null;
        JsonReader reader = null;
        try {
            fis = context.getAssets().open(filename);
            Gson gson = ApiStore.getGson();
            isReader = new InputStreamReader(fis);
            reader = new JsonReader(isReader);
            return gson.fromJson(reader, ChapterList.class);
        } catch (Exception e) {
            LogUtil.i(  e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    LogUtil.e(  "assets解析异常" + e.getMessage());
                }
            }

            if (isReader != null) {
                try {
                    isReader.close();
                } catch (Exception e) {
                    LogUtil.e(  "assets解析异常");
                }
            }

            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    LogUtil.e(  "assets解析异常");
                }
            }
        }
        return null;
    }

    private void getBooksMap() {
        try {
            String[] pathFiles = mContext.getAssets().list("book");
            ArrayList<String> all = new ArrayList<>();
            ArrayList<String> man = new ArrayList<>();
            ArrayList<String> woman = new ArrayList<>();
            ArrayList<String> publicBook = new ArrayList<>();
            for (String name : pathFiles) {
                if (name.endsWith("txt")) {
                    if (name.contains("bookinfo")) {
                        String bookid = name.substring(0, name.indexOf("_"));
                        all.add(bookid);
                        if (name.contains(BOY)) {
                            man.add(bookid);
                        }
                        if (name.contains(GIRL)) {
                            woman.add(bookid);
                        }
                        if (name.contains(PUBLIC_BOOK)) {
                            publicBook.add(bookid);
                        }
                    }
                }
            }
            InlayBookUtils.addDates(InlayBookUtils.ALL, getStringsFromList(all));
            InlayBookUtils.addDates(InlayBookUtils.MAN_BOOK, getStringsFromList(man));
            InlayBookUtils.addDates(InlayBookUtils.WOMAN_BOOK, getStringsFromList(woman));
            InlayBookUtils.addDates(InlayBookUtils.PUBLIC_ARTICLE_BOOK, getStringsFromList(publicBook));
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e("get book  from assets:" + e.getMessage());
        }
    }

    private String[] getStringsFromList(ArrayList<String> list) {
        String[] string = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            string[i] = list.get(i);
        }
        return string;
    }

    private BookInfo doReadFileForBook(Context context, String filename) {
        InputStream fis = null;
        InputStreamReader isReader = null;
        JsonReader reader = null;
        try {
            fis = context.getAssets().open(filename);
            Gson gson = ApiStore.getGson();
            isReader = new InputStreamReader(fis);
            reader = new JsonReader(isReader);
            return gson.fromJson(reader, BookInfo.class);
        } catch (Exception e) {
            LogUtil.i( e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    LogUtil.e(  "assets解析异常" + e.getMessage());
                }
            }

            if (isReader != null) {
                try {
                    isReader.close();
                } catch (Exception e) {
                    LogUtil.e(  "assets解析异常");
                }
            }

            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    LogUtil.e(  "assets解析异常");
                }
            }
        }
        return null;
    }
}
