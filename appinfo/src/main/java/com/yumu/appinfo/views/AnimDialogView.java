package com.yumu.appinfo.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

/**
 * @author sunan
 * @date 2023/3/18 14:24
 */
public class AnimDialogView extends FrameLayout {
    private int startX;
    private int startY;

    public AnimDialogView(Context context) {
        super(context);
    }

    public AnimDialogView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimDialogView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void startAnimation(View startView) {
        int width = getWidth();
        int hight = getHeight();
        int[] location = new int[2];
        startView.getLocationOnScreen(location);
        startX = location[0];
        startY = location[1];
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                int newX = (int) (startX - startX * value);
                int newY = (int) (startY - startY * value);

                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.width = (int) (width * value);
                layoutParams.height = (int) (hight * value);
                setLayoutParams(layoutParams);
                Log.d("snn", " startX  : " + startX + "  startY : " + startY);
                Log.d("snn", " newX  : " + newX + "  newY : " + newY + " value  " + value);

                setX(newX);
                setY(newY);
                invalidate();
            }
        });
        animator.start();
    }


    public void endAnimation(View startView) {
        int width = getWidth();
        int hight = getHeight();
        int[] location = new int[2];
        startView.getLocationOnScreen(location);
        startX = location[0];
        startY = location[1];
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 0f);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                int newX = (int) (startX - startX * value);
                int newY = (int) (startY - startY * value);
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.width = (int) (width * value);
                layoutParams.height = (int) (hight * value);
                setLayoutParams(layoutParams);
                Log.d("snn", " newX  : " + newX + "  newY : " + newY);
                setX(newX);
                setY(newY);
                invalidate();
            }
        });
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.width = width;
                layoutParams.height = hight;
                setLayoutParams(layoutParams);
                setVisibility(GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }


}

