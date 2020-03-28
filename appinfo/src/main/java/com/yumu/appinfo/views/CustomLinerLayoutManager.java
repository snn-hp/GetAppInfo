package com.yumu.appinfo.views;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Date :  2019-12-19.
 * Time :  17:14.
 * Created by sunan.
 */
public class CustomLinerLayoutManager extends LinearLayoutManager {
    private float speedPerPixel = 0.001f;//滚动速率 越小越快 越大越慢
    private Context context;

    public CustomLinerLayoutManager(Context context) {
        super(context);
        this.context = context;
    }

    public CustomLinerLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CustomLinerLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setSpeedPerPixel(float speedPerPixel) {
        this.speedPerPixel = speedPerPixel;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        RecyclerView.SmoothScroller smoothScroller = new CenterSmoothScroller(recyclerView.getContext());
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    private class CenterSmoothScroller extends LinearSmoothScroller {

        CenterSmoothScroller(Context context) {
            super(context);
        }

        @Nullable
        @Override //滑动到目标索引
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return CustomLinerLayoutManager.this.computeScrollVectorForPosition(targetPosition);
        }

        @Override // 滑动位置
        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
            return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2);
        }

        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {// 控制滑动速度的
            return speedPerPixel;
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_START;
        }
    }

    public void setSpeedSlow() {
        speedPerPixel = context.getResources().getDisplayMetrics().density * 3f;
    }

    public void setSpeedFast() {
        speedPerPixel = context.getResources().getDisplayMetrics().density * 0.03f;
    }

    public void visible() {
        if (findLastVisibleItemPosition() > 40) {
            speedPerPixel = speedPerPixel / 10;
        }
    }
}





