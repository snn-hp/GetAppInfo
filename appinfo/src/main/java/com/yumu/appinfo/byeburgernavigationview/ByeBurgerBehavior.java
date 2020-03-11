package com.yumu.appinfo.byeburgernavigationview;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;

/**
 * Base Behavior
 * Created by wing on 11/8/16.
 */

abstract public class ByeBurgerBehavior extends CoordinatorLayout.Behavior<View> {

    protected final int mTouchSlop;
    protected boolean isFirstMove = true;
    protected boolean canInit = true;
    protected AnimateHelper mAnimateHelper;
    private Context context;

    public ByeBurgerBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    // on Scroll Started
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child,
                                       View directTargetChild, View target, int nestedScrollAxes) {

        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        onNestPreScrollInit(child);

        if (child instanceof SlidingTabLayout) { // 拦截掉横向切换tab
            Log.d("snn", "dx: " + Math.abs(dx) + "  dy :" + Math.abs(dy));
            if (Math.abs(dx) > Math.abs(dy)) {
                if (mAnimateHelper.getState() == TranslateAnimateHelper.STATE_HIDE) {
                    sendBroadcast(false);
                    mAnimateHelper.show();
                }
                return;
            }
        }

        if (Math.abs(dy) > 2) {
            if (dy < 0) {
                if (mAnimateHelper.getState() == TranslateAnimateHelper.STATE_HIDE) {
                    mAnimateHelper.show();
                }
            } else if (dy > 0) {
                if (mAnimateHelper.getState() == TranslateAnimateHelper.STATE_SHOW) {
                    mAnimateHelper.hide();
                }
            }
        }
    }

    public void sendBroadcast(boolean isShow) {
        Intent intent = new Intent();
        intent.setAction("action.list.action");
        if (android.os.Build.VERSION.SDK_INT >= 12) {
            intent.setFlags(32);//3.1以后的版本需要设置Intent.FLAG_INCLUDE_STOPPED_PACKAGES
        }
        intent.putExtra("isShow", isShow);
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(context);
        lbm.sendBroadcast(intent);
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }

    protected abstract void onNestPreScrollInit(View child);

    public void show() {
        mAnimateHelper.show();
    }

    public void hide() {
        mAnimateHelper.hide();
    }

    public static ByeBurgerBehavior from(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
        if (!(behavior instanceof ByeBurgerBehavior)) {
            throw new IllegalArgumentException("The view is not associated with ByeBurgerBehavior");
        }
        return (ByeBurgerBehavior) behavior;
    }
}
