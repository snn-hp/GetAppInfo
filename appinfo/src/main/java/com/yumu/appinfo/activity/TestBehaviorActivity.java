package com.yumu.appinfo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.yumu.appinfo.R;
import com.yumu.appinfo.fragment.HomeFragment;
import com.yumu.appinfo.fragment.DiscoveryFragment;
import com.yumu.appinfo.fragment.MailboxFragment;
import com.yumu.appinfo.fragment.NewHomeFragment;
import com.yumu.appinfo.fragment.PersonFragment;
import com.yumu.appinfo.utils.StatusBarHelper;

/**
 * Date :  2020-03-03.
 * Time :  14:26.
 * Created by sunan.
 */
public class TestBehaviorActivity extends BaseActivity {
    private TextView tvHome, tvMailbox, tvPerson, tvDiscovery;
    private DiscoveryFragment discoveryFragment;
//    private HomeFragment homeFragment;
    private NewHomeFragment homeFragment;
    private MailboxFragment mailboxFragment;
    private PersonFragment personFragment;
    private View llbottom;
    private int currentId = R.id.tv_home;//当前选中id
    public final static String BROADCAST_ACTION_LIST_MOVE_ACTION = "action.list.action";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_behavior);
        initStatusBar();

        tvHome = findViewById(R.id.tv_home);
        llbottom = findViewById(R.id.ll_bottom);

        tvHome.setSelected(true);
        homeFragment = new NewHomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container, homeFragment).commit();
        tvDiscovery = findViewById(R.id.tv_discovery);
        tvMailbox = findViewById(R.id.tv_mailbox);
        tvPerson = findViewById(R.id.tv_person);

        registerReceiver();
        addViewAction();

        Intent intent = getIntent();
        int currentTab = intent.getIntExtra("currentTab", -1);

        if (currentTab == 0) {
            changeFragment(tvHome);
        } else if (currentTab == 1) {
            changeFragment(tvDiscovery);
        }else if (currentTab == 2) {
            changeFragment(tvMailbox);
        }else if (currentTab == 3) {
            changeFragment(tvPerson);
        }
    }


    /**
     * 为了美观，滑动的时候，内容需要填充到状态栏下，根据需要 可以自行设置 状态栏背景颜色 和 字体图标 以适配自己的app
     */
    public void initStatusBar() {
        StatusBarHelper.setStatusBarLightMode(this); // 状态栏 黑字
        StatusBarHelper.setStatusBarColor(this, android.R.color.transparent, true);
    }


    protected void addViewAction() {
        tvHome.setOnClickListener(onTabClickListener);
        tvMailbox.setOnClickListener(onTabClickListener);
        tvPerson.setOnClickListener(onTabClickListener);
        tvDiscovery.setOnClickListener(onTabClickListener);
    }

    private View.OnClickListener onTabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changeFragment(v);
        }
    };

    private void changeFragment(View v) {
        if (v.getId() != currentId) {//如果当前选中跟上次选中的一样,不需要处理
            changeSelect(v.getId());//改变图标跟文字颜色的选中
            changeFragment(v.getId());//fragment的切换
            currentId = v.getId();//设置选中id
        }
    }

    /**
     * 改变fragment的显示
     *
     * @param resId
     */
    private void changeFragment(int resId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();//开启一个Fragment事务
        hideFragments(transaction);//隐藏所有fragment
        if (resId == R.id.tv_home) {//首页
            if (homeFragment == null) {//如果为空先添加进来.不为空直接显示
                homeFragment = new NewHomeFragment();
                transaction.add(R.id.main_container, homeFragment);
            } else {
                transaction.show(homeFragment);
            }
        } else if (resId == R.id.tv_discovery) {
            if (discoveryFragment == null) {
                discoveryFragment = new DiscoveryFragment();
                transaction.add(R.id.main_container, discoveryFragment);
            } else {
                transaction.show(discoveryFragment);
            }
        } else if (resId == R.id.tv_mailbox) {//信箱
            if (mailboxFragment == null) {
                mailboxFragment = new MailboxFragment();
                transaction.add(R.id.main_container, mailboxFragment);
            } else {
                transaction.show(mailboxFragment);
            }
        } else if (resId == R.id.tv_person) {
            if (personFragment == null) {
                personFragment = new PersonFragment();
                transaction.add(R.id.main_container, personFragment);
            } else {
                transaction.show(personFragment);
            }
        }
        transaction.commitAllowingStateLoss();//一定要记得提交事务
    }


    /**
     * 改变TextView选中颜色
     *
     * @param resId
     */
    private void changeSelect(int resId) {
        tvHome.setSelected(false);
        tvMailbox.setSelected(false);
        tvPerson.setSelected(false);
        tvDiscovery.setSelected(false);

        if (resId == R.id.tv_home) {
            tvHome.setSelected(true);
        } else if (resId == R.id.tv_mailbox) {
            tvMailbox.setSelected(true);
        } else if (resId == R.id.tv_person) {
            tvPerson.setSelected(true);
        } else if (resId == R.id.tv_discovery) {
            tvDiscovery.setSelected(true);
        }
    }

    /**
     * 显示之前隐藏所有fragment
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null)//不为空才隐藏,如果不判断第一次会有空指针异常
            transaction.hide(homeFragment);
        if (mailboxFragment != null) {
            transaction.hide(mailboxFragment);
        }
        if (personFragment != null) {
            transaction.hide(personFragment);
        }
        if (discoveryFragment != null) {
            transaction.hide(discoveryFragment);
        }
    }

    public void registerReceiver() {
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getApplicationContext());
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_ACTION_LIST_MOVE_ACTION);
        lbm.registerReceiver(broadcastReceiver, filter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isShow = intent.getBooleanExtra("isShow", false);
            // TODO: 2020-03-10 根据值去判断 显示隐藏
            if (isShow) {
                showViewAnimation(llbottom);
            } else {
                closeViewAnimation(llbottom);
            }
        }
    };


    public void showViewAnimation(View view) {
        if (view == null || view.getVisibility() == View.VISIBLE) {
            return;
        }
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_dialog_enter);//加载动画资源
        view.startAnimation(animation);
        view.setVisibility(View.VISIBLE);
    }

    public void closeViewAnimation(View view) {
        if (view == null || view.getVisibility() == View.GONE) {
            return;
        }
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_dialog_exit);
        view.startAnimation(animation);
        view.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(broadcastReceiver);
        }
    }
}
