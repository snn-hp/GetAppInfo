package com.yumu.appinfo.transform;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ansen.shape.AnsenImageView;
import com.bumptech.glide.Glide;
import com.yumu.appinfo.R;
import com.yumu.appinfo.bean.Album;

public class CardOverlayAdapter extends BaseOverlayPageAdapter<Album> {
    private LayoutInflater mInflater;
    private Context context;

    public CardOverlayAdapter(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    protected View itemView(int position) {
        return mInflater.inflate(R.layout.item_viewpager_image, null);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        final Album data = getList().get(position);
        View rootView = itemView(position);
        if (null == rootView) {
            throw new RuntimeException("you should set a item layout");
        }
        AnsenImageView imageView = rootView.findViewById(R.id.iv_avatar);
        if (null == imageView) {
            throw new RuntimeException("you should set a item layout");
        }
        Glide.with(context).load(data.getPreview_url()).into(imageView);
        container.addView(rootView);
        return rootView;
    }
}
