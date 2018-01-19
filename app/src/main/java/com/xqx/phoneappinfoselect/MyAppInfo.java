package com.xqx.phoneappinfoselect;

import android.graphics.drawable.Drawable;

/**
 * Created by xuqunxing on 2017/11/6.
 */
public class MyAppInfo {
    private Drawable image;
    private String appName;
    private String pkgName;
    private String className;

    public MyAppInfo(Drawable image, String appName,String pkgName) {
        this.image = image;
        this.appName = appName;
        this.pkgName = pkgName;
        this.className = className;
    }
    public MyAppInfo() {

    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}