package com.wuzp.newspace.view.main;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RadioGroup;

import com.wuzp.newspace.R;
import com.wuzp.newspace.adapter.MainPageAdapter;
import com.wuzp.newspace.base.BaseFragment;
import com.wuzp.newspace.base.NewActivity;
import com.wuzp.newspace.databinding.ActivityMainBinding;
import com.wuzp.newspace.network.entity.main.GirlsBean;
import com.wuzp.newspace.utils.eventbus.LogEvent;
import com.wuzp.newspace.view.main.fragment.FunFragment;
import com.wuzp.newspace.view.main.fragment.InfoFragment;
import com.wuzp.newspace.view.main.fragment.NewsFragment;
import com.wuzp.newspace.view.main.fragment.ReadFragment;
import com.wuzp.newspace.view.main.fragment.UserFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends NewActivity<ActivityMainBinding,MainPresenter> implements MainView {

    private MainPageAdapter pageAdapter;
    private List<BaseFragment> fragments = new ArrayList<>();
    private String[] titles = {"资讯","新闻","娱乐","阅读","我的"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        BaseFragment info = new InfoFragment();
        BaseFragment news = new NewsFragment();
        BaseFragment fun  = new FunFragment();
        BaseFragment read = new ReadFragment();
        BaseFragment user = new UserFragment();
        fragments.add(info);
        fragments.add(news);
        fragments.add(fun);
        fragments.add(read);
        fragments.add(user);

        pageAdapter = new MainPageAdapter(getSupportFragmentManager(),fragments,titles);
        binding.pagers.setAdapter(pageAdapter);
        binding.pagers.setOffscreenPageLimit(5);
        binding.pagers.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {//针对fragment 的切换的监听
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        binding.layoutIndicator.indicatorInfo.setChecked(true);
                        break;
                    case 1:
                        binding.layoutIndicator.indicatorNews.setChecked(true);
                        break;
                    case 2:
                        binding.layoutIndicator.indicatorFun.setChecked(true);
                        break;
                    case 3:
                        binding.layoutIndicator.indicatorRead.setChecked(true);
                        break;
                    case 4:
                        binding.layoutIndicator.indicatorUser.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                binding.layoutIndicator.indicatorInfo.setClickable( state == 0);
                binding.layoutIndicator.indicatorNews.setClickable( state == 0);
                binding.layoutIndicator.indicatorFun.setClickable( state == 0);
                binding.layoutIndicator.indicatorRead.setClickable( state == 0);
                binding.layoutIndicator.indicatorUser.setClickable( state == 0);
            }
        });
        binding.layoutIndicator.layoutMainIndicator.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.indicator_info:
                        binding.pagers.setCurrentItem(0,true);
                        break;
                    case R.id.indicator_news:
                        binding.pagers.setCurrentItem(1,true);
                        break;
                    case R.id.indicator_fun:
                        binding.pagers.setCurrentItem(2,true);
                        break;
                    case R.id.indicator_read:
                        binding.pagers.setCurrentItem(3,true);
                        break;
                    case R.id.indicator_user:
                        binding.pagers.setCurrentItem(4,true);
                        break;
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLogEvent(LogEvent event){
     LogEvent event1 = event;
        Log.e("abc","event:" + event.toString());
    }

    @Override
    public void setData(List<GirlsBean.GirlBean> data) {

    }

    @Override
    public void error(int code, String msg) {
        binding.pagers.post(new Runnable() {
            @Override
            public void run() {
                //使用Intent 有好几种方式
                //0.直接就是一个空的Intent 然后在之后设置其他的值(Action  class 等信息)
                //1.直接是一个Intent 这样可以方便参数的设定   //使用的场景
                //2.参数是action
                //3.参数是action 和 uri
                //4.参数Context 和 class
                //5.参数是action uri context class
                /*
                Intent tempIntent = null;
                Intent intent = new Intent(tempIntent);//
                intent = new Intent();//
                intent = new Intent("action");
                intent = new Intent("action","uri");
                intent = new Intent("context","class");
                intent = new Intent("action","uri","context","class");*/
            }
        });
    }

    //继承线程 重写线程方法
    public class MyThread extends Thread{
        //如果在子线程种吗，没有初始化looper 没有调用looper.prepare方法，
        // 那么在创建handler时 会报 没有初始化looper.prepare
        // 不能创建handler的错
        //在主线程中就能创建 那是因为在主线程中 已经初始化了looper.prepare 所以不会报错
        //看来在activity 以及fragment 中创建handler 都是在主线程中执行的
        Handler myHandler = new Handler(){
            //重新handlermessage 方法 进行处理消息
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
        public MyThread(){}

        @Override
        public void run() {
            super.run();
        }
    }
}
