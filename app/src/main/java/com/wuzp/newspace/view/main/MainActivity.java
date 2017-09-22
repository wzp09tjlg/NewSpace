package com.wuzp.newspace.view.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.wuzp.newspace.R;
import com.wuzp.newspace.adapter.MainAdapter;
import com.wuzp.newspace.base.NewActivity;
import com.wuzp.newspace.databinding.ActivityMainBinding;
import com.wuzp.newspace.network.entity.GirlBean;
import com.wuzp.newspace.utils.eventbus.LogEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import static java.lang.Boolean.FALSE;

public class MainActivity extends NewActivity<ActivityMainBinding,MainPresenter> implements MainView ,View.OnClickListener{
    private MainAdapter mainAdapter;

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
        binding.btnShow.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mainAdapter = new MainAdapter(mContext);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,FALSE);
        binding.recycler.setLayoutManager(manager);
        binding.recycler.setAdapter(mainAdapter);
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.btn_show:
              presenter.start();
              break;
      }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getLogEvent(LogEvent event){
     LogEvent event1 = event;
        Log.e("abc","event:" + event.toString());
    }

    @Override
    public void setData(List<GirlBean> data) {
        mainAdapter.setData(data);
    }
}
