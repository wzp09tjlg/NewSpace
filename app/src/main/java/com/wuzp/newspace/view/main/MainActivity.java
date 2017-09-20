package com.wuzp.newspace.view.main;

import android.view.View;

import com.wuzp.newspace.R;
import com.wuzp.newspace.base.NewActivity;
import com.wuzp.newspace.databinding.ActivityMainBinding;

public class MainActivity extends NewActivity<ActivityMainBinding,MainPresenter> implements MainView ,View.OnClickListener{

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
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.btn_show:
              
              break;
      }
    }
}
