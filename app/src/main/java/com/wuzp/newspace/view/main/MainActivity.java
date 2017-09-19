package com.wuzp.newspace.view.main;

import android.databinding.ViewDataBinding;

import com.wuzp.newspace.base.NewActivity;

public class MainActivity extends NewActivity<ViewDataBinding,MainPresenter> implements MainView {

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected MainPresenter createPresenter() {
        return null;
    }
}
