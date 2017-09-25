package com.wuzp.newspace.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuzp.newspace.R;
import com.wuzp.newspace.network.ApiCallback;
import com.wuzp.newspace.network.ApiService;
import com.wuzp.newspace.network.ApiStore;
import com.wuzp.newspace.network.entity.base.HttpBase;
import com.wuzp.newspace.utils.LogUtil;
import com.wuzp.newspace.widget.dialog.PreWaitingDialog;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wuzp on 2017/9/23.
 */
public class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();

    public ApiService apiService = ApiStore.getApiService();
    private CompositeDisposable mCompositeDisposable;
    protected Context mContext;
    protected PreWaitingDialog preWaitingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = container.getContext();
        //EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //EventBus.getDefault().unregister(this);
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

    protected void initPreWaitingDialog(){
        setPreWaitingDialog(getPreWaitingDialog());
    }

    protected void setPreWaitingDialog(PreWaitingDialog dialog){
        if(preWaitingDialog == null){
            preWaitingDialog = dialog;
        }
    }

    //基本的等待框展示及隐藏...
    protected PreWaitingDialog getPreWaitingDialog(){
        return new PreWaitingDialog(mContext, R.style.dialog_common);
    }

    protected void showRefreshing(){
        if(preWaitingDialog != null){
            preWaitingDialog.showRefreshing();
        }
    }


    protected void showLoading(){
        if(preWaitingDialog != null){
            preWaitingDialog.showLoading();
        }
    }

    protected void showWaiting(){
        if(preWaitingDialog != null){
            preWaitingDialog.showLoading();
        }
    }

    protected void hideWaiting(){
        if(preWaitingDialog != null && preWaitingDialog.isShowing()){
            preWaitingDialog.dismiss();
        }
    }
}
