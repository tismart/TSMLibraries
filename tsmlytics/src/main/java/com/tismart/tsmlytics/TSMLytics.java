package com.tismart.tsmlytics;

import android.content.Context;

import com.tismart.tsmlytics.app.AppInfo;
import com.tismart.tsmlytics.device.DeviceInfo;
import com.tismart.tsmlytics.disk.DiskInfo;
import com.tismart.tsmlytics.enums.TSMLyticsEnum;
import com.tismart.tsmlytics.memory.MemoryInfo;
import com.tismart.tsmlytics.network.NetworkInfo;
import com.tismart.tsmlytics.os.OSInfo;
import com.tismart.tsmlytics.screen.ScreenInfo;

import java.util.HashMap;

/**
 * Created by luis.rios on 28/04/2015.
 *
 *
 */
public class TSMLytics {

    //ScreenSize, ScreenDensity, ScreenOrientation, MemoryRAMFree, MemoryRAMUsed, DiskHDFree, DiskHDUsed, DiskHDTotal, DiskSDFree, DiskSDUsed, DiskSDTotal, AppsOpen, AppsName, NetworkConnection, NetworkType, NetworkStrength, OSVersion, OSName, DeviceBatery, DeviceType, DeviceModel, DeviceID, DeviceRooted

    private final Context mContext;

    public TSMLytics(Context mContext) {
        this.mContext = mContext;
    }

    public HashMap<TSMLyticsEnum, String> getAll() {
        HashMap<TSMLyticsEnum, String> hashTSMLytics = new HashMap<>();
        try {
            hashTSMLytics.put(TSMLyticsEnum.AppsName, AppInfo.getAppsOpenName(mContext));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.AppsOpen, AppInfo.getAppsOpenNumber(mContext));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.DeviceBattery, DeviceInfo.getDeviceBattery(mContext));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.DeviceType, DeviceInfo.getDeviceType(mContext));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.DeviceModel, DeviceInfo.getDeviceModel());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            hashTSMLytics.put(TSMLyticsEnum.DeviceID, DeviceInfo.getDeviceID(mContext));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.DeviceRooted, DeviceInfo.getDeviceRooted());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.DiskHDFree, DiskInfo.getDiskHDFree());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            hashTSMLytics.put(TSMLyticsEnum.DiskHDUsed, DiskInfo.getDiskHDUsed());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.DiskHDTotal, DiskInfo.getDiskHDTotal());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.DiskSDFree, DiskInfo.getDiskSDFree());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.DiskSDUsed, DiskInfo.getDiskSDUsed());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.DiskSDTotal, DiskInfo.getDiskSDTotal());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.ScreenSize, ScreenInfo.getScreenSize(mContext));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.ScreenDensity, ScreenInfo.getScreenDensity(mContext));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.ScreenOrientation, ScreenInfo.getScreenOrientation(mContext));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.MemoryRAMFree, MemoryInfo.getMemoryRAMFree(mContext));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.MemoryRAMUsed, MemoryInfo.getMemoryRAMUsed(mContext));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.MemoryRAMTotal, MemoryInfo.getMemoryRAMTotal());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.NetworkConnection, NetworkInfo.getNetworkConnection(mContext));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.NetworkType, NetworkInfo.getNetworkType(mContext));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.NetworkStrength, NetworkInfo.getNetworkStrength(mContext));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.OSName, OSInfo.getOSName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.OSVersion, OSInfo.getOSVersion());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return hashTSMLytics;
    }
}
