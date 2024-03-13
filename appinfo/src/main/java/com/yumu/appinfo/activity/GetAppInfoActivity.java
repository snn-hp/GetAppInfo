package com.yumu.appinfo.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;


public class GetAppInfoActivity extends BaseActivity {
    private TextView tvAppInfo, tvGetInfo, tvAppNum, tvSearch, tv_click_info, tv_md5;
    private List<APPInfo> appInfoList;
    private List<APPInfo> sysInfoList;
    private RecyclerView recyclerview;
    private AppInfoAdapter appInfoAdapter;
    private EditText etSearch;
    private EditText et_packet_name;
    private String MD5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_app_info);
        tvGetInfo = findViewById(R.id.tv_get_info);
        tvAppInfo = findViewById(R.id.tv_app_info);
        tvSearch = findViewById(R.id.tv_search);
        etSearch = findViewById(R.id.et_search);
        et_packet_name = findViewById(R.id.et_packet_name);
        tv_click_info = findViewById(R.id.tv_click_info);
        tv_md5 = findViewById(R.id.tv_md5);

        tvAppNum = findViewById(R.id.tv_app_num);
        recyclerview = findViewById(R.id.recyclerview);
        tvGetInfo.setOnClickListener(onClickListener);
        tvSearch.setOnClickListener(onClickListener);
        tv_click_info.setOnClickListener(onClickListener);
        tv_md5.setOnClickListener(onClickListener);

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
            } else if (view.getId() == R.id.tv_click_info) {
                MD5 = getMd5(et_packet_name.getText().toString());
                if (TextUtils.isEmpty(MD5)) {
                    return;
                }
                tv_md5.setText("签名 ： " + MD5 + "（ 点击我可复制） ");
                Log.d("snn", "Signature MD5: " + MD5);
            } else if (view.getId() == R.id.tv_md5) {
                if (TextUtils.isEmpty(MD5)) {
                    Toast.makeText(getApplicationContext(), "没有要复制的内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 获取剪贴板管理器
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建一个ClipData对象
                ClipData clipData = ClipData.newPlainText("text", MD5);
                // 将ClipData对象放入剪贴板
                clipboardManager.setPrimaryClip(clipData);
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
            for (APPInfo appinfo : sysInfoList) {
                if (searchKey.equals(appinfo.getAppName()) || searchKey.equals(appinfo.getPackageName())) {
                    currentPosition = sysInfoList.indexOf(appinfo);
                    Log.d("snn", "  currentPosition " + currentPosition);
                }
            }
            if (currentPosition != -1) {
                recyclerview.scrollToPosition(currentPosition);
            } else {
                Toast.makeText(getApplicationContext(), "没有找到匹配的App", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getMd5(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            Toast.makeText(getApplicationContext(), "输入要搜索的包名", Toast.LENGTH_SHORT).show();
            return "";
        }

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            Signature[] signatures = packageInfo.signatures;

            for (Signature signature : signatures) {
                byte[] signatureBytes = signature.toByteArray();
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(signatureBytes);
                byte[] digest = md.digest();

                StringBuilder sb = new StringBuilder();
                for (byte b : digest) {
                    sb.append(String.format("%02x", b & 0xff));
                }
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    /**
     * 获取app信息
     */
    private void getAppInfos() {
        appInfoList.clear();
        sysInfoList.clear();
//        PackageManager packageManager = getPackageManager();
//        // 获取已安装的应用程序列表
//        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
//        for (PackageInfo packageInfo : installedPackages) {
//            // 获取应用程序的名称
//            String appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
//            // 获取应用程序的包名
//            String packageName = packageInfo.packageName;
//            // 将应用程序的名称添加到列表中
//            APPInfo appInfo = new APPInfo();
//            appInfo.appName = appName;
//            appInfo.packageName = packageName;
//            appInfo.md5info = getMd5(packageName);
//            appInfoList.add(appInfo);
//        }


//         获取已经安装的所有应用, PackageInfo　系统类，包含应用信息
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(PackageManager.MATCH_UNINSTALLED_PACKAGES);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
//            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {    //非系统应用
//                // AppInfo 自定义类，包含应用信息
//                APPInfo appInfo = new APPInfo();
//                appInfo.setAppName(packageInfo.applicationInfo.loadLabel(getPackageManager()).toString());//获取应用名称
//                appInfo.setPackageName(packageInfo.packageName); //获取应用包名，可用于卸载和启动应用
//                appInfo.setVersionName(packageInfo.versionName);//获取应用版本名   V1.0.1
//                appInfo.setVersionCode(packageInfo.versionCode);//获取应用版本号    10
//                appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(getPackageManager()));//获取应用图标
//                appInfo.setFirstinstallation(Utils.format(packageInfo.firstInstallTime));//获取应用版本名
//                appInfo.setLastupdate(Utils.format(packageInfo.lastUpdateTime));//获取最后一次安装时间
//                appInfo.setSha1info(Utils.getSignaturesInfo(getApplicationContext(), packageInfo.packageName, "SHA1"));//获取应用SHA1
//                appInfo.setSha256(Utils.getSignaturesInfo(getApplicationContext(), packageInfo.packageName, "SHA256"));//获取应用MD5
//                appInfo.setMd5info(Utils.getSignaturesInfo(getApplicationContext(), packageInfo.packageName, "MD5"));//获取应用MD5
//                appInfoList.add(appInfo);
//            } else { // 系统应用
//                sysInfoList.add(new APPInfo());
//            }
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
            appInfo.setMd5MainInfo(getMd5(packageInfo.packageName));//获取应用MD5 小写
            appInfoList.add(appInfo);
            Log.d("snn", "  appInfo " + appInfo);
        }
        tvAppNum.setText("非系统应用数量：" + appInfoList.size() + "个\n系统应用数量：" + sysInfoList.size() + "个");
        appInfoAdapter.setAppInfoList(appInfoList);
    }
}
