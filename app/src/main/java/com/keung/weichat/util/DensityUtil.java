package com.keung.weichat.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import com.keung.weichat.MainApplication;

/**
 * Created by Aspsine on 2015/11/5.
 */
public class DensityUtil {
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * dp转换成px
     */
    public static int dp2px(float dpValue) {
        float scale = MainApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转换成dp
     */
    public static int px2dp(float pxValue) {
        float scale = MainApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转换成px
     */
    public static int sp2px(float spValue) {
        float fontScale = MainApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * sp转换成px
     */
    public static int sp2dp(float spValue) {
        int pxValue = sp2px(spValue);
        float scale = MainApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * px转换成sp
     */
    public static int px2sp(float pxValue) {
        float fontScale = MainApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = MainApplication.getContext().getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = MainApplication.getContext().getResources().getDimensionPixelSize(resourceId);
            //LogUtils.d("getStatusBarHeight : " + result);
        }
        return result;
    }
}
