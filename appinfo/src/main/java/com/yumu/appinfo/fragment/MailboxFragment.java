package com.yumu.appinfo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yumu.appinfo.R;
import com.yumu.appinfo.utils.DisplayHelper;
import com.yumu.appinfo.utils.StatusBarHelper;


public class MailboxFragment extends Fragment {

    private EditText etSendMsg;
    private TextView tv_anim_view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = View.inflate(getContext(), R.layout.fragment_holder, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_anim_view = view.findViewById(R.id.tv_anim_view);
        etSendMsg = view.findViewById(R.id.et_sendmsg);
        addViewAction();
    }

    private void addViewAction() {
        etSendMsg.setOnEditorActionListener(onEditorActionListener);
    }

    TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_GO) {
                //todo 触发动画
                startViewAnim(tv_anim_view);
            }
            return false;
        }
    };


    public void startViewAnim(View view) {
        //动画集合
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(2000);

        //平移动画
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -StatusBarHelper.getScreenHeight(getActivity()) / 2);
        translateAnimation.setDuration(2000);
//        view.startAnimation(translateAnimation);
        animationSet.addAnimation(translateAnimation);

        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_anim);
        animationSet.addAnimation(animation);
        //缩放动画
//        ScaleAnimation scalanimation = new ScaleAnimation(1, 1, 0, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        animation.setDuration(2000);
////        view.startAnimation(animationSet);
//        animationSet.addAnimation(scalanimation);

        view.startAnimation(animationSet);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
