package com.yumu.appinfo.utils;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ansen
 * @create time 2018/3/16
 */
public class StringUtil {
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

}
