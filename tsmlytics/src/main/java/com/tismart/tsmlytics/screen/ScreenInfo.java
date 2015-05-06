package com.tismart.tsmlytics.screen;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by luis.rios on 29/04/2015.
 */
public class ScreenInfo {

    //ScreenSize, ScreenDensity, ScreenOrientation, MemoryRAMFree, MemoryRAMUsed, MemoryRAMTotal, DiskHDFree, DiskHDUsed, DiskHDTotal, DiskSDFree, DiskSDUsed, DiskSDTotal, AppsOpen, AppsName, NetworkConnection, NetworkType, NetworkStrength, OSVersion, OSName, DeviceBattery, DeviceType, DeviceModel, DeviceID, DeviceRooted

    public static String getScreenSize(Context mContext) {
        try {
            Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getMetrics(displayMetrics);

            // since SDK_INT = 1;
            int mWidthPixels = displayMetrics.widthPixels;
            int mHeightPixels = displayMetrics.heightPixels;

            // includes window decorations (statusbar bar/menu bar)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                try {
                    mWidthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                    mHeightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
                } catch (Exception ignored) {
                }
            }

            // includes window decorations (statusbar bar/menu bar)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                try {
                    Point realSize = new Point();
                    Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
                    mWidthPixels = realSize.x;
                    mHeightPixels = realSize.y;
                } catch (Exception ignored) {
                }
            }
            int density = Integer.parseInt(getScreenDensity(mContext));
            return String.valueOf(Math.sqrt(Math.pow(mWidthPixels / density, 2) + Math.pow(mHeightPixels / density, 2)));
        } catch (Exception ex) {
            return "";
        }
    }

    public static String getScreenDensity(Context mContext) {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        return metrics.densityDpi + "";
    }

    public static String getScreenOrientation(Context mContext) {
        switch (mContext.getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                return "Landscape";
            case Configuration.ORIENTATION_PORTRAIT:
                return "Portrait";
            default:
                return "";
        }
    }
}