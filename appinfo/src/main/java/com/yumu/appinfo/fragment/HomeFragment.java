package com.yumu.appinfo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.android.material.appbar.AppBarLayout;
import com.yumu.appinfo.R;
import com.yumu.appinfo.utils.DisplayHelper;
import com.yumu.appinfo.utils.StatusBarHelper;
import com.yumu.appinfo.views.ViewPagerAdapter;


/**
 * Date :  2020-03-28.
 * Time :  15:39.
 * Created by sunan.
 */
public class HomeFragment extends BaseFragment {
    protected SlidingTabLayout slidingTabLayout;
    protected ViewPager viewpager;
    protected ViewPagerAdapter mAdapter;
    protected AppBarLayout appbarlayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = View.inflate(getContext(), R.layout.fragment_home, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        slidingTabLayout = view.findViewById(R.id.slidingTabLayout);
        viewpager = view.findViewById(R.id.viewpager);
        appbarlayout = view.findViewById(R.id.appbarlayout);

        mAdapter = new ViewPagerAdapter(getChildFragmentManager());
        addFragment(mAdapter);

        viewpager.setAdapter(mAdapter);
        viewpager.setOffscreenPageLimit(4);
        slidingTabLayout.setViewPager(viewpager);
        slidingTabLayout.setOnTabSelectListener(onTabSelectListener);
        viewpager.addOnPageChangeListener(onPageChangeListener);
        initTabLayout();
    }


    /**
     * 父类activity 页面设置了 全屏 透明状态栏 ，所以 fragment 内容区域会填充到状态栏下，这里需要把内容区域的 高度往下 一个状态栏高度，
     * 两种方案： 1： slidingTabLayout 的高度 等于 状态栏高度 + slidingTabLayout原有的高度 再设置一下 paddingtop 为 状态栏高度
     * 2：  直接设置个Margintop 值 为状态栏 高度
     */
    public void initTabLayout() {
        // 设置 padding
//        AppBarLayout.LayoutParams layoutParamsPadding = (AppBarLayout.LayoutParams) slidingTabLayout.getLayoutParams();
//        layoutParamsPadding.height = DisplayHelper.dp2px(getActivity(), 45) + StatusBarHelper.getStatusBarHeight(getActivity());
//        layoutParamsPadding.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//        slidingTabLayout.setLayoutParams(layoutParamsPadding);
//        slidingTabLayout.setPadding(0, StatusBarHelper.getStatusBarHeight(getActivity()), 0, 0);

        // 设置  Margins
        AppBarLayout.LayoutParams layoutParamsMargins = (AppBarLayout.LayoutParams) slidingTabLayout.getLayoutParams();
        layoutParamsMargins.height = DisplayHelper.dp2px(getActivity(), 45);
        layoutParamsMargins.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParamsMargins.setMargins(0, StatusBarHelper.getStatusBarHeight(getActivity()), 0, 0);
        slidingTabLayout.setLayoutParams(layoutParamsMargins);
    }

    /**
     * 恢复 appbarlayout
     */
    public void expandAppBar() {
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appbarlayout.getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        AppBarLayout.Behavior appBehavior = (AppBarLayout.Behavior) behavior;
        appBehavior.setTopAndBottomOffset(0);
    }

    /**
     * 恢复显示: 顶部和底部view,上拉过程中 隐藏view，期间左右滑动viewpager 恢复导航栏 和 tablayout
     */
    public void resetView() {
        sendBroadcast(true);
        expandAppBar();
    }

    OnTabSelectListener onTabSelectListener = new OnTabSelectListener() {
        @Override
        public void onTabSelect(int position) {
            resetView();
        }

        @Override
        public void onTabReselect(int position) {

        }
    };

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            resetView();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    protected void addFragment(ViewPagerAdapter mAdapter) {
        mAdapter.addFragment(new HomeTabFragment(), "TAB1");
        mAdapter.addFragment(new HomeTabFragment(), "TAB2");
        mAdapter.addFragment(new HomeTabFragment(), "TAB3");
        mAdapter.addFragment(new HomeTabFragment(), "TAB4");
        mAdapter.addFragment(new HomeTabFragment(), "TAB5");
        mAdapter.addFragment(new HomeTabFragment(), "TAB6");
    }

}
