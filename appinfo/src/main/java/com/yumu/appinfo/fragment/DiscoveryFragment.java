package com.yumu.appinfo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import com.flyco.tablayout.SlidingTabLayout;
import com.yumu.appinfo.R;
import com.yumu.appinfo.utils.DisplayHelper;
import com.yumu.appinfo.utils.StatusBarHelper;
import com.yumu.appinfo.views.ViewPagerAdapter;

/**
 * Date :  2020-03-03.
 * Time :  14:53.
 * Created by sunan.
 */

public class DiscoveryFragment extends BaseFragment {
    protected SlidingTabLayout slidingTabLayout;
    protected ViewPager viewpager;
    protected ViewPagerAdapter mAdapter;

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

        mAdapter = new ViewPagerAdapter(getChildFragmentManager());
        addFragment(mAdapter);

        viewpager.setAdapter(mAdapter);
        viewpager.setOffscreenPageLimit(4);
        slidingTabLayout.setViewPager(viewpager);
        initTabLayout();
    }


    /**
     * 父类activity 页面设置了 全屏 透明状态栏 ，所以 fragment 内容区域会填充到状态栏下，这里需要把内容区域的 高度往下 一个状态栏高度，
     * 两种方案： 1： slidingTabLayout 的高度 等于 状态栏高度 + slidingTabLayout原有的高度 再设置一下 paddingtop 为 状态栏高度
     * 2：  直接设置个Margintop 值 为状态栏 高度
     */
    public void initTabLayout() {
        // 设置 padding
//        LinearLayout.LayoutParams layoutParamsPadding = (LinearLayout.LayoutParams) slidingTabLayout.getLayoutParams();
//        layoutParamsPadding.height = DisplayHelper.dp2px(getActivity(), 45) + StatusBarHelper.getStatusBarHeight(getActivity());
//        layoutParamsPadding.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//        slidingTabLayout.setLayoutParams(layoutParamsPadding);
//        slidingTabLayout.setPadding(0, StatusBarHelper.getStatusBarHeight(getActivity()), 0, 0);

        // 设置  Margins
        LinearLayout.LayoutParams layoutParamsMargins = (LinearLayout.LayoutParams) slidingTabLayout.getLayoutParams();
        layoutParamsMargins.height = DisplayHelper.dp2px(getActivity(), 45);
        layoutParamsMargins.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParamsMargins.setMargins(0, StatusBarHelper.getStatusBarHeight(getActivity()), 0, 0);
        slidingTabLayout.setLayoutParams(layoutParamsMargins);
    }


    protected void addFragment(ViewPagerAdapter mAdapter) {
        mAdapter.addFragment(new DiscoveryTabFragment(), "TAB1");
        mAdapter.addFragment(new DiscoveryTabFragment(), "TAB2");
        mAdapter.addFragment(new DiscoveryTabFragment(), "TAB3");
        mAdapter.addFragment(new DiscoveryTabFragment(), "TAB4");
        mAdapter.addFragment(new DiscoveryTabFragment(), "TAB5");
        mAdapter.addFragment(new DiscoveryTabFragment(), "TAB6");
    }
}
