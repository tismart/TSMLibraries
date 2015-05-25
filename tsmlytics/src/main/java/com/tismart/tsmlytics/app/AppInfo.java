package com.tismart.tsmlytics.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.tismart.tsmlytics.entities.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis.rios on 29/04/2015.
 */
public class AppInfo {

    public static ArrayList<App> getAppInfo(Context mContext) {
        ArrayList<App> mLstApp = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mLstApp = new ArrayList<>();
            App app = null;
            try {
                ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
                PackageManager packageManager = mContext.getPackageManager();

                for (int i = 0; i < tasks.size(); i++) {
                    try {
                        app = new App();
                        app.setName(packageManager.getApplicationLabel(packageManager.getApplicationInfo(tasks.get(i).baseActivity.getPackageName(), 0)).toString());
                        app.setIcon(packageManager.getApplicationIcon(packageManager.getApplicationInfo(tasks.get(i).baseActivity.getPackageName(), 0)));
                        app.setPackage(tasks.get(i).baseActivity.getPackageName());
                        mLstApp.add(app);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (Exception ex) {
            }
        } else
            mLstApp = null;

        return mLstApp;
    }

    @Deprecated
    public static String getAppsOpenNumber(Context mContext) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            try {
                ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
                int numberApps = activityManager.getRunningTasks(Integer.MAX_VALUE).size();
                return String.valueOf(numberApps);
            } catch (Exception ex) {
                return "";
            }
        } else
            return "";
    }

    @Deprecated
    public static String getAppsOpenName(Context mContext) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            try {
                ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
                PackageManager packageManager = mContext.getPackageManager();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < tasks.size(); i++) {
                    try {
                        sb.append(packageManager.getApplicationLabel(packageManager.getApplicationInfo(tasks.get(i).baseActivity.getPackageName(), 0))).append("\n");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                String sAppsName = sb.toString();
                if (sAppsName.length() > 0)
                    return sAppsName.substring(0, sAppsName.length() - 1);
                else
                    return sAppsName;
            } catch (Exception ex) {
                return "";
            }
        } else
            return "";
    }
}