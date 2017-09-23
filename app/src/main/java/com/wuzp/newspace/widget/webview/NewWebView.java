package com.wuzp.newspace.widget.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by wuzp on 2017/9/24.
 */
public class NewWebView extends WebView {

    public NewWebView(Context context){
        super(context);
        init(context);
    }

    public NewWebView(Context context, AttributeSet attri){
        super(context,attri);
        init(context);
    }

    public NewWebView(Context context,AttributeSet attri,int flag){
        super(context,attri,flag);
        init(context);
    }

    private void init(Context context){
        setWebViewClient(new NewWebViewClient());
        setWebChromeClient(new NewWebChromeClient());
    }

    public class NewWebViewClient extends WebViewClient{

        //拦截wenview中的请求，可以选择是调用本地的文件还是请求网络的文件
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
          /*  LogUtil.i("url:", url);
            if (ApplicationUtils.isStatic) {
                int lastSlash = url.lastIndexOf("/");
                if (lastSlash != -1) {
                    String suffix = url.substring(lastSlash + 1);
                    if (suffix.contains("?")) {
                        suffix = suffix.substring(0, suffix.indexOf("?"));
                    }
                    if (offlineResources.contains(suffix)) {
                        String mimeType;
                        if (suffix.endsWith(".js")) {
                            mimeType = "application/x-javascript";
                        } else if (suffix.endsWith(".css")) {
                            mimeType = "text/css";
                        } else {
                            mimeType = "text/html";
                        }
                        try {
                            InputStream is = new FileInputStream(new File(BaseApp.gContext.getFilesDir(), "h5Res/build/static/" + suffix));
                            LogUtil.e("本地获取url:", url);
                            return new WebResourceResponse(mimeType, "UTF-8", is);
                        } catch (IOException e) {
                            LogUtil.e("获取失败");
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (url.contains("book.sina.cn") && url.contains("ischecklogin=1")) {
                try {
                    URL url2 = new URL(injectIsParams(url.toString()));
                    URLConnection connection = url2.openConnection();
                    return new WebResourceResponse("text/html", "UTF-8", connection.getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
            return super.shouldInterceptRequest(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }
    public class NewWebChromeClient extends WebChromeClient{

    }
}
