package com.tismart.tsmlytics.disk;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.tismart.tsmlytics.entities.Disk;

import java.io.File;

/**
 * Created by luis.rios on 29/04/2015.
 */
public class DiskInfo {

    public static Disk getDiskInfo() {
        Disk disk = null;
        long blockSizeInternal = 0L, availableBlocksInternal = 0L, totalBlocksInternal = 0L, blockSizeExternal = 0L, availableBlocksExternal = 0L, totalBlocksExternal = 0L;

        try {
            disk = new Disk();
            StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSizeInternal = stat.getBlockSize();
                availableBlocksInternal = stat.getAvailableBlocks();
                totalBlocksInternal = stat.getBlockCount();
            } else {
                blockSizeInternal = stat.getBlockSizeLong();
                availableBlocksInternal = stat.getAvailableBlocksLong();
                totalBlocksInternal = stat.getBlockCountLong();
            }

            if (externalMemoryAvailable()) {
                stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    blockSizeExternal = stat.getBlockSize();
                    availableBlocksExternal = stat.getAvailableBlocks();
                    totalBlocksExternal = stat.getBlockCount();
                } else {
                    blockSizeExternal = stat.getBlockSizeLong();
                    availableBlocksExternal = stat.getAvailableBlocksLong();
                    totalBlocksExternal = stat.getBlockCountLong();
                }
            } else {
                availableBlocksExternal = 0L;
                blockSizeExternal = 0L;
                totalBlocksExternal = 0L;
            }

            disk.setInternalFree(availableBlocksInternal * blockSizeInternal);
            disk.setInternalUsed((totalBlocksInternal - availableBlocksInternal) * blockSizeInternal);
            disk.setInternalTotal(totalBlocksInternal * blockSizeInternal);
            disk.setExternalFree(availableBlocksExternal * blockSizeExternal);
            disk.setExternalUsed((totalBlocksExternal - availableBlocksExternal) * blockSizeExternal);
            disk.setExternalTotal(totalBlocksExternal * blockSizeExternal);

        } catch (Exception ex) {
            disk = null;
        }

        return disk;
    }

    @Deprecated
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

    @Deprecated
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

    @Deprecated
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

    @Deprecated
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

    @Deprecated
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

    @Deprecated
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
