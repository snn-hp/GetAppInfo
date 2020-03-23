package com.yumu.appinfo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.yumu.appinfo.R;
import com.yumu.appinfo.utils.StatusBarHelper;


public class MainActivity extends AppCompatActivity {
    private TextView tvGoBehavior, tvGetInfo, tvGoViewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvGetInfo = findViewById(R.id.tv_get_info);
        tvGoBehavior = findViewById(R.id.tv_go_behavior);
        tvGoViewPager2 = findViewById(R.id.tv_go_viewpager2);

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
