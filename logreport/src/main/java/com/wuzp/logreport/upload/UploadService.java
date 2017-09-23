package com.wuzp.logreport.upload;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.wuzp.logreport.LogReport;
import com.wuzp.logreport.util.CompressUtil;
import com.wuzp.logreport.util.FileUtil;
import com.wuzp.logreport.util.LogUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * 此Service用于后台发送日志
 */
public class UploadService extends IntentService {

    public static final String TAG = "UploadService";

    /**
     * 压缩包名称的一部分：时间戳
     */
    public final static SimpleDateFormat ZIP_FOLDER_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS", Locale.getDefault());

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public UploadService() {
        super(TAG);
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 同一时间只会有一个耗时任务被执行，其他的请求还要在后面排队，
     * onHandleIntent()方法不会多线程并发执行，所有无需考虑同步问题
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        final File logfolder = new File(LogReport.getInstance().getROOT() + "Log/");
        // 如果Log文件夹都不存在，说明不存在崩溃日志，检查缓存是否超出大小后退出
        if (!logfolder.exists() || logfolder.listFiles().length == 0) {
            LogUtil.d("Log文件夹都不存在，无需上传");
            return;
        }
        //只存在log文件，但是不存在崩溃日志，也不会上传
        ArrayList<File> crashFileList = FileUtil.getCrashList(logfolder);
        if (crashFileList.size() == 0) {
            LogUtil.d(TAG, "只存在log文件，但是不存在崩溃日志，所以不上传");
            return;
        }
        final File zipfolder = new File(LogReport.getInstance().getROOT() + "AlreadyUploadLog/");
        File zipfile = new File(zipfolder, "UploadOn" + ZIP_FOLDER_TIME_FORMAT.format(System.currentTimeMillis()) + ".zip");
        final File rootdir = new File(LogReport.getInstance().getROOT());
        StringBuilder content = new StringBuilder();

        //创建文件，如果父路径缺少，创建父路径
        zipfile = FileUtil.createFile(zipfolder, zipfile);

        //把日志文件压缩到压缩包中
        if (CompressUtil.zipFileAtPath(logfolder.getAbsolutePath(), zipfile.getAbsolutePath())) {
            LogUtil.d("把日志文件压缩到压缩包中 ----> 成功");
            content.append(FileUtil.getText(crashFileList.get(0)));
            content.append("\n");
            /*for (File crash : crashFileList) { //邮件中附带的是只需要日志的一部分而已，不全放在内容当中
                content.append(FileUtil.getText(crash));
                content.append("\n");
            }*/
            LogReport.getInstance().getUpload().sendFile(zipfile, content.toString(), new ILogUpload.OnUploadFinishedListener() {
                @Override
                public void onSuceess() {
                    LogUtil.d("日志发送成功！！");
                    FileUtil.deleteDir(logfolder);//删除日志信息
                    FileUtil.deleteDir(zipfolder);//删除压缩文件的信息
                    boolean checkresult = checkCacheSize(rootdir);
                    LogUtil.d("缓存大小检查，是否删除root下的所有文件 = " + checkresult);
                    stopSelf();
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "日志已上传成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onError(String error) {
                    LogUtil.d("日志发送失败：  = " + error);
                    FileUtil.deleteDir(zipfolder);//删除压缩文件的信息
                    boolean checkresult = checkCacheSize(rootdir);
                    LogUtil.d("缓存大小检查，是否删除root下的所有文件 " + checkresult);
                    stopSelf();
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "日志上传失败,请稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else {
            FileUtil.deleteDir(zipfolder);//删除压缩文件的信息
            LogUtil.d("把日志文件压缩到压缩包中 ----> 失败");
        }
    }

    /**
     * 检查文件夹是否超出缓存大小
     *
     * @param dir 需要检查大小的文件夹
     * @return 返回是否超过大小，true为是，false为否
     */
    public boolean checkCacheSize(File dir) {
        long dirSize = FileUtil.folderSize(dir);
        return dirSize >= LogReport.getInstance().getCacheSize() && FileUtil.deleteDir(dir);
    }
}
