package com.yumu.appinfo.dialog;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yumu.appinfo.R;


/**
 * @author sunan
 * @time 2020/07/23
 */
public class RedPacketDialog extends Dialog {

    private View rl_no_open, rl_open;
    private TextView tv_red_packet_outside_title, tv_red_packet_title, tv_red_packet_value, tv_red_packet_unit, tv_red_packet_tips;
    private ImageView ivOpen;
    private boolean isOpen;

    public RedPacketDialog(Context activity) {
        super(activity, R.style.bottom_dialog);
        setContentView(R.layout.dialog_share_qr_code_layout);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        tv_red_packet_outside_title = findViewById(R.id.tv_red_packet_outside_title);
        tv_red_packet_title = findViewById(R.id.tv_red_packet_title);
        tv_red_packet_value = findViewById(R.id.tv_red_packet_value);
        tv_red_packet_unit = findViewById(R.id.tv_red_packet_unit);
        tv_red_packet_tips = findViewById(R.id.tv_red_packet_tips);


        rl_no_open = findViewById(R.id.rl_no_open);
        rl_open = findViewById(R.id.rl_open);

        ivOpen = findViewById(R.id.iv_open);
        ivOpen.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.iv_open) {
                propertyValuesHolderDown(ivOpen);
            }
        }
    };


    public void propertyValuesHolderDown(final View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f, 0.9f, 0.9f, 0.91f, 0.92f, 0.93f, 0.94f, 0.95f, 0.96f, 0.97f, 0.98f, 0.99f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.9f, 0.9f, 0.91f, 0.92f, 0.93f, 0.94f, 0.95f, 0.96f, 0.97f, 0.98f, 0.99f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.9f, 0.9f, 0.91f, 0.92f, 0.93f, 0.94f, 0.95f, 0.96f, 0.97f, 0.98f, 0.99f, 1f);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ);
        objectAnimator.setDuration(200);
        objectAnimator.addListener(animatorListener);
        objectAnimator.start();
        isOpen = false;

    }

    public void setAnimation() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
//                ObjectAnimator.ofFloat(ivOpen, "rotationX", 0, 360),//绕X轴翻转
                ObjectAnimator.ofFloat(ivOpen, "rotationY", 0, 360)//绕Y轴旋转
//                ObjectAnimator.ofFloat(ivOpen,"rotation",0,-90),//绕中心点逆时针旋转
//                ObjectAnimator.ofFloat(ivOpen,"translationX",0,90),//X轴平移
//                ObjectAnimator.ofFloat(ivOpen,"translationY",0,90),//y轴平移
//                ObjectAnimator.ofFloat(ivOpen,"scaleX",1,1.5f),//X轴拉伸
//                ObjectAnimator.ofFloat(ivOpen,"scaleY",0,0.5f),//Y轴从零拉伸
//                ObjectAnimator.ofFloat(ivOpen,"alpha",1,0.25f,1)//透明度
        );
        set.setDuration(1000).start();//时间
        isOpen = true;
        set.addListener(animatorListener);
    }

    Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (isOpen) {
                rl_no_open.setVisibility(View.GONE);
                rl_open.setVisibility(View.VISIBLE);
            } else {
                setAnimation();
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };
}