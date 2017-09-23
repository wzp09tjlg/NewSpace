package com.wuzp.newspace.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wuzp.newspace.R;
import com.wuzp.newspace.databinding.ItemMainBinding;
import com.wuzp.newspace.network.entity.GirlsBean.GirlBean;
import com.wuzp.newspace.utils.GlideUtil;

import java.util.List;

/**
 * Created by wuzp on 2017/9/20.
 * 使用DataBinding 来写Adapter
 */
public class MainAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    private List<GirlBean> data = null;
    private Context mContext = null;

    public MainAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMainBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_main, parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        ItemMainBinding binding =  (ItemMainBinding)holder.getBinding();
        GlideUtil.load(mContext,data.get(position).getPicUrl(),binding.img);
        binding.textTitle.setText(data.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<GirlBean> data){
        this.data = data;
        notifyDataSetChanged();
    }
}
