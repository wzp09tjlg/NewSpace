package com.wuzp.newspace.widget.webview;

import android.webkit.JavascriptInterface;

/**
 * Created by wuzp on 2017/9/24.
 * 实现类中 重写这个类中的方法就行
 */
public abstract class NewJsCallback {

    @JavascriptInterface
    public String jsGetName(){
        return "";
    }

    @JavascriptInterface
    public void jsSetData(Object obj){}


}
