package com.wuzp.newspace.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by wuzp on 2017/9/23.
 */
public class ActivityUtil {

    public static void start(Activity src,Class dest){
        Intent intent = new Intent(src,dest);
        src.startActivity(intent);
    }

    public static void start(Context context,Class dest){
        Intent intent = new Intent(context,dest);
        context.startActivity(intent);
    }

    public static void startForResult(Activity src,Class dest,int requestCode){
        Intent intent = new Intent(src,dest);
        src.startActivityForResult(intent,requestCode);
    }

    public static void startWithBundle(Activity src, Class dest, Bundle bundle){
        Intent intent = new Intent(src,dest);
        intent.putExtras(bundle);
        src.startActivity(intent);
    }

    public static void startWithBundle(Context context,Class dest,Bundle bundle){
        Intent intent = new Intent(context,dest);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void startWithBundleForResult(Activity src,Class dest,Bundle bundle,int requestCode){
        Intent intent = new Intent(src,dest);
        intent.putExtras(bundle);
        src.startActivityForResult(intent,requestCode);
    }

}
