package com.yumu.appinfo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yumu.appinfo.R;
import com.yumu.appinfo.views.ViewPagerAdapter;


/**
 * Date :  2020-03-28.
 * Time :  15:39.
 * Created by sunan.
 */
public class BaseFragment extends Fragment {

    public void sendBroadcast(boolean isShow) {
        Intent intent = new Intent();
        intent.setAction("action.list.action");
        if (android.os.Build.VERSION.SDK_INT >= 12) {
            intent.setFlags(32);//3.1以后的版本需要设置Intent.FLAG_INCLUDE_STOPPED_PACKAGES
        }
        intent.putExtra("isShow", isShow);
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getActivity());
        lbm.sendBroadcast(intent);
    }

}
