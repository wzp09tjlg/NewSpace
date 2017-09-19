package com.wuzp.newspace.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by wuzp on 2017/9/19.
 */
@SuppressWarnings("All")
public abstract class NewActivity<B extends ViewDataBinding,P extends BasePresenter> extends BaseActivity {
    private B binding;
    private P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,getLayoutId());
        presenter = createPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter != null){
            presenter.detachView();
        }
    }

    protected abstract int getLayoutId();

    protected abstract P createPresenter();
}
