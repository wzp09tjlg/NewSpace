package com.wuzp.newspace.widget.read;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Region;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import com.wuzp.newspace.utils.PixelUtil;
import com.wuzp.newspace.widget.read.help.PageFactory;
import com.wuzp.newspace.widget.read.help.PageGesture;
import com.wuzp.newspace.widget.read.help.ThemeManager;
import com.wuzp.newspace.widget.read.listener.PageViewListener;

/**
 * Created by wuzp on 2017/9/26.
 * 自己去写一个阅读器
 * 支持仿真翻页和左右翻页
 */
public class BookView extends View {
    private final String TAG = BookView.class.getSimpleName();
    /**翻页模式*/
    public static final int BROWSE_MODE_EMULATION = 0; // 仿真翻页
    public static final int BROWSE_MODE_SCROOL_H = 1; // 左右切换翻页
    private int browseMode = BROWSE_MODE_SCROOL_H;

    //状态值
    boolean mIsRTandLB; // 是否属于右上左下
    //其他属性
    /**滑动翻页相关*/
    //上下滑动需要计算值
    private int lastTonchY;//最后一点触摸位置Y坐标
    private float downTouchX;//点击的位置的X坐标
    private int offsetheight = 0;//绘制需求偏移量
    private float offsetcover = 0;//覆盖翻页偏移量
    private int offset = 0;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private VelocityTracker mVelocityTracker;

    private int mWidth;
    private int mHeight;
    private int contentHeight;
    private PageFactory pageFactory;
    private PageViewListener pageViewListener;
    private Bitmap curBitmap, nextBitmap, cacheBitmap, totalBitmap, titleBgBitmap, coverCurBitmap, covreNextBitmep;//当前页、下一页、滑动翻页的第三页、滑动翻页合成图、滑动翻页title部分背景图。
    private Canvas curCanvas, nextCanvas, cacheCanvas, titleBgCanvas;

    /**仿真翻页需求*/
    private int mCornerX = 0; // 拖拽点对应的页脚
    private int mCornerY = 0;
    private Path mPath0;//贝塞尔曲线一
    private Path mPath1;//贝塞尔曲线二

    PointF mTouch = new PointF(); // 拖拽点
    PointF mBezierStart1 = new PointF(); // 贝塞尔曲线一起始点
    PointF mBezierControl1 = new PointF(); // 贝塞尔曲线一控制点
    PointF mBeziervertex1 = new PointF(); // 贝塞尔曲线一顶点
    PointF mBezierEnd1 = new PointF(); // 贝塞尔曲线一结束点

    PointF mBezierStart2 = new PointF(); // 另一条贝塞尔曲线二
    PointF mBezierControl2 = new PointF();//贝塞尔曲线二控制点
    PointF mBeziervertex2 = new PointF();//贝塞尔曲线二顶点
    PointF mBezierEnd2 = new PointF();//贝塞尔曲线二终点

    /**阴影的默认宽度*/
    private static final int SHADOW_DEF_DP = 20;
    float mMiddleX;
    float mMiddleY;
    float mDegrees;
    float mTouchToCornerDis;
    ColorMatrixColorFilter mColorMatrixFilter;
    Matrix mMatrix;
    float[] mMatrixArray = {0, 0, 0, 0, 0, 0, 0, 0, 1.0f};
    int bgColor;

    float mMaxLength;
    int[] mBackShadowColors;
    int[] mFrontShadowColors;
    GradientDrawable mBackShadowDrawableLR;
    GradientDrawable mBackShadowDrawableRL;
    GradientDrawable mFolderShadowDrawableLR;
    GradientDrawable mFolderShadowDrawableRL;

    GradientDrawable mFrontShadowDrawableHBT;
    GradientDrawable mFrontShadowDrawableHTB;
    GradientDrawable mFrontShadowDrawableVLR;
    GradientDrawable mFrontShadowDrawableVRL;
    GradientDrawable mSilideShadowDrawable;
    GradientDrawable mCoverShadowDrawable;

    Scroller mScroller;
    Paint mPaint;

    Bitmap mCurPageBitmap = null; // 当前页
    Bitmap mNextPageBitmap = null; // 下一页
    Bitmap cachePageBitmap = null;
    private float mDefaultShadow;
    private PageGesture pageGesture;

    private long touchStartTime = 0;
    private boolean skipEnd = false;
    private boolean isCancelTurnPage = false;
    private int isTouchArea ;
    private boolean isTouchCentral;
    private boolean isVaildSlide;
    private boolean isStartAnim;
    private boolean isSlideing;
    private boolean isMoveUpdateStart;
    private boolean isEditState;
    private boolean canSlide;
    private boolean isFirstPage;

    public BookView(Context context){
        super(context);
    }

    public BookView(Context context, AttributeSet attri){
        super(context,attri);
    }

    public BookView(Context context,AttributeSet attri,int flag){
        super(context,attri,flag);
    }

    public void init(Context context,PageFactory pageFactory){
        mPath0 = new Path();
        mPath1 = new Path();
        createDrawable();
        mDefaultShadow = PixelUtil.dp2px(SHADOW_DEF_DP);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);

        ColorMatrix cm = new ColorMatrix();
        float array[] = {0.55f, 0, 0, 0, 80.0f, 0, 0.55f, 0, 0, 80.0f, 0, 0,
                0.55f, 0, 80.0f, 0, 0, 0, 0.2f, 0};
        cm.set(array);

        mColorMatrixFilter = new ColorMatrixColorFilter(cm);
        mMatrix = new Matrix();
        mScroller = new Scroller(context, new LinearInterpolator());

        mTouch.x = 0.01f; // 不让x,y为0,否则在点计算时会有问题
        mTouch.y = 0.01f;

        this.pageFactory = pageFactory;
        contentHeight = pageFactory.getContentHeight();//针对上下滑动需求参数
        this.mHeight = pageFactory.getScreenHeight();
        this.mWidth = pageFactory.getScreenWidth();
        bgColor = pageFactory.getBgColor();
        mMaxLength = (float) Math.hypot(mWidth, mHeight);
        curBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.RGB_565);
        nextBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.RGB_565);


        curCanvas = new Canvas(curBitmap);
        nextCanvas = new Canvas(nextBitmap);

        final ViewConfiguration configuration = ViewConfiguration.get(context);//fling操作参数需求
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();

        pageGesture = new PageGesture(mWidth, mHeight);//点击区域判断类
    }

    public void updatePageFactory(PageFactory pageFactory) {
        this.pageFactory = pageFactory;
        contentHeight = pageFactory.getContentHeight();//针对上下滑动需求参数
        this.mHeight = pageFactory.getScreenHeight();
        this.mWidth = pageFactory.getScreenWidth();
        bgColor = pageFactory.getBgColor();
        mMaxLength = (float) Math.hypot(mWidth, mHeight);
        curBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.RGB_565);
        nextBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.RGB_565);
        curCanvas = new Canvas(curBitmap);
        nextCanvas = new Canvas(nextBitmap);
        pageGesture = new PageGesture(mWidth, mHeight);//点击区域判断类
    }

    private void createDrawable() {
        int[] color = {0x222222, 0x60222222};
        mFolderShadowDrawableRL = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, color);
        mFolderShadowDrawableRL.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        mFolderShadowDrawableLR = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, color);
        mFolderShadowDrawableLR.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        mBackShadowColors = new int[]{0x80111111, 0x111111};
        mBackShadowDrawableRL = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, mBackShadowColors);
        mBackShadowDrawableRL.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        mBackShadowDrawableLR = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, mBackShadowColors);
        mBackShadowDrawableLR.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        mFrontShadowColors = new int[]{0x30222222, 0x222222};
        mFrontShadowDrawableVLR = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, mFrontShadowColors);
        mFrontShadowDrawableVLR.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        mFrontShadowDrawableVRL = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, mFrontShadowColors);
        mFrontShadowDrawableVRL.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        mFrontShadowDrawableHTB = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, mFrontShadowColors);
        mFrontShadowDrawableHTB.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        mFrontShadowDrawableHBT = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, mFrontShadowColors);
        mFrontShadowDrawableHBT.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        int[] silideColors = new int[]{0x60222222, 0x222222};
        mSilideShadowDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, silideColors);
        mSilideShadowDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        mCoverShadowDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, silideColors);
        mCoverShadowDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
    }

    private void setBitmaps(Bitmap curBitmap, Bitmap nextBitmap, Bitmap cacheBitmap) {
        mCurPageBitmap = curBitmap;
        mNextPageBitmap = nextBitmap;
        cachePageBitmap = cacheBitmap;
    }

    private void setBitmaps(Bitmap curBitmap, Bitmap nextBitmap) {
        setBitmaps(curBitmap, nextBitmap, null);
    }

    /**********************************************************************************************/
    /******************************** 绘制过程相关的方法 ******************************************/
    /**********************************************************************************************/
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(bgColor);
        if (browseMode == BROWSE_MODE_EMULATION) {
            calcPoints();
            mPath0.reset();
            mPath0.moveTo(mBezierStart1.x, mBezierStart1.y);
            mPath0.quadTo(mBezierControl1.x, mBezierControl1.y, mBezierEnd1.x,
                    mBezierEnd1.y);
            mPath0.lineTo(mTouch.x, mTouch.y);
            mPath0.lineTo(mBezierEnd2.x, mBezierEnd2.y);
            mPath0.quadTo(mBezierControl2.x, mBezierControl2.y, mBezierStart2.x,
                    mBezierStart2.y);
            mPath0.lineTo(mCornerX, mCornerY);
            mPath0.close();
            drawCurrentPageArea(canvas, mCurPageBitmap, mPath0);
            drawNextPageAreaAndShadow(canvas, mNextPageBitmap);
            drawCurrentPageShadow(canvas);
            drawCurrentBackArea(canvas, mCurPageBitmap);
        } else if (browseMode == BROWSE_MODE_SCROOL_H) {
            drawCurrentPage(canvas, mCurPageBitmap);
            drawNextPage(canvas, mNextPageBitmap);
            drawCurPageShadow(canvas);
        }
    }

    /**
     * 重新绘制当前页
     */
    public void reDraw() {
        mTouch.x = 0.01f; // 不让x,y为0,否则在点计算时会有问题
        mTouch.y = 0.01f;
        mCornerX = 0; // 拖拽点对应的页脚
        mCornerY = 0;
        pageFactory.skipPage();
        pageFactory.draw(curCanvas);
        pageFactory.draw(nextCanvas);
        setBitmaps(curBitmap, nextBitmap, cacheBitmap);
        postInvalidate();
    }

    public void reDraw(int page) {
        mTouch.x = 0.01f; // 不让x,y为0,否则在点计算时会有问题
        mTouch.y = 0.01f;
        mCornerX = 0; // 拖拽点对应的页脚
        mCornerY = 0;
        pageFactory.skipPage(page);
        pageFactory.draw(curCanvas);
        pageFactory.draw(nextCanvas);
        setBitmaps(curBitmap, nextBitmap, cacheBitmap);
        postInvalidate();
    }

    //这里是计算两条贝塞尔曲线的 各个点(利用的知识是三角形相似 边的比例相等)
    //控制点和 边角的顶点 以中间线对称，中间线相交右侧边和底部边 的两个交点分别
        // 就是连那个条贝塞尔曲线的控制点(按照此方法可以获取两条贝塞尔曲线的控制点)
        private void calcPoints() {
        mMiddleX = (mTouch.x + mCornerX) / 2;
        mMiddleY = (mTouch.y + mCornerY) / 2;
        // 贝塞尔曲线一的控制点 是根绝直角三角形 斜边上的高 区分出来的两个三角形相似，边的比例相等 (贝塞尔曲线二的顶点获取方法类似)
        mBezierControl1.x = mMiddleX - (mCornerY - mMiddleY)
                * (mCornerY - mMiddleY) / (mCornerX - mMiddleX);
        mBezierControl1.y = mCornerY;
        mBezierControl2.x = mCornerX;
        mBezierControl2.y = mMiddleY - (mCornerX - mMiddleX)
                * (mCornerX - mMiddleX) / (mCornerY - mMiddleY);

        mBezierStart1.x = mBezierControl1.x - (mCornerX - mBezierControl1.x)
                / 2;
        mBezierStart1.y = mCornerY;

        // 当mBezierStart1.x < 0或者mBezierStart1.x > 480时
        // 如果继续翻页，会出现BUG故在此限制
        if (mTouch.x > 0 && mTouch.x < mWidth) {
            if (mBezierStart1.x < 0 || mBezierStart1.x > mWidth) {
                if (mBezierStart1.x < 0)
                    mBezierStart1.x = mWidth - mBezierStart1.x;

                float f1 = Math.abs(mCornerX - mTouch.x);
                float f2 = mWidth * f1 / mBezierStart1.x;
                mTouch.x = Math.abs(mCornerX - f2);

                float f3 = Math.abs(mCornerX - mTouch.x)
                        * Math.abs(mCornerY - mTouch.y) / f1;
                mTouch.y = Math.abs(mCornerY - f3);

                mMiddleX = (mTouch.x + mCornerX) / 2;
                mMiddleY = (mTouch.y + mCornerY) / 2;

                mBezierControl1.x = mMiddleX - (mCornerY - mMiddleY)
                        * (mCornerY - mMiddleY) / (mCornerX - mMiddleX);
                mBezierControl1.y = mCornerY;

                mBezierControl2.x = mCornerX;
                mBezierControl2.y = mMiddleY - (mCornerX - mMiddleX)
                        * (mCornerX - mMiddleX) / (mCornerY - mMiddleY);
                mBezierStart1.x = mBezierControl1.x
                        - (mCornerX - mBezierControl1.x) / 2;
            }
        }
        mBezierStart2.x = mCornerX;
        mBezierStart2.y = mBezierControl2.y - (mCornerY - mBezierControl2.y)
                / 2;

        mTouchToCornerDis = (float) Math.hypot((mTouch.x - mCornerX),
                (mTouch.y - mCornerY));

        //利用两条直线的相交 分别求取两条贝塞尔曲线的终点
        mBezierEnd1 = getCross(mTouch, mBezierControl1, mBezierStart1,
                mBezierStart2);
        mBezierEnd2 = getCross(mTouch, mBezierControl2, mBezierStart1,
                mBezierStart2);

        //分别求取贝塞尔曲线的顶点坐标 (贝塞尔曲线起点和终点为底边，控制点为另外的一个点的三角形。
        // 其中贝塞尔曲线的顶点是这个三角形的中点,左边计算如下)
        mBeziervertex1.x = (mBezierStart1.x + 2 * mBezierControl1.x + mBezierEnd1.x) / 4;
        mBeziervertex1.y = (2 * mBezierControl1.y + mBezierStart1.y + mBezierEnd1.y) / 4;
        mBeziervertex2.x = (mBezierStart2.x + 2 * mBezierControl2.x + mBezierEnd2.x) / 4;
        mBeziervertex2.y = (2 * mBezierControl2.y + mBezierStart2.y + mBezierEnd2.y) / 4;
    }

    private void drawCurrentPageArea(Canvas canvas, Bitmap bitmap, Path path) {
        canvas.save();
        canvas.clipPath(path, Region.Op.XOR);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.restore();
    }

    private void drawNextPageAreaAndShadow(Canvas canvas, Bitmap bitmap) {
        mPath1.reset();
        mPath1.moveTo(mBezierStart1.x, mBezierStart1.y);
        mPath1.lineTo(mBeziervertex1.x, mBeziervertex1.y);
        mPath1.lineTo(mBeziervertex2.x, mBeziervertex2.y);
        mPath1.lineTo(mBezierStart2.x, mBezierStart2.y);
        mPath1.lineTo(mCornerX, mCornerY);
        mPath1.close();

        mDegrees = (float) Math.toDegrees(Math.atan2(mBezierControl1.x
                - mCornerX, mBezierControl2.y - mCornerY));
        int leftx;
        int rightx;
        GradientDrawable mBackShadowDrawable;
        if (mIsRTandLB) {
            leftx = (int) (mBezierStart1.x);
            rightx = (int) (mBezierStart1.x + mTouchToCornerDis / 4);
            mBackShadowDrawable = mBackShadowDrawableLR;
        } else {
            leftx = (int) (mBezierStart1.x - mTouchToCornerDis / 4);
            rightx = (int) mBezierStart1.x;
            mBackShadowDrawable = mBackShadowDrawableRL;
        }
        canvas.save();
        canvas.clipPath(mPath0);
        canvas.clipPath(mPath1, Region.Op.INTERSECT);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.rotate(mDegrees, mBezierStart1.x, mBezierStart1.y);
        mBackShadowDrawable.setBounds(leftx, (int) mBezierStart1.y, rightx,
                (int) (mMaxLength + mBezierStart1.y));
        mBackShadowDrawable.draw(canvas);
        canvas.restore();
    }

    /**
     * 绘制翻起页的阴影
     */
    public void drawCurrentPageShadow(Canvas canvas) {
        double degree;
        if (mIsRTandLB) {
            degree = Math.PI
                    / 4
                    - Math.atan2(mBezierControl1.y - mTouch.y, mTouch.x
                    - mBezierControl1.x);
        } else {
            degree = Math.PI
                    / 4
                    - Math.atan2(mTouch.y - mBezierControl1.y, mTouch.x
                    - mBezierControl1.x);
        }
        // 翻起页阴影顶点与touch点的距离
        double d1 = (float) 25 * 1.414 * Math.cos(degree);
        double d2 = (float) 25 * 1.414 * Math.sin(degree);
        float x = (float) (mTouch.x + d1);
        float y;
        if (mIsRTandLB) {
            y = (float) (mTouch.y + d2);
        } else {
            y = (float) (mTouch.y - d2);
        }
        mPath1.reset();
        mPath1.moveTo(x, y);
        mPath1.lineTo(mTouch.x, mTouch.y);
        mPath1.lineTo(mBezierControl1.x, mBezierControl1.y);
        mPath1.lineTo(mBezierStart1.x, mBezierStart1.y);
        mPath1.close();
        float rotateDegrees;
        canvas.save();

        canvas.clipPath(mPath0, Region.Op.XOR);
        canvas.clipPath(mPath1, Region.Op.INTERSECT);
        int leftx;
        int rightx;
        GradientDrawable mCurrentPageShadow;
        if (mIsRTandLB) {
            leftx = (int) (mBezierControl1.x);
            rightx = (int) mBezierControl1.x + 25;
            mCurrentPageShadow = mFrontShadowDrawableVLR;
        } else {
            leftx = (int) (mBezierControl1.x - 25);
            rightx = (int) mBezierControl1.x + 1;
            mCurrentPageShadow = mFrontShadowDrawableVRL;
        }

        rotateDegrees = (float) Math.toDegrees(Math.atan2(mTouch.x
                - mBezierControl1.x, mBezierControl1.y - mTouch.y));
        canvas.rotate(rotateDegrees, mBezierControl1.x, mBezierControl1.y);
        mCurrentPageShadow.setBounds(leftx,
                (int) (mBezierControl1.y - mMaxLength), rightx,
                (int) (mBezierControl1.y));
        mCurrentPageShadow.draw(canvas);
        canvas.restore();

        mPath1.reset();
        mPath1.moveTo(x, y);
        mPath1.lineTo(mTouch.x, mTouch.y);
        mPath1.lineTo(mBezierControl2.x, mBezierControl2.y);
        mPath1.lineTo(mBezierStart2.x, mBezierStart2.y);
        mPath1.close();
        canvas.save();
        canvas.clipPath(mPath0, Region.Op.XOR);
        canvas.clipPath(mPath1, Region.Op.INTERSECT);
        if (mIsRTandLB) {
            leftx = (int) (mBezierControl2.y);
            rightx = (int) (mBezierControl2.y + 25);
            mCurrentPageShadow = mFrontShadowDrawableHTB;
        } else {
            leftx = (int) (mBezierControl2.y - 25);
            rightx = (int) (mBezierControl2.y + 1);
            mCurrentPageShadow = mFrontShadowDrawableHBT;
        }
        rotateDegrees = (float) Math.toDegrees(Math.atan2(mBezierControl2.y
                - mTouch.y, mBezierControl2.x - mTouch.x));
        canvas.rotate(rotateDegrees, mBezierControl2.x, mBezierControl2.y);
        float temp;
        if (mBezierControl2.y < 0)
            temp = mBezierControl2.y - mHeight;
        else
            temp = mBezierControl2.y;

        int hmg = (int) Math.hypot(mBezierControl2.x, temp);
        if (hmg > mMaxLength)
            mCurrentPageShadow
                    .setBounds((int) (mBezierControl2.x - 25) - hmg, leftx,
                            (int) (mBezierControl2.x + mMaxLength) - hmg,
                            rightx);
        else
            mCurrentPageShadow.setBounds(
                    (int) (mBezierControl2.x - mMaxLength), leftx,
                    (int) (mBezierControl2.x), rightx);

        mCurrentPageShadow.draw(canvas);
        canvas.restore();
    }

    /**
     * 绘制翻起页背面
     */
    private void drawCurrentBackArea(Canvas canvas, Bitmap bitmap) {
        int i = (int) (mBezierStart1.x + mBezierControl1.x) / 2;
        float f1 = Math.abs(i - mBezierControl1.x);
        int i1 = (int) (mBezierStart2.y + mBezierControl2.y) / 2;
        float f2 = Math.abs(i1 - mBezierControl2.y);
        float f3 = Math.min(f1, f2);
        mPath1.reset();
        mPath1.moveTo(mBeziervertex2.x, mBeziervertex2.y);
        mPath1.lineTo(mBeziervertex1.x, mBeziervertex1.y);
        mPath1.lineTo(mBezierEnd1.x, mBezierEnd1.y);
        mPath1.lineTo(mTouch.x, mTouch.y);
        mPath1.lineTo(mBezierEnd2.x, mBezierEnd2.y);
        mPath1.close();
        GradientDrawable mFolderShadowDrawable;
        int left;
        int right;
        if (mIsRTandLB) {
            left = (int) (mBezierStart1.x - 1);
            right = (int) (mBezierStart1.x + f3 + 1);
            mFolderShadowDrawable = mFolderShadowDrawableLR;
        } else {
            left = (int) (mBezierStart1.x - f3 - 1);
            right = (int) (mBezierStart1.x + 1);
            mFolderShadowDrawable = mFolderShadowDrawableRL;
        }
        canvas.save();
        canvas.clipPath(mPath0);
        canvas.clipPath(mPath1, Region.Op.INTERSECT);
        mPaint.setColorFilter(mColorMatrixFilter);

        float dis = (float) Math.hypot(mCornerX - mBezierControl1.x,
                mBezierControl2.y - mCornerY);
        float f8 = (mCornerX - mBezierControl1.x) / dis;
        float f9 = (mBezierControl2.y - mCornerY) / dis;
        mMatrixArray[0] = 1 - 2 * f9 * f9;
        mMatrixArray[1] = 2 * f8 * f9;
        mMatrixArray[3] = mMatrixArray[1];
        mMatrixArray[4] = 1 - 2 * f8 * f8;
        mMatrix.reset();
        mMatrix.setValues(mMatrixArray);
        mMatrix.preTranslate(-mBezierControl1.x, -mBezierControl1.y);
        mMatrix.postTranslate(mBezierControl1.x, mBezierControl1.y);
        canvas.drawBitmap(bitmap, mMatrix, mPaint);
        mPaint.setColorFilter(null);
        canvas.rotate(mDegrees, mBezierStart1.x, mBezierStart1.y);
        mFolderShadowDrawable.setBounds(left, (int) mBezierStart1.y, right,
                (int) (mBezierStart1.y + mMaxLength));
        mFolderShadowDrawable.draw(canvas);
        canvas.restore();
    }

    /**
     * 覆盖动画--画出当前页
     *
     * @param canvas canvas
     * @param bitmap bitmap
     */
    private void drawCurrentPage(Canvas canvas, Bitmap bitmap) {
        if (canvas == null || bitmap == null || bitmap.isRecycled()) {
            return;
        }
        float dx = mTouch.x;
        canvas.save();
        canvas.clipRect(0, 0, mTouch.x, mHeight);
        canvas.drawBitmap(bitmap, -mWidth + dx, 0, null);
        canvas.restore();
    }

    /**
     * 覆盖动画--画出下一页
     *
     * @param canvas canvas
     * @param bitmap bitmap
     */
    private void drawNextPage(Canvas canvas, Bitmap bitmap) {
        if (canvas == null || bitmap == null || bitmap.isRecycled())
            return;

        canvas.save();
        canvas.clipRect(mTouch.x, 0, mWidth, mHeight);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.restore();
    }

    /**
     * 覆盖动画--画出当前页阴影
     *
     * @param canvas canvas
     */
    private void drawCurPageShadow(Canvas canvas) {
        if (mTouch.x <= 0.01f && mTouch.y <= 0.01f) {
            return;
        }
        GradientDrawable mCurrentPageShadow = mSilideShadowDrawable;

        mCurrentPageShadow.setBounds((int) mTouch.x, 0, (int) (mTouch.x + mDefaultShadow), mHeight);
        canvas.save();
        mCurrentPageShadow.draw(canvas);
        canvas.restore();
    }

    /**
     * 求解直线P1P2和直线P3P4的交点坐标
     */
    public PointF getCross(PointF P1, PointF P2, PointF P3, PointF P4) {
        PointF CrossP = new PointF();
        // 二元函数通式： y=ax+b
        float a1 = (P2.y - P1.y) / (P2.x - P1.x);
        float b1 = ((P1.x * P2.y) - (P2.x * P1.y)) / (P1.x - P2.x);

        float a2 = (P4.y - P3.y) / (P4.x - P3.x);
        float b2 = ((P3.x * P4.y) - (P4.x * P3.y)) / (P3.x - P4.x);
        CrossP.x = (b2 - b1) / (a1 - a2);
        CrossP.y = a1 * CrossP.x + b1;
        return CrossP;
    }

    /**********************************************************************************************/
    /******************************** 触摸事件相关的方法 ******************************************/
    /**********************************************************************************************/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    skipEnd = false;
        //down事件
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //防止频繁点击，当点击间隔小于200毫秒时，拦截事件
            if (System.currentTimeMillis() - touchStartTime < 200) {
                return false;
            }

            //在每次点击时将是否取消翻页默认为false
            isCancelTurnPage = false;
            mTouch.x = event.getX();
            mTouch.y = event.getY();
            lastTonchY = (int) (event.getY());
            downTouchX = event.getX();
            touchStartTime = System.currentTimeMillis();
            abortAnimation();//结束动画
            //防止下一次点击时 页面出现的闪屏
            setBitmaps(nextBitmap, nextBitmap);
            isTouchArea = pageGesture.judgeTouchArea(event);
            isTouchCentral = isTouchArea == PageGesture.TOUCH_TOOLBAR_AREA;
            calcCornerXY(event);//计算页脚位置，如果有手势滑动在move事件中重置x轴页脚，y轴不再变化，没有滑动则在up事件中直接使用，做到优先判断手势再判断点击位置
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //判断是否是有效操作
            isVaildSlide = pageGesture.isaVaildMove(event, mTouch.x, mTouch.y);
            //当手指抖动时候会触发move事件，当认为是无效操作，所以当前有可能处于长按监听状态
                    //当滑动为有效滑动时，移除对划线区域和中心控制区域的判断，并移除可能存在的正在划线内容
                    if (isVaildSlide) {
                        isTouchCentral = false;
                    }
                    if (isTouchCentral) {
                        return true;
                    } else {
                        //通过手势判断翻页状态
                        if (isMoveUpdateStart) {
                            if (event.getX() > downTouchX) {
                                mCornerX = 0;
                                mIsRTandLB = (mCornerX == 0 && mCornerY == mHeight) || (mCornerX == mWidth && mCornerY == 0);
                                isStartAnim = updatePageForNotScroolV(true);
                            } else {
                                mCornerX = mWidth;
                                mIsRTandLB = (mCornerX == 0 && mCornerY == mHeight) || (mCornerX == mWidth && mCornerY == 0);
                                isStartAnim = updatePageForNotScroolV(false);
                            }
                            isMoveUpdateStart = false;
                        }
                    }
                mTouch.x = event.getX();
                mTouch.y = event.getY();
                return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
                if (isTouchCentral) {
                    pageViewListener.callTitleBarWithBottomBar();
                    isEditState = true;
                    canSlide = false;
                    mTouch.x = 0.01f; // 不让x,y为0,否则在点计算时会有问题
                    mTouch.y = 0.01f;
                    mCornerX = 0; // 拖拽点对应的页脚
                    mCornerY = 0;
                    invalidate();
                    return true;
                }
                if (isSlideing) {
                    if (isTouchArea == PageGesture.TOUCH_LEFT_AREA) {
                        isStartAnim = updatePageForNotScroolV(true);
                    } else if (isTouchArea == PageGesture.TOUCH_RIGHT_AREA) {
                        isStartAnim = updatePageForNotScroolV(false);
                    }
                }
                if (!isMoveUpdateStart) {
                    if (event.getX() > downTouchX && mCornerX == mWidth) {
                        isSlideing = true;
                        isCancelTurnPage = true;
                        isStartAnim = updatePageForNotScroolV(true);
                    } else if (event.getX() < downTouchX && mCornerX == 0) {
                        isSlideing = true;
                        isCancelTurnPage = true;
                        isStartAnim = updatePageForNotScroolV(false);
                    }
                }
                isSlideing = true;
                isMoveUpdateStart = true;
                if (isStartAnim) {
                    startAnimation();//动画速率控制
                } else {
                    mTouch.x = 0.01f; // 不让x,y为0,否则在点计算时会有问题
                    mTouch.y = 0.01f;
                    mCornerX = 0; // 拖拽点对应的页脚
                    mCornerY = 0;
                }
                invalidate();
                isStartAnim = false;
                return true;
        }
        return false;
    }

    /**
     * 计算拖拽点对应的拖拽脚
     */
    public void calcCornerXY(MotionEvent event) {
        if (event.getX() <= mWidth / 2) {
            mCornerX = 0;
        } else {
            mCornerX = mWidth;
        }
        if (event.getY() <= mHeight / 2) {
            mCornerY = 0;
        } else {
            mCornerY = mHeight;
        }
        mIsRTandLB = (mCornerX == 0 && mCornerY == mHeight) || (mCornerX == mWidth && mCornerY == 0);
    }

    public void calcCornerXY(float x, float y) {
        if (x <= mWidth / 2) {
            mCornerX = 0;
        } else {
            mCornerX = mWidth;
        }
        if (y <= mHeight / 2) {
            mCornerY = 0;
        } else {
            mCornerY = mHeight;
        }
    }

    public void abortAnimation() {
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
    }

    private void startAnimation() {
        int dx = 0, dy;
        if (mCornerX > 0) {
            if (browseMode == BROWSE_MODE_SCROOL_H) {
                dx = -(int) (mTouch.x + mDefaultShadow);
                if (isCancelTurnPage) {
                    dx = (int) (mWidth - mTouch.x);
                }
            } else if (browseMode == BROWSE_MODE_EMULATION) {
                dx = -(int) (mWidth + mTouch.x);
                if (isCancelTurnPage) {
                    dx = (int) (mWidth + mWidth - mTouch.x);
                }
            }
        } else {
            if (browseMode == BROWSE_MODE_SCROOL_H) {
                dx = (int) (mWidth - mTouch.x);
                if (isCancelTurnPage) {
                    dx = -(int) (mTouch.x + mDefaultShadow);
                }
            } else if (browseMode == BROWSE_MODE_EMULATION) {
                dx = (int) (mWidth + mWidth - mTouch.x);
                if (isCancelTurnPage) {
                    dx = -(int) (mWidth + mTouch.x);
                }
            }
        }
        if (mCornerY > 0) {
            dy = (int) (mHeight - mTouch.y);
            if (isCancelTurnPage) {
                dy = (int) (-mTouch.y);
            }
        } else {
            dy = (int) (-mTouch.y);
            if (isCancelTurnPage) {
                dy = (int) (mHeight - mTouch.y);
            }
        }
        mScroller.startScroll((int) mTouch.x, (int) mTouch.y, dx, dy,
                200);
    }

    public boolean updatePageForNotScroolV(boolean isleft) {
        if (isSlideing) {
            pageFactory.draw(curCanvas);
            if (isleft) {
                if (pageFactory.prePage()) {
                    pageFactory.draw(nextCanvas);
                    if (browseMode == BROWSE_MODE_SCROOL_H) {
                        setBitmaps(nextBitmap, curBitmap);
                    } else {
                        if (isCancelTurnPage) {
                            setBitmaps(nextBitmap, curBitmap);
                        } else {
                            setBitmaps(curBitmap, nextBitmap);
                        }
                    }
                } else {
                    pageViewListener.readEnd(false);
                    isSlideing = false;
                    return false;
                }
            } else {
                if (pageFactory.nextPage()) {
                    pageFactory.draw(nextCanvas);
                    if (browseMode == BROWSE_MODE_SCROOL_H) {
                        setBitmaps(curBitmap, nextBitmap);
                    } else {
                        if (isCancelTurnPage) {
                            setBitmaps(nextBitmap, curBitmap);
                        } else {
                            setBitmaps(curBitmap, nextBitmap);
                        }
                    }
                } else {
                    pageViewListener.readEnd(true);
                    isSlideing = false;
                    return false;
                }
            }
            isSlideing = false;
        }
        return true;
    }

    /**********************************************************************************************/
    /******************************** 触摸事件相关的方法 ******************************************/
    /**********************************************************************************************/
    public void setTextSize(int size) {
        if (pageFactory.getFontSize() != size) {
            pageFactory.setFontSize(size);
            pageFactory.skipPage(pageFactory.reSlicePage());
            reDraw();
        }
    }

    public int setTextSizeDown() {
        int size = pageFactory.getFontSize() - 2 > 12 ? pageFactory.getFontSize() - 2 : 12;
        setTextSize(size);
        return size;
    }

    public int setTextSizeUp() {
        int size = pageFactory.getFontSize() + 2 < 30 ? pageFactory.getFontSize() + 2 : 30;
        setTextSize(size);
        return size;
    }

    public int getTextSize() {
        return pageFactory.getFontSize();
    }

    public void setPageViewListener(PageViewListener pageViewListener) {
        this.pageViewListener = pageViewListener;
    }

    public void changeTheme() {
        bgColor = Color.parseColor(ThemeManager.getTheme());
        pageFactory.setBgColor(bgColor);
        if (titleBgCanvas != null) {
            titleBgCanvas.drawColor(bgColor);
        }
        reDraw();
    }

    public void setFirstPage(boolean first) {
        this.isFirstPage = first;
    }

    public void setPageFactory(PageFactory pageFactory) {
        this.pageFactory = pageFactory;
        contentHeight = pageFactory.getContentHeight();
        bgColor = pageFactory.getBgColor();
    }

    public int getBrowseMode() {
        return browseMode;
    }

    public boolean setBrowseMode(int browseMode) {
        skipEnd = false;
        if (this.browseMode != browseMode) {
            this.browseMode = browseMode;
            curBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.RGB_565);
            nextBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.RGB_565);
            curCanvas = new Canvas(curBitmap);
            nextCanvas = new Canvas(nextBitmap);
            return true;
        }
        return false;
    }
}
