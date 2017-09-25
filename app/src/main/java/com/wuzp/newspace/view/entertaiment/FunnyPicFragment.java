package com.wuzp.newspace.view.entertaiment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wuzp.newspace.R;
import com.wuzp.newspace.adapter.BindingViewHolder;
import com.wuzp.newspace.adapter.CommonAdapter;
import com.wuzp.newspace.base.MvpFragment;
import com.wuzp.newspace.databinding.FragmentFunnyPicBinding;
import com.wuzp.newspace.databinding.ItemFunnyPicBinding;
import com.wuzp.newspace.network.entity.entertaiment.EntertainmentBean;
import com.wuzp.newspace.utils.GlideUtil;
import com.wuzp.newspace.utils.LogUtil;
import com.wuzp.newspace.widget.common.RecyclerItemDecoration;

import java.util.List;

/**
 * Created by wuzp on 2017/9/24.
 */
public class FunnyPicFragment extends MvpFragment<FragmentFunnyPicBinding, FunnyPicPresenter> implements FunnyPicView {
    private CommonAdapter<EntertainmentBean.ContentBean> adapter;
    private List<EntertainmentBean.ContentBean> mData;

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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        RecyclerItemDecoration itemDecoration = new RecyclerItemDecoration(mContext,R.drawable.drawable_item_divider_joke_text);
        binding.recyclerFunny.setLayoutManager(layoutManager);
        binding.recyclerFunny.addItemDecoration(itemDecoration);

        presenter.start();
    }

    @Override
    protected void initData() {
        super.initData();
        adapter = new CommonAdapter<EntertainmentBean.ContentBean>(mContext, R.layout.item_funny_pic) {
            @Override
            public void convert(BindingViewHolder holder, int position) {
                ItemFunnyPicBinding binding = holder.getBinding();
                binding.textTitle.setText(mData.get(position).getTitle());
                binding.textTime.setText(mData.get(position).getCt());
                LogUtil.d("wzp","url:" + mData.get(position).getImg());
                GlideUtil.load(mContext,mData.get(position).getImg(),R.drawable.icon_defualt_loading,R.drawable.icon_defualt_loading,binding.imgFunny);
            }
        };
        binding.recyclerFunny.setAdapter(adapter);
    }

    //设置搞笑图片数据
    @Override
    public void setFunnyPicData(List<EntertainmentBean.ContentBean> data) {
        mData = data;
        adapter.setData(data);
    }

    @Override
    public void error(int code, String msg) {

    }
}
