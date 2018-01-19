package com.xqx.phoneappinfoselect;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gray_dog3 on 16/3/3.
 * 扫描本地安装的应用,工具类
 */
public class ApkTool {

    public static void scanLocalInstallAppList(final Context context, final PackageManager packageManager, final AppInfoListener appInfoListener) {
        try {
            ThreadUtil.executeMore(new Runnable() {
                @Override
                public void run() {
                    final List<MyAppInfo> myAppInfos = new ArrayList<MyAppInfo>();
                    List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
                    for (int i = 0; i < packageInfos.size(); i++) {
                        final PackageInfo packageInfo = packageInfos.get(i);
                        //系统app
                        if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
                            getMainActivityName(context, packageInfo,packageManager, new AppInfoListener() {

                                @Override
                                public void getAppInfoListener(List<MyAppInfo> appInfoList) {
                                    myAppInfos.addAll(appInfoList);
                                }
                            });
                        }
                    }
                    if(appInfoListener != null){
                        appInfoListener.getAppInfoListener(myAppInfos);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**获取apk的启动类名*/
    public static void getMainActivityName(Context context,PackageInfo packageInfo,PackageManager packageManager,AppInfoListener appInfoListener){
        try {
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(packageInfo.packageName);
            PackageManager pManager = context.getApplicationContext().getPackageManager();
            List apps = pManager.queryIntentActivities(resolveIntent,0);
            final List<MyAppInfo> myAppInfos = new ArrayList<MyAppInfo>();
            if(apps != null && apps.size() > 0){
                for(int i=0 ;i<apps.size();i++){
                    ResolveInfo ri = (ResolveInfo) apps.get(i);
                    if (ri != null) {
                        String appName1 = (String) ri.activityInfo.loadLabel(packageManager);
                        String startappName = ri.activityInfo.packageName;
                        String className = ri.activityInfo.name;
                        Drawable drawable = ri.loadIcon(pManager);
                       // Log.e("====","####classNAme:"+packageInfo.applicationInfo.loadLabel(packageManager).toString()+packageInfo.packageName+":"+className);
                        if(!TextUtils.isEmpty(className) && drawable != null && !TextUtils.isEmpty(appName1)){
                            if(drawable instanceof BitmapDrawable){
                                //String appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                                Log.e("====","====appName："+appName1);
                                final Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                                boolean saveSuccess = BitmapUtil.getInstance().saveBitmap(bitmap, appName1 + "-" + packageInfo.packageName + "-" + className + ".png");
                                if(saveSuccess){
                                    final MyAppInfo myAppInfo = new MyAppInfo();
                                    myAppInfo.setAppName(appName1);
                                    myAppInfo.setPkgName(packageInfo.packageName);
                                    myAppInfo.setClassName(className);
                                    myAppInfo.setImage(drawable);
                                    myAppInfos.add(myAppInfo);
                                }
                            }
                        }
                    }
                }
            }
            if(appInfoListener != null){
                appInfoListener.getAppInfoListener(myAppInfos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private AppInfoListener appInfoListener;

    public void setAppInfoListener(AppInfoListener appInfoListener) {
        this.appInfoListener = appInfoListener;
    }

    public interface AppInfoListener{
        public void getAppInfoListener(List<MyAppInfo> appInfoList);
    }
}