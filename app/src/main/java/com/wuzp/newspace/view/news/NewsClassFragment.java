package com.wuzp.newspace.view.news;

import com.wuzp.newspace.R;
import com.wuzp.newspace.base.BasePresenter;
import com.wuzp.newspace.base.MvpFragment;
import com.wuzp.newspace.databinding.FragmentNewsClassBinding;

/**
 * Created by wuzp on 2017/9/24.
 */
public class NewsClassFragment extends MvpFragment<FragmentNewsClassBinding,BasePresenter> implements NewsClassView {
    public static final int TYPE_AREA = 1;
    public static final int TYPE_DYNAMIC = 2;

    private int mType = TYPE_AREA;
    @Override
    protected BasePresenter createPresenter() {
        if(mType == TYPE_AREA){
            return new NewsAreaPresenter(this);
        }else{
            return new NewsDynamicPresenter(this);
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_news_class;
    }

    public void setType(int type){
        this.mType = type;
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void initData() {
        super.initData();
    }

    //设置地区类数据
    @Override
    public void setNewsAreaData() {

    }

    //设置实时类数据
    @Override
    public void setNewsDynamicData() {

    }
}
