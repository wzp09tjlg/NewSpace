package com.wuzp.newspace.view.main.fragment;

import com.wuzp.newspace.R;
import com.wuzp.newspace.base.MvpFragment;
import com.wuzp.newspace.databinding.FragmentFunBinding;

/**
 * Created by wuzp on 2017/9/23.
 */
public class UserFragment extends MvpFragment<FragmentFunBinding,FunPresenter> implements FunView {

    @Override
    protected FunPresenter createPresenter() {
        return new FunPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
