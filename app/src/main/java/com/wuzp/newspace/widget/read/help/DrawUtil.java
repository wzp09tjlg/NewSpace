package com.wuzp.newspace.widget.read.help;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.text.format.Time;

import com.wuzp.newspace.utils.PixelUtil;

/**
 * Created by wuzp on 2017/9/27.
 */
public class DrawUtil {

    /**
     * 根据坐标绘制title
     */
    public static void drawTitle(Canvas canvas, String title, float left, float bottom, Paint paint) {
        try{
            if(TextUtils.isEmpty(title)) title = "-";//测试环境下 经常出现title是null
            canvas.drawText(title, left, bottom, paint);
        }catch (Exception e){}
    }

    /**
     * 根据坐标绘制电量
     */
    public static void drawElectric(Canvas c, int left, int top, int percent, int color) {
        Paint elect = new Paint();
        elect.setColor(color);
        elect.setAntiAlias(true);
        elect.setStyle(Paint.Style.STROKE);
        elect.setTextSize(PixelUtil.sp2px(12));
        Paint.FontMetrics fontMetrics = elect.getFontMetrics();
        int maxY = (int) ((top + fontMetrics.descent) - (fontMetrics.descent - fontMetrics.ascent) / 6);
        int minY = (int) ((top + fontMetrics.ascent) + (fontMetrics.descent - fontMetrics.ascent) / 6);
        Rect rect = new Rect(left, minY, left + 50, maxY);
        c.drawRect(rect, elect);
        Paint paint2 = new Paint(elect);
        paint2.setStyle(Paint.Style.FILL);
        Rect rect2 = new Rect(left + 2, minY + 2, left + percent - 2, maxY - 2);
        c.drawRect(rect2, paint2);
        Rect rect3 = new Rect(left + 50, (maxY + minY) / 2 - 3, left + 55, (maxY + minY) / 2 + 3);
        c.drawRect(rect3, paint2);
    }

    /**
     * 通过坐标绘制时间
     */
    public static void drawTime(Canvas canvas, float left, float bottom, Paint paint) {
        Time time = new Time();
        time.setToNow();
        String timeStr;
        if (time.minute < 10) {
            timeStr = "" + time.hour + " : 0" + time.minute;
        } else {
            timeStr = "" + time.hour + " : " + time.minute;
        }
        canvas.drawText(timeStr, left, bottom, paint);
    }

    /**
     * 通过坐标绘制页码
     */
    public static void drawCurpage(Canvas c, int pageNum, int total, float left, float top, Paint paint) {
        String percetStr = pageNum + 1 + "/" + total;
        int pSWidth = (int) paint.measureText(percetStr) + 10;
        c.drawText(percetStr, left - pSWidth, top, paint);
    }
}
