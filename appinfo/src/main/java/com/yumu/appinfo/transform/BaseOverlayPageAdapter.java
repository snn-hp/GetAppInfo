package com.yumu.appinfo.transform;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.Collections;
import java.util.List;


public abstract class BaseOverlayPageAdapter<T> extends PagerAdapter {
    private Context context;

    public Context getContext() {
        return context;
    }

    public List<T> getList() {
        return list;
    }

    private List<T> list = Collections.emptyList();

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public BaseOverlayPageAdapter(Context context) {
        this.context = context;
    }

    /**
     * item布局
     *
     * @return
     */
    protected abstract View itemView(int position);

    public void setImgUrlsAndBindViewPager(ViewPager vp, List<T> imgUrls, int layerAmount) {
        setImgUrlsAndBindViewPager(vp, imgUrls, layerAmount, -1, -1, "");
    }

    public void setImgUrlsAndBindViewPager(ViewPager vp, List<T> imgUrls, int layerAmount, String transformer) {
        setImgUrlsAndBindViewPager(vp, imgUrls, layerAmount, -1, -1, transformer);
    }


    /**
     * @param vp
     * @param imgUrls
     * @param layerAmount  显示层数
     * @param transformers 样式
     */
    public void setImgUrlsAndBindViewPager(ViewPager vp, List<T> imgUrls, int layerAmount, float scaleOffset, float transOffset, String transformers) {
        this.list = imgUrls;
        if (imgUrls != null && imgUrls.size() > 0) {
            vp.setOffscreenPageLimit(layerAmount);
            if (TextUtils.equals(transformers, "OverlayTransformerLan2")) {
                OverlayTransformerLan2 transformer = new OverlayTransformerLan2(vp, transOffset);
                vp.setPageTransformer(true, transformer);
            } else {
                OverlayTransformer transformer = new OverlayTransformer(layerAmount, scaleOffset, transOffset);
                vp.setPageTransformer(true, transformer);
            }
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        final T itemObj = list.get(position);
        View rootView = itemView(position);
        if (null == rootView) {
            throw new RuntimeException("you should set a item layout");
        }
        return rootView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.mOnItemClickListener = clickListener;
    }

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }
}
