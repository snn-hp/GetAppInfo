package com.yumu.appinfo.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.aohanyao.transformer.library.conf.CardPageTransformer;
import com.aohanyao.transformer.library.conf.OnPageTransformerListener;
import com.aohanyao.transformer.library.conf.PageTransformerConfig;
import com.yumu.appinfo.R;
import com.yumu.appinfo.fragment.EmptyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义画廊效果
 */
public class GalleryActivity extends AppCompatActivity {
    private ViewPager2 vpMain;
    private BaseFragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFragments = getFragments();
        vpMain.setOffscreenPageLimit(mFragments.size());
        initPager(PageTransformerConfig.BOTTOM);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_top:
                initPager(PageTransformerConfig.TOP);
                break;
            case R.id.action_top_left:
                initPager(PageTransformerConfig.TOP_LEFT);
                break;
            case R.id.action_top_right:
                initPager(PageTransformerConfig.TOP_RIGHT);
                break;
            case R.id.action_bottom:
                initPager(PageTransformerConfig.BOTTOM);
                break;
            case R.id.action_bottom_left:
                initPager(PageTransformerConfig.BOTTOM_LEFT);
                break;
            case R.id.action_bottom_right:
                initPager(PageTransformerConfig.BOTTOM_RIGHT);
                break;
            case R.id.action_right:
                initPager(PageTransformerConfig.RIGHT);
                break;
            case R.id.action_Left:
                initPager(PageTransformerConfig.LEFT);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initPager(@PageTransformerConfig.ViewType int mViewType) {
        vpMain.setPageTransformer(CardPageTransformer.getBuild()//建造者模式
                // .addAnimationType(PageTransformerConfig.ROTATION)//默认动画  旋转  当然 也可以一次性添加两个  后续会增加更多动画
//                .setRotation(-45)//旋转角度
                .setViewType(mViewType)
                .setOnPageTransformerListener(new OnPageTransformerListener() {
                    @Override
                    public void onPageTransformerListener(View page, float position) {
                        //你也可以在这里对 page 实行自定义动画
                    }
                })
                .setTranslationOffset(40)
                .setScaleOffset(80)
                .create());
        //创建适配器
        mAdapter = new BaseFragmentPagerAdapter(this, mFragments);
        vpMain.setAdapter(mAdapter);
    }

    /**
     * 模拟创建数据
     *
     * @return
     */
    @NonNull
    private List<Fragment> getFragments() {
        vpMain = (ViewPager2) findViewById(R.id.vp_main);
        List<Fragment> mFragments = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mFragments.add(EmptyFragment.getInstance(i + ""));
        }
        return mFragments;
    }
}
