package com.yumu.appinfo.floatbtn;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.yumu.appinfo.R;
import com.yumu.appinfo.bean.Album;
import com.yumu.appinfo.floatbtn.ArcMenu;
import com.yumu.appinfo.transform.CardOverlayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义画廊效果
 */
public class FloatingBtnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_action_btn);
        ArcMenu arcMenu = findViewById(R.id.arcMenu);
        ListView listView = findViewById(R.id.listview);

        // 填充假数据
        List<String> data = new ArrayList<>();
        for (char c = 'A'; c <= 'z'; c++) data.add(String.valueOf(c));
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data));

        // 滑动时若菜单打开则关闭
        listView.setOnScrollListener(new android.widget.AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(android.widget.AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(android.widget.AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (arcMenu.isOpen()) arcMenu.toggleMenu(200);
            }
        });

        // 子菜单点击
        arcMenu.setOnMenuItemClickListener((view, position) -> {
            String tag = (String) view.getTag();
            Toast.makeText(this, position + " : " + tag, Toast.LENGTH_SHORT).show();
        });


    }
}
