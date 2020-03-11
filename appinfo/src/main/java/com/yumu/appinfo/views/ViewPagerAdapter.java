package com.yumu.appinfo.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yumu.appinfo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ViewPager适配器
 * @author ansen
 * @create time 2016/05/18
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<Fragment>();
    private final List<String> fragmentTitleList = new ArrayList<String>();

    private Map<String, String> tags = new HashMap<>();
    
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(fragmentList==null || fragmentList.size()==0){
            return null;
        }
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        MLog.i(Const.ANSEN,"标题内容:"+fragmentTitleList.get(position));
        return fragmentTitleList.get(position);
    }

    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

    public View getTabView(int position, Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_detail_tab, null);
        TextView tv= view.findViewById(R.id.tv_title);
        tv.setText(fragmentTitleList.get(position));
        return view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);

        String tag = fragment.getClass().getSimpleName();
        long itemId = this.getItemId(position);
        String t_name = makeFragmentName(container.getId(), itemId);

        tags.put(tag,t_name);
        return fragment;
    }

    public Fragment getFragment(int position) {
        return fragmentList.get(position);
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    public String getTagByName(String frgName){
        return tags.get(frgName);
    }

    public List<String> getFragmentTitleList() {
        return fragmentTitleList;
    }


}
