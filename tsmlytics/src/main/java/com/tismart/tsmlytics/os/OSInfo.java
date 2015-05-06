package com.tismart.tsmlytics.os;

import android.os.Build;

import java.lang.reflect.Field;

/**
 * Created by luis.rios on 29/04/2015.
 *
 *
 */
public class OSInfo {

    //ScreenSize, ScreenDensity, ScreenOrientation, MemoryRAMFree, MemoryRAMUsed, MemoryRAMTotal, DiskHDFree, DiskHDUsed, DiskHDTotal, DiskSDFree, DiskSDUsed, DiskSDTotal, AppsOpen, AppsName, NetworkConnection, NetworkType, NetworkStrength, OSVersion, OSName, DeviceBattery, DeviceType, DeviceModel, DeviceID, DeviceRooted

    @SuppressWarnings("SameReturnValue")
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getOSName() {
        Field[] fields = Build.VERSION_CODES.class.getFields();
        String fieldName = "";
        for (Field field : fields) {
            fieldName = field.getName();
            int fieldValue = -1;
            try {
                fieldValue = field.getInt(new Object());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (fieldValue == Build.VERSION.SDK_INT) {
                fieldName = fieldName.replace("_", " ");
                break;
            }
        }
        return fieldName;
    }
}
