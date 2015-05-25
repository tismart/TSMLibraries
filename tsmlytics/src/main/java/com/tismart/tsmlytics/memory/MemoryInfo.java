package com.tismart.tsmlytics.memory;

import android.app.ActivityManager;
import android.content.Context;

import com.tismart.tsmlytics.entities.Memory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by luis.rios on 29/04/2015.
 */
public class MemoryInfo {

    public static Memory getMemoryInfo(Context mContext) {
        Memory memory = new Memory();

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

            long totalMem;

            if (aLine == null || aLine.length() < 0)
                memory = null;
            else {
                aLine = aLine.replace("MemTotal:", "").replace(" ", "").toUpperCase().replace("KB", "");
                totalMem = Long.parseLong(aLine) * 1024L;
                memory.setFree(mi.availMem);
                memory.setTotal(totalMem);
                memory.setUsed(totalMem - mi.availMem);
            }
        } catch (Exception ex) {
            memory = null;
        }

        return memory;
    }

    @Deprecated
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

    @Deprecated
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

            long totalMem;

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

    @Deprecated
    public static String getMemoryRAMTotal() {
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

            long totalMem;

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
