package com.yumu.appinfo.bean;

import java.io.Serializable;

/**
 * @author ansen
 * @create time 2019/1/2
 */
public class Album implements Serializable {
    private String id;//
    private String type;//image/video
    private int status = -1;//0/1/2 待审核/审核成功/失败
    private String file_url;//原图，大图
    private String preview_url;//预览图
    private boolean selected;//是否选择
    private long duration;
    private String videoUrl;//视频播放Url

    public Album() {

    }

    public Album(String previewUrl) {
        this.preview_url = previewUrl;
    }

    public boolean isVideo() {
        if (type.equals("video")) {
            return true;
        }
        return false;
    }

    public boolean isImage() {
        if (type.equals("image")) {
            return true;
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public void setPreview_url(String preview_url) {
        this.preview_url = preview_url;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
