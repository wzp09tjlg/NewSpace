package com.wuzp.newspace.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wuzp.newspace.widget.bitmap.CircleTransform;

/**
 * Created by wuzp on 2017/9/20.
 * 图片加载工具类
 */
public class GlideUtil {

    public static void load(Context context,String url,ImageView dest){
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                //防止切换图片时默认动画造成的闪烁
                .dontAnimate()
                .crossFade()
                .into(dest);
    }

    public static void load(Context context, String url, int placeHolder, int errHolder, ImageView dest){
        Glide.with(context)
                .load(url)
                .placeholder(placeHolder)
                .error(errHolder)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                //防止切换图片时默认动画造成的闪烁
                .dontAnimate()
                .crossFade()
                .into(dest);
    }

    public static void loadCircle(Context context,String url,ImageView dest){
        Glide.with(context)
                .load(url)
                .transform(new CircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                //防止切换图片时默认动画造成的闪烁
                .dontAnimate()
                .crossFade()
                .into(dest);
    }

    public static void loadCircle(Context context,String url,int placeHolder,int errHolder,ImageView dest){
        Glide.with(context)
                .load(url)
                .placeholder(placeHolder)
                .error(errHolder)
                .transform(new CircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                //防止切换图片时默认动画造成的闪烁
                .dontAnimate()
                .crossFade()
                .into(dest);
    }

}
