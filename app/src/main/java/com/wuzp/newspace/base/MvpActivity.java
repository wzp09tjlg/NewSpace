package com.wuzp.newspace.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by wuzp on 2017/9/17.
 */
public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P mvpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter = createPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mvpPresenter != null){
            mvpPresenter.detachView();
        }
    }

    protected abstract P createPresenter();
}
