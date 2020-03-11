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

public class DiscoveryTabFragment extends BaseFragment {
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
                if (dy < 0 && !isShow) {
                    // TODO: 2020-03-10 发送显示广播 保证同一个状态 广播只发送一次 避免浪费性能
                    Log.d("snn", "发送    显示      广播 111： ");
                    sendBroadcast(true);
                    isShow = true;
                }
                if (dy > 0 && isShow) {
                    // TODO: 2020-03-10 发送隐藏广播
                    Log.d("snn", "发送        隐藏      广播222： ");
                    sendBroadcast(false);
                    isShow = false;
                }
            }
        });
    }


    public RecyclerView getRecyclerView() {
        return recyclerView;
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