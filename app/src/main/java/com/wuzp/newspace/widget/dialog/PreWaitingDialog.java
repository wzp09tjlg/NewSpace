package com.wuzp.newspace.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.wuzp.newspace.R;

/**
 * Created by wuzp on 2017/9/24.
 */
public class PreWaitingDialog extends Dialog {

    private View dialogView;

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(true);
        LayoutInflater inflater = LayoutInflater.from(context);
        dialogView = inflater.inflate(R.layout.view_dialog_waiting,null);
        setContentView(dialogView);
    }

}
