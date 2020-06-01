package com.yumu.appinfo.activity;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mjzuo.location.GeocodingManager;
import com.mjzuo.location.ReverseGeocodingManager;
import com.mjzuo.location.bean.Latlng;
import com.mjzuo.location.constant.Constant;
import com.mjzuo.location.location.GoogleGeocoding;
import com.mjzuo.location.location.IGeocoding;
import com.mjzuo.location.regelocation.IReGe;
import com.yumu.appinfo.R;
import com.yumu.appinfo.utils.StatusBarHelper;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class LocationTaskActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final String LOG_TAG = "snn";
    private GeocodingManager siLoManager; //定位获取经纬度，包括LocationManager、基站地位
    private ReverseGeocodingManager reGeManager; //反地理编码的manager，包括google反地理、高德反地理、百度反地理、腾讯反地理
    private TextView tvSimpleLo, tvSimpleAd, tv_get_location;
    private SweetAlertDialog pDialog;
    private String[] permsLocation = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_location_activity);
        tvSimpleLo = findViewById(R.id.tv_simple_location_txt);
        tvSimpleAd = findViewById(R.id.tv_simple_address_txt);
        tv_get_location = findViewById(R.id.tv_get_location);
        tv_get_location.setOnClickListener(onClickListener);
        initStatusBar();
        if (!EasyPermissions.hasPermissions(this, permsLocation)) {
            EasyPermissions.requestPermissions(this
                    , "必要的权限"
                    , 0
                    , permsLocation);
        }
    }


    public void initStatusBar() {
        StatusBarHelper.setStatusBarLightMode(this);
        StatusBarHelper.setStatusBarColor(this, R.color.whitecolorPrimaryDark, false);
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.tv_get_location) {
                showDialog();
                getLocalTion();
            }
        }
    };


    public void showDialog() {
        pDialog = new SweetAlertDialog(LocationTaskActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(true);
        pDialog.show();
    }

    public void dismissDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    public void getLocalTion() {

        ReverseGeocodingManager.ReGeOption reGeOption = new ReverseGeocodingManager.ReGeOption()
                .setReGeType(Constant.GOOGLE_API)// 百度api返地理编码
                .setSn(true)// sn 签名校验方式
                .setIslog(true);// 打印log
        reGeManager = new ReverseGeocodingManager(this, reGeOption);
        reGeManager.addReGeListener(new IReGe.IReGeListener() {
            @Override
            public void onSuccess(int state, Latlng latlng) {
                Log.e(LOG_TAG, "reGeManager onSuccess:" + latlng);
                tvSimpleAd.setText("country:" + latlng.getCountry()
                        + "\ncity:" + latlng.getCity()
                        + "\nsublocality:" + latlng.getSublocality()
                        + "\naddress:" + latlng.getAddress()
                        + "\nname:" + latlng.getName());
                tvSimpleAd.setVisibility(View.VISIBLE);
                dismissDialog();
            }

            @Override
            public void onFail(int errorCode, String error) {
                Log.e(LOG_TAG, "error:" + error);
                tvSimpleAd.setText("errorCode:" + errorCode + ", error:" + error);
                tvSimpleAd.setVisibility(View.VISIBLE);
            }
        });


        GeocodingManager.GeoOption option = new GeocodingManager.GeoOption()
                .setGeoType(Constant.LM_API) // 使用openCellid服务器的基站地位
                .setOption(new GoogleGeocoding.SiLoOption()
                        .setGpsFirst(true));// locationManager定位方式时，gps优先
        siLoManager = new GeocodingManager(this, option);
        siLoManager.start(new IGeocoding.ISiLoResponseListener() {
            @Override
            public void onSuccess(Latlng latlng) {
                Log.e(LOG_TAG, "siLoManager onSuccess:" + latlng);
                tvSimpleLo.setText("latlng:" + latlng.getLatitude()
                        + "\nlong:" + latlng.getLongitude()
                        + "\nprovider:" + latlng.getProvider());
                reGeManager.reGeToAddress(latlng);
                tvSimpleLo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFail(String msg) {
                Log.e(LOG_TAG, "error:" + msg);
                tvSimpleAd.setText("error:" + msg);
                tvSimpleLo.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (siLoManager != null)
            siLoManager.stop();
        if (reGeManager != null) {
            reGeManager.stop();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == 0 && siLoManager != null)
            siLoManager.reStart();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        EasyPermissions.requestPermissions(this, "必要的权限", 0, permsLocation);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
