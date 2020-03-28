package com.yumu.appinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yumu.appinfo.R;

/**
 * Date :  2020-03-28.
 * Time :  15:39.
 * Created by sunan.
 */
public class CirculationViewAdapter extends RecyclerView.Adapter<CirculationViewAdapter.ViewHolder> {
    private Context context;
    private CardViewAdapterCallback callback;
    private int type;

    public CirculationViewAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    public interface CardViewAdapterCallback {
        void onItemClick(int position);
    }

    public void setCallback(CardViewAdapterCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mailbox_item_view, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final CirculationViewAdapter.ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        if (type == 1 || type == 2) {
            return 15;
        }
        return Integer.MAX_VALUE;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_front;

        public ViewHolder(View view) {
            super(view);
            iv_front = view.findViewById(R.id.iv_front);
        }
    }
}
