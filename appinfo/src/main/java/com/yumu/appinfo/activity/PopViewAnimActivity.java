package com.yumu.appinfo.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.yumu.appinfo.R;
import com.yumu.appinfo.views.AnimDialogView;

/**
 * Date :  2020-03-28.
 * Time :  15:39.
 * Created by sunan.
 */
public class PopViewAnimActivity extends BaseActivity {
    private ImageView tv_btn;
    private AnimDialogView pop_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_view_anim);
        pop_view = findViewById(R.id.pop_view);
        tv_btn = findViewById(R.id.tv_btn);
        tv_btn.setOnClickListener(onClickListener);
    }

    private boolean isexplan;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (pop_view.findViewById(R.id.imageView) == null) {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setId(R.id.imageView);
                imageView.setBackgroundColor(R.color.colorAccent);
//                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                FrameLayout.LayoutParams layoutParam = new FrameLayout.LayoutParams(700, 900);
                layoutParam.gravity = Gravity.CENTER;
                imageView.setImageResource(R.mipmap.bg_launcher_bottom_icon);
                pop_view.addView(imageView, layoutParam);
            }

            pop_view.getParent().requestLayout();
            pop_view.requestLayout();

            if (isexplan) {
                pop_view.endAnimation(v);
            } else {
                pop_view.startAnimation(v);
            }
            isexplan = !isexplan;
        }
    };
}
