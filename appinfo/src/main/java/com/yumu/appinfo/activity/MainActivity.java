package com.yumu.appinfo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yumu.appinfo.R;
import com.yumu.appinfo.utils.StatusBarHelper;
import com.yumu.appinfo.views.NumberAnimTextView;

import java.util.Random;

/**
 * Date :  2020-03-28.
 * Time :  15:39.
 * Created by sunan.
 */
public class MainActivity extends AppCompatActivity {
    private TextView tvGoBehavior, tvGetInfo, tvGoViewPager2;
    private NumberAnimTextView tv_number_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvGetInfo = findViewById(R.id.tv_get_info);
        tvGoBehavior = findViewById(R.id.tv_go_behavior);
        tvGoViewPager2 = findViewById(R.id.tv_go_viewpager2);
        tv_number_text = findViewById(R.id.tv_number_text);
        addViewAction();
        initStatusBar();
    }

    public void initStatusBar() {
        StatusBarHelper.setStatusBarDarkMode(this); //白字
        StatusBarHelper.setStatusBarColor(this, R.color.colorAccent, false);
    }

    public void addViewAction() {
        tvGetInfo.setOnClickListener(onClickListener);
        tvGoBehavior.setOnClickListener(onClickListener);
        tvGoViewPager2.setOnClickListener(onClickListener);
        findViewById(R.id.tv_go_cardview).setOnClickListener(onClickListener);
        findViewById(R.id.tv_recyclerview).setOnClickListener(onClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showTime();
    }

    public void showTime() {
        CountDownTimer mTimer = new CountDownTimer(200 * 1000, 5000) {
            @Override
            public void onTick(long l) {
                int second = new Random().nextInt(800) % (800 - 500 + 1) + 500;//随机 跑圈秒数 4 -8秒 至少要超过 翻转动画 和跑圈 延时时间
                tv_number_text.setNumberString(tv_number_text.getmNumEnd(), second + "");
            }

            @Override
            public void onFinish() {

            }
        };
        mTimer.start();
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.tv_get_info) {
                gotoActivity(GetAppInfoActivity.class);
            } else if (view.getId() == R.id.tv_go_behavior) {
                gotoActivity(TestBehaviorActivity.class, 0);
            } else if (view.getId() == R.id.tv_go_viewpager2) {
                gotoActivity(TestBehaviorActivity.class, 1);
            } else if (view.getId() == R.id.tv_recyclerview) {
                gotoActivity(TestBehaviorActivity.class, 2);
            } else if (view.getId() == R.id.tv_go_cardview) {
                gotoActivity(TestBehaviorActivity.class, 3);
            }
        }
    };

    public void gotoActivity(Class<? extends Activity> next) {
        gotoActivity(next, -1);
    }

    public void gotoActivity(Class<? extends Activity> next, int currentTab) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), next);
        if (currentTab != -1) {
            intent.putExtra("currentTab", currentTab);
        }
        startActivity(intent);
    }
}
