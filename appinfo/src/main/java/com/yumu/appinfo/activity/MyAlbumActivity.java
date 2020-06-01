package com.yumu.appinfo.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.yumu.appinfo.R;
import com.yumu.appinfo.adapter.MyAlbumAdapter;
import com.yumu.appinfo.bean.Album;
import com.yumu.appinfo.utils.PictureSelectUtil;
import com.yumu.appinfo.utils.StatusBarHelper;
import com.yumu.appinfo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MyAlbumActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    protected RecyclerView recyclerView;
    protected MyAlbumAdapter adapter;
    private List<Album> albumList;
    private int maxNumber = 9;

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
            selectImage();
        }

        @Override
        public void deleteItem(int position) {
            if (albumList.size() > position) {
                albumList.remove(position);
                dataChanged();
            }
        }
    };


    private void selectImage() {
        int maxSize = maxNumber - albumList.size();
        PictureSelectUtil.selectImage(this, maxSize, true, false, true, PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST) { //图片、视频、音频选择结果回调
            List<LocalMedia> selectList = PictureSelectUtil.getSelectResult(data);
            for (LocalMedia localMedia : selectList) {
                Album album = new Album();

                String path = Utils.getFilePathFromContentUri(localMedia.getPath(), getContentResolver());
                album.setFile_url(path);
                album.setPreview_url(path);
                album.setSelected(true);

                album.setType(Utils.getMimeTypeValue(localMedia.getMimeType()));
                albumList.add(album);
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
