package com.wuzp.newspace.view.main.fragment;

import android.view.View;

import com.wuzp.newspace.R;
import com.wuzp.newspace.base.MvpFragment;
import com.wuzp.newspace.databinding.FragmentReadBinding;
import com.wuzp.newspace.network.ApiError;

/**
 * Created by wuzp on 2017/9/23.
 */
public class ReadFragment extends MvpFragment<FragmentReadBinding,FunPresenter> implements FunView {

    @Override
    protected FunPresenter createPresenter() {
        return new FunPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_read;
    }

    @Override
    protected void initView() {
        super.initView();
        binding.layoutTitle.imgTitleBack.setVisibility(View.INVISIBLE);
        binding.layoutTitle.imgTitleMenu.setVisibility(View.INVISIBLE);
        binding.layoutTitle.textTitle.setText("读书");
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void error(int code, String msg) {
        switch (code){
            case ApiError.S_NULL_DATA:
                binding.layoutError.layoutError.setVisibility(View.VISIBLE);
                binding.layoutError.layoutError.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                break;
        }
    }
}
