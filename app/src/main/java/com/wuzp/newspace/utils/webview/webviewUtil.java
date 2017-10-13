package com.wuzp.newspace.utils.webview;

import android.webkit.ValueCallback;
import android.webkit.WebView;

/**
 * Created by wuzp on 2017/10/3.
 */
@SuppressWarnings("all")
public class webviewUtil {
    private WebView webView = new WebView(null);//这里只是简单的作为功能

    public void testWebViewJsExecuteCallback(String script){
        webView.evaluateJavascript(script, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                //TODO
                //js在执行完之后 会回调这个callback，就是在客户端这边需要H5执行某个动作之后，然后本地再执行接下来的动作。
                //所以会有这个回调 (以前虽然调用这个方法使用，但是不知道这里还是可以传递回调的)
            }
        });
    }
}
