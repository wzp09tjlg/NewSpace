package com.wuzp.newspace.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by wuzp on 2017/9/20.
 */
public class BindingViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding binding;

    public BindingViewHolder(ViewDataBinding viewBinding) {
        super(viewBinding.getRoot());
        this.binding = viewBinding;
    }

    public <B extends ViewDataBinding> B getBinding() {
        return (B) this.binding;
    }
}