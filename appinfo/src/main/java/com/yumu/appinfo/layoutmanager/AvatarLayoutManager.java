package com.yumu.appinfo.layoutmanager;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author sunan
 * @date 2023/2/15 10:24
 */
public class AvatarLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() > 0) {
            detachAndScrapAttachedViews(recycler);
        }
        int itemCount = getItemCount();
        if (itemCount < 6) return;

        int width = getWidth();
        int gap = width / 3;

        int left = 0, top = 0;
        for (int i = 0; i < 6; i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            if (i == 0) {
                layoutDecoratedWithMargins(view, left, top, left + gap * 2, top + gap * 2);
                left += gap * 2;

            } else {
                layoutDecoratedWithMargins(view, left, top, left + gap, top + gap);
                if (i > 0 && i < 3) {
                    top += gap;
                } else {
                    left -= gap;
                }
            }
        }
    }
}
