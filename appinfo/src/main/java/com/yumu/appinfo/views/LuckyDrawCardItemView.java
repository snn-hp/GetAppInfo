package com.yumu.appinfo.views;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.yumu.appinfo.R;


/**
 * Date :  2020-03-28.
 * Time :  15:39.
 * Created by sunan.
 */
public class LuckyDrawCardItemView extends FrameLayout implements IView {

    private View view_border, item_bg;
    private ImageView iv_back, iv_front;
    private boolean isNeedCallBack;

    private CardItemViewCallBack callback;


    public LuckyDrawCardItemView(Context context) {
        this(context, null);
    }

    public LuckyDrawCardItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public interface CardItemViewCallBack {
        void onAnimationEnd();
    }

    public void setCallback(CardItemViewCallBack callback) {
        this.callback = callback;
    }


    public LuckyDrawCardItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_lucky_draw_card_item, this);
        item_bg = findViewById(R.id.item_bg);
        view_border = findViewById(R.id.view_border);
        iv_back = findViewById(R.id.iv_back);
        iv_front = findViewById(R.id.iv_front);
    }

    @Override
    public void setSelect(boolean isFocused) {
        if (view_border != null) {
            view_border.setVisibility(isFocused ? VISIBLE : INVISIBLE);
        }
    }

    @Override
    public void setBack(boolean isFocused) {
        if (view_border != null) {
            view_border.setVisibility(isFocused ? VISIBLE : INVISIBLE);
        }

        int direction = 1;
        if (iv_back.isShown()) {
            direction = -1;
        }

        flip(item_bg, 600, direction).addListener(listener);
        item_bg.postDelayed(new Runnable() {
            @Override
            public void run() {
                switchViewVisibility(iv_back, iv_front);
            }
        }, 600);
    }

    @Override
    public void setAction(boolean isNeedCallBack) {
        this.isNeedCallBack = isNeedCallBack;
    }

    @Override
    public void resetView() {
        iv_back.setVisibility(View.GONE);
        iv_front.setVisibility(View.VISIBLE);
        isNeedCallBack = false;
    }

    @Override
    public ImageView getImageFront() {
        return iv_front;
    }

    private void switchViewVisibility(View back, View front) {
        if (back.isShown()) {
            back.setVisibility(View.GONE);
            front.setVisibility(View.VISIBLE);
        } else {
            back.setVisibility(View.VISIBLE);
            front.setVisibility(View.GONE);
        }
    }

    Animator.AnimatorListener listener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            if (isNeedCallBack && callback != null) {
                Log.d("snn", "onAnimationEnd 卡片动画翻转OK");
                callback.onAnimationEnd();
            }
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    };

    /**
     * 水平翻转
     *
     * @param view      target
     * @param duration  time
     * @param direction 只能传1或-1，1为从左开始翻转，-1位从右开始翻转
     * @return 动画集合
     */
    public static AnimatorSet flip(View view, int duration, int direction) {
        if (direction != 1 && direction != -1) direction = 1;
        view.setCameraDistance(16000 * view.getResources().getDisplayMetrics().density);
        AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator rotationY = new ObjectAnimator();
        rotationY.setDuration(duration).setPropertyName("rotationY");
        rotationY.setFloatValues(0, -90 * direction);
        ObjectAnimator _rotationY = new ObjectAnimator();
        _rotationY.setDuration(duration).setPropertyName("rotationY");
        _rotationY.setFloatValues(90 * direction, 0);
        _rotationY.setStartDelay(duration);
        ObjectAnimator scale = new ObjectAnimator();
        scale.setDuration(duration).setPropertyName("scaleY");
        scale.setFloatValues(1, 0.94f);
        ObjectAnimator _scale = new ObjectAnimator();
        _scale.setDuration(duration).setPropertyName("scaleY");
        _scale.setFloatValues(0.94f, 1);
        _scale.setStartDelay(duration);
        animSet.setTarget(view);
        rotationY.setTarget(view);
        _rotationY.setTarget(view);
        scale.setTarget(view);
        _scale.setTarget(view);
        animSet.playTogether(rotationY, _rotationY, scale, _scale);
        animSet.start();
        return animSet;
    }
}
