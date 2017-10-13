package com.wuzp.newspace.utils.anim;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.BaseInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import com.wuzp.newspace.R;

/**
 * Created by wuzp on 2017/10/11.
 */
@SuppressWarnings("ALL")
public class animutil {

    private static float doublefactor = 2.0f;
    private static float cubefactor = 3.0f;

    public static void startAnim(View view) {
        //差值器
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(1f, 2f, 3f, 4f, 5f, 6f);
        valueAnimator.setDuration(5000);
        valueAnimator.setInterpolator(new BaseInterpolator() {
            @Override
            public float getInterpolation(float input) {
                //这个插值器就是为了计算在这个时间段当中 根据给定的这个数如何产生目标数
                float result = (float) Math.pow(input, doublefactor) + input * input;
                return result;
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //也不知道这个listener do what?
                //动画在每一帧都会回调这个listener
            }
        });

        Button btn = new Button(null);
        valueAnimator.setTarget(btn);//设置目标对象
        //这就是一个产值器，针对这个插值器能够依据给定的值 通过算法 得到需要变化的值

        //插值器实例
        Button mButton = (Button) view.findViewById(R.id.btn_show);
        // 创建动画作用对象：此处以Button为例
        float curTranslationX = mButton.getTranslationX();
        // 获得当前按钮的位置
        ObjectAnimator animator = ObjectAnimator.ofFloat(mButton, "translationX", curTranslationX, 300, curTranslationX);
        // 创建动画对象 & 设置动画
        // 表示的是:
        // 动画作用对象是mButton
        // 动画作用的对象的属性是X轴平移
        // 动画效果是:从当前位置平移到 x=1500 再平移到初始位置
        animator.setDuration(5000);
        animator.setInterpolator(new DecelerateInterpolator(1));
        // 设置插值器
        animator.start();
        // 启动动画

        //估值器
        TypeEvaluator<Integer> typeEvaluator = new TypeEvaluator<Integer>() {
            @Override
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {

                return null;
            }
        };
        ObjectAnimator anim = ObjectAnimator.ofObject(btn, "height", null,1,3);
         // 在第4个参数中传入对应估值器类的对象
         // 系统内置的估值器有3个：
          // IntEvaluator：以整型的形式从初始值 - 结束值 进行过渡
          // FloatEvaluator：以浮点型的形式从初始值 - 结束值 进行过渡
          // ArgbEvaluator：以Argb类型的形式从初始值 - 结束值 进行过渡

        //所以在objectAnimation中 有ofInt ofFloat onArgb三种属性动画
        //对于ofObject 估值器 就是需要我么自己去实现了

    }

    class PointEvaluator implements TypeEvaluator {

        // 复写evaluate（）
        // 在evaluate（）里写入对象动画过渡的逻辑
        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {

            // 将动画初始值startValue 和 动画结束值endValue 强制类型转换成Point对象
            Point startPoint = (Point) startValue;
            Point endPoint = (Point) endValue;

            // 根据fraction来计算当前动画的x和y的值
            float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
            float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());

            // 将计算后的坐标封装到一个新的Point对象中并返回
            Point point = new Point(x, y);
            return point;
        }

    }
}
