package com.wuzp.newspace.utils.database;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/***/
public class StorageUtil {
    /**
     * SDCard主目录.
     */
    public static final String EXTERNAL_STORAGE = Environment.getExternalStorageDirectory().toString();
    public static final String DIR_HOME = EXTERNAL_STORAGE + "/wuzp/reader";
    private static final String DIR_APK = "/apk2";
    private static final String DIR_IMAGE = "/image";
    private static final String DIR_BOOK = "/book";
    private static final String DIR_MARK = "/mark";
    private static final String DIR_LOG = "/log";
    private static final String DIR_TEMP = "/temp";
    private static final String DIR_DB = "/db";


    public static final int DIR_TYPE_BOOK = 21;
    public static final int DIR_TYPE_IMAGE = 23;
    public static final int DIR_TYPE_APK = 25;
    public static final int DIR_TYPE_MARK = 24;
    public static final int DIR_TYPE_LOG = 26;
    public static final int DIR_TYPE_TEMP = 27;


     /**
     * 是否插入sdcard
     *
     * @return
     */
    public static boolean isSDCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean isDBFileExist() {
        if (isSDCardExist()) {
            String path = DIR_HOME + DIR_DB + "/sinareader.db";
            File file = new File(path);
            if (file.exists()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 生成临时图片文件路径
     *
     * @return
     */
    public static String createPath(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            fileName = "temp";
        }

        fileName = fileName.replace('/', '_').replace(':', '_').replace("?", "_");

        String filePath = getDirByType(DIR_TYPE_TEMP) + File.separator + fileName + ".png";

        File file = new File(filePath);
        // 如果文件存在则先删除
        if (file.exists()) {
            file.delete();
        }

        return filePath;
    }

    /**
     * 通过类型获取目录路径
     *
     * @param type
     * @return
     */
    public static String getDirByType(int type) {
        String dir = "/";
        String filePath = "";
        switch (type) {
            case DIR_TYPE_BOOK: {
                filePath = DIR_HOME + DIR_BOOK;
                break;
            }
            case DIR_TYPE_IMAGE: {
                filePath = DIR_HOME + DIR_IMAGE;
                break;
            }
            case DIR_TYPE_APK: {
                filePath = DIR_HOME + DIR_APK;
                break;
            }
            case DIR_TYPE_MARK: {
                filePath = DIR_HOME + DIR_MARK;
                break;
            }
            case DIR_TYPE_LOG: {
                filePath = DIR_HOME + DIR_LOG;
                break;
            }
            case DIR_TYPE_TEMP: {
                filePath = DIR_HOME + DIR_TEMP;
                break;
            }
            default:
                break;
        }

        File file = new File(filePath);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        if (file.exists()) {
            if (file.isDirectory()) {
                dir = file.getPath();
            }
        } else {
            // 文件没创建成功，可能是sd卡不存在，但是还是把路径返回
            dir = filePath;
        }
        return dir;
    }
}
