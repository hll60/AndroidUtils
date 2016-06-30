package com.hll.android.demo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lin on 2016/6/30.
 */
public class HUtils {


    private static Context mContext;
    private static boolean DEBUG = false;

    /********************************初始化 相关**************************************/

    /**
     * 初始化 HUtils，
     * @param app Application 运行实例
     */
    public static void initialize(Application app){
        mContext = app.getApplicationContext();
    }

    /**
     * 初始化 HUtils，
     * @param app Application 运行实例
     */
    public static void destroy(Application app){
        mContext = app.getApplicationContext();
    }

    public static void setDebug(boolean debug){
        HUtils.DEBUG = debug;
    }


    /********************************日志 相关**************************************/

    /**
     * 打印日志信息
     * @param tag
     * @param info
     */
    public static void log(String tag, String info){
        if(DEBUG){
            Log.i(tag, info);
        }
    }

    public void showToast(String text){
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    public void showToastLong(String text){
        Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
    }


    /********************************屏幕 信息 相关**************************************/

    /**
     * 获取屏幕高度
     * @return width of screen (pixels)
     */
    public static int getScreenWidth(){
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度 （包含状态栏）
     * @return height of screen (pixels)
     */
    public static int getScreenHeight(){
        return mContext.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕状态栏高度
     * @return height of status bar
     */
    public static int getStatusBarHeight(){
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取导航栏高度
     * @return
     */
    public static int getNavigationBarHeight() {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /********************************dip、px 相关**************************************/

    /**
     * dp 转 px
     * @param dip
     * @return px
     */
    public static int dip2px(float dip){
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px 转 dip
     * @param px
     * @return dip
     */
    public static int px2dip(float px){
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }


    /********************************输入法 相关**************************************/

    /**
     * 显示输入法
     * @param activity
     * @param view
     * @param requestFocus
     */
    public static void showInputMethod(final Activity activity, final View view, boolean requestFocus){
        if (requestFocus) {
            view.requestFocus();
        }
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                    }
                });
            }
        }, 100);
    }

    /**
     * 隐藏输入法
     * @param activity
     */
    public static void hideInputMethod(final Activity activity){
        View v = activity.getCurrentFocus();
        if(v != null){
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /********************************网络 信息 相关**************************************/

    /**
     * 检查网络是否可用
     * @return
     */
    public static boolean isNetWorkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetInfo == null || !activeNetInfo.isAvailable()){
            return false;
        }else{
            return true;
        }
    }


    /********************************App 版本信息 相关**************************************/

    /**
     * 取APP版本号
     * @return
     */
    public static int getAppVersionCode(){
        try{
            PackageManager mPackageManager = mContext.getPackageManager();
            PackageInfo info = mPackageManager.getPackageInfo(mContext.getPackageName(),0);
            return info.versionCode;
        }catch(PackageManager.NameNotFoundException e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 取APP版本名
     * @return
     */
    public static String getAppVersionName(){
        try{
            PackageManager mPackageManager = mContext.getPackageManager();
            PackageInfo info = mPackageManager.getPackageInfo(mContext.getPackageName(),0);
            return info.versionName;
        }catch(PackageManager.NameNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }


    /********************************加密 相关**************************************/

    /**
     * MD5 加密
     * @param data
     * @return
     */
    public static String MD5(String data) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(data.getBytes());
            byte[] m = md5.digest();//加密
            return Base64.encodeToString(m, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    /********************************字符串操作 相关**************************************/

    /**
     * 字符串 判空
     * @param str
     * @return
     */
    public static boolean isEmpty(CharSequence str){
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }

    /**
     *  正则表达式匹配
     *  example "\\d{3}\\d{8}"
     * @param reg
     * @param str
     * @return
     */
    public static boolean match(String reg, CharSequence str){
        if(str == null){
            return false;
        }
        return str.toString().matches(reg);
    }

}
