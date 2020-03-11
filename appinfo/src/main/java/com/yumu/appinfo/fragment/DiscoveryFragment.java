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
import com.yumu.appinfo.views.ViewPagerAdapter;


/**
 * Created by wing on 11/4/16.
 */

public class DiscoveryFragment extends BaseFragment {
    protected SlidingTabLayout slidingTabLayout;
    protected ViewPager viewpager;
    protected ViewPagerAdapter mAdapter;
    protected AppBarLayout appbarlayout;
    private DiscoveryTabFragment discoveryTabFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = View.inflate(getContext(), R.layout.fragment_discovery, null);
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


    }

    public void expandAppBar() {
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appbarlayout.getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        AppBarLayout.Behavior appBehavior = (AppBarLayout.Behavior) behavior;
        appBehavior.setTopAndBottomOffset(0);
    }

    OnTabSelectListener onTabSelectListener = new OnTabSelectListener() {
        @Override
        public void onTabSelect(int position) {
            sendBroadcast(true);
            expandAppBar();
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
            sendBroadcast(true);
            expandAppBar();

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    protected void addFragment(ViewPagerAdapter mAdapter) {
        mAdapter.addFragment(discoveryTabFragment = new DiscoveryTabFragment(), "TAB1");
        mAdapter.addFragment(discoveryTabFragment = new DiscoveryTabFragment(), "TAB2");
        mAdapter.addFragment(discoveryTabFragment = new DiscoveryTabFragment(), "TAB3");
        mAdapter.addFragment(discoveryTabFragment = new DiscoveryTabFragment(), "TAB4");
        mAdapter.addFragment(discoveryTabFragment = new DiscoveryTabFragment(), "TAB5");
        mAdapter.addFragment(discoveryTabFragment = new DiscoveryTabFragment(), "TAB6");
    }

}
