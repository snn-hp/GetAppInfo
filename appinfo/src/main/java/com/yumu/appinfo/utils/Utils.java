package com.yumu.appinfo.utils;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ansen
 * @create time 2018/3/16
 */
public class Utils {
    private String str;

    @SuppressLint("NewApi")
    public static void copyString(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT > 10) {
            android.content.ClipboardManager clip = (android.content.ClipboardManager)
                    context.getSystemService(Context.CLIPBOARD_SERVICE);
            clip.setPrimaryClip(ClipData.newPlainText(text, text));
        } else {
            android.text.ClipboardManager clipM = (android.text.ClipboardManager)
                    context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipM.setText(text);
        }
    }

    /**
     * if string is empty
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static String getPasteString(Context context) {
        android.content.ClipboardManager clip = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //GET贴板是否有内容
        ClipData mClipData = clip.getPrimaryClip();
        //获取到内容
        if (mClipData == null) {
            return "";
        }
        ClipData.Item item = mClipData.getItemAt(0);
        if (item == null) {
            return "";
        }
        String text = item.getText().toString();
        return text;
    }


    public static boolean checkPassword(String password) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static boolean isMobileNO(String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            return false;
        }

        if (mobile.length() != 11) {
            return false;
        }
//        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
//        Matcher m = p.matcher(mobile);
//        return m.matches();
        return true;
    }

    public static String checkAddress(String input) {
        String[] sp;
        if (!TextUtils.isEmpty(input) && input.contains(":")) {
            sp = input.split(":");
            return sp[sp.length - 1];
        } else if (!TextUtils.isEmpty(input) && input.contains("：")) {
            sp = input.split("：");
            return sp[sp.length - 1];
        }
        return input;
    }

    /**
     * 获取APK版本号
     */
    public static String getVersionName(Context ctx) {
        // 获取packagemanager的实例
        String version = "";
        int versionCode = 0;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(
                    ctx.getPackageName(), 0);
            version = packInfo.versionName;
            versionCode = packInfo.versionCode;
        } catch (Exception e) {
        }
        return version + "." + versionCode;
    }

    /**
     * 获取换行文本
     * text 原文本
     * max 每行最大个数
     */
    public static String getMaxEms(String text, int max) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        if (text.length() <= max) {
            return text;
        } else {
            int rows = 0;
            if (text.length() % max == 0) {
                rows = text.length() / max;
            } else {
                rows = text.length() / max + 1;
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < rows; i++) {
                if (i != rows - 1) {
                    sb.append(text.substring(i * max, (i + 1) * max) + "\n");
                } else {
                    sb.append(text.substring(i * max));
                }
            }
            return sb.toString();

        }
    }


    public static String getSuffixWithUrl(String url) {
        String suffixes = "avi|mpeg|3gp|mp3|mp4|wav|jpeg|gif|jpg|png|apk|exe|pdf|rar|zip|docx|doc";
        Pattern pat = Pattern.compile("[\\w]+[\\.](" + suffixes + ")");//正则判断
        Matcher mc = pat.matcher(url);
        //条件匹配
        while (mc.find()) {
            String suffix = mc.group();//截取带后缀的文件名
            Log.e("suffix:", suffix);
            return suffix;
        }

        return "";
    }

    public static String getPayTag(String address) {
        if (address != null && address.contains("-")) {
            String[] split = address.split("-");
            if (split.length > 1) {
                return split[split.length - 1];
            }
        }
        return "";
    }


    public static String getTimeForAnounce(long timetemp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timetemp * 1000);
        SimpleDateFormat fmat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        String time = fmat.format(calendar.getTime());
        return time;
    }

    public static String getTime(long timetemp, String pattern) {
        if (TextUtils.isEmpty(pattern)) {
            pattern = "yyyy.MM.dd HH:mm";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timetemp);
        SimpleDateFormat fmat = new SimpleDateFormat(pattern);
        String time = fmat.format(calendar.getTime());
        return time;
    }

    /**
     * 半角转全角
     *
     * @param input String.
     * @return 全角字符串.
     */
    public static String ToSBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);

            }
        }
        return new String(c);
    }

    /**
     * 日期格式化
     *
     * @param timestamp
     * @return
     */
    public static String format(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置格式
        String timeText = format.format(timestamp);
        return timeText;
    }

    /**
     * 这里是将获取到得编码进行16进制转换
     *
     * @param arr
     * @return
     */
    public static String byte2HexFormatted(byte[] arr) {
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

    /**
     * 这个是获取SHA1的方法
     *
     * @param context
     * @param packageName
     * @param tpye
     * @return
     */
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
            hexString = Utils.byte2HexFormatted(publicKey);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return hexString;
    }

}
