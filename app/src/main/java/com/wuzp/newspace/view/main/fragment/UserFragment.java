package com.wuzp.newspace.view.main.fragment;

import android.view.View;

import com.wuzp.newspace.R;
import com.wuzp.newspace.base.MvpFragment;
import com.wuzp.newspace.databinding.FragmentUserBinding;
import com.wuzp.newspace.widget.dialog.PreWaitingDialog;

/**
 * Created by wuzp on 2017/9/23.
 */
public class UserFragment extends MvpFragment<FragmentUserBinding,UserPresenter> implements UserView {

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected PreWaitingDialog getPreWaitingDialog() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        binding.layoutTitle.textTitle.setText("我的");
        binding.layoutTitle.imgTitleBack.setVisibility(View.INVISIBLE);
        binding.layoutTitle.imgTitleMenu.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void error(int code, String msg) {

    }
}
