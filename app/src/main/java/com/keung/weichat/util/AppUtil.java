package com.keung.weichat.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;

import com.keung.weichat.MainApplication;
import com.keung.weichat.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class AppUtil {
    private static final String KEY_EMUI_VERSION_CODE = "ro.build.version.emui";
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static Locale locale;

    /**
     * 获取本地软件版本号
     */
    public static int getVersionCode() {
        Context ctx = MainApplication.getContext();
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName()
                    , 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 获取本地软件版本号名称
     */
    public static String getVersionName() {
        String localVersion = "";
        Context ctx = MainApplication.getContext();
        try {
            PackageInfo packageInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName()
                    , 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    public static String getBigDecimal(double cash) {
        //        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00000000");
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.########");
        return decimalFormat.format(cash);
    }

    private static long lastClickTime;
    private final static long INTERVAL = 500;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < INTERVAL) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * @param timeInterval 时间间隔
     * @return
     */
    public static boolean isFastDoubleClick(Long timeInterval) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < timeInterval) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 获取本地手机的IMEI信息
     *
     * @return IMEI号码
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI() {
        try {
            // 获取IMEI
            TelephonyManager phoneMgr = (TelephonyManager) MainApplication.getContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);
            return phoneMgr.getDeviceId();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getImei() {
        TelephonyManager manager =
                (TelephonyManager) MainApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Method method = manager.getClass().getMethod("getImei", int.class);
            String imei1 = (String) method.invoke(manager, 0);
            String imei2 = (String) method.invoke(manager, 1);
            if (TextUtils.isEmpty(imei2)) {
                return imei1;
            }
            if (!TextUtils.isEmpty(imei1)) {
                //因为手机卡插在不同位置，获取到的imei1和imei2值会交换，所以取它们的最小值,保证拿到的imei都是同一个
                String imei = "";
                if (imei1.compareTo(imei2) <= 0) {
                    imei = imei1;
                } else {
                    imei = imei2;
                }
                return imei;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // 获取电话的MAC
    public static String getLocalPhoneMac() {
        Context context = MainApplication.getContext();
        if (context == null) {
            return null;
        }
        String macAddress = null;
        try {
            WifiManager wifiMgr =
                    (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
            if (null != info) {
                macAddress = info.getMacAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return macAddress;
    }

    public static String getSerialNumber() {
        if (Build.SERIAL.equals("unknown")) {
            return Settings.System.getString(MainApplication.getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }
        return Build.SERIAL;
    }

    // 是否是小米手机/系统
    public static boolean isXiaomi() {
        return "Xiaomi".equalsIgnoreCase(Build.MANUFACTURER) || isMIUI();
    }

    // 设置小米状态栏
    public static void setXiaomiStatusBar(Window window, boolean isLightStatusBar) {
        Class<? extends Window> clazz = window.getClass();
        try {
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(window, isLightStatusBar ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getMannufacturer() {
        return Build.MANUFACTURER + " " + Build.BOARD;
    }

    public static String getSystemVersion() {
        return "Android " + Build.VERSION.RELEASE;
    }

    public static void test() {
        String phoneInfo = "Product: " + Build.PRODUCT + "\n";
        phoneInfo += ", CPU_ABI: " + Build.CPU_ABI + "\n";
        phoneInfo += ", TAGS: " + Build.TAGS + "\n";
        phoneInfo += ", VERSION_CODES.BASE: " + Build.VERSION_CODES.BASE + "\n";
        phoneInfo += ", MODEL: " + Build.MODEL + "\n";
        phoneInfo += ", SDK: " + Build.VERSION.SDK + "\n";
        phoneInfo += ", VERSION.RELEASE: " + Build.VERSION.RELEASE + "\n";
        phoneInfo += ", DEVICE: " + Build.DEVICE + "\n";
        phoneInfo += ", DISPLAY: " + Build.DISPLAY + "\n";
        phoneInfo += ", BRAND: " + Build.BRAND + "\n";
        phoneInfo += ", BOARD: " + Build.BOARD + "\n";
        phoneInfo += ", FINGERPRINT: " + Build.FINGERPRINT + "\n";
        phoneInfo += ", ID: " + Build.ID + "\n";
        phoneInfo += ", MANUFACTURER: " + Build.MANUFACTURER + "\n";
        phoneInfo += ", USER: " + Build.USER + "\n";
        phoneInfo += ", BOOTLOADER: " + Build.BOOTLOADER + "\n";
        phoneInfo += ", HARDWARE: " + Build.HARDWARE + "\n";
        phoneInfo += ", INCREMENTAL: " + Build.VERSION.INCREMENTAL + "\n";
        phoneInfo += ", CODENAME: " + Build.VERSION.CODENAME + "\n";
        phoneInfo += ", SDK: " + Build.VERSION.SDK_INT + "\n";

        LogUtils.d(phoneInfo);
    }

    /* 
     * 判断服务是否启动,context上下文对象 ，className服务的name 
     */
    public static boolean isServiceRunning(String className) {

        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) MainApplication.getContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(30);

        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    /**
     *  * 拨打电话（直接拨打电话）
     *  * @param phoneNum 电话号码
     *  
     */
    @SuppressLint("MissingPermission")
    public static void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        MainApplication.getContext().startActivity(intent);
    }

    /**
     *  * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *  *
     *  * @param phoneNum 电话号码
     *  
     */
    public static void dialPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        MainApplication.getContext().startActivity(intent);
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static void FlymeSetStatusBarLightMode(Window window, boolean dark) {
        MeizuStatusbarUtils.setStatusBarDarkIcon(window, dark);
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param activity 需要设置的窗口
     * @param dark     是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static void FlymeSetStatusBarLightMode(Activity activity, boolean dark) {
        MeizuStatusbarUtils.setStatusBarDarkIcon(activity, dark);
    }

    /**
     * 需要MIUIV6以上
     *
     * @param activity
     * @param dark     是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Activity activity, boolean dark) {
        boolean result = false;
        Window window = activity.getWindow();
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (dark) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View
                                .SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View
                                .SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View
                                .SYSTEM_UI_FLAG_VISIBLE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // 是否是魅族系统/手机
    public static boolean isMeizu() {
        return "meizu".equalsIgnoreCase(Build.MANUFACTURER) || isFlyme();
    }

    public static String getLanguage() {
        Context context = MainApplication.getContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }
        return locale.getLanguage();
    }

    public static boolean isFlyme() {
        try {
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }

    /**
     * 华为系统
     *
     * @return
     */
    public static boolean isEMUI() {
        return isPropertiesExist(KEY_EMUI_VERSION_CODE);
    }

    /**
     * 小米系统
     *
     * @return
     */
    public static boolean isMIUI() {
        return isPropertiesExist(KEY_MIUI_VERSION_CODE, KEY_MIUI_VERSION_NAME,
                KEY_MIUI_INTERNAL_STORAGE);
    }

    private static boolean isPropertiesExist(String... keys) {
        if (keys == null || keys.length == 0) {
            return false;
        }
        try {
            BuildProperties properties = BuildProperties.newInstance();
            for (String key : keys) {
                String value = properties.getProperty(key);
                if (value != null)
                    return true;
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public static void exeCommand(String command) {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process proc = runtime.exec(command);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final class BuildProperties {

        private final Properties properties;

        private BuildProperties() throws IOException {
            properties = new Properties();
            // 读取系统配置信息build.prop类
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build" +
                    ".prop")));
        }

        public boolean containsKey(final Object key) {
            return properties.containsKey(key);
        }

        public boolean containsValue(final Object value) {
            return properties.containsValue(value);
        }

        public Set<Map.Entry<Object, Object>> entrySet() {
            return properties.entrySet();
        }

        public String getProperty(final String name) {
            return properties.getProperty(name);
        }

        public String getProperty(final String name, final String defaultValue) {
            return properties.getProperty(name, defaultValue);
        }

        public boolean isEmpty() {
            return properties.isEmpty();
        }

        public Enumeration<Object> keys() {
            return properties.keys();
        }

        public Set<Object> keySet() {
            return properties.keySet();
        }

        public int size() {
            return properties.size();
        }

        public Collection<Object> values() {
            return properties.values();
        }

        public static BuildProperties newInstance() throws IOException {
            return new BuildProperties();
        }
    }
}
