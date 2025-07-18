package com.yumu.appinfo.floatbtn;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.yumu.appinfo.R;

/**
 * 卫星菜单（ArcMenu）
 * 支持自定义起止角度、半径，动画路径与 FloatingActionMenu 完全一致。
 */
public class ArcMenu extends FrameLayout {

    /* ===== 枚举 ===== */
    public enum Position {LEFT_TOP, RIGHT_TOP, LEFT_BOTTOM, RIGHT_BOTTOM}

    public enum Status {OPEN, CLOSE}

    /* ===== 接口 ===== */
    public interface OnMenuItemClickListener {
        void onClick(View view, int position);
    }

    /* ===== 属性 ===== */
    private Position mPosition = Position.RIGHT_BOTTOM;
    private Status mCurrentStatus = Status.CLOSE;
    private int mRadius;
    private int mStartAngle;
    private int mEndAngle;
    private View mCButton;
    private OnMenuItemClickListener mListener;
    private int mMarginLeft, mMarginTop, mMarginRight, mMarginBottom;

    /* ===== 构造 ===== */
    public ArcMenu(Context context) {
        this(context, null);
    }

    public ArcMenu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ArcMenu, defStyleAttr, 0);
        int pos = ta.getInt(R.styleable.ArcMenu_position, 3);
        // 构造函数里加：
        mMarginLeft = ta.getDimensionPixelSize(R.styleable.ArcMenu_mainBtnMarginLeft, 0);
        mMarginTop = ta.getDimensionPixelSize(R.styleable.ArcMenu_mainBtnMarginTop, 0);
        mMarginRight = ta.getDimensionPixelSize(R.styleable.ArcMenu_mainBtnMarginRight, 0);
        mMarginBottom = ta.getDimensionPixelSize(R.styleable.ArcMenu_mainBtnMarginBottom, 0);
        mStartAngle = ta.getInt(R.styleable.ArcMenu_startAngle, 270); // 默认从正下方开始
        mEndAngle = ta.getInt(R.styleable.ArcMenu_endAngle, 90);   // 默认 90° 扇形
        mRadius = (int) ta.getDimension(R.styleable.ArcMenu_radius, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics()));
        switch (pos) {
            case 0:
                mPosition = Position.LEFT_TOP;
                break;
            case 1:
                mPosition = Position.RIGHT_TOP;
                break;
            case 2:
                mPosition = Position.LEFT_BOTTOM;
                break;
            case 3:
                mPosition = Position.RIGHT_BOTTOM;
                break;
        }
        ta.recycle();
    }

    /* ===== 测量 ===== */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /* ===== 布局 ===== */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            setPadding(0, 0, 0, 0); // 避免自身 padding 影响
            layoutCButton();
            layoutItems();   // 预先计算子按钮最终坐标
        }
    }

    private void layoutCButton() {
        mCButton = findViewById(R.id.iv_main_btn);
        if (mCButton == null) {
            throw new IllegalArgumentException("ArcMenu must contain a child with id=\"@id/iv_main_btn\"");
        }
        mCButton.setOnClickListener(v -> toggleMenu(500));
        int width = mCButton.getMeasuredWidth();
        int height = mCButton.getMeasuredHeight();
        int left = 0, top = 0;
        switch (mPosition) {
            case LEFT_TOP:
                left = mMarginLeft;
                top = mMarginTop;
                break;
            case RIGHT_TOP:
                left = getMeasuredWidth() - width - mMarginRight;
                top = mMarginTop;
                break;
            case LEFT_BOTTOM:
                left = mMarginLeft;
                top = getMeasuredHeight() - height - mMarginBottom;
                break;
            case RIGHT_BOTTOM:
                left = getMeasuredWidth() - width - mMarginRight;
                top = getMeasuredHeight() - height - mMarginBottom;
                break;
        }
        mCButton.layout(left, top, left + width, top + height);
    }

    /* ===== 子按钮坐标计算 ===== */
    private void layoutItems() {
        final int count = getChildCount();
        if (count <= 1) return;
        final Point center = getCButtonScreenCenter();
        center.y += mCButton.getMeasuredHeight() / 4;

        /* 2. 用补偿后的圆心画 180° 左侧半圆 */
        RectF area = new RectF(center.x - mRadius, center.y - mRadius, center.x + mRadius, center.y + mRadius);
        Path path = new Path();
        path.addArc(area, mStartAngle, mEndAngle - mStartAngle);   // ✅ 用自定义角度
        PathMeasure measure = new PathMeasure(path, false);
        int divisor = count - 1;

        for (int i = 0; i < count - 1; i++) {
            View child = getChildAt(i + 1);
            child.setVisibility(GONE);
            float[] coords = new float[2];
            measure.getPosTan(i * measure.getLength() / divisor, coords, null);
            int x = (int) coords[0] - child.getMeasuredWidth() / 2;
            int y = (int) coords[1] - child.getMeasuredHeight() / 2;

            Log.d("snn"," 比例： " + i * measure.getLength() / divisor + " x " + x + " y " + y + " getX " +mCButton.getX() + " getY " + mCButton.getY());

            child.layout(x, y, x + child.getMeasuredWidth(), y + child.getMeasuredHeight());
        }
    }

    /**
     * 返回主按钮在屏幕上的绝对中心点
     */
    private Point getCButtonScreenCenter() {
        int[] loc = new int[2];
        mCButton.getLocationOnScreen(loc);          // 屏幕绝对坐标
        int cx = loc[0] + mCButton.getMeasuredWidth() / 2;
        int cy = loc[1] + mCButton.getMeasuredHeight() / 2;
        /* 再映射回 ArcMenu 内部坐标系 */
        int[] selfLoc = new int[2];
        this.getLocationOnScreen(selfLoc);
        return new Point(cx - selfLoc[0], cy - selfLoc[1]);
    }

    /* ===== 展开 / 收起 ===== */
    public void toggleMenu(int duration) {
        final int count = getChildCount();
        if (count <= 1) return;

        final Point center = getCButtonScreenCenter();
        RectF area = new RectF(center.x - mRadius, center.y - mRadius, center.x + mRadius, center.y + mRadius);

        Path path = new Path();
        path.addArc(area, mStartAngle, mEndAngle - mStartAngle);

        PathMeasure measure = new PathMeasure(path, false);
        int divisor = Math.abs(mEndAngle - mStartAngle) >= 360 || count == 2 ? count - 1 : count - 2;

        for (int i = 0; i < count - 1; i++) {
            final View child = getChildAt(i + 1);
            child.setVisibility(VISIBLE);

            float[] coords = new float[2];
            measure.getPosTan(i * measure.getLength() / divisor, coords, null);

            int targetX = (int) coords[0] - child.getMeasuredWidth() / 2;
            int targetY = (int) coords[1] - child.getMeasuredHeight() / 2;

            int dx = targetX - center.x;
            int dy = targetY - center.y;

            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, mCurrentStatus == Status.CLOSE ? 0 : dx, mCurrentStatus == Status.CLOSE ? dx : 0);
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, mCurrentStatus == Status.CLOSE ? 0 : dy, mCurrentStatus == Status.CLOSE ? dy : 0);
            PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, mCurrentStatus == Status.CLOSE ? 0 : 720, mCurrentStatus == Status.CLOSE ? 720 : 0);
            PropertyValuesHolder pvhsX = PropertyValuesHolder.ofFloat(View.SCALE_X, mCurrentStatus == Status.CLOSE ? 0 : 1, mCurrentStatus == Status.CLOSE ? 1 : 0);
            PropertyValuesHolder pvhsY = PropertyValuesHolder.ofFloat(View.SCALE_Y, mCurrentStatus == Status.CLOSE ? 0 : 1, mCurrentStatus == Status.CLOSE ? 1 : 0);
            PropertyValuesHolder pvhA = PropertyValuesHolder.ofFloat(View.ALPHA, mCurrentStatus == Status.CLOSE ? 0 : 1, mCurrentStatus == Status.CLOSE ? 1 : 0);

            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(child, pvhX, pvhY, pvhR, pvhsX, pvhsY, pvhA);
            animator.setDuration(duration);
            animator.setInterpolator(mCurrentStatus == Status.CLOSE ? new OvershootInterpolator(0.9f) : new android.view.animation.AccelerateDecelerateInterpolator());
            animator.setStartDelay((count - i - 1) * 20);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (mCurrentStatus == Status.CLOSE) child.setVisibility(GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            animator.start();

            final int pos = i + 1;
            child.setOnClickListener(v -> {
                if (mListener != null) mListener.onClick(child, pos);
            });
        }

        changeStatus();
    }

    private void changeStatus() {
        mCurrentStatus = (mCurrentStatus == Status.CLOSE ? Status.OPEN : Status.CLOSE);
    }

    public boolean isOpen() {
        return mCurrentStatus == Status.OPEN;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        this.mListener = listener;
    }
}