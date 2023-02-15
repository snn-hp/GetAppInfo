package com.yumu.appinfo.card_tantan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.mcxtzhang.commonadapter.rv.ViewHolder;
import com.yumu.appinfo.R;
import com.yumu.appinfo.layoutmanager.BaseLoopGallery;
import com.yumu.appinfo.layoutmanager.CardConfig;
import com.yumu.appinfo.layoutmanager.GalleryLayoutManager;
import com.yumu.appinfo.layoutmanager.OverLayCardLayoutManager;
import com.yumu.appinfo.layoutmanager.RenRenCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Date :  2023-02-15.
 * Time :  11:39.
 * Created by sunan.
 * 画廊效果
 */
public class GalleryActivity extends AppCompatActivity {

    private List<Integer> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        initData();
        initView();
    }

    private void initView() {
        BaseLoopGallery<Integer> alyLoopGallery = (BaseLoopGallery) findViewById(R.id.alyLoopGallery);
        alyLoopGallery.setDatasAndLayoutId(initData(), R.layout.uc_item_main_image_header, new BaseLoopGallery.BindDataListener<Integer>() {
            @Override
            public void onBindData(ViewHolder holder, Integer res) {
                ImageView imageView = holder.getView(R.id.image);
                imageView.setImageResource(res);
            }
        });
    }

    private List<Integer> initData() {
        list.add(R.mipmap.img_avatar_01);
        list.add(R.mipmap.img_avatar_02);
        list.add(R.mipmap.img_avatar_03);
        list.add(R.mipmap.img_avatar_04);
        list.add(R.mipmap.img_avatar_05);
        list.add(R.mipmap.img_avatar_06);
        list.add(R.mipmap.img_avatar_07);
        return list;
    }
}
