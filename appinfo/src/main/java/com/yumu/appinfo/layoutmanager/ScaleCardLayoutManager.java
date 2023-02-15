package com.yumu.appinfo.layoutmanager;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author sunan
 * @date 2023/2/15 14:02
 */
public class ScaleCardLayoutManager extends RecyclerView.LayoutManager {

    private int mChildWidth, mChildHeight;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        //没有Item，界面空着吧
        if (getItemCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            return;
        }

        //state.isPreLayout()是支持动画的
        if (getChildCount() == 0 && state.isPreLayout()) {
            return;
        }

        //onLayoutChildren方法在RecyclerView 初始化时 会执行两遍
        detachAndScrapAttachedViews(recycler);

        if (mChildHeight == 0 || mChildWidth == 0) {
            View firstView = recycler.getViewForPosition(0);
            addView(firstView);
            measureChildWithMargins(firstView, 0, 0);
            mChildWidth = getDecoratedMeasuredWidth(firstView);
            mChildHeight = getDecoratedMeasuredHeight(firstView);
            removeAndRecycleView(firstView, recycler);
        }


        int itemCount = getItemCount();
        View child;
        int leftOffset = getPaddingLeft();
        int bottomOffset = getHeight() - getPaddingBottom();
        for (int i = 0; i < itemCount; i++) {
            child = recycler.getViewForPosition(i);
            addView(child);
            measureChildWithMargins(child, 0, 0);
            layoutDecoratedWithMargins(child, leftOffset, bottomOffset - mChildHeight, leftOffset + mChildWidth, bottomOffset);


            bottomOffset -= mChildHeight / 2;

        }

        //为了能给每个childView做动画
        for (int i = 0; i < getChildCount(); i++) {
            child = getChildAt(i);
            changeViewUIProperty(0, child);
        }

    }

    private void changeViewUIProperty(int dy, View child) {
        float distance = child.getY() - getPaddingBottom();
        int horizontalSpace = getHeight() - getPaddingTop() - getPaddingBottom();
        //0.8f-1.0f
        child.setScaleX(0.8f + distance / horizontalSpace * 0.2f);
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int offset = dy;
        offsetChildrenVertical(-offset);
        //为了能给每个childView做动画
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            changeViewUIProperty(0, child);
        }
        return offset;
    }

    //由于上述方法没有考虑margin的存在，所以我参考LinearLayoutManager的源码：

    /**
     * 获取某个childView在水平方向所占的空间
     *
     * @param view
     * @return
     */
    public int getDecoratedMeasurementHorizontal(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        return getDecoratedMeasuredWidth(view) + params.leftMargin + params.rightMargin;
    }

    /**
     * 获取下一个View的left
     *
     * @param view
     * @return
     */
    public int getNextViewLeft(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        return getDecoratedRight(view) + params.rightMargin;
    }

    /**
     * 获取上一个View的Right
     *
     * @param view
     * @return
     */
    public int getLastViewRight(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        return getDecoratedLeft(view) - params.leftMargin;
    }

    /**
     * 获取某个childView在竖直方向所占的空间
     *
     * @param view
     * @return
     */
    public int getDecoratedMeasurementVertical(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        return getDecoratedMeasuredHeight(view) + params.topMargin + params.bottomMargin;
    }


    public int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    public int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

}
