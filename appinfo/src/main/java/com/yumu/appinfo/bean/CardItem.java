package com.yumu.appinfo.bean;

/**
 * Date :  2020/3/26.
 * Time :  15:50.
 * Created by sunan.
 */
public class CardItem {
    private boolean isSelect;
    private boolean isBack;
    private String content;


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isBack() {
        return isBack;
    }

    public void setBack(boolean back) {
        isBack = back;
    }
}
