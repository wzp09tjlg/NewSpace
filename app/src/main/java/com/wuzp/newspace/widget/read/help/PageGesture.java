package com.wuzp.newspace.widget.read.help;

import android.content.res.Resources;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.wuzp.newspace.utils.LogUtil;

/**
 * Created by wuzp on 2017/9/27.
 */
public class PageGesture {
    //点击区域
    public static final int TOUCH_TOOLBAR_AREA = 1;
    public static final int TOUCH_LEFT_AREA = 2;
    public static final int TOUCH_RIGHT_AREA = 3;
    //操作类型
    public static final int TOUCH_TYPE_DEF = 0;
    public static final int TOUCH_TYPE_PREPAGE = 1;
    public static final int TOUCH_TYPE_NEXTPAGE = 2;
    public static final int TOUCH_TYPE_TOOLBAR = 3;
    public static final int TOUCH_TYPE_MARK = 4;
    public static final int TOUCH_TYPE_SUMMARY = 6;

    /**
     * 与书签垂直向下滑动的事件相反的垂直向上滑动<br>
     * 需要暂时屏蔽
     */
    public static final int TOUCH_TYPE_TOWARDS_UP = 5;

    /**
     * touch触发多大范围认为是移动
     */
    public static final int VAILD_MOVE_DISTANCE = 10;

    private int mDisplayWidth;
    private int mDisplayHeight;

    public PageGesture(int displayWidth, int displayHeight) {
        mDisplayWidth = displayWidth;
        mDisplayHeight = displayHeight;
    }

    /**
     * 当前touch类型是否触发翻页
     *
     * @param touchType
     * @return
     */
    public boolean isTurnPage(int touchType) {
        return (touchType == TOUCH_TYPE_NEXTPAGE || touchType == TOUCH_TYPE_PREPAGE);
    }

    /**
     * 判断触发区域
     *
     * @param e
     * @return
     */
    public int judgeTouchArea(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        return judgeTouchArea(x, y);
    }

    public int judgeTouchArea(float x, float y) {
        if ((x > mDisplayWidth / 4 && x <= mDisplayWidth * 3 / 4)
                && (y > mDisplayHeight / 3 && y <= mDisplayHeight * 2 / 3)) {
            return TOUCH_TOOLBAR_AREA;
        } else if (x <= mDisplayWidth / 4
                || (x > mDisplayWidth / 4 && x <= mDisplayWidth * 3 / 4 && y <= mDisplayHeight / 3)) {
            return TOUCH_LEFT_AREA;
        } else {
            return TOUCH_RIGHT_AREA;
        }
    }

    /**
     * 判断触发类型
     *
     * @param e
     * @return
     */
    public int judgeTouchType(MotionEvent e, PointF downTouch, int touchArea) {
        if (isaVaildMove(e, downTouch.x, downTouch.y)) {
            if (judgeMarkTouchType(e, downTouch.x, downTouch.y)) {
                return TOUCH_TYPE_MARK;
            } else if (judgeTowardsUpTouchType(e, downTouch.x, downTouch.y)) {
                return TOUCH_TYPE_TOWARDS_UP;
            }

            if (e.getX() > downTouch.x) {
                return TOUCH_TYPE_PREPAGE;
            } else if (e.getX() < downTouch.x) {
                return TOUCH_TYPE_NEXTPAGE;
            }
        }

        if (touchArea == TOUCH_LEFT_AREA) {
            return TOUCH_TYPE_PREPAGE;
        } else if (touchArea == TOUCH_RIGHT_AREA) {
            return TOUCH_TYPE_NEXTPAGE;
        } else {
            return TOUCH_TYPE_TOOLBAR;
        }
    }

    public boolean isaVaildMove(MotionEvent e, PointF downTouch) {
        return isaVaildMove(e, downTouch.x, downTouch.y);
    }


    public boolean isaVaildMove(MotionEvent e, float x, float y) {
        float xOff = Math.abs(x - e.getX());
        float yOff = Math.abs(y - e.getY());
        if ((xOff > VAILD_MOVE_DISTANCE) || (yOff > VAILD_MOVE_DISTANCE)) {
            return true;
        }
        return false;
    }


    /**
     * sp转px.
     *
     * @param value the value
     * @return the int
     */
    public static int sp2px(float value) {
        Resources r;
        r = Resources.getSystem();
        float spvalue = value * r.getDisplayMetrics().scaledDensity;
        return (int) (spvalue + 0.5f);
    }

    /**
     * 垂直向下的手势
     *
     * @param e
     * @param
     * @return
     */
    public boolean judgeMarkTouchType(MotionEvent e, float x, float y) {
        LogUtil.e(":"+x+y);
        if (e.getY() < y) {
            return false;
        }
        float distanceX = Math.abs(e.getX() - x);
        float distanceY = Math.abs(e.getY() - y);

        if (distanceX < VAILD_MOVE_DISTANCE && distanceY > 3 * VAILD_MOVE_DISTANCE) {
            return true;
        }

        // y轴移动距离是x轴移动的2倍及以上
        if (distanceY / distanceX >= 3) {
            return true;
        }

        return false;
    }

    /**
     * 垂直向上的手势
     *
     * @param e
     * @param
     * @return
     */
    public boolean judgeTowardsUpTouchType(MotionEvent e, float x, float y) {
        if (e.getY() > y) {
            return false;
        }
        float distanceX = Math.abs(e.getX() - x);
        float distanceY = Math.abs(e.getY() - y);

        if (distanceX < VAILD_MOVE_DISTANCE && distanceY > 2 * VAILD_MOVE_DISTANCE) {
            return true;
        }

        // y轴移动距离是x轴移动的3倍及以上
        if (distanceY / distanceX >= 3) {
            return true;
        }

        return false;
    }

    /**
     * 改变纵轴位置，使翻页动画为平移态
     *
     * @param e
     */
    public void transPageAnimFormatPoint(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        y = 0.1f;
        e.setLocation(x, y);
    }

    /**
     * 规则触发点
     *
     * @param e
     */
    public void formatTouchPoint(MotionEvent e) {
        // 当点的座标越出时，规则点
        float x = e.getX();
        float y = e.getY();
        if (x < 0.1f) {
            x = 0.1f;
        } else if (x >= mDisplayWidth - 0.1f) {
            x = mDisplayWidth - 0.1f;
        }
        if (y < 0.1f) {
            y = 0.1f;
        } else if (y >= mDisplayHeight - 0.1f) {
            y = mDisplayHeight - 0.1f;
        }
        e.setLocation(x, y);
    }
}
