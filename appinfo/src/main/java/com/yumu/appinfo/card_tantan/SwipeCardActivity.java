package com.yumu.appinfo.card_tantan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.yumu.appinfo.R;
import com.yumu.appinfo.layoutmanager.CardConfig;
import com.yumu.appinfo.layoutmanager.OverLayCardLayoutManager;
import com.yumu.appinfo.layoutmanager.RenRenCallback;
import com.yumu.appinfo.layoutmanager.TanTanCallback;
import com.yumu.appinfo.tantan_card.CardItemTouchHelperCallback;
import com.yumu.appinfo.tantan_card.CardLayoutManager;
import com.yumu.appinfo.tantan_card.OnSwipeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Date :  2023-02-15.
 * Time :  11:39.
 * Created by sunan.
 * 画廊效果
 */
public class SwipeCardActivity extends AppCompatActivity {

    private List<Integer> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        initData();
        initView();
    }

    private void initView() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new OverLayCardLayoutManager());
        MyAdapter myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        CardConfig.initConfig(this);
        final RenRenCallback callback = new RenRenCallback(recyclerView, myAdapter, list);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initData() {
        list.add(R.mipmap.img_avatar_01);
        list.add(R.mipmap.img_avatar_02);
        list.add(R.mipmap.img_avatar_03);
        list.add(R.mipmap.img_avatar_04);
        list.add(R.mipmap.img_avatar_05);
        list.add(R.mipmap.img_avatar_06);
        list.add(R.mipmap.img_avatar_07);
    }

    private class MyAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ImageView avatarImageView = ((MyViewHolder) holder).avatarImageView;
            avatarImageView.setImageResource(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView avatarImageView;
            ImageView likeImageView;
            ImageView dislikeImageView;

            MyViewHolder(View itemView) {
                super(itemView);
                avatarImageView = (ImageView) itemView.findViewById(R.id.iv_avatar);
                likeImageView = (ImageView) itemView.findViewById(R.id.iv_like);
                dislikeImageView = (ImageView) itemView.findViewById(R.id.iv_dislike);
            }

        }
    }

}
