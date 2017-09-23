package com.wuzp.newspace.widget.tag;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.wuzp.newspace.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzp on 2017/7/19.
 * 展示 标签 的控件，使用时 就是一个ViewGroup添加TextView，每个TextView是一个标签
 */
public class TagView extends ViewGroup {
    protected static final int[] LIMITELINE = {R.attr.limiteLine};
    /**存储所有的View，按行记录*/
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    /**记录每一行的最大高度*/
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    private int mMaxLines = -1;//默认是全部展示全

    public TagView(Context context){
        super(context);
        init(context,null);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public TagView(Context context, AttributeSet attrs, int flag){
        super(context, attrs,flag);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attr){
        if(attr == null) return;
        TypedArray array = context.obtainStyledAttributes(attr,LIMITELINE);
        if (array != null) {
            mMaxLines = array.getInt(R.styleable.TagView_limiteLine, -1);
            array.recycle();
        }
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
    }

    /**负责设置子控件的测量模式和大小 根据所有子控件设置自己的宽和高*/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获得它的父容器为它设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // 如果是warp_content情况下，记录宽和高
        int width = 0;
        int height = 0;
        /**记录每一行的宽度，width不断取最大宽度*/
        int lineWidth = 0;
        /**每一行的高度，累加至height*/
        int lineHeight = 0;
        /**记录第几行*/
        int lineCount = 0;

        int cCount = getChildCount();

        // 遍历每个子元素
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            // 测量每一个child的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 得到child的lp
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            // 当前子空间实际占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin
                    + lp.rightMargin;
            // 当前子空间实际占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin
                    + lp.bottomMargin;
            /**
             * 如果加入当前child，则超出最大宽度，则的到目前最大宽度给width，类加height 然后开启新行
             */
            if (lineWidth + childWidth > sizeWidth) {
                width = Math.max(lineWidth, childWidth);// 取最大的
                // 叠加当前高度，
                height += lineHeight;
                lineWidth = childWidth; // 重新开启新行，开始记录
                // 开启记录下一行的高度
                lineHeight = childHeight;
                lineCount = lineCount +1;
                if(mMaxLines != -1 && mMaxLines == lineCount){
                    break; //如果只是显示一行,获取最大宽度 和 一行的高度 之后不在计算view的大小
                }
            } else
            // 否则累加值lineWidth,lineHeight取最大高度
            {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            if ((mMaxLines == -1) && (i == cCount - 1)) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }else if((i == cCount - 1) && (mMaxLines != -1 && mMaxLines > lineCount)){
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth
                : width, (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight
                : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();

        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;
        // 存储每一行所有的childView
        List<View> lineViews = new ArrayList<View>();
        int cCount = getChildCount();
        // 遍历所有的孩子
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            // 如果已经需要换行
            if (childWidth + lp.leftMargin + lp.rightMargin + lineWidth > width) {
                // 记录这一行所有的View以及最大高度
                mLineHeight.add(lineHeight);
                // 将当前行的childView保存，然后开启新的ArrayList保存下一行的childView
                mAllViews.add(lineViews);

                lineWidth = 0;// 重置行宽
                lineViews = new ArrayList<View>();
            }
            /**如果不需要换行，则累加*/
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin);
            lineViews.add(child);
            if(mMaxLines != -1 && mMaxLines == (mAllViews.size())){
                break;
            }
        }
        // 记录最后一行
        if(mMaxLines == -1){
            mLineHeight.add(lineHeight);
            mAllViews.add(lineViews);
        }else if(mMaxLines != -1 && mMaxLines > (mAllViews.size())){
            mLineHeight.add(lineHeight);
            mAllViews.add(lineViews);
        }

        int left = 0;
        int top = 0;
        // 得到总行数
        int lineNums = mAllViews.size();
        for (int i = 0; i < lineNums; i++) {
            // 每一行的所有的views
            lineViews = mAllViews.get(i);
            // 当前行的最大高度
            lineHeight = mLineHeight.get(i);

            // 遍历当前行所有的View
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child
                        .getLayoutParams();

                //计算childView的left,top,right,bottom
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc =lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);
                left += child.getMeasuredWidth() + lp.rightMargin
                        + lp.leftMargin;
            }
            left = 0;
            top += lineHeight;
        }
    }

    public void setMaxLines(int line){
        if(line<=0){
            this.mMaxLines = -1;
        }else{
            this.mMaxLines = line;
        }
    }
}
