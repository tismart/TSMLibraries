package com.tismart.tsmlytics.disk;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by luis.rios on 29/04/2015.
 *
 *
 */
public class DiskInfo {

    //ScreenSize, ScreenDensity, ScreenOrientation, MemoryRAMFree, MemoryRAMUsed, MemoryRAMTotal, DiskHDFree, DiskHDUsed, DiskHDTotal, DiskSDFree, DiskSDUsed, DiskSDTotal, AppsOpen, AppsName, NetworkConnection, NetworkType, NetworkStrength, OSVersion, OSName, DeviceBattery, DeviceType, DeviceModel, DeviceID, DeviceRooted

    @SuppressWarnings("deprecation")
    public static String getDiskHDFree() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize;
        long availableBlocks;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSize();
            availableBlocks = stat.getAvailableBlocks();
        } else {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
        }
        return formateDisk(blockSize * availableBlocks);
    }

    @SuppressWarnings("deprecation")
    public static String getDiskHDUsed() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize;
        long availableBlocks;
        long totalBlocks;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSize();
            availableBlocks = stat.getAvailableBlocks();
            totalBlocks = stat.getBlockCount();
        } else {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
            totalBlocks = stat.getBlockCountLong();
        }
        return formateDisk((blockSize * totalBlocks) - (blockSize * availableBlocks));
    }

    @SuppressWarnings("deprecation")
    public static String getDiskHDTotal() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize;
        long totalBlocks;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSize();
            totalBlocks = stat.getBlockCount();
        } else {
            blockSize = stat.getBlockSizeLong();
            totalBlocks = stat.getBlockCountLong();
        }
        return formateDisk(blockSize * totalBlocks);
    }

    @SuppressWarnings("deprecation")
    public static String getDiskSDFree() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize;
            long availableBlocks;

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = stat.getBlockSize();
                availableBlocks = stat.getAvailableBlocks();
            } else {
                blockSize = stat.getBlockSizeLong();
                availableBlocks = stat.getAvailableBlocksLong();
            }
            return formateDisk(blockSize * availableBlocks);
        } else
            return "";
    }

    @SuppressWarnings("deprecation")
    public static String getDiskSDUsed() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize;
            long availableBlocks;
            long totalBlocks;

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = stat.getBlockSize();
                availableBlocks = stat.getAvailableBlocks();
                totalBlocks = stat.getBlockCount();
            } else {
                blockSize = stat.getBlockSizeLong();
                availableBlocks = stat.getAvailableBlocksLong();
                totalBlocks = stat.getBlockCountLong();
            }
            return formateDisk((blockSize * totalBlocks) - (blockSize * availableBlocks));
        } else
            return "";
    }

    @SuppressWarnings("deprecation")
    public static String getDiskSDTotal() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize;
            long totalBlocks;

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = stat.getBlockSize();
                totalBlocks = stat.getBlockCount();
            } else {
                blockSize = stat.getBlockSizeLong();
                totalBlocks = stat.getBlockCountLong();
            }
            return formateDisk(blockSize * totalBlocks);
        } else
            return "";
    }

    private static String formateDisk(long lDisk) {
        try {
            return String.valueOf(lDisk / 1048576L) + "MB";
        } catch (Exception ex) {
            return "";
        }
    }

    private static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    }
}
