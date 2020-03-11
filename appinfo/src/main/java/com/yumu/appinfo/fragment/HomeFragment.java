package com.yumu.appinfo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yumu.appinfo.R;
import com.yumu.appinfo.views.ViewPagerAdapter;

/**
 * Date :  2020-03-03.
 * Time :  14:53.
 * Created by sunan.
 */


public class HomeFragment extends BaseFragment {
    protected SlidingTabLayout slidingTabLayout;
    protected ViewPager viewpager;
    protected ViewPagerAdapter mAdapter;

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

        mAdapter = new ViewPagerAdapter(getChildFragmentManager());
        addFragment(mAdapter);

        viewpager.setAdapter(mAdapter);
        viewpager.setOffscreenPageLimit(4);
        slidingTabLayout.setViewPager(viewpager);
        slidingTabLayout.setOnTabSelectListener(onTabSelectListener);
    }

    OnTabSelectListener onTabSelectListener = new OnTabSelectListener() {
        @Override
        public void onTabSelect(int position) {
            sendBroadcast(false);
        }

        @Override
        public void onTabReselect(int position) {

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
