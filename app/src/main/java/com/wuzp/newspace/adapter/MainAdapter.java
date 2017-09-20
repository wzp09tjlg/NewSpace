package com.wuzp.newspace.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wuzp.newspace.R;
import com.wuzp.newspace.databinding.ItemMainBinding;

/**
 * Created by wuzp on 2017/9/20.
 * 使用DataBinding 来写Adapter
 */
public class MainAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMainBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_main, parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
