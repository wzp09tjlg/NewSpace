package com.wuzp.newspace.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wuzp on 2017/9/23.
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<BindingViewHolder> {

    private Context mContext;
    private List<T> mData;
    private int layoutId;

    public CommonAdapter(Context context,int layoutId){
        this.mContext = context;
        this.layoutId = layoutId;
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),layoutId,parent,false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        convert(holder,position);
    }

    public abstract void convert(BindingViewHolder holder,int position);

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<T> data){
        this.mData = data;
        notifyDataSetChanged();
    }
}
