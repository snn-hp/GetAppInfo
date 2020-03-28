package com.yumu.appinfo.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yumu.appinfo.R;

/**
 * Date :  2020-03-28.
 * Time :  15:39.
 * Created by sunan.
 */

public class LuckyDrawView extends FrameLayout {

    private LuckyDrawCardItemView itemView1, itemView2, itemView3, itemView4, itemView5, itemView6;
    private IView[] itemViewArr = new IView[6];
    private int currentIndex = 0;
    private int currentTotal = 0;
    private int stayIndex = 0;
    private boolean isGameRunning = false;
    private boolean isTryToStop = false;

    private static final int DEFAULT_SPEED = 200;
    private static final int MIN_SPEED = 200;
    private int currentSpeed = DEFAULT_SPEED;

    public LuckyDrawView(@NonNull Context context) {
        this(context, null);
    }

    public LuckyDrawView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private LuckyDrawViewCallBack callback;

    public interface LuckyDrawViewCallBack {
        void onAnimationEnd();
    }

    public void setCallback(LuckyDrawViewCallBack callback) {
        this.callback = callback;
    }


    public LuckyDrawView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_lucky_draw, this);
        initView();
    }

    @Override
    protected void onDetachedFromWindow() {
        stopMarquee();
        super.onDetachedFromWindow();
    }

    private void initView() {
        itemView1 = (LuckyDrawCardItemView) findViewById(R.id.item1);
        itemView2 = (LuckyDrawCardItemView) findViewById(R.id.item2);
        itemView3 = (LuckyDrawCardItemView) findViewById(R.id.item3);
        itemView4 = (LuckyDrawCardItemView) findViewById(R.id.item4);
        itemView5 = (LuckyDrawCardItemView) findViewById(R.id.item5);
        itemView6 = (LuckyDrawCardItemView) findViewById(R.id.item6);

        itemViewArr[0] = itemView1;
        itemViewArr[1] = itemView2;
        itemViewArr[2] = itemView3;
        itemViewArr[3] = itemView6;
        itemViewArr[4] = itemView5;
        itemViewArr[5] = itemView4;


        itemView1.setCallback(cardItemViewCallBack);
        itemView2.setCallback(cardItemViewCallBack);
        itemView3.setCallback(cardItemViewCallBack);
        itemView4.setCallback(cardItemViewCallBack);
        itemView5.setCallback(cardItemViewCallBack);
        itemView6.setCallback(cardItemViewCallBack);
    }

    LuckyDrawCardItemView.CardItemViewCallBack cardItemViewCallBack = new LuckyDrawCardItemView.CardItemViewCallBack() {
        @Override
        public void onAnimationEnd() {
            if (callback != null) {
                callback.onAnimationEnd();
            }
        }
    };

    private void stopMarquee() {
        isGameRunning = false;
        isTryToStop = false;
    }

    private long getInterruptTime() {
        currentTotal++;
        if (isTryToStop) {
            currentSpeed += 10;
            if (currentSpeed > DEFAULT_SPEED) {
                currentSpeed = DEFAULT_SPEED;
            }
        } else {
            if (currentTotal / itemViewArr.length > 0) {
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

    /**
     * 重置数据
     */
    public void resetData() {
        for (int i = 0; i < itemViewArr.length; i++) {
            itemViewArr[i].resetView();
        }
    }


    public IView[] getItemViewArr() {
        return itemViewArr;
    }

    /**
     * 开始游戏
     * 先翻转 翻转动画 1秒 ，延时2秒 开始跑圈
     */
    public void startGame() {
        for (int i = 0; i < itemViewArr.length; i++) {
            itemViewArr[i].setBack(true);
            itemViewArr[i].setSelect(false);
        }
        postDelayed(new Runnable() {
            @Override
            public void run() {
                startGames();
            }
        }, 2000);
    }

    public void startGames() {
        isGameRunning = true;
        isTryToStop = false;
        currentSpeed = DEFAULT_SPEED;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isGameRunning) {

                    post(new Runnable() {
                        @Override
                        public void run() {
                            int preIndex = currentIndex;
                            currentIndex++;
                            if (currentIndex >= itemViewArr.length) {
                                currentIndex = 0;
                            }

                            itemViewArr[preIndex].setSelect(false);
                            itemViewArr[currentIndex].setSelect(true);

                            if (isTryToStop && currentSpeed == DEFAULT_SPEED && stayIndex == currentIndex) {
                                isGameRunning = false;

                                postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        itemViewArr[currentIndex].setAction(true);
                                        itemViewArr[currentIndex].setBack(false);
                                    }
                                }, 1000);
                            }
                        }
                    });
                    try {
                        Thread.sleep(getInterruptTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void tryToStop(int position) {
        stayIndex = position;
        isTryToStop = true;
    }

}
