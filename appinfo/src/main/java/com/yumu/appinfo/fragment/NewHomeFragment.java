package com.yumu.appinfo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.yumu.appinfo.R;
import com.yumu.appinfo.utils.DisplayHelper;
import com.yumu.appinfo.utils.StatusBarHelper;
import com.yumu.appinfo.views.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Date :  2020-03-28.
 * Time :  15:39.
 * Created by sunan.
 */
public class NewHomeFragment extends BaseFragment {
    private List<Class> fragments;
    private TabLayout tabs;
    private FrameLayout container;
    private FragmentManager fragmentManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = View.inflate(getContext(), R.layout.fragment_home_new, null);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentManager = getChildFragmentManager();
        fragmentManager.setFragmentFactory(new FragmentFactory());
        tabs = view.findViewById(R.id.tabs);
        container = view.findViewById(R.id.container);
        addFragment();
        addTabs();
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("snn"," ab.getPosition() " + tab.getPosition());
                onTabItemSelect(tab.getPosition(), tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                onItemUnselect(tab.getPosition(), tab.getText().toString());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void addFragment() {
        if (fragments == null) {
            fragments = new ArrayList<>();
        }
        fragments.clear();
        fragments.add(HomeTabFragment.class);
        fragments.add(HomeTabFragment.class);
        fragments.add(HomeTabFragment.class);
        fragments.add(HomeTabFragment.class);
//        fragments.add(DiscoveryFragment.class);
//        fragments.add(MailboxFragment.class);
//        fragments.add(PersonFragment.class);
    }

    private void addTabs() {
        if (tabs == null) {
            return;
        }
        tabs.addTab(tabs.newTab().setText("首页"));
        tabs.addTab(tabs.newTab().setText("发现"));
        tabs.addTab(tabs.newTab().setText("消息"));
        tabs.addTab(tabs.newTab().setText("我的"));
    }

    private void onTabItemSelect(int pos, String text) {
        Class fragmentCls = fragments.get(pos);
        Fragment fragment = fragmentManager.findFragmentByTag(text);
        FragmentTransaction transaction = fragmentManager.beginTransaction();//开启一个Fragment事务
        if (fragment == null) {
            try {
                transaction.add(container.getId(), (Fragment) fragmentCls.newInstance(), text);
            } catch (Exception e) {

             }
        } else {
            transaction.setMaxLifecycle(fragment, Lifecycle.State.RESUMED);
            transaction.show(fragment);
        }
        try {
            transaction.commitNow();
        } catch (Exception e) {
            transaction.commitNowAllowingStateLoss();
        }
    }

    private void onItemUnselect(int pos, String text) {
        Fragment fragment = fragmentManager.findFragmentByTag(text);
        FragmentTransaction transaction = fragmentManager.beginTransaction();//开启一个Fragment事务

        if (fragment != null) {
            transaction.setMaxLifecycle(fragment, Lifecycle.State.STARTED);
            transaction.hide(fragment);
            try {
                transaction.commit();
            } catch (Exception e) {
                transaction.commitAllowingStateLoss();
            }
        }
    }

}
