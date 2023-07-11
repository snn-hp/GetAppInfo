package  com.yumu.appinfo.transform;


import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;


public class OverlayTransformerLan2 implements ViewPager.PageTransformer {
    public static final String TAG = OverlayTransformerLan2.class.getSimpleName();

    private static final float DEFAULT_CENTER = 0.5f;
    public static final float DEFAULT_MIN_SCALE = 0.85f;
    public static final float MAX_SCALE = 0.999f;
    public float mOffset = 40 * 2 - 20;
    public float PagerOffset = 40 * 2;

    private float mMinScale;
    public ViewPager mViewPager;
    private int saveIndex = -1;

    public OverlayTransformerLan2(ViewPager mViewPager, float minScale) {
        mMinScale = minScale;
        this.mViewPager = mViewPager;
    }

    @Override
    public void transformPage(View view, float position) {

        int current = mViewPager.getCurrentItem();
        int dataSize = mViewPager.getAdapter().getCount();
        Log.i(TAG, "current=" + current + ",position=" + position
                + ",dataSize=" + dataSize);
        if (saveIndex != current && dataSize > 1) {
            if (0 == current) {
                mViewPager.setTranslationX(-PagerOffset);
            } else if (current == dataSize - 1) {
                mViewPager.setTranslationX(PagerOffset);
            } else {
                mViewPager.setTranslationX(0f);
            }
            saveIndex = current;
        }
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();
//
        view.setPivotY(pageHeight / 2);
        view.setPivotX(pageWidth / 2);
        if (position < -1) {
            view.setTranslationZ(position);
            if (position >= -2) {
                float scaleFactor = (1 + position) * (1 - mMinScale) + mMinScale;
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
                view.setPivotX(0);
                if (current == dataSize - 1) {
                    view.setTranslationX(-position * pageWidth
                            + 0.58f * mOffset * position
                    );
                } else {
//                    view.setTranslationX(-position * pageWidth
//                            - 0.5f*mOffset * position
//                    );
                }
            }
        } else if (position <= 1) {
            // Modify the default slide transition to shrink the page as well
            if (position < 0) {
                view.setTranslationX(-position * pageWidth + mOffset * position);

                float scaleFactor = (1 + position) * (1 - mMinScale) + mMinScale;
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
                view.setPivotX(0);
                view.setTranslationZ(position);
            } else {
                view.setTranslationX(-position * pageWidth + mOffset * position);
                float scaleFactor = (1 - position) * (1 - mMinScale) + mMinScale;
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                view.setPivotX(pageWidth);
                view.setTranslationZ(-position);

            }
        } else {
            view.setTranslationZ(-position);
            if (position <= 2) {
                if (0 == current) {
                    view.setTranslationX(-position * pageWidth + 3.385f * mOffset * position);
                } else {
                    view.setTranslationX(-position * pageWidth + 3 * mOffset * position);
                }
                view.setPivotX(0);
                float scaleFactor = (1 - position) * (1 - mMinScale) + mMinScale;
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            } else {

            }
        }
    }
}