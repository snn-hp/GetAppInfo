package com.yumu.appinfo.floatbtn;

/**
 * Time:2025/7/15
 * Authors:su_nan
 * description :卫星菜单
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

import androidx.annotation.Nullable;

import com.yumu.appinfo.R;

public class ArcMenu extends ViewGroup {

    public enum Position {
        LEFT_TOP, RIGHT_TOP, LEFT_BOTTOM, RIGHT_BOTTOM
    }

    public enum Status {
        OPEN, CLOSE
    }

    public interface OnMenuItemClickListener {
        void onClick(View view, int position);
    }

    private Position mPosition = Position.RIGHT_BOTTOM;
    private Status mCurrentStatus = Status.CLOSE;
    private int mRadius = 50;
    private View mCButton;                    // 主按钮
    private OnMenuItemClickListener mListener;

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
        mRadius = (int) ta.getDimension(R.styleable.ArcMenu_radius, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics()));
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            layoutCButton();
            layoutItems();
        }
    }

    private void layoutCButton() {
        mCButton = getChildAt(0);
        mCButton.setOnClickListener(v -> {
            mCButton.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate_main));
            toggleMenu(300);
        });

        int l = 0, t = 0;
        int width = mCButton.getMeasuredWidth();
        int height = mCButton.getMeasuredHeight();
        switch (mPosition) {
            case LEFT_TOP:
                l = 0;
                t = 0;
                break;
            case RIGHT_TOP:
                l = getMeasuredWidth() - width;
                t = 0;
                break;
            case LEFT_BOTTOM:
                l = 0;
                t = getMeasuredHeight() - height;
                break;
            case RIGHT_BOTTOM:
                l = getMeasuredWidth() - width;
                t = getMeasuredHeight() - height;
                break;
        }
        mCButton.layout(l, t, l + width, t + height);
    }

    private void layoutItems() {
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {
            View child = getChildAt(i + 1);
            child.setVisibility(GONE);

            int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
            int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

            int cWidth = child.getMeasuredWidth();
            int cHeight = child.getMeasuredHeight();

            if (mPosition == Position.LEFT_BOTTOM || mPosition == Position.RIGHT_BOTTOM) {
                ct = getMeasuredHeight() - cHeight - ct;
            }
            if (mPosition == Position.RIGHT_TOP || mPosition == Position.RIGHT_BOTTOM) {
                cl = getMeasuredWidth() - cWidth - cl;
            }
            child.layout(cl, ct, cl + cWidth, ct + cHeight);
        }
    }

    public void toggleMenu(int duration) {
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {
            final View child = getChildAt(i + 1);
            child.setVisibility(View.VISIBLE);

            int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
            int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

            int xFlag = 1, yFlag = 1;
            if (mPosition == Position.LEFT_TOP || mPosition == Position.LEFT_BOTTOM) xFlag = -1;
            if (mPosition == Position.LEFT_TOP || mPosition == Position.RIGHT_TOP) yFlag = -1;

            TranslateAnimation tranAnim;
            if (mCurrentStatus == Status.CLOSE) {
                tranAnim = new TranslateAnimation(xFlag * cl, 0, yFlag * ct, 0);
                child.setClickable(true);
                child.setFocusable(true);
            } else {
                tranAnim = new TranslateAnimation(0, xFlag * cl, 0, yFlag * ct);
                child.setClickable(false);
                child.setFocusable(false);
            }
            tranAnim.setFillAfter(true);
            tranAnim.setDuration(duration);
            tranAnim.setStartOffset((i * 100) / count);
            tranAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mCurrentStatus == Status.CLOSE) child.setVisibility(GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

            RotateAnimation rotateAnim = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnim.setDuration(duration);
            rotateAnim.setFillAfter(true);

            child.startAnimation(tranAnim);
            child.startAnimation(rotateAnim);

            final int pos = i + 1;
            child.setOnClickListener(v -> {
                if (mListener != null) mListener.onClick(child, pos);
//                itemAnim(pos - 1);
//                toggleMenu(300);
            });
        }
        changeStatus();
    }

    private void itemAnim(int pos) {
        for (int i = 0; i < getChildCount() - 1; i++) {
            View child = getChildAt(i + 1);
            if (i == pos) {
                child.startAnimation(scaleBigAnim(300));
            } else {
                child.startAnimation(scaleSmallAnim(300));
            }
            child.setClickable(false);
            child.setFocusable(false);
        }
    }

    private Animation scaleBigAnim(int duration) {
        RotateAnimation anim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(duration);
        anim.setFillAfter(true);
        return anim;
    }

    private Animation scaleSmallAnim(int duration) {
        RotateAnimation anim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(duration);
        anim.setFillAfter(true);
        return anim;
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

