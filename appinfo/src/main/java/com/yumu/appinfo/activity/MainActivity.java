package com.yumu.appinfo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yumu.appinfo.R;
import com.yumu.appinfo.adapter.MenuAdapter;
import com.yumu.appinfo.bean.MainMenu;
import com.yumu.appinfo.card_tantan.CardActivity;
import com.yumu.appinfo.card_tantan.TanTanAvatarActivity;
import com.yumu.appinfo.card_tantan.TanTanCardActivity;
import com.yumu.appinfo.dialog.RedPacketDialog;
import com.yumu.appinfo.utils.StatusBarHelper;
import com.yumu.appinfo.views.NumberAnimTextView;
import com.yumu.appinfo.views.AnimDialogView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Date :  2020-03-28.
 * Time :  15:39.
 * Created by sunan.
 */
public class MainActivity extends BaseActivity {
    private NumberAnimTextView tv_number_text;
    private RecyclerView recyclerview;
    private ImageView tv_btn;
    private AnimDialogView pop_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_number_text = findViewById(R.id.tv_number_text);
        recyclerview = findViewById(R.id.recyclerview);
        pop_view = findViewById(R.id.pop_view);
        tv_btn = findViewById(R.id.tv_btn);
        tv_btn.setOnClickListener(onClickListener);
        initStatusBar();
        initMenu();
    }

    private void initMenu() {
        List<MainMenu> menuList = new ArrayList<>();
        menuList.add(new MainMenu("应用签名", R.mipmap.icon_main_menu, "get_app_info", "获取已安装应用签名信息"));
        menuList.add(new MainMenu("沉浸式状态栏", R.mipmap.icon_main_menu, "statusbar_helper", "沉浸式联动状态栏"));
        menuList.add(new MainMenu("循环滚动", R.mipmap.icon_main_menu, "recyclerview", "RecyclerView循环滚动"));
        menuList.add(new MainMenu("ViewPage2", R.mipmap.icon_main_menu, "viewpage2", "RecyclerView循环滚动"));
        menuList.add(new MainMenu("卡片抽奖", R.mipmap.icon_main_menu, "card_luck", "卡片抽奖"));
        menuList.add(new MainMenu("定位", R.mipmap.icon_main_menu, "location", "获取手机位置信息"));
        menuList.add(new MainMenu("探探卡片_1", R.mipmap.icon_main_menu, "tantan_card", "仿照探探实现切卡片，老版效果"));
        menuList.add(new MainMenu("探探卡片_2", R.mipmap.icon_main_menu, "tantan_card_new", "仿照探探实现切卡片，相比上一个，更丝滑点，更接近最新版本的探探"));
        menuList.add(new MainMenu("探探头像效果", R.mipmap.icon_main_menu, "tantan_avatar", "仿照探探 头像 实现"));
        menuList.add(new MainMenu("画廊效果", R.mipmap.icon_main_menu, "GalleryActivity", "图片画廊效果"));
        menuList.add(new MainMenu("红包弹窗", R.mipmap.icon_main_menu, "redpacket", "红包弹窗"));
        menuList.add(new MainMenu("相册拍照", R.mipmap.icon_main_menu, "takephoto", "相册拍照"));
        menuList.add(new MainMenu("相册拍照", R.mipmap.icon_main_menu, "camera_kit", "相册拍照"));
        menuList.add(new MainMenu("动画展示", R.mipmap.icon_main_menu, "pop_anim", "动画展示"));
        menuList.add(new MainMenu("viewPager2 画廊 展示", R.mipmap.icon_main_menu, "gallery", "viewPager2 画廊 展示"));

        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        MenuAdapter menuAdapter = new MenuAdapter(getApplicationContext(), menuList);
        recyclerview.setAdapter(menuAdapter);
        menuAdapter.setCallback(new MenuAdapter.MenuClickCallback() {
            @Override
            public void onItemClick(MainMenu mainMenu) {
                if (TextUtils.equals(mainMenu.getType(), "get_app_info")) {
                    gotoActivity(GetAppInfoActivity.class);
                } else if (TextUtils.equals(mainMenu.getType(), "statusbar_helper")) {
                    gotoActivity(TestBehaviorActivity.class, 0);
                } else if (TextUtils.equals(mainMenu.getType(), "recyclerview")) {
                    gotoActivity(TestBehaviorActivity.class, 2);
                } else if (TextUtils.equals(mainMenu.getType(), "viewpage2")) {
                    gotoActivity(TestBehaviorActivity.class, 1);
                } else if (TextUtils.equals(mainMenu.getType(), "card_luck")) {
                    gotoActivity(TestBehaviorActivity.class, 3);
                } else if (TextUtils.equals(mainMenu.getType(), "location")) {
                    gotoActivity(LocationTaskActivity.class);
                } else if (TextUtils.equals(mainMenu.getType(), "tantan_card")) {
                    gotoActivity(CardActivity.class);
                } else if (TextUtils.equals(mainMenu.getType(), "tantan_avatar")) {
                    gotoActivity(TanTanAvatarActivity.class);
                } else if (TextUtils.equals(mainMenu.getType(), "redpacket")) {
                    RedPacketDialog dialog = new RedPacketDialog(MainActivity.this);
                    dialog.show();
                } else if (TextUtils.equals(mainMenu.getType(), "takephoto")) {
                    gotoActivity(MyAlbumActivity.class);
                } else if (TextUtils.equals(mainMenu.getType(), "GalleryActivity")) {
                    gotoActivity(com.yumu.appinfo.card_tantan.GalleryActivity.class);
                } else if (TextUtils.equals(mainMenu.getType(), "tantan_card_new")) {
                    gotoActivity(TanTanCardActivity.class);
                } else if (TextUtils.equals(mainMenu.getType(), "camera_kit")) {
                    gotoActivity(CameraKitActivity.class);
                } else if (TextUtils.equals(mainMenu.getType(), "pop_anim")) {
                    gotoActivity(PopViewAnimActivity.class);
                } else if (TextUtils.equals(mainMenu.getType(), "gallery")) {
                    gotoActivity(GalleryActivity.class);
                }
            }
        });
    }


    private boolean isexplan;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (pop_view.findViewById(R.id.imageView) == null) {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setId(R.id.imageView);
                FrameLayout.LayoutParams layoutParam = new FrameLayout.LayoutParams(500, 500);
                layoutParam.gravity = Gravity.CENTER;
                imageView.setImageResource(R.mipmap.bg_launcher_bottom_icon);
                pop_view.addView(imageView, layoutParam);
            }

            if (isexplan) {
                pop_view.endAnimation(v);
            } else {
                pop_view.startAnimation(v);
            }
            isexplan = !isexplan;
        }
    };

    public void initStatusBar() {
        StatusBarHelper.setStatusBarDarkMode(this); //白字
        StatusBarHelper.setStatusBarColor(this, R.color.colorAccent, false);
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
