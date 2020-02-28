package com.yumu.appinfo.bean;

import android.graphics.drawable.Drawable;

/**
 * Date :  2019-10-29.
 * Time :  16:54.
 * Created by sunan.
 */
public class APPInfo {

    public String appName = "";//app 名字

    public String packageName = "";//包名
    public String versionName = ""; //版本号
    public String firstinstallation = "";//首次安装时间
    public String lastupdate = "";//最后一次更新时间
    public String sha1info = "";//sha1信息
    public String sha256 = "";//sha256信息
    public String md5info = "";//md5签名
    public int versionCode = 0;
    public Drawable appIcon = null;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }


    public void getAppinfo() {

    }

    public String getFirstinstallation() {
        return firstinstallation;
    }

    public void setFirstinstallation(String firstinstallation) {
        this.firstinstallation = firstinstallation;
    }

    public String getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getSha1info() {
        return sha1info;
    }

    public void setSha1info(String sha1info) {
        this.sha1info = sha1info;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public String getMd5info() {
        return md5info;
    }

    public void setMd5info(String md5info) {
        this.md5info = md5info;
    }

    @Override
    public String toString() {
        return "APPInfo{" +
                "appName='" + appName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", versionName='" + versionName + '\'' +
                ", firstinstallation='" + firstinstallation + '\'' +
                ", lastupdate='" + lastupdate + '\'' +
                ", sha1info='" + sha1info + '\'' +
                ", sha256='" + sha256 + '\'' +
                ", md5info='" + md5info + '\'' +
                ", versionCode=" + versionCode +
                ", appIcon=" + appIcon +
                '}';
    }
}