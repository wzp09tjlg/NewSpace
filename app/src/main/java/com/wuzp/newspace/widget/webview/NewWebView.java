package com.wuzp.newspace.widget.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.AttributeSet;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wuzp.newspace.utils.LogUtil;

/**
 * Created by wuzp on 2017/9/24.
 */
public class NewWebView extends WebView {

    private OnWebViewStatusInterface statusInterface;

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

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if(statusInterface != null){
                statusInterface.onPageStartLoad();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(statusInterface != null){
                statusInterface.onPageLoadFinished();
            }
        }

        //加载错误类处理


        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            if(statusInterface != null){
                statusInterface.onPageLoadError();
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            if(statusInterface != null){
                statusInterface.onPageLoadError();
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if(statusInterface != null){
                statusInterface.onPageLoadError();
            }
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            if(statusInterface != null){
                statusInterface.onPageLoadError();
            }
        }

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
            view.loadUrl(url);
            LogUtil.e("url:" + url);
            return true;
        }
    }
    public class NewWebChromeClient extends WebChromeClient{

    }

    public void setStatusInterface(OnWebViewStatusInterface statusInterface) {
        this.statusInterface = statusInterface;
    }

    public interface OnWebViewStatusInterface{
        void onPageLoadFinished();
        void onPageStartLoad();
        void onPageLoadError();
    }
}
