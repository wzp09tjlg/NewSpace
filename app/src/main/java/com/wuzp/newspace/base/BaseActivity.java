package com.wuzp.newspace.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.wuzp.newspace.network.ApiCallback;
import com.wuzp.newspace.network.ApiService;
import com.wuzp.newspace.network.ApiStore;
import com.wuzp.newspace.network.entity.base.HttpBase;
import com.wuzp.newspace.utils.LogUtil;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wuzp on 2017/9/17.
 * 基类
 * 1.对网络层的封装
 * 2.对EventBus的注册
 */
public class BaseActivity extends FragmentActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    public ApiService apiService = ApiStore.getApiService();
    private CompositeDisposable mCompositeDisposable;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //EventBus.getDefault().register(this);//不在基类中声明EventBus 在哪儿用，在哪儿调
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(this);//注销同声明
    }

    protected final <T> void addSubscription(Flowable flowable, final ApiCallback<T> apiCallback) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        if (apiCallback == null) {
            LogUtil.e(TAG, "callback can not be null");
            return;
        }

        Disposable disposable = flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HttpBase<T>>() {
                    @Override
                    public void accept(HttpBase<T> o) throws Exception {
                        apiCallback.onNext(o);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        apiCallback.onError(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        apiCallback.onComplete();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    protected final void addSubscription(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    protected final void onUnsubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
