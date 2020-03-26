package com.yumu.appinfo.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yumu.appinfo.R;
import com.yumu.appinfo.bean.APPInfo;
import com.yumu.appinfo.bean.CardItem;
import com.yumu.appinfo.utils.DisplayHelper;
import com.yumu.appinfo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Date :  2020-02-28.
 * Time :  11:39.
 * Created by sunan.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {
    private Context context;
    private int direction = 1;//翻转方向
    private int status = -1;// 当前状态
    private List<CardItem> cardItemList = new ArrayList<>();

    public CardViewAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CardItem> cardItemList) {
        this.cardItemList = cardItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_view, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final CardViewAdapter.ViewHolder holder, int position) {
        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        layoutParams.width = (DisplayHelper.getWidthPixels() - DisplayHelper.dp2px(context, 20)) / 3;
        layoutParams.height = layoutParams.width;
        holder.itemView.setLayoutParams(layoutParams);

        CardItem cardItem = cardItemList.get(position);

        holder.tv_num.setText(cardItem.getContent());

        if (status == 1) {
            status = -1;

            if (cardItem.isBack()) {
                direction = -1;
            } else {
                direction = 1;
            }

            flip(holder.itemView, 500, direction).addListener(listener);

            holder.itemView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!cardItem.isBack()) {
                        holder.iv_back.setVisibility(View.GONE);
                        holder.iv_font.setVisibility(View.VISIBLE);
                    } else {
                        holder.iv_back.setVisibility(View.VISIBLE);
                        holder.iv_font.setVisibility(View.GONE);
                    }
                }
            }, 500);
        }

        if (cardItem.isSelect()) {
            holder.view_border.setVisibility(View.VISIBLE);
        } else {
            holder.view_border.setVisibility(View.INVISIBLE);
        }
    }


    /**
     * 开始翻转 全部
     */
    public void startReversal() {
        this.status = 1;
        notifyDataSetChanged();
    }

    /**
     * 开始翻转 某一个
     */
    public void startReversal(int position) {
        this.status = 1;
        notifyItemChanged(position);
    }

    private void switchViewVisibility(View back, View front) {
        if (back.isShown()) {
            back.setVisibility(View.GONE);
            front.setVisibility(View.VISIBLE);
        } else {
            back.setVisibility(View.VISIBLE);
            front.setVisibility(View.GONE);
        }
    }

    /**
     * 水平翻转
     *
     * @param view      target
     * @param duration  time
     * @param direction 只能传1或-1，1为从左开始翻转，-1位从右开始翻转
     * @return 动画集合
     */
    public static AnimatorSet flip(View view, int duration, int direction) {
        if (direction != 1 && direction != -1) direction = 1;
        view.setCameraDistance(16000 * view.getResources().getDisplayMetrics().density);
        AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator rotationY = new ObjectAnimator();
        rotationY.setDuration(duration).setPropertyName("rotationY");
        rotationY.setFloatValues(0, -90 * direction);
        ObjectAnimator _rotationY = new ObjectAnimator();
        _rotationY.setDuration(duration).setPropertyName("rotationY");
        _rotationY.setFloatValues(90 * direction, 0);
        _rotationY.setStartDelay(duration);
        ObjectAnimator scale = new ObjectAnimator();
        scale.setDuration(duration).setPropertyName("scaleY");
        scale.setFloatValues(1, 0.94f);
        ObjectAnimator _scale = new ObjectAnimator();
        _scale.setDuration(duration).setPropertyName("scaleY");
        _scale.setFloatValues(0.94f, 1);
        _scale.setStartDelay(duration);
        animSet.setTarget(view);
        rotationY.setTarget(view);
        _rotationY.setTarget(view);
        scale.setTarget(view);
        _scale.setTarget(view);
        animSet.playTogether(rotationY, _rotationY, scale, _scale);
        animSet.start();
        return animSet;
    }

    Animator.AnimatorListener listener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            Toast.makeText(context, "翻转结束", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    };

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_back, iv_font;
        private View view_border;
        private TextView tv_num;

        public ViewHolder(View view) {
            super(view);

            iv_back = view.findViewById(R.id.iv_back);
            iv_font = view.findViewById(R.id.iv_font);
            tv_num = view.findViewById(R.id.tv_num);
            view_border = view.findViewById(R.id.view_border);
        }
    }
}
