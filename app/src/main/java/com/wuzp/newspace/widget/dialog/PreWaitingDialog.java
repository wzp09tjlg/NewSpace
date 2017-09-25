package com.wuzp.newspace.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuzp.newspace.R;

/**
 * Created by wuzp on 2017/9/24.
 */
public class PreWaitingDialog extends Dialog {
    private Context mContext;
    private View dialogView;
    private TextView textMsg;
    private ImageView imgRefresh;

    public PreWaitingDialog(Context context){
        super(context);
    }

    public PreWaitingDialog(Context context, int themeResId){
        super(context,themeResId);
        init(context);
    }

    public PreWaitingDialog(Context context, boolean cancelable, OnCancelListener cancelListener){
        super(context,cancelable,cancelListener);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(true);
        LayoutInflater inflater = LayoutInflater.from(context);
        dialogView = inflater.inflate(R.layout.view_dialog_waiting,null);
        textMsg = (TextView)dialogView.findViewById(R.id.text_loading);
        imgRefresh = (ImageView) dialogView.findViewById(R.id.img_refresh);
        setContentView(dialogView);
    }

    public void show(String msg){
        textMsg.setText(msg);
        Animation operatingAnim = AnimationUtils.loadAnimation(mContext, R.anim.anim_loading_rotate);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        imgRefresh.startAnimation(operatingAnim);
        show();
    }

    public void showRefreshing(){
        show("正在刷新...");
    }

    public void showLoading(){
        show("正在加载...");
    }
}
