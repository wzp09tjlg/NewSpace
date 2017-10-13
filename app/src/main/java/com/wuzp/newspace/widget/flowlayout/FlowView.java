package com.wuzp.newspace.widget.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by wuzp on 2017/10/13.
 * 继承ViewGroup 一定要重写onlayout方法
 */
public class FlowView extends ViewGroup {

    private ArrayList<ArrayList<View>> childrenViews = new ArrayList<>();
    private ArrayList<Integer> childredHeight = new ArrayList<>();


    public FlowView(Context context) {
        super(context);
    }

    public FlowView(Context context, AttributeSet attri) {
        super(context, attri);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);

        int maxLineWidth = 0;
        int totalHeight = 0;

        int childWidth = 0;
        int childHeight = 0;

        int tempLineWidth = 0;
        int tempLineHeight = 0;
        int i = 0;
        int totalChild = getChildCount();
        for(i=0;i<totalChild;i++){
            View child = getChildAt(i);
            if(child.getVisibility() == GONE) continue;
            measureChild(child, widthMeasureSpec, heightMeasureSpec);//让父布局测量下子布局的宽高
            MarginLayoutParams params = (MarginLayoutParams)child.getLayoutParams();

            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();

            if(tempLineWidth + childWidth + params.leftMargin + params.rightMargin > width){
                //大于推荐宽度，那么就换新行
                maxLineWidth = Math.max(maxLineWidth,tempLineWidth);
                tempLineWidth = childWidth;

                totalHeight = totalHeight + tempLineHeight;
                tempLineHeight = childHeight;
            }else{//在原来的基础上添加view
                tempLineWidth = tempLineWidth + childWidth + params.rightMargin + params.leftMargin;
                maxLineWidth = Math.max(maxLineWidth,tempLineWidth);

                tempLineHeight =  Math.max(tempLineHeight,childHeight + params.topMargin + params.bottomMargin);
            }
            if(i == totalChild - 1){
                totalHeight = totalHeight + tempLineHeight;
            }
        }
        setMeasuredDimension((widthModel == MeasureSpec.EXACTLY) ? width : tempLineWidth,
                (heightModel == MeasureSpec.EXACTLY) ? height : totalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        childrenViews.clear();
        childredHeight.clear();
        ArrayList<View> lineChildren = new ArrayList<>();

        int viewWidth = getWidth();//不需要再计算了

        int i,j=0;
        int childrenCount = getChildCount();

        int childWidth = 0;
        int childHeight = 0;

        int tempLineChildrenWidth = 0;
        int tempLineChildrenHeight = 0;

        //循环将子view添加到每一行的list中，然后在换行的时候 将其添加到总的views中  并且针对每行的高度做记录
        for(i=0;i<childrenCount;i++){
            View child = getChildAt(i);
            if(child.getVisibility() == GONE) continue;
            MarginLayoutParams param = (MarginLayoutParams)child.getLayoutParams();
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();

            if(tempLineChildrenWidth + childWidth + param.leftMargin + param.rightMargin > viewWidth){
               //新的一行
                childrenViews.add(lineChildren);
                lineChildren = new ArrayList<>();
                lineChildren.add(child);
                childredHeight.add(tempLineChildrenHeight);
                tempLineChildrenWidth = childWidth + param.leftMargin + param.rightMargin;
            }else{ //添加到不满一行的位置
                lineChildren.add(child);
                tempLineChildrenWidth = tempLineChildrenWidth + childWidth + param.leftMargin + param.rightMargin;
                tempLineChildrenHeight = Math.max(tempLineChildrenHeight , childHeight + param.topMargin + param.bottomMargin);
            }
            if(i == childrenCount - 1){
                childrenViews.add(lineChildren);
                childredHeight.add(tempLineChildrenHeight);
            }
        }

        int top = 0;
        int left = 0;
        int lines = childredHeight.size();
        int tempLineHeight = 0;
        int lineNum = 0;
        ArrayList<View> tempLineChildren = null;
        //根据每一行，然后针对子view做layout布局
        for(i=0;i<lines;i++){
            tempLineChildren  = childrenViews.get(i);
            tempLineHeight = childredHeight.get(i);

            lineNum = tempLineChildren.size();
            for(j=0;j<lineNum;j++){
                View child = tempLineChildren.get(j);
                if(child.getVisibility() == GONE) continue;
                MarginLayoutParams param = (MarginLayoutParams) child.getLayoutParams();

                int tempLeft = left + param.leftMargin;
                int tempTop  = top  + param.topMargin;
                int tempRight = tempLeft + child.getWidth();
                int tempBottom = tempTop + child.getHeight();

                child.layout(tempLeft,tempTop,tempRight,tempBottom);
                left = left + param.leftMargin + child.getWidth() + param.rightMargin;
            }
            left = 0;
            top = top + tempLineHeight;
        }
    }
}
