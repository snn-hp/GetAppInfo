package com.yumu.appinfo.activity;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class BaseFragmentPagerAdapter extends FragmentStateAdapter {
    private List<Fragment> mFragments;

    public BaseFragmentPagerAdapter(FragmentActivity fm, List<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments.size();
    }
}
