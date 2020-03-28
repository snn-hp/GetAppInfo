package com.yumu.appinfo.views;

import android.widget.ImageView;

/**
 * Date :  2020-03-28.
 * Time :  15:39.
 * Created by sunan.
 */
public interface IView {

    void setSelect(boolean isFocused);

    void setBack(boolean isFocused);

    void setAction(boolean isNeedCallBack);

    void resetView();

    ImageView getImageFront();
}
