package com.wuzp.newspace.network.model.read;

import android.support.annotation.NonNull;

import com.wuzp.newspace.network.entity.read.Chapter;

import java.util.List;

/**
 * Created by wuzp on 2017/9/27.
 */
public class ChapterListModel {
    public static final String IS_NEED_UPDATE = "isneedupdate";

    public static String queryNextChapterByTagWithCid(List<Chapter> chapters, String cid, boolean next) {
        if (chapters.size() == 0) {
            return null;
        }
        if ("-1".equals(cid) && next) {
            return chapters.get(0).getC_id();
        }
        if ("-1".equals(cid) && !next) {
            return null;
        }
        String nextChapterId;
        for (int i = 0; i < chapters.size(); i++) {
            if (chapters.get(i).getC_id().equals(cid)) {
                for (int j = 1; j < chapters.size(); j++) {
                    //防止重复章节信息
                    if (!next) {
                        if (i >= j) {
                            nextChapterId = chapters.get(i - j).getC_id();
                        } else {
                            return null;
                        }
                    } else {
                        if (i + j < chapters.size()) {
                            nextChapterId = chapters.get(i + j).getC_id();
                        } else {
                            return null;
                        }
                    }
                    if (!nextChapterId.equals(cid)) {
                        return nextChapterId;
                    }
                }
            }
        }
        return null;
    }

    public static int queryPosByTagWithCid(List<Chapter> chapters,String cid){
        if (chapters.size() == 0) {
            return -1;
        }
        if (cid == null || cid.equals("-1")) {
            return 0;
        }
        for (int i = 0; i < chapters.size(); i++) {
            if (chapters.get(i).getC_id().equals(cid)) {
                return i;
            }
        }
        return 0;
    }

    public static String[] queryChapterForPagefactory(List<Chapter> chapters, @NonNull String firstCid, @NonNull String lastCid){
        String pre = null;
        String next = null;
        int pos = 0;
        if (chapters.size() == 0){
            pre = null;
            next = null;
            pos = -1;
        }else {
            if ("-1".equals(firstCid)){
                pre = null;
            }
            if ("-1".equals(lastCid)){
                next = chapters.get(0).getC_id();
            }
            if (firstCid == null || "-1".equals(firstCid)) {
                pos =  0;
            }
            for (int i = 0; i < chapters.size(); i++) {
                if (chapters.get(i).getC_id().equals(firstCid)){
                    pos = i;
                    for (int j = 1; j < chapters.size(); j++) {
                        if (i >= j){
                            pre = chapters.get(i - j).getC_id();
                        }else {
                            pre = null;
                        }
                        if (!firstCid.equals(pre)){
                            break;
                        }
                    }
                }
                if (chapters.get(i).getC_id().equals(lastCid)){
                    for (int j = 0; j < chapters.size(); j++) {
                        if (i + j < chapters.size()) {
                            next = chapters.get(i + j).getC_id();
                        } else {
                            next = null;
                        }
                        if (!lastCid.equals(next)){
                            break;
                        }
                    }
                }
            }
        }
        return new String[]{pre,next,String.valueOf(pos)};
    }
}
