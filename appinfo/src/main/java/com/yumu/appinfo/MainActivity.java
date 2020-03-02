package com.yumu.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
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

import com.yumu.appinfo.bean.APPInfo;
import com.yumu.appinfo.utils.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tvAppInfo, tvGetInfo, tvAppNum, tvSearch;
    private List<APPInfo> appInfoList;
    private List<APPInfo> sysInfoList;
    private RecyclerView recyclerview;
    private AppInfoAdapter appInfoAdapter;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        if (StringUtil.isEmpty(searchKey)) {
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
        }
    }

    /**
     * 获取app信息
     */
    private void getAppInfos() {
        appInfoList.clear();
//        int num = 0;
        StringBuilder stringBuilder = new StringBuilder();
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
                appInfo.setFirstinstallation(format(packageInfo.firstInstallTime));//获取应用版本名
                appInfo.setLastupdate(format(packageInfo.lastUpdateTime));//获取最后一次安装时间
                appInfo.setSha1info(getSignaturesInfo(getApplicationContext(), packageInfo.packageName, "SHA1"));//获取应用SHA1
                appInfo.setSha256(getSignaturesInfo(getApplicationContext(), packageInfo.packageName, "SHA256"));//获取应用MD5
                appInfo.setMd5info(getSignaturesInfo(getApplicationContext(), packageInfo.packageName, "MD5"));//获取应用MD5
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


    public String format(long timestamp) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置格式
        String timeText = format.format(timestamp);
        return timeText;
    }


    //这个是获取SHA1的方法
    public static String getSignaturesInfo(Context context, String packageName, String tpye) {
//        //获取包管理器
        PackageManager pm = context.getPackageManager();
//        //获取当前要获取SHA1值的包名，也可以用其他的包名，但需要注意，
//        //在用其他包名的前提是，此方法传递的参数Context应该是对应包的上下文。
//        String packageName = context.getPackageName();

        //返回包括在包中的签名信息
        int flags = PackageManager.GET_SIGNATURES;
        PackageInfo packageInfo = null;
        try {
            //获得包的所有内容信息类
            packageInfo = pm.getPackageInfo(packageName, flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //签名信息
        Signature[] signatures = packageInfo.signatures;
        byte[] cert = signatures[0].toByteArray();
        //将签名转换为字节数组流
        InputStream input = new ByteArrayInputStream(cert);
        //证书工厂类，这个类实现了出厂合格证算法的功能
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        //X509证书，X.509是一种非常通用的证书格式
        X509Certificate c = null;
        try {
            c = (X509Certificate) cf.generateCertificate(input);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        String hexString = null;
        try {
            //加密算法的类，这里的参数可以使MD4,MD5等加密算法
//            MessageDigest md = MessageDigest.getInstance("SHA1");
            MessageDigest md = MessageDigest.getInstance(tpye);
            //获得公钥
            byte[] publicKey = md.digest(c.getEncoded());
            //字节到十六进制的格式转换
            hexString = byte2HexFormatted(publicKey);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return hexString;
    }

    //这里是将获取到得编码进行16进制转换
    private static String byte2HexFormatted(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);
        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1)
                h = "0" + h;
            if (l > 2)
                h = h.substring(l - 2, l);
            str.append(h.toUpperCase());
            if (i < (arr.length - 1))
                str.append(':');
        }
        return str.toString();
    }

}
