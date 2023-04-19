package com.yumu.appinfo.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.yumu.appinfo.views.SyFloatView;

/**
 * @author sunan
 * @date 2023/3/17 10:36
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        SyFloatView.getInstance(this).bind(this);
    }
}
