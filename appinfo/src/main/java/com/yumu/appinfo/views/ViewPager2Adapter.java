package com.yumu.appinfo.views;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Date :  2020-03-03.
 * Time :  14:53.
 * Created by sunan.
 */
public class ViewPager2Adapter extends FragmentStateAdapter {
    private List<Fragment> fragmentList = new ArrayList<Fragment>();

    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }

    public Fragment getFragment(int position) {
        return fragmentList.get(position);
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
