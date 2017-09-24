package com.wuzp.newspace.view.entertaiment;

import com.wuzp.newspace.R;
import com.wuzp.newspace.base.MvpFragment;
import com.wuzp.newspace.databinding.FragmentJokeTextBinding;
import com.wuzp.newspace.network.entity.entertaiment.EntertainmentBean;

import java.util.List;

/**
 * Created by wuzp on 2017/9/24.
 */
public class JokeTextFragment extends MvpFragment<FragmentJokeTextBinding,JokeTextPresenter> implements JokeTextView {
    @Override
    protected JokeTextPresenter createPresenter() {
      return new JokeTextPresenter(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_joke_text;
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void initData() {
        super.initData();
    }

    //设置笑话数据
    @Override
    public void setJokeTextData(List<EntertainmentBean.ContentBean> data) {

    }
}
