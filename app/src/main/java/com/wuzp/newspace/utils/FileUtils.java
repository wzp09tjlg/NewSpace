package com.wuzp.newspace.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    public synchronized static File checkAndCreateFile(String fileName) {
        return checkOrCreateFile(fileName, true);
    }

    // 检测并创建文件
    public synchronized static File checkOrCreateFile(String fileName, boolean isCreate) {
        if (fileName == null) {
            return null;
        }

        File file = new File(fileName);
        if (!file.exists()) {
            try {
                if (isCreate) {
                    String parentPath = file.getParent();
                    boolean result = fileProberParent(parentPath);
                    if (result) {
                        long size = getAvailableExternalMemorySize();
                        if (size != -1) {
                            file.createNewFile();
                            return file;
                        }
                    } else {
                        file = null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Log.e("FileUtil", "检测并创建文件 >> checkOrCreateFile e:" + e);
                file = null;
            }
        }
        return file;
    }

    // 递归创建父级目录
    private synchronized static boolean fileProberParent(String dirPath) {
        if (dirPath == null) {
            return false;
        }
        File file = new File(dirPath);
        if (file == null || !file.exists()) {
            // 文件夹不存在，递归寻找上级目录
            String parentPath = file.getParent();
            boolean result = fileProberParent(parentPath);
            if (result) {
                file.mkdir();
            }
            return result;
        }
        return true;
    }

    // 获取存储卡剩余空间
    public synchronized static long getAvailableExternalMemorySize() {
        boolean sdcard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdcard) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            long formatSize = availableBlocks * blockSize;
            // long formatSize = (availableBlocks * blockSize) / (1024 * 1024);
            return formatSize;
        } else {
            return -1;
        }
    }

    // 删除文件或文件夹
    public synchronized static void deleteFile(String path) {
        if (path == null) {
            return;
        }

        File file = new File(path);
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                String[] list = file.list();
                if (list != null && list.length > 0) {
                    for (int i = 0; i < list.length; ++i) {
                        String tmpPath = path + "/" + list[i];
                        deleteFile(tmpPath);
                    }
                }
                file.delete();
            } else if (file.isFile()) {
                file.delete();
            }
        }
    }

    /**
     * assets目录下的文件是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean assertsFileExist(String filePath) {
        try {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static byte[] readData(String path) {
        File file = checkOrCreateFile(path, false);
        return readData(file);
    }

    public static byte[] readData(File file) {
        if (file == null) {
            return null;
        }
        if (!file.exists()) {
            return null;
        }

        // 文件输入流对象
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            // 文件流总大小
            int size = fis.available();
            // 存储数据的字节数组
            byte[] data = new byte[size];
            // 读取数据
            fis.read(data, 0, data.length);
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fis = null;
            }
        }
        return null;
    }

    public static boolean writeData(String path, byte[] data) {
        File file = checkOrCreateFile(path, true);
        if (file != null && file.exists()) {
            return writeData(file, data);
        }
        return false;
    }

    public static boolean writeData(File file, byte[] data) {
        if (file == null) {
            return false;
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, false);
            fos.write(data);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fos = null;
            }
        }
        return true;
    }


    public static final String ROOT_DIR = "1601";
    public static final String SYS_DIR_CACHE = "cache";
    private static final String JSON_CACHE = "json";

    public static File getRootFile(Context context) {
        String externalCacheDir;
        //判断外置sd卡是否存在
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //当前手机运行的版本号
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                //sd卡/Android/data/包名/cache
                externalCacheDir = context.getCacheDir().getAbsolutePath();
            } else {
                //sd卡/1601
                externalCacheDir = Environment.getExternalStorageDirectory() + ROOT_DIR;
            }

        } else {
            //表示系统目录
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                //sys/data/data/包名/cache
                externalCacheDir = context.getCacheDir().getAbsolutePath();
            } else {
                //sys/data/data/包名/cache
                externalCacheDir = Environment.getDataDirectory() + File.separator + context.getPackageName() + File.separator + SYS_DIR_CACHE;
            }

        }
        File file = new File(externalCacheDir);

        if (!file.exists()) {
            file.mkdirs();
        }

        return file;

    }

    public static File getJsonCache(Context context) {

        String jsonPath = getRootFile(context) + JSON_CACHE;
        File jsonFile = new File(jsonPath);

        if (!jsonFile.exists()) {
            jsonFile.mkdirs();
        }
        return jsonFile;

    }

    /**
     * 追加文件：使用RandomAccessFile
     *
     * @param fileName 文件名
     * @param content  追加的内容
     */
    public synchronized static void addContentForFile(String fileName, String content) {
        FileWriter writer = null;
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(fileName, true);
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
