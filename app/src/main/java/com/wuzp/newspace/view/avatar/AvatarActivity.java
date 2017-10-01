package com.wuzp.newspace.view.avatar;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wuzp.newspace.R;
import com.wuzp.newspace.adapter.AvatarAdapter;
import com.wuzp.newspace.adapter.FolderAdapter;
import com.wuzp.newspace.base.NewActivity;
import com.wuzp.newspace.databinding.ActivityAvatarBinding;
import com.wuzp.newspace.network.entity.avatar.PicFolder;
import com.wuzp.newspace.widget.common.RecyclerItemDecoration;
import com.wuzp.newspace.widget.toast.Msg;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/***
 * 仿写微信图片选择器(参考鸿洋的博客)
 * 1.查找手机上存在图片的文件
 * 2.将图片展示出来
 * 3.menu上展示有图片的文件夹，点击文件夹 将文件中存在的图片展示出来
 * */
public class AvatarActivity extends NewActivity<ActivityAvatarBinding,AvatarPresenter> implements AvatarView, View.OnClickListener {
    //title
    private ImageView imgTitleBack;
    private ImageView imgTitleMenu;
    private TextView textTitle;
    //otherView
    //底层显示图片的recycler
    private RecyclerView recyclerView;
    //中间显示文件夹列表
    private RelativeLayout layoutFolders;
    private RecyclerView recyclerViewFolders;
    //底部菜单栏
    private TextView textMenu;
    private ImageView imgMenu;
    private RelativeLayout layoutMenu;
    private RelativeLayout layoutMenuClickArea;

    private Context mContext = this;
    private File mImgDir;
    private List<String> mImgs = new ArrayList<>();
    private List<PicFolder> picFolders = new ArrayList<PicFolder>();
    private SearchThread searchThread;
    private AvatarAdapter avatarAdapter;
    private FolderAdapter folderAdapter;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            folderAdapter.setmImgDir(mImgDir);
            setData();
        }
    };

    @Override
    protected int getLayoutId() {
        return  R.layout.activity_avatar;
    }

    @Override
    protected AvatarPresenter createPresenter() {
        return new AvatarPresenter(this);
    }

    protected void initView() {
        imgTitleBack = $(R.id.img_title_back);
        imgTitleMenu = $(R.id.img_title_menu);
        textTitle = $(R.id.text_title);

        recyclerView = $(R.id.recycler_avatar);
        layoutFolders = $(R.id.layout_folders);
        recyclerViewFolders = $(R.id.recycler_folders);

        textMenu = $(R.id.text_menu);
        imgMenu = $(R.id.img_menu);
        layoutMenu = $(R.id.layout_menu);
        layoutMenuClickArea = $(R.id.layout_menu_click_area);

        imgTitleBack.setOnClickListener(this);
        layoutFolders.setOnClickListener(this);
        layoutMenuClickArea.setOnClickListener(this);

        imgTitleMenu.setVisibility(View.INVISIBLE);
        textTitle.setText("图片");
        //设置显示图片的adapater
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        RecyclerItemDecoration itemDecoration = new RecyclerItemDecoration(mContext);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(gridLayoutManager);

        //设置显示图片所在文件夹的adapter
        folderAdapter = new FolderAdapter(mContext,picFolders,mImgDir);
        folderAdapter.setOnFolderItemClickListener(getFolderItemClickListener());
        recyclerViewFolders.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false));
        recyclerViewFolders.setAdapter(folderAdapter);
    }

    protected void initData() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Msg.getInstance().show("没有找到SD卡");
            return;
        }
        searchThread = new SearchThread();
        searchThread.start();
    }

    private void setData() {
        if (mImgDir == null) {
            Msg.getInstance().show("没有找到图片");
            return;
        }
        mImgs = Arrays.asList(mImgDir.list(new FilenameFilter()
        {
            @Override
            public boolean accept(File dir, String filename)
            {
                if (filename.endsWith(".jpg") || filename.endsWith(".png")
                        || filename.endsWith(".jpeg"))
                    return true;
                return false;
            }
        }));
        String tempName = mImgDir.getAbsolutePath();
        if(tempName.lastIndexOf("/") > 0){
            tempName = tempName.substring(tempName.lastIndexOf("/"));
            if(tempName.startsWith("/")){
                tempName = tempName.replace("/","");
            }
            textMenu.setText(tempName);
        }
        avatarAdapter = new AvatarAdapter(mContext,mImgs,mImgDir.getAbsolutePath());
        avatarAdapter.setOnItemClickListener(getItemClickListener());
        recyclerView.setAdapter(avatarAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_title_back:
                if (layoutFolders.getVisibility() == View.VISIBLE) {
                    showFolderListMenu();
                    return;
                }
                finish();
                break;
            case R.id.layout_folders:
            case R.id.layout_menu_click_area:
            case R.id.text_menu:
                if (picFolders.size() > 0) {
                    showFolderListMenu();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (layoutFolders.getVisibility() == View.VISIBLE) {
            showFolderListMenu();
            return;
        }
        super.onBackPressed();
    }

    private AvatarAdapter.OnAvatarItemClickListener getItemClickListener(){
        AvatarAdapter.OnAvatarItemClickListener listener = new AvatarAdapter.OnAvatarItemClickListener() {
            @Override
            public void onItemClickListener(String path) {
               Msg.getInstance().show("path:" + path);
            }
        };
        return listener;
    }

    private FolderAdapter.OnFolderItemClickListener getFolderItemClickListener(){
        FolderAdapter.OnFolderItemClickListener listener = new FolderAdapter.OnFolderItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                PicFolder picFolder = picFolders.get(position);
                String currentPath = mImgDir.getAbsolutePath();
                String clickItemFolderPath   = picFolder.getDir();
                if(!TextUtils.isEmpty(currentPath) && currentPath.equals(clickItemFolderPath)){
                    showFolderListMenu();
                    return;
                }

                mImgDir = new File(picFolder.getDir());
                mImgs = Arrays.asList(mImgDir.list(new FilenameFilter()
                {
                    @Override
                    public boolean accept(File dir, String filename)
                    {
                        if (filename.endsWith(".jpg") || filename.endsWith(".png")
                                || filename.endsWith(".jpeg"))
                            return true;
                        return false;
                    }
                }));
                String tempName = picFolder.getName();
                if(tempName.startsWith("/")){
                    tempName = tempName.replace("/","");
                }
                textMenu.setText(tempName);
                setData();
                showFolderListMenu();
            }
        };
        return listener;
    }

    private void showFolderListMenu(){
        if(layoutFolders.getVisibility() == View.VISIBLE){
            layoutFolders.clearAnimation();
            Animation animationExit = AnimationUtils.loadAnimation(mContext,R.anim.anim_bottom_dialog_exit);
            layoutFolders.startAnimation(animationExit);
            layoutFolders.setVisibility(View.INVISIBLE);
        }else{
            layoutFolders.clearAnimation();
            Animation animationEnter = AnimationUtils.loadAnimation(mContext,R.anim.anim_bottom_dialog_enter);
            recyclerViewFolders.startAnimation(animationEnter);
            layoutFolders.setVisibility(View.VISIBLE);
        }
    }

    private <T> T $(int id) {
        return (T) findViewById(id);
    }

    //循环查找手机图片的线程
    class SearchThread extends Thread {
        @Override
        public void run() {
            super.run();
            int mPicsSize = 0;
            HashSet<String> mDirPaths = new HashSet<String>();
            Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentResolver mContentResolver = AvatarActivity.this
                    .getContentResolver();

            Cursor mCursor = null;
            try {
                // 只查询jpeg和png的图片
                mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);

                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;
                    String dirPath = parentFile.getAbsolutePath();

                    mImgs.add(path);
                    PicFolder picFolder = null;
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        picFolder = new PicFolder();
                        picFolder.setDir(dirPath);
                        picFolder.setFirstImagePath(path);
                    }

                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith(".jpg")
                                    || filename.endsWith(".png")
                                    || filename.endsWith(".jpeg"))
                                return true;
                            return false;
                        }
                    }).length;

                    picFolder.setCount(picSize);
                    picFolders.add(picFolder);

                    if (picSize > mPicsSize) {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
            } catch (Exception e) {
            } finally {
                if (mCursor != null) {
                    mCursor.close();
                }
            }
            // 扫描完成，辅助的HashSet也就可以释放内存了
            mDirPaths = null;

            // 通知Handler扫描图片完成
            mHandler.sendEmptyMessage(0x110);
        }
    }

    @Override
    public void error(int code, String msg) {

    }
}
