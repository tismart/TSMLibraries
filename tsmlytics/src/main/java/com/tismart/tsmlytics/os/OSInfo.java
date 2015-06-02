package com.tismart.tsmlytics.os;

import android.os.Build;

import com.tismart.tsmlytics.entities.OS;

import java.lang.reflect.Field;

/**
 * Created by luis.rios on 29/04/2015.
 */
@SuppressWarnings("SameReturnValue")
public class OSInfo {

    public static OS getOSInfo() {
        OS os = new OS();
        try {
            os.setVersion(Build.VERSION.RELEASE);
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
            os.setName(fieldName);
        } catch (Exception ex) {
            os = null;
        }

        return os;
    }

    @Deprecated
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    @Deprecated
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
