package com.wuzp.newspace.view.splash;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.view.View;

import com.wuzp.newspace.R;
import com.wuzp.newspace.base.NewActivity;
import com.wuzp.newspace.databinding.ActivitySplashBinding;
import com.wuzp.newspace.utils.ActivityUtil;
import com.wuzp.newspace.utils.PreferenceUtil;
import com.wuzp.newspace.view.main.MainActivity;

/**
 * Created by wuzp on 2017/9/25.
 */

public class SplashActivity extends NewActivity<ActivitySplashBinding,SplashPresenter> implements SplashView, View.OnClickListener {
    private boolean isLoading = true;
    private boolean isFirstOpen = false;
    private LoadingTimeCounter loadingTimeCounter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected SplashPresenter createPresenter() {
        return new SplashPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        isFirstOpen = PreferenceUtil.getBoolean(PreferenceUtil.COMMON_FIRST_OPEN,false);
        //设置播放加载路径
        binding.videoSplash.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.girl_dance));
        //播放
        binding.videoSplash.start();//暂停播放
        //循环播放
        binding.videoSplash.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                binding.videoSplash.start();
            }
        });
    }

    //返回重启加载
    @Override
    protected void onRestart() {
        initView();
        super.onRestart();
    }

    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        binding.videoSplash.stopPlayback();
        super.onStop();
    }

    @Override
    protected void initData() {
        super.initData();
        loadingTimeCounter = new LoadingTimeCounter(2000,1000);
        loadingTimeCounter.start();

        binding.textLoading.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_loading:
                if(!isFirstOpen){
                    ActivityUtil.start(mContext, GuideActivity.class);
                }else{
                    ActivityUtil.start(mContext, MainActivity.class);
                }

                binding.videoSplash.stopPlayback();
                finish();
                break;
        }
    }

    @Override
    public void error(int code, String msg) {

    }

    class LoadingTimeCounter extends CountDownTimer {
        private int totalCount = 0;
        public LoadingTimeCounter(int totalCount, int perCount) {
            super(totalCount, perCount);
            this.totalCount = totalCount;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            isLoading = true;
            binding.textLoading.setEnabled(false);
            binding.textLoading.setClickable(false);
            binding.textTime.setText("(" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            isLoading = false;
            binding.textTime.setText("");
            binding.textLoading.setClickable(true);
            binding.textLoading.setEnabled(true);
        }
    }
}
