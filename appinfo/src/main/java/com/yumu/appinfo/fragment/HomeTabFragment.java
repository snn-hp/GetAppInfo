package com.yumu.appinfo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yumu.appinfo.R;


/**
 * Created by wing on 11/4/16.
 */

public class HomeTabFragment extends BaseFragment {
    public static String NAME = "NAME";
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = View.inflate(getContext(), R.layout.fragment_mail, null);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new Adapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }

    boolean first = true;
    boolean isShow = true;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (Math.abs(dy) > 2 && Math.abs(dy) > Math.abs(dx)) {// 竖直滑动 并且有滑动值
                    if (dy < 0 && recyclerView.canScrollVertically(1) && linearLayoutManager.findFirstVisibleItemPosition() < 3) {
                        Log.d("snn", "下滑 返回顶部 设置padding  dy ： " + " " + dy);
                        recyclerView.setPadding(0, 45 * 3, 0, 0);
                    }

                    if (dy < 0 && isShow) {
                        // TODO: 2020-03-10 发送显示广播 保证同一个状态 广播只发送一次 避免浪费性能
                        Log.d("snn", "发送    显示      广播 111： ");
                        sendBroadcast(true);
                        isShow = true;
                    }
                    if (dy > 0 && !isShow) {
                        // TODO: 2020-03-10 发送隐藏广播
                        Log.d("snn", "发送        隐藏      广播222： ");
                        sendBroadcast(false);
                        isShow = false;
                    }

                    if (dy > 0 && recyclerView.getPaddingTop() != 0) {
                        Log.d("snn", "上滑的时候 去底部  恢复padding dy ： " + dy);
                        recyclerView.setPadding(0, 0, 0, 0);
                    }

//                第一次进去 列表 先设置一下padding值
                    if (recyclerView.canScrollVertically(1) && linearLayoutManager.findFirstVisibleItemPosition() == 0 && first) {
                        Log.d("snn", "第一次进去初始化 padding： ");
                        recyclerView.setPadding(0, 45 * 3, 0, 0);
//                    recyclerView.requestLayout();
//                    recyclerView.invalidate();
                        first = false;
                    }
                }
            }
        });
    }

    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_main, null));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText("第" + position + "个");
        }

        @Override
        public int getItemCount() {
            return 100;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.text);
            }
        }
    }

}
