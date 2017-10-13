package com.wuzp.newspace.widget.plane;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by wuzp on 2017/10/13.
 * 就是简单集成ViewGroup
 * 一定是要求重写layout的布局 因为viewgroup不知道你怎么摆放子view的
 */
public class PlaneView extends ViewGroup {

    private ArrayList<ArrayList<View>> linesViews = new ArrayList<>();//每一行的view
    ArrayList<Integer> lineHeights = new ArrayList<>();

    public PlaneView(Context context) {
        super(context);
    }

    public PlaneView(Context context, AttributeSet attri) {
        super(context, attri);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return super.generateLayoutParams(attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return super.generateLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);

        int totalViewSize = getChildCount();//所以的view的总数
        //这里需要计算每个子View的大小 然后进行排列和计算
        int i = 0;
        int childWidth = 0;
        int childHight = 0;
        int tempLineWidth = 0;
        int maxlineHeight = 0;
        int totalHeight = 0;
        int totalWidth = 0;
        for (i = 0; i < totalViewSize; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();

            childWidth = params.width + params.leftMargin + params.rightMargin;
            childHight = params.height + params.topMargin + params.bottomMargin;

            if (childWidth + tempLineWidth < width) { //one line is big than width ,create a new line
                tempLineWidth = tempLineWidth + childWidth;
                maxlineHeight = Math.max(childHight, maxlineHeight);
            } else {//大于宽度 新起一行
                totalHeight = totalHeight + childHight;//一行中最大的高度
                tempLineWidth = childWidth;
                totalWidth = Math.max(totalWidth, tempLineWidth);
            }

            if (i == totalViewSize - 1) {//最后一个view时
                totalHeight = totalHeight + childHight;
                totalWidth = Math.max(totalWidth, tempLineWidth);
            }
        }

        setMeasuredDimension((widthModel == MeasureSpec.EXACTLY) ? width : totalWidth,
                (heightModel == MeasureSpec.EXACTLY) ? height : totalHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        linesViews.clear();
        int totalSize = getChildCount();

        int i = 0;

        int width = getWidth();
        ArrayList<View> lineChild = new ArrayList<>();

        int lineWidth = 0;
        int lineHeight = 0;
        int childWidth = 0;
        int childHeight = 0;
        for (i = 0; i < totalSize; i++) {
            View child = getChildAt(i);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();

            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            if (params.width + params.leftMargin + params.rightMargin + lineWidth > width) {
                linesViews.add(lineChild);
                lineHeights.add(lineHeight);
                lineWidth = 0;
                lineChild = new ArrayList<>();
            }

            lineWidth = lineWidth + childWidth + params.rightMargin + params.leftMargin;
            lineHeight = Math.max(lineHeight, childHeight + params.topMargin + params.bottomMargin);

            lineChild.add(child);
        }

        linesViews.add(lineChild);
        lineHeights.add(lineHeight);

        int left = 0;
        int top = 0;
        int lines = lineHeights.size();
        for (i = 0; i < lines; i++) {
            lineChild = linesViews.get(i);
            lineHeight = lineHeights.get(i);

            int childNum = lineChild.size();
            for (int j = 0; j < childNum; j++) {
                View view = lineChild.get(j);
                if (view.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) view
                        .getLayoutParams();
//计算childView的left,top,right,bottom
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + view.getMeasuredWidth();
                int bc = tc + view.getMeasuredHeight();

                view.layout(lc, tc, rc, bc);
                left += view.getMeasuredWidth() + lp.rightMargin
                        + lp.leftMargin;
            }
            left = 0;
            top = top + lineHeight;
        }
    }

    //不需要重写onDraw方法
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
