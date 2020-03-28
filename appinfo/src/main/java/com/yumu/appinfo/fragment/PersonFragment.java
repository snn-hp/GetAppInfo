package com.yumu.appinfo.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yumu.appinfo.R;
import com.yumu.appinfo.views.IView;
import com.yumu.appinfo.views.LuckyDrawView;

import java.util.Random;

/**
 * Date :  2020-03-28.
 * Time :  15:39.
 * Created by sunan.
 */
public class PersonFragment extends Fragment implements View.OnClickListener {
    private LuckyDrawView luckView;
    private int luckindex = -1;
    private CountDownTimer mTimer;
    private Button btn_start, btn_reset;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = View.inflate(getContext(), R.layout.activity_person_new, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        luckView = view.findViewById(R.id.lucky_panel);
        btn_start = view.findViewById(R.id.btn_start);
        btn_reset = view.findViewById(R.id.btn_reset);
        addViewAction();
        initView();
    }

    private void addViewAction() {
        luckView.setCallback(drawViewCallBack);
        btn_start.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
    }

    /**
     * 在这里获取 每个卡片view 的 正面 ImageView 然后通过数据 设置正面图片，也可以用同样的方式 获取反面背景图
     */
    public void initView() {
        IView[] itemViewArr = luckView.getItemViewArr();
        for (int i = 0; i < itemViewArr.length; i++) {
            ImageView ivFront = itemViewArr[i].getImageFront();
            ivFront.setImageResource(R.mipmap.icon_def_avatar);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_reset) {
            luckView.resetData();
        } else if (view.getId() == R.id.btn_start) {
            startGame();
        }
    }

    public void startGame() {
        luckView.resetData();//防止连续点击 数据错乱 每次开始时候 重置数据
        luckView.startGame();
        btn_start.setClickable(false);
        btn_reset.setClickable(false);
//      int second = new Random().nextInt(8) % (8 - 4 + 1) + 4;//随机 跑圈秒数 4 -8秒 至少要超过 翻转动画 和跑圈 延时时间
        mTimer = new CountDownTimer(6 * 1000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                luckindex = new Random().nextInt(6);
                Log.e("snn", "====luckindex===" + luckindex);
                luckView.tryToStop(luckindex);
                mTimer = null;
            }
        };
        mTimer.start();
    }

    LuckyDrawView.LuckyDrawViewCallBack drawViewCallBack = new LuckyDrawView.LuckyDrawViewCallBack() {
        @Override
        public void onAnimationEnd() {
            Log.d("snn", "drawViewCallBack 选中卡片 翻转OK");
            btn_start.setClickable(true);
            btn_reset.setClickable(true);
            if (luckindex != -1) {
                // TODO: 2020/3/28 在这里可以根据数据源 去做选中的操作  luckindex
                luckindex = -1;
            }
        }
    };
}
