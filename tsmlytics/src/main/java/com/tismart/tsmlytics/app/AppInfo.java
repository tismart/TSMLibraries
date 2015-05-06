package com.tismart.tsmlytics.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.List;

/**
 * Created by luis.rios on 29/04/2015.
 */
public class AppInfo {

    //ScreenSize, ScreenDensity, ScreenOrientation, MemoryRAMFree, MemoryRAMUsed, MemoryRAMTotal, DiskHDFree, DiskHDUsed, DiskHDTotal, DiskSDFree, DiskSDUsed, DiskSDTotal, AppsOpen, AppsName, NetworkConnection, NetworkType, NetworkStrength, OSVersion, OSName, DeviceBattery, DeviceType, DeviceModel, DeviceID, DeviceRooted

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