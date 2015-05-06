package com.tismart.tsmlytics.memory;

import android.app.ActivityManager;
import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by luis.rios on 29/04/2015.
 */
public class MemoryInfo {

    //ScreenSize, ScreenDensity, ScreenOrientation, MemoryRAMFree, MemoryRAMUsed, MemoryRAMTotal, DiskHDFree, DiskHDUsed, DiskHDTotal, DiskSDFree, DiskSDUsed, DiskSDTotal, AppsOpen, AppsName, NetworkConnection, NetworkType, NetworkStrength, OSVersion, OSName, DeviceBattery, DeviceType, DeviceModel, DeviceID, DeviceRooted

    public static String getMemoryRAMFree(Context mContext) {
        try {
            android.app.ActivityManager.MemoryInfo mi = new android.app.ActivityManager.MemoryInfo();
            ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.getMemoryInfo(mi);
            return (mi.availMem / 1048576L) + "MB";
        } catch (Exception ex) {
            return "";
        }
    }

    public static String getMemoryRAMUsed(Context mContext) {
        try {
            android.app.ActivityManager.MemoryInfo mi = new android.app.ActivityManager.MemoryInfo();
            ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.getMemoryInfo(mi);

            File f = new File("/proc/meminfo");
            String aLine = null;
            if (f.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(f));
                while ((aLine = br.readLine()) != null) {
                    if (aLine.contains("MemTotal"))
                        break;
                }
                br.close();
            }

            long totalMem = 0L;

            if (aLine == null || aLine.length() < 0)
                return "";
            else {
                aLine = aLine.replace("MemTotal:", "").replace(" ", "").toUpperCase().replace("KB", "");
                totalMem = Long.parseLong(aLine) / 1024L;
            }

            return (totalMem - (mi.availMem / 1048576L)) + "MB";
        } catch (Exception ex) {
            return "";
        }
    }

    public static String getMemoryRAMTotal(Context mContext) {
        try {
            File f = new File("/proc/meminfo");
            String aLine = null;
            if (f.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(f));
                while ((aLine = br.readLine()) != null) {
                    if (aLine.contains("MemTotal"))
                        break;
                }
                br.close();
            }

            long totalMem = 0L;

            if (aLine == null || aLine.length() < 0)
                return "";
            else {
                aLine = aLine.replace("MemTotal:", "").replace(" ", "").toUpperCase().replace("KB", "");
                totalMem = Long.parseLong(aLine) / 1024L;
            }

            return totalMem + "MB";
        } catch (Exception ex) {
            return "";
        }
    }
}
