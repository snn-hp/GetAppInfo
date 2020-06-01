package com.yumu.appinfo.utils;

import android.app.Activity;
import android.content.Intent;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.SdkVersionUtils;
import com.yumu.appinfo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ansen
 * @create time 2020-01-06
 */
public class PictureSelectUtil {
    public static void selectAvatar(Activity activity) {
        selectImage(activity, 1, true, true);
    }

    public static void selectALLMime(Activity activity, int maxSelectNum, boolean isCamera, boolean enableCrop, boolean enablePreview, int requestCode) {
        int selectionMode = maxSelectNum == 1 ? PictureConfig.SINGLE : PictureConfig.MULTIPLE;
        select(activity, PictureMimeType.ofAll(), maxSelectNum, 4, selectionMode, enablePreview, isCamera, enableCrop, 10, 15, 15, requestCode);
    }


    public static void selectImage(Activity activity, int maxSelectNum, boolean isCamera, boolean enableCrop) {
        selectImage(activity, maxSelectNum, isCamera, enableCrop, true, BaseConstans.RequestCode.SELECT_IMAGE);
    }

    public static void selectImage(Activity activity, int maxSelectNum, boolean isCamera, boolean enableCrop, boolean enablePreview, int requestCode) {
        int selectionMode = maxSelectNum == 1 ? PictureConfig.SINGLE : PictureConfig.MULTIPLE;
        select(activity, PictureMimeType.ofImage(), maxSelectNum, 4, selectionMode, enablePreview, isCamera, enableCrop, 0, 0, 0, requestCode);
    }

    public static void selectVideo(Activity activity, int maxSelectNum, boolean isCamera, int videoMinSecond, int videoMaxSecond, int recordVideoSecond) {
        selectVideo(activity, maxSelectNum, isCamera, videoMinSecond, videoMaxSecond, recordVideoSecond, BaseConstans.RequestCode.SELECT_VIDEO);
    }

    public static void selectVideo(Activity activity, int maxSelectNum, boolean isCamera, int videoMinSecond, int videoMaxSecond, int recordVideoSecond, int requestCode) {
        int selectionMode = maxSelectNum == 1 ? PictureConfig.SINGLE : PictureConfig.MULTIPLE;
        select(activity, PictureMimeType.ofVideo(), maxSelectNum, 4, selectionMode, true, isCamera, true, videoMinSecond, videoMaxSecond, recordVideoSecond, requestCode);
    }

    /**
     * @param mimeType          选择类型 PictureMimeType.ofAll()/ofImage()/ofVideo()/ofAudio()
     * @param maxSelectNum      最大选择数量
     * @param imageSpanCount    一行显示几个
     * @param selectionMode     选择模式 PictureConfig.SINGLE/PictureConfig.MULTIPLE
     * @param enablePreview     是否可预览图片
     * @param isCamera          是否显示拍照按钮
     * @param enableCrop        是否裁剪
     * @param videoMinSecond    视频最小时间 秒
     * @param videoMaxSecond    视频最大时间 秒
     * @param recordVideoSecond 录制时长 秒
     * @param requestCode       请求码
     */
    public static void select(Activity activity, int mimeType, int maxSelectNum, int imageSpanCount, int selectionMode, boolean enablePreview, boolean isCamera, boolean enableCrop, int videoMinSecond, int videoMaxSecond, int recordVideoSecond, int requestCode) {
        if (!Utils.isActivityUseable(activity)) {
            return;
        }

        PictureSelector.create(activity)
                .openGallery(mimeType)//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageSpanCount(imageSpanCount)// 每行显示个数 int
                .theme(R.style.picture_default_style)
                .maxSelectNum(maxSelectNum)
                .selectionMode(selectionMode)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .isPreviewImage(enablePreview)// 是否可预览图片 true or false
                .isPreviewVideo(true)// 是否可预览视频 true or false
                .isCamera(isCamera)// 是否显示拍照按钮 true or false
                .isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
                .maxVideoSelectNum(maxSelectNum) // 视频最大选择数量
//                .imageFormat(PictureMimeType.PNG_Q)// 单独 拍照 拍照保存图片格式后缀,默认jpeg
//                .imageFormat(PictureMimeType.ofMP4())// 单独 录制视频配置
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .enableCrop(enableCrop)// 是否裁剪 true or false
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .isDragFrame(true)// 是否可拖动裁剪框(固定)
                .videoMaxSecond(videoMaxSecond)
                .videoMinSecond(videoMinSecond)
                .recordVideoSecond(recordVideoSecond)
                .compress(true)//压缩
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .forResult(requestCode);//结果回调onActivityResult code
    }

    public static void preview(Activity activity, String urlOrPath) {
        List<LocalMedia> list = new ArrayList<>();
        LocalMedia localMedia = new LocalMedia();
//        localMedia.setPictureType("image/jpeg");
        localMedia.setPath(urlOrPath);
        list.add(localMedia);
        preview(activity, 0, list);
    }

    public static void previewUrls(Activity activity, int index, String[] imageUrls) {
        List<LocalMedia> images = new ArrayList<>();
        for (int i = 0; i < imageUrls.length; i++) {
            LocalMedia localMedia = new LocalMedia();
//            localMedia.setMimeType(PictureMimeType.ofImage());
//            localMedia.setPictureType("image/jpeg");
            localMedia.setPath(imageUrls[i]);
            images.add(localMedia);
        }
        preview(activity, index, images);
    }

    public static void preview(Activity activity, int position, List<LocalMedia> list) {
        if (!Utils.isActivityUseable(activity)) {
            return;
        }

        PictureSelector.create(activity)
                .themeStyle(R.style.picture_default_style) // xml设置主题
                //.setPictureWindowAnimationStyle(animationStyle)// 自定义页面启动动画
//                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)// 设置相册Activity方向，不设置默认使用系统
                .isNotPreviewDownload(false)//预览图片长按是否可以下载
                .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .openExternalPreview(position, list);
    }

    public static void openCamera(Activity activity) {
        if (!Utils.isActivityUseable(activity)) {
            return;
        }
        PictureSelector.create(activity)
                .openCamera(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine())
                .forResult(PictureConfig.REQUEST_CAMERA);
    }

    public static List<LocalMedia> getSelectResult(Intent data) {
        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
        boolean isAndroidQ = SdkVersionUtils.checkedAndroid_Q();
        if (isAndroidQ) {
            for (LocalMedia localMedia : selectList) {
                localMedia.setPath(localMedia.getAndroidQToPath());
            }
        }
        return selectList;
    }
}
