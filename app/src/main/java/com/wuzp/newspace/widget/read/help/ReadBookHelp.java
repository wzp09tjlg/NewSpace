package com.wuzp.newspace.widget.read.help;

import android.support.annotation.NonNull;

import com.sina.weibo.sdk.utils.Base64;
import com.wuzp.newspace.database.service.DBService;
import com.wuzp.newspace.database.table.BookTable;
import com.wuzp.newspace.network.entity.read.Chapter;
import com.wuzp.newspace.network.entity.read.ChapterForReader;
import com.wuzp.newspace.network.entity.read.ChapterSingle;
import com.wuzp.newspace.network.entity.read.ChapterSinglePrice;
import com.wuzp.newspace.utils.FileUtils;
import com.wuzp.newspace.utils.LogReportManager;
import com.wuzp.newspace.utils.database.StorageUtil;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;

import retrofit2.Response;

/**
 * Created by wuzp on 2017/9/27.
 */
public abstract class ReadBookHelp {
    public static final String SRC_BOOK_LOCAL = "SRC_BOOK_LOCAL";//书籍类型，本地书籍
    public static final String SRC_BOOK_DOWNLOAD = "SRC_BOOK_DOWNLOAD";//书籍类型，网络下载书籍
    public static final String SRC_BOOK_ONLINE = "SRC_BOOK_ONLINE";//书籍类型和，在线阅读书籍
    // TODO: 2017/3/10 未知的逻辑需梳理
    public static final String UNKNOW = "unknow";//未知，当前未知的作用只是重新刷新pagefactory
    public static final String AUTO_STOP_VIP = "AUTO_STOP_VIP";//标志，当前阅读行为停止是因为vip章节
    public static final String AUTO_STOP_END = "AUTO_STOP_END";//标志，当前阅读行为停止是因为结束
    public static final String AUTO_STOP_NOLOGIN = "AUTO_STOP_NOLOGIN";//标志，当前阅读行为停止是因为未登录
    public static final String AUTO_STOP_NOMONEY = "AUTO_STOP_NOMONEY";//标志，当前阅读行为停止是因为余额不足
    public static final String AUTO_STOP_NONET = "AUTO_STOP_NONET";//标志，当前阅读行为停止是因为无网络
    public static final String AUTO_STOP_NOPREMISSION = "AUTO_STOP_NOPREMISSION";//标志，当前阅读行为停止是因为无权限（已下架或者无授权）
    public static final String CHECKOUT_IS_END = "CHECKOUTISEND";//不是阅读行为停止标志，当下一章为空时，在网络状况良好且是网络书籍时通过网络访问确认是否是最后一章

    /**
     * 获取章节内容，通过回调传递成功或失败状态
     */
    public synchronized void getContent(final String tag, final String cid, final boolean auto, final boolean isNext, final boolean isSkip) {
        Chapter chapter = DBService.queryChapterInfoByTagWithCid(tag, cid);
        if (chapter == null) {
            disposeNullChapter(tag, cid,isNext, isSkip);
            return;
        }
        switch (checkBookContentSrc(chapter)) {
            case SRC_BOOK_LOCAL:
                localChapterLoad(tag, cid, isNext, isSkip);
                break;
            case SRC_BOOK_DOWNLOAD:
                downloadChapterLoad(tag, cid, isNext, isSkip);
                break;
            case SRC_BOOK_ONLINE:
                onlineChapterLoad(tag, cid, auto, isNext, isSkip, chapter);
                break;
            default:
                break;
        }
    }

    /**
     * 在线书籍加载
     */
    private void onlineChapterLoad(final String tag, final String cid, boolean auto, final boolean isNext, final boolean isSkip, Chapter chapter) {
        if (chapter.getVip().equals("N")) {
            getContentFromNet(tag, cid, isNext, isSkip);
        } else {
            if (true) {
                if (auto) {
                    getContentFromNet(tag, cid, isNext, isSkip);
                } else {
                    getChapterHasBuy(tag, cid, isNext, isSkip);
                }
            } else {
                sendStopType(AUTO_STOP_NOLOGIN, null, cid,isNext, isSkip, null);
            }
        }
    }

    /**
     * 获取章节是否购买过
     */
    private void getChapterHasBuy(final String tag, final String cid, final boolean isNext, final boolean isSkip) {
       /* ModelFactory.getChapterSinglePriceModel().getChapterSinglePriceData(tag, cid, new CallBack<ChapterSinglePrice>() {
            @Override
            public void success(Call<ChapterSinglePrice> call, Response<ChapterSinglePrice> response) {
                if (response.body().getChapter().getHas_buy().equals("Y")) {
                    getContentFromNet(tag, cid, isNext, isSkip);
                } else {
                    sendStopType(AUTO_STOP_VIP, response.body(), cid,isNext, isSkip, response.body().getStatus().getMsg());
                }
            }

            @Override
            public void unKnowCode(Call<ChapterSinglePrice> call, Response<ChapterSinglePrice> response) {
                if (response.body().getStatus().getCode() == 34 || response.body().getStatus().getCode() == 35) {
                    sendStopType(AUTO_STOP_NOPREMISSION, null,cid, isNext, isSkip, response.body().getStatus().getMsg());
                } else {
                    sendStopType(UNKNOW, null, cid,isNext, isSkip, response.body().getStatus().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ChapterSinglePrice> call, Throwable t) {
                if (NetWorkUtil.isConnected(null) && t instanceof SocketTimeoutException) {
                    sendStopType(UNKNOW, null,cid, isNext, isSkip, null);
                } else {
                    sendStopType(AUTO_STOP_NONET, null,cid, isNext, isSkip, null);
                }
            }
        });*/
    }

    /**
     * 网络下载章节加载
     */
    private void downloadChapterLoad(final String tag, final String cid, final boolean isNext, final boolean isSkip) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                resultOfContent(ChapterTransform.getChapterByDownloadBook(tag, cid), isNext, isSkip);
            }
        }).start();
    }

    /**
     * 本地章节加载
     */
    private void localChapterLoad(final String tag, final String cid, final boolean isNext, final boolean isSkip) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                resultOfContent(ChapterTransform.getChapterByLocal(tag, cid), isNext, isSkip);
            }
        }).start();
    }

    /**
     * 当章节为空时处理办法
     */
    private void disposeNullChapter(String tag, String cid,boolean isNext, boolean isSkip) {
        if (isNext) {
            if ( StringUtils.isNumeric(tag)) {
                sendStopType(CHECKOUT_IS_END, null, cid,true, isSkip, null);//当数据库中没有这个章节时，如果是网络书籍则通过网络访问判断当前章是否正的是最后一章
            } else {
                sendStopType(AUTO_STOP_END, null, cid,true, isSkip, null);//
            }
        } else {
            sendStopType(AUTO_STOP_END, null, cid,false, isSkip, null);
        }
    }

    /**
     * 检查当前章是否真的是最后一章
     */
    public void checkoutIsEnd(final String tag, final String cid, final boolean isNext, final boolean isSkip) {
    }

    /**
     * 通过网络或去章节内容，并进行处理
     */
    public synchronized void getContentFromNet(String tag, final String cid, final boolean isNext, final boolean isSkip) {
     /*   ModelFactory.getChapterSingleMode().getChapterSingleDate(tag, cid, new CallBack<ChapterSingle>() {
            @Override
            public void success(Call<ChapterSingle> call, final Response<ChapterSingle> response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        isReadVip(response.body().getIs_vip().equals("Y"));
                        ChapterModel.updateChapterInfoFromChapterSingle(response);//更新上下章信息
                        resultOfContent(ChapterTransform.getChapterByOnlineRead(response.body()), isNext, isSkip);
                        saveContent2File(response);
                    }
                }).start();
            }

            @Override
            public void moneyNoEnough(Call<ChapterSingle> call, Response<ChapterSingle> response) {
                sendStopType(AUTO_STOP_NOMONEY, null, cid,isNext, isSkip, response.body().getStatus().getMsg());
            }

            @Override
            public void noLogin(Call<ChapterSingle> call, Response<ChapterSingle> response) {
                sendStopType(AUTO_STOP_NOLOGIN, null, cid,isNext, isSkip, response.body().getStatus().getMsg());
            }

            @Override
            public void unKnowCode(Call<ChapterSingle> call, Response<ChapterSingle> response) {
                if (response.body().getStatus().getCode() == 34 || response.body().getStatus().getCode() == 35) {
                    sendStopType(AUTO_STOP_NOPREMISSION, null, cid,isNext, isSkip, response.body().getStatus().getMsg());
                } else {
                    sendStopType(UNKNOW, null,cid, isNext, isSkip, response.body().getStatus().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ChapterSingle> call, Throwable t) {
                sendStopType(AUTO_STOP_NONET, null, cid,isNext, isSkip, null);
            }
        });*/
    }

    /**
     * 将章节内容返回
     */
    protected abstract void resultOfContent(ChapterForReader chapterForReader, boolean isNext, boolean isSkip);

    /**
     * 将章节vip状态返回
     */
    protected abstract void isReadVip(boolean isVip);

    /**
     * 将阅读行为停止类型及附加信息（只在vip状态时有属性）返回
     */
    protected abstract void sendStopType(String type, ChapterSinglePrice extra, String cid, boolean isNext, boolean isSkip, String msg);

    /**
     * 通过章节信息判断书籍类型
     */
    public static String checkBookContentSrc(@NonNull Chapter chapter) {
        if (chapter.getBook_id().equals("-1")) {
            return SRC_BOOK_LOCAL;
        } else {
            if (chapter.getLength() == 0) {
                return SRC_BOOK_ONLINE;
            } else {
                //当length不为零但是又获取失败的时候，在内容获取时会在重置，下一次获取会通过网络获取
                return SRC_BOOK_DOWNLOAD;
            }
        }
    }

    /**
     * 将网络获取的内容后保存进对应的文件中
     */
    private synchronized void saveContent2File(Response<ChapterSingle> response) {
        String bookid = response.body().getBook_id();
        String filePath = StorageUtil.getDirByType(StorageUtil.DIR_TYPE_BOOK) + "/" + bookid + "|" + ".dat";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File file2 = new File(filePath);
        if (file1.exists()) {
            Chapter chapter = DBService.queryChapterInfoByTagWithCid(bookid, response.body().getChapter_id());
            if (chapter != null){
                String content = new String(Base64.decode(response.body().getContent().getBytes()));
                chapter.setStartPos(file2.length());
                chapter.setLength(content.getBytes().length);
                FileUtils.addContentForFile(filePath, content);
                DBService.saveChapterInfo(chapter, true);
                DBService.updateBookByBookid(BookTable.FILE_PATH, filePath, bookid);
            }else {
                LogReportManager.writeLog("数据异常","数据异常，保存章节时获取章节为空"+response.body().toString());
            }
        }
    }

}

