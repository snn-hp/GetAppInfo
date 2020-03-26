package com.yumu.appinfo.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yumu.appinfo.R;
import com.yumu.appinfo.adapter.CardViewAdapter;
import com.yumu.appinfo.bean.CardItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Date :  2020-02-28.
 * Time :  11:39.
 * Created by sunan.
 */
public class LuckyDrawView extends FrameLayout {
    private RecyclerView recyclerView;
    private int currentIndex = 0;
    private int currentTotal = 0;
    private int stayIndex = 0;
    private boolean isGameRunning = false;
    private boolean isTryToStop = false;

    private static final int DEFAULT_SPEED = 200;
    private static final int MIN_SPEED = 200;
    private int currentSpeed = DEFAULT_SPEED;
    private Context context;
    private CardViewAdapter cardViewAdapter;

    private List<CardItem> cardItemList = new ArrayList<>();


    public LuckyDrawView(@NonNull Context context) {
        this(context, null);
    }

    public LuckyDrawView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuckyDrawView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        inflate(context, R.layout.view_lucky_draw, this);
        setupView();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        stopMarquee();
        super.onDetachedFromWindow();
    }

    private void setupView() {
        recyclerView = findViewById(R.id.recyclerView);
        for (int i = 0; i < 6; i++) {
            CardItem cardItem = new CardItem();
            cardItem.setContent("第" + i + "个");
            cardItemList.add(cardItem);
        }
        initAdapter();
    }

    private void stopMarquee() {
        isGameRunning = false;
        isTryToStop = false;
        currentIndex = 0;
        currentTotal = 0;

    }

    public void initAdapter() {
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setAdapter(cardViewAdapter = new CardViewAdapter(context));
        cardViewAdapter.setData(cardItemList);
    }

    public void startReversal() {
        for (CardItem cardItem : cardItemList) {
            if (cardItem.isBack()) {
                cardItem.setBack(false);
            } else {
                cardItem.setBack(true);
            }
            cardItem.setSelect(false);
        }
        cardViewAdapter.setData(cardItemList);
        cardViewAdapter.startReversal();
    }

    public void resetAdapter() {
        for (CardItem cardItem : cardItemList) {
            cardItem.setSelect(false);
            cardItem.setBack(false);
        }
        cardViewAdapter.setData(cardItemList);
        cardViewAdapter.notifyDataSetChanged();
    }


    private long getInterruptTime() {
        currentTotal++;
        if (isTryToStop) {
            currentSpeed += 10;
            if (currentSpeed > DEFAULT_SPEED) {
                currentSpeed = DEFAULT_SPEED;
            }
        } else {
            if (currentTotal / cardItemList.size() > 0) {
                currentSpeed -= 10;
            }
            if (currentSpeed < MIN_SPEED) {
                currentSpeed = MIN_SPEED;
            }
        }
        return currentSpeed;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public void startGame() {
        stopMarquee();// 每次开始 要重置索引

        isGameRunning = true;
        isTryToStop = false;
        currentSpeed = DEFAULT_SPEED;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isGameRunning) {
                    try {
                        Thread.sleep(getInterruptTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    post(new Runnable() {
                        @Override
                        public void run() {
                            int preIndex = currentIndex;
                            currentIndex++;
                            if (currentIndex >= cardItemList.size()) {
                                currentIndex = 0;
                            }
                            cardItemList.get(preIndex).setSelect(false);
                            cardItemList.get(currentIndex).setSelect(true);

                            cardViewAdapter.setData(cardItemList);
                            cardViewAdapter.notifyDataSetChanged();

                            Log.d("snn", " preIndex : " + preIndex + "     currentIndex : " + currentIndex);
                            if (isTryToStop && currentSpeed == DEFAULT_SPEED && stayIndex == currentIndex) {
                                isGameRunning = false;
                                cardItemList.get(currentIndex).setBack(true);
                                cardViewAdapter.setData(cardItemList);
                                cardViewAdapter.startReversal();
                                Log.d("snn", " 结束  currentIndex : " + currentIndex);
                            }
                        }
                    });
                }
            }
        }).start();
    }

    public void tryToStop(int position) {
        stayIndex = position;
        isTryToStop = true;
    }
}
