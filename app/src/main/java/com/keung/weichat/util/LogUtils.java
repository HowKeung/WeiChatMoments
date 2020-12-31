package com.keung.weichat.util;


import android.util.Log;

/**
 * Created by lion on 2016/6/15 0015.
 */
public class LogUtils {

    static String className;
    static String methodName;
    static int lineNumber;

    private static String createLog(String log) {
        return className + "【" + methodName + ":" + lineNumber + "】" + log;
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        try {
            className = sElements[1].getFileName();
            methodName = sElements[1].getMethodName();
            lineNumber = sElements[1].getLineNumber();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void v(String args) {
        getMethodNames(new Throwable().getStackTrace());
        Log.v(className,createLog(args));
    }

    public static void d(String args) {
        getMethodNames(new Throwable().getStackTrace());
        Log.d(className,createLog(args));
    }

    public static void i(String args) {
        getMethodNames(new Throwable().getStackTrace());
        Log.i(className,createLog(args));
    }

    public static void e(String args) {
        getMethodNames(new Throwable().getStackTrace());
        Log.e(className,createLog(args));
    }

    public static void w(String args) {
        getMethodNames(new Throwable().getStackTrace());
        Log.w(className,createLog(args));
    }
}
