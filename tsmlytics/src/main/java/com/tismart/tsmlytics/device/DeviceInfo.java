package com.tismart.tsmlytics.device;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.Settings;

import com.tismart.tsmlytics.R;
import com.tismart.tsmlytics.entities.Device;
import com.tismart.tsmlytics.utils.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * Created by luis.rios on 29/04/2015.
 */
public class DeviceInfo {

    /**
     * Obtiene los estados de algunos elmentos del dispositivo. Este método siempre devuelve un dato diferente a null
     * Device.Baterry  = Porcentaje de batería en float
     * Device.IsTablet = True or False en boolean
     * Device.Model = Modelo del dispositivo en String
     * Device.ID = ID del dispositivo Android.Secure.ID en String
     * Device.IsRooted = Si el dispositivo ha sido rooteado en boolean
     * @param mContext contexto de la aplicación
     * @return
     */
    public static Device getDeviceInfo(Context mContext) {
        Device device = new Device();

        try {
            Intent batteryIntent = mContext.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            int level = batteryIntent != null ? batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : 0;
            int scale = batteryIntent != null ? batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : 0;

            if (level == -1 || scale == -1) {
                device.setBattery(0.0f);
            } else
                device.setBattery(((float) level / (float) scale) * 100.0f);
        } catch (Exception ex) {
            device.setBattery(0.0f);
        }

        try {
            device.setIsTablet(mContext.getResources().getBoolean(R.bool.isTablet));
        } catch (Exception ex) {
            device.setIsTablet(false);
        }

        try {
            String sModel;
            if (Build.MODEL.startsWith(Build.MANUFACTURER)) {
                sModel = Build.MODEL + " (" + Build.BOARD + ")";
            } else {
                sModel = Build.MANUFACTURER + " " + Build.MODEL + " (" + Build.BOARD + ")";
            }
            device.setModel(StringUtils.capitalize(sModel));
        } catch (Exception ex) {
            device.setModel(null);
        }

        try {
            device.setID(Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID));
        } catch (Exception ex) {
            device.setID(null);
        }

        try {
            device.setIsRooted(checkRootMethod1() || checkRootMethod2() || checkRootMethod3() || checkRootMethod4());
        } catch (Exception ex) {
            device.setIsRooted(false);
        }

        return device;
    }

    @Deprecated
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

    @Deprecated
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

    @Deprecated
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

    @Deprecated
    public static String getDeviceID(Context mContext) {
        try {
            return Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception ex) {
            return "";
        }
    }

    @Deprecated
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
