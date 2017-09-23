package com.wuzp.newspace.widget.tag;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;

/**
 * Created by wuzp on 2017/7/20.
 * 用于添加到TagView中
 */
public class TagViewSub extends AppCompatTextView {

    public TagViewSub(Context context){
        super(context);
        init(context);
    }

    public TagViewSub(Context context, AttributeSet attr){
        super(context,attr);
        init(context);
    }

    public TagViewSub(Context context,AttributeSet attr,int flag){
        super(context,attr,flag);
        init(context);
    }

    private void init(Context context){
        setGravity(Gravity.CENTER);
        setSingleLine(true);
        setMaxEms(8);
        setEllipsize(TextUtils.TruncateAt.END);
    }
}
