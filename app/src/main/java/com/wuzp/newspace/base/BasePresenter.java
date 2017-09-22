package com.wuzp.newspace.base;

import com.wuzp.newspace.network.ApiCallback;
import com.wuzp.newspace.network.ApiService;
import com.wuzp.newspace.network.ApiStore;
import com.wuzp.newspace.network.entity.HttpBase;
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
 */
public class BasePresenter<V>{
    private static String TAG = BasePresenter.class.getSimpleName();

    public V mvpView = null;
    public ApiService apiService = ApiStore.getApiService();
    private CompositeDisposable mCompositeDisposable;

    public BasePresenter(V view) {
        attachView(view);
    }

    public void attachView(V view){
       this.mvpView = view;
    }

    public void detachView(){
       this.mvpView = null;
        onUnsubscribe();
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
