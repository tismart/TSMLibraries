package com.tismart.tsmlytics.device;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.Settings;

import com.tismart.tsmlytics.R;
import com.tismart.tsmlytics.utils.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * Created by luis.rios on 29/04/2015.
 *
 */
public class DeviceInfo {

    //ScreenSize, ScreenDensity, ScreenOrientation, MemoryRAMFree, MemoryRAMUsed, MemoryRAMTotal, DiskHDFree, DiskHDUsed, DiskHDTotal, DiskSDFree, DiskSDUsed, DiskSDTotal, AppsOpen, AppsName, NetworkConnection, NetworkType, NetworkStrength, OSVersion, OSName, DeviceBattery, DeviceType, DeviceModel, DeviceID, DeviceRooted

    public static String getDeviceBattery(Context mContext) {
        try {
            Intent batteryIntent = mContext.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            int level = batteryIntent != null ? batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : 0;
            int scale = batteryIntent != null ? batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : 0;

            // Error checking that probably isn't needed but I added just in case.
            if (level == -1 || scale == -1) {
                return "";
            }
            return String.valueOf(((float) level / (float) scale) * 100.0f);
        } catch (Exception ex) {
            return "";
        }

    }

    public static String getDeviceType(Context mContext) {
        try {
            if (mContext.getResources().getBoolean(R.bool.isTablet))
                return "true";
            else
                return "false";
        } catch (Exception ex) {
            return "";
        }
    }

    public static String getDeviceModel() {
        try {
            String sModel;
            if (Build.MODEL.startsWith(Build.MANUFACTURER)) {
                sModel = Build.MODEL + " (" + Build.BOARD + ")";
            } else {
                sModel = Build.MANUFACTURER + " " + Build.MODEL + " (" + Build.BOARD + ")";
            }
            return StringUtils.capitalize(sModel);
        } catch (Exception ex) {
            return "";
        }
    }

    public static String getDeviceID(Context mContext) {
        try {
            return Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception ex) {
            return "";
        }
    }

    public static String getDeviceRooted() {
        return (checkRootMethod1() || checkRootMethod2() || checkRootMethod3() || checkRootMethod4()) + "";
    }

    private static boolean checkRootMethod1() {
        try {
            String buildTags = android.os.Build.TAGS;
            return buildTags != null && buildTags.contains("test-keys");
        } catch (Exception ex) {
            return false;
        }
    }

    private static boolean checkRootMethod2() {
        try {
            return new File("/system/app/Superuser.apk").exists();
        } catch (Exception ex) {
            return false;
        }
    }

    private static boolean checkRootMethod3() {
        try {
            String[] paths = {"/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                    "/system/bin/failsafe/su", "/data/local/su"};
            for (String path : paths) {
                if (new File(path).exists()) return true;
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    private static boolean checkRootMethod4() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return in.readLine() != null;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }
}
