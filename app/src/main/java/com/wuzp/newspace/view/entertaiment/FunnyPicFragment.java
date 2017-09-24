package com.wuzp.newspace.view.entertaiment;

import com.wuzp.newspace.R;
import com.wuzp.newspace.base.MvpFragment;
import com.wuzp.newspace.databinding.FragmentFunnyPicBinding;
import com.wuzp.newspace.network.entity.entertaiment.EntertainmentBean;

import java.util.List;

/**
 * Created by wuzp on 2017/9/24.
 */
public class FunnyPicFragment extends MvpFragment<FragmentFunnyPicBinding,FunnyPicPresenter> implements FunnyPicView {
    @Override
    protected FunnyPicPresenter createPresenter() {
      return new FunnyPicPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_funny_pic;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    //设置高效图片数据
    @Override
    public void setFunnyPicData(List<EntertainmentBean.ContentBean> data) {

    }
}
