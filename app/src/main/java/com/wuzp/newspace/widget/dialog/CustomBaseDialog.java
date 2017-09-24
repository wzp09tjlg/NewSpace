package com.wuzp.newspace.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.wuzp.newspace.R;

/**
 * Created by wuzp on 2017/8/17.
 * 网兜自定义的Dialog,需要提供展示的布局ID
 */
public abstract class CustomBaseDialog extends Dialog {
    private View dialogView = null;

    public CustomBaseDialog(Context context){
        super(context, R.style.dialog_common);
        init(context);
    }

    public CustomBaseDialog(Context context, int themeResId){
        super(context,themeResId);
        init(context);
    }

    public CustomBaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener){
      super(context,cancelable,cancelListener);
        init(context);
    }

    private void init(Context context){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(true);
        LayoutInflater inflater = LayoutInflater.from(context);
        dialogView = inflater.inflate(getDialogLayoutId(),null);
        setContentView(dialogView);
    }

    @Override
    public void show() {
        super.show();
        setViewClickListener();
    }

    public View getDialogView(){
        return dialogView;
    }

    //暴露的方法
    abstract public int getDialogLayoutId();

    //可设置View的事件
    abstract public void setViewClickListener();

}
