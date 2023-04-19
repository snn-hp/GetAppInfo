package com.yumu.appinfo.activity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yumu.appinfo.R;
import com.yumu.appinfo.adapter.AppInfoAdapter;
import com.yumu.appinfo.bean.APPInfo;
import com.yumu.appinfo.utils.StatusBarHelper;
import com.yumu.appinfo.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class GetAppInfoActivity extends BaseActivity {
    private TextView tvAppInfo, tvGetInfo, tvAppNum, tvSearch;
    private List<APPInfo> appInfoList;
    private List<APPInfo> sysInfoList;
    private RecyclerView recyclerview;
    private AppInfoAdapter appInfoAdapter;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_app_info);
        tvGetInfo = findViewById(R.id.tv_get_info);
        tvAppInfo = findViewById(R.id.tv_app_info);
        tvSearch = findViewById(R.id.tv_search);
        etSearch = findViewById(R.id.et_search);

        tvAppNum = findViewById(R.id.tv_app_num);
        recyclerview = findViewById(R.id.recyclerview);
        tvGetInfo.setOnClickListener(onClickListener);
        tvSearch.setOnClickListener(onClickListener);

        appInfoList = new ArrayList<>();
        sysInfoList = new ArrayList<>();
        setAdapter();
        addViewAction();
        initStatusBar();
    }


    public void initStatusBar() {
        StatusBarHelper.setStatusBarLightMode(this);
        StatusBarHelper.setStatusBarColor(this, R.color.whitecolorPrimaryDark, false);
    }

    public void setAdapter() {
        appInfoAdapter = new AppInfoAdapter(getApplicationContext());
        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerview.setAdapter(appInfoAdapter);
    }

    public void addViewAction() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO) {
                    searchApps();
                    return true;
                }
                return false;
            }
        });

    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.tv_get_info) {
                getAppInfos();
            } else if (view.getId() == R.id.tv_search) {
                searchApps();
            }
        }
    };


    public void searchApps() {

        InputMethodManager mInputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mInputManager.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);

        if (appInfoList.isEmpty()) {
            Toast.makeText(getApplicationContext(), "先获取一下应用信息啊", Toast.LENGTH_SHORT).show();
            return;
        }

        String searchKey = etSearch.getText().toString();

        if (Utils.isEmpty(searchKey)) {
            Toast.makeText(getApplicationContext(), "输入一下包名或者App名字啊", Toast.LENGTH_SHORT).show();
            return;
        }

        int currentPosition = -1;

        for (APPInfo appinfo : appInfoList) {
            if (searchKey.equals(appinfo.getAppName()) || searchKey.equals(appinfo.getPackageName())) {
                currentPosition = appInfoList.indexOf(appinfo);
                Log.d("snn", "  currentPosition " + currentPosition);
            }
        }

        if (currentPosition != -1) {
            recyclerview.scrollToPosition(currentPosition);
        } else {
            Toast.makeText(getApplicationContext(), "没有找到匹配的App", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取app信息
     */
    private void getAppInfos() {
        appInfoList.clear();
//        int num = 0;
//        StringBuilder stringBuilder = new StringBuilder();
        // 获取已经安装的所有应用, PackageInfo　系统类，包含应用信息
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {    //非系统应用
                // AppInfo 自定义类，包含应用信息
                APPInfo appInfo = new APPInfo();
                appInfo.setAppName(packageInfo.applicationInfo.loadLabel(getPackageManager()).toString());//获取应用名称
                appInfo.setPackageName(packageInfo.packageName); //获取应用包名，可用于卸载和启动应用
                appInfo.setVersionName(packageInfo.versionName);//获取应用版本名   V1.0.1
                appInfo.setVersionCode(packageInfo.versionCode);//获取应用版本号    10
                appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(getPackageManager()));//获取应用图标
                appInfo.setFirstinstallation(Utils.format(packageInfo.firstInstallTime));//获取应用版本名
                appInfo.setLastupdate(Utils.format(packageInfo.lastUpdateTime));//获取最后一次安装时间
                appInfo.setSha1info(Utils.getSignaturesInfo(getApplicationContext(), packageInfo.packageName, "SHA1"));//获取应用SHA1
                appInfo.setSha256(Utils.getSignaturesInfo(getApplicationContext(), packageInfo.packageName, "SHA256"));//获取应用MD5
                appInfo.setMd5info(Utils.getSignaturesInfo(getApplicationContext(), packageInfo.packageName, "MD5"));//获取应用MD5
//                System.out.println(appInfo.toString());
//                num++;
//                stringBuilder.append("\n\n第  " + num + " 个");
//                stringBuilder.append("\nAPPName: " + appInfo.getAppName());
//                stringBuilder.append("\nPackage: " + appInfo.getPackageName());
//                stringBuilder.append("\nversionName: " + appInfo.getVersionName());
//                stringBuilder.append("\nversionCode: " + appInfo.getVersionCode());
//                stringBuilder.append("\n首次安装时间: " + appInfo.getFirstinstallation());
//                stringBuilder.append("\n最后更新时间: " + appInfo.getLastupdate());
//                stringBuilder.append("\nMD5签名信息:\n " + appInfo.getMd5info());
//                stringBuilder.append("\nSHA1签名信息:\n " + appInfo.getSha1info());
//                stringBuilder.append("\nSHA256签名信息:\n " + appInfo.getSha256());
                appInfoList.add(appInfo);
            } else { // 系统应用
                sysInfoList.add(new APPInfo());
            }
        }
        tvAppNum.setText("非系统应用数量：" + appInfoList.size() + "个\n系统应用数量：" + sysInfoList.size() + "个");
        appInfoAdapter.setAppInfoList(appInfoList);
//        tvAppInfo.setText(stringBuilder);
//        tvAppInfo.setVisibility(View.VISIBLE);
    }
}
