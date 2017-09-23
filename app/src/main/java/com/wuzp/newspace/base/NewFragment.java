package com.wuzp.newspace.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wuzp on 2017/9/23.
 */
public abstract class NewFragment<B extends ViewDataBinding> extends BaseFragment {

    private B binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater,layoutId(),container,true);

        initView();
        initData();
        return binding.getRoot();
    }

    protected abstract int layoutId();

    protected void initView(){}

    protected void initData(){}
}
