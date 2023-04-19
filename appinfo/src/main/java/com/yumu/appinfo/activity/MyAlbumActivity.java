package com.yumu.appinfo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yumu.appinfo.R;
import com.yumu.appinfo.adapter.MyAlbumAdapter;
import com.yumu.appinfo.bean.Album;
import com.yumu.appinfo.utils.GlideEngine;
import com.yumu.appinfo.utils.PictureSelectUtil;
import com.yumu.appinfo.utils.StatusBarHelper;
import com.yumu.appinfo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MyAlbumActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    protected RecyclerView recyclerView;
    protected MyAlbumAdapter adapter;
    private List<Album> albumList;
    private int maxNumber = 9;
    private List<LocalMedia> selectMediaList;
    private String[] permsLocation = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_album_activity);
        recyclerView = findViewById(R.id.recyclerview);
        albumList = new ArrayList<>();
        selectMediaList = new ArrayList<>();
        initStatusBar();
        initRecyclerView();
        if (!EasyPermissions.hasPermissions(this, permsLocation)) {
            EasyPermissions.requestPermissions(this
                    , "必要的权限"
                    , 0
                    , permsLocation);
        }
    }

    public void initStatusBar() {
        StatusBarHelper.setStatusBarDarkMode(this);
        StatusBarHelper.setStatusBarColor(this, R.color.colorAccent, false);
    }

    protected void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4, RecyclerView.VERTICAL, false));//设置布局管理器
        recyclerView.setAdapter(adapter = new MyAlbumAdapter(this, albumList));
        adapter.setCallback(callback);
    }

    MyAlbumAdapter.MyAlbumAdapterCallback callback = new MyAlbumAdapter.MyAlbumAdapterCallback() {
        @Override
        public void onItemClick(int position) {
            Log.d("snn", "  deleteItem: position ： " + position+ "albumList.size()" + albumList.size() + "   selectMediaList.size() : " + selectMediaList.size());

            if (selectMediaList.size() > 0 && selectMediaList.size() > position) {
                LocalMedia media = selectMediaList.get(position);
                String mimeType = media.getMimeType();
                int mediaType = PictureMimeType.getMimeType(mimeType);
                switch (mediaType) {
                    case PictureConfig.TYPE_VIDEO:
                        // 预览视频
                        PictureSelector.create(MyAlbumActivity.this)
                                .themeStyle(R.style.picture_default_style)
                                .externalPictureVideo(TextUtils.isEmpty(media.getAndroidQToPath()) ? media.getPath() : media.getAndroidQToPath());
                        break;
                    case PictureConfig.TYPE_AUDIO:
                        // 预览音频
                        PictureSelector.create(MyAlbumActivity.this).externalPictureAudio(PictureMimeType.isContent(media.getPath()) ? media.getAndroidQToPath() : media.getPath());
                        break;
                    case PictureConfig.TYPE_IMAGE:
                        // 预览图片 可自定长按保存路径
                        PictureSelector.create(MyAlbumActivity.this)
                                .themeStyle(R.style.picture_default_style) // xml设置主题
                                //.setPictureWindowAnimationStyle(animationStyle)// 自定义页面启动动画
                                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                                .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                                //.bindCustomPlayVideoCallback(new MyVideoSelectedPlayCallback(getContext()))// 自定义播放回调控制，用户可以使用自己的视频播放界面
                                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                                .openExternalPreview(position, selectMediaList);
                        break;
                    default:
                        selectImage();
                        break;
                }
            } else {
                selectImage();
            }
        }

        @Override
        public void deleteItem(int position) {
            if (albumList.size() > position) {
                albumList.remove(position);
                dataChanged();
            }
            if (selectMediaList.size() > position) {
                selectMediaList.remove(position);
            }

            Log.d("snn", "  deleteItem: albumList.size()" + albumList.size() + "   selectMediaList.size() : " + selectMediaList.size());

        }
    };


    private void selectImage() {
        int maxSize = maxNumber - albumList.size();
        PictureSelectUtil.selectALLMime(this, maxSize, true, false, true, PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST) { //图片、视频、音频选择结果回调
            List<LocalMedia> mediaList = PictureSelectUtil.getSelectResult(data);
            for (LocalMedia localMedia : mediaList) {
                Album album = new Album();

                String path = Utils.getFilePathFromContentUri(localMedia.getPath(), getContentResolver());
                album.setFile_url(path);
                album.setPreview_url(path);
                album.setSelected(true);
                album.setDuration(localMedia.getDuration());
//                album.setType(Utils.getMimeTypeValue(localMedia.getMimeType()));
                albumList.add(album);

                selectMediaList.add(localMedia);
                dataChanged();
            }
        }
    }

    public void dataChanged() {
        if (adapter != null) {
            adapter.setAppInfoList(albumList);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        selectImage();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        EasyPermissions.requestPermissions(this
                , "必要的权限"
                , 0
                , permsLocation);
    }
}
