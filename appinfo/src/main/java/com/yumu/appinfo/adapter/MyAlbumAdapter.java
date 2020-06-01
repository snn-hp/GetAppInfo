package com.yumu.appinfo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.tools.DateUtils;
import com.yumu.appinfo.R;
import com.yumu.appinfo.imageutil.ImageLoaderManager;
import com.yumu.appinfo.bean.Album;
import com.yumu.appinfo.utils.DisplayHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Date :  2020-02-28.
 * Time :  11:39.
 * Created by sunan.
 */
public class MyAlbumAdapter extends RecyclerView.Adapter<MyAlbumAdapter.ViewHolder> {
    private List<Album> albumList;
    private Context context;
    private MyAlbumAdapterCallback callback;

    public MyAlbumAdapter(Context context, List<Album> albumList) {
        this.context = context;
        this.albumList = albumList;
    }

    public void setAppInfoList(List<Album> albumList) {
        this.albumList = albumList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_my_album_adapter, null);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (position == albumList.size()) {
            holder.iv_image.setImageBitmap(null);
            holder.iv_image.setImageResource(R.mipmap.icon_album_add);
            holder.iv_delete.setVisibility(View.INVISIBLE);
            holder.iv_image.setVisibility(View.VISIBLE);
        } else {
            Album album = albumList.get(position);
            holder.iv_delete.setVisibility(View.VISIBLE);
            ImageView ivImage = holder.iv_image;
            ImageLoaderManager.displayLocalImage(context, album.getPreview_url(), ivImage);

            if (album.isVideo()) {
                holder.iv_video.setVisibility(View.VISIBLE);
                holder.tvDuration.setVisibility(View.VISIBLE);
                holder.tvDuration.setText(DateUtils.formatDurationTime(album.getDuration()));
            } else {
                holder.iv_video.setVisibility(View.GONE);
                holder.tvDuration.setVisibility(View.GONE);
            }
        }


        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.iv_image.getLayoutParams();
        layoutParams.width = (DisplayHelper.getWidthPixels() - DisplayHelper.dpToPx(context, 19)) / 4;
        layoutParams.height = (DisplayHelper.getWidthPixels() - DisplayHelper.dpToPx(context, 19)) / 4;
        holder.iv_image.setLayoutParams(layoutParams);

        holder.iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onItemClick(position);
                }
            }
        });

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.deleteItem(position);
                }
            }
        });
    }


    public interface MyAlbumAdapterCallback {
        void onItemClick(int position);

        void deleteItem(int position);
    }

    public void setCallback(MyAlbumAdapterCallback callback) {
        this.callback = callback;
    }


    @Override
    public int getItemCount() {
        if (albumList.size() >= 9) {
            return albumList.size();
        } else {
            return albumList.size() + 1;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_image, iv_delete, iv_video;
        private TextView tvDuration;

        public ViewHolder(View view) {
            super(view);
            iv_image = view.findViewById(R.id.iv_image);
            iv_delete = view.findViewById(R.id.iv_delete);
            iv_video = view.findViewById(R.id.iv_video);
            tvDuration = view.findViewById(R.id.tv_duration);
        }
    }
}
