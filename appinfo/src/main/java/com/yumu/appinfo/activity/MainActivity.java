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
    private TextView tvGoBehavior, tvGetInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvGetInfo = findViewById(R.id.tv_get_info);
        tvGoBehavior = findViewById(R.id.tv_go_behavior);

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
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.tv_get_info) {
                gotoActivity(GetAppInfoActivity.class);
            } else if (view.getId() == R.id.tv_go_behavior) {
                gotoActivity(TestBehaviorActivity.class);
            }
        }
    };


    public void gotoActivity(Class<? extends Activity> next) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), next);
        startActivity(intent);
    }
}
