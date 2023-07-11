package com.yumu.appinfo.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.yumu.appinfo.R;
import com.yumu.appinfo.bean.Album;
import com.yumu.appinfo.transform.CardOverlayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义画廊效果
 */
public class GalleryActivity extends AppCompatActivity {
    private ViewPager vpMain, vpMain1, vpMain2, vpMain3;
    private List<Album> albumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        vpMain = findViewById(R.id.vp_main);
        vpMain1 = findViewById(R.id.vp_main_1);
        vpMain2 = findViewById(R.id.vp_main_2);
        vpMain3 = findViewById(R.id.vp_main_3);

        initPager(vpMain,"OverlayTransformerLan2");
        initPager(vpMain1,"");
        initPager(vpMain2,"");
        initPager(vpMain3,"");
    }


    private void initPager(ViewPager viewPager,String transformer) {
        intiData();
        viewPager.setOffscreenPageLimit(albumList.size());
        CardOverlayAdapter pagerAdapter = new CardOverlayAdapter(GalleryActivity.this);
        pagerAdapter.setImgUrlsAndBindViewPager(viewPager, albumList, 3,transformer);
        viewPager.setAdapter(pagerAdapter);
    }

    private void intiData() {
        if (albumList == null) {
            albumList = new ArrayList<>();
        }
        if (albumList.isEmpty()) {
            albumList.add(new Album("https://img1.baidu.com/it/u=2278717026,2923133725&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=665"));
            albumList.add(new Album("https://img1.baidu.com/it/u=2555904807,2390319494&fm=253&fmt=auto&app=138&f=JPEG?w=333&h=500"));
            albumList.add(new Album("https://img1.baidu.com/it/u=640593135,209279600&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500"));
            albumList.add(new Album("https://img0.baidu.com/it/u=233196052,109563712&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800"));
            albumList.add(new Album("https://img1.baidu.com/it/u=2805604174,586521884&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500"));
            albumList.add(new Album("https://img1.baidu.com/it/u=1649094455,2789980245&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800"));
            albumList.add(new Album("https://img0.baidu.com/it/u=732498250,1887863602&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500"));
            albumList.add(new Album("https://img0.baidu.com/it/u=1405889028,2371985328&fm=253&fmt=auto&app=138&f=JPEG?w=750&h=500"));
            albumList.add(new Album("https://img2.baidu.com/it/u=1229150163,1065025162&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1163"));
        }
    }

}
