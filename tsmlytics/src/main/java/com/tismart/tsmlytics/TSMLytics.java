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
@SuppressWarnings("deprecation")
public class TSMLytics {

    private final Context mContext;

    public TSMLytics(Context mContext) {
        this.mContext = mContext;
    }

    @Deprecated
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

    /**
     * <p>APP INFO</p>
     * <p>Obtiene las aplicaciones abiertas actuales en el dispositivo.</p>
     * <p>Si la versión de Android es mayor o igual al API 21 este método devolverá null</p>
     * <p>Si la versión de Android es menor al API 21 este método te devolverá una lista del tipo App</p>
     * <p>App.Name nombre de la aplicación en String</p>
     * <p>App.Icon ícono de la aplicación en Drawable</p>
     * <p>App.Package nombre del paquete de la aplicación en String</p>
     * <p> </p>
     * <p>DEVICE INFO</p>
     * <p>Obtiene los estados de algunos elmentos del dispositivo. Este método siempre devuelve un dato diferente a null</p>
     * <p>Device.Baterry  = Porcentaje de batería en float</p>
     * <p>Device.IsTablet = True or False en boolean</p>
     * <p>Device.Model = Modelo del dispositivo en String</p>
     * <p>Device.ID = ID del dispositivo Android.Secure.ID en String</p>
     * <p>Device.IsRooted = Si el dispositivo ha sido rooteado en boolean</p>
     * <p> </p>
     * <p>DISK INFO</p>
     * <p>Obtiene la información del disco interno del dispositivo. Si hubiera una SD-Card conectada también obtiene la información de ella.</p>
     * <p>Si hay algún error el método devolverá null</p>
     * <p>Disk.InternalFree = Disco interno libre en bytes long</p>
     * <p>Disk.InternalUsed = Disco interno usado en bytes long</p>
     * <p>Disk.InternalTotal = Disco interno total en bytes long</p>
     * <p>Disk.ExternalFree = Disco externo libre en bytes long</p>
     * <p>Disk.ExternalUsed = Disco externo usado en bytes long</p>
     * <p>Disk.ExternalTotal = Disco externo total en bytes long</p>
     * <p> </p>
     * <p>MEMORY INFO</p>
     * <p>Obtiene la información de la memoria del dispositivo.</p>
     * <p>Si hay algún error el método devolverá null</p>
     * <p>Memory.Free = Memoria libre en bytes long</p>
     * <p>Memory.Used = Memoria usado en bytes long</p>
     * <p>Memory.Total = Memoria total en bytes long</p>
     * <p> </p>
     * <p>NETWORK INFO</p>
     * <p>Obtiene la información de la red del dispositivo.</p>
     * <p>Si hay algún error el método devolverá null</p>
     * <p>Network.Connection = Modo de conexión Wi-Fi, Mobile, Ethernet, Bluetooth en String</p>
     * <p>Network.Type = Tipo de conexión Wi-Fi, 3G, Edge entre otros en String</p>
     * <p>Network.Streght = Intensidad en la conexión. Siempre es un número negativo en String</p>
     * <p> </p>
     * <p>OS INFO</p>
     * <p>Obtiene la información del dispositivo. Este método siempre devuelve un dato diferente a null</p>
     * <p>OS.Name = Nombre del sistema operativo en String</p>
     * <p>OS.Version = Versión del sistema operativo en String</p>
     * <p> </p>
     * <p>SCREEN INFO</p>
     * <p>Obtiene la información de la pantalla del dispositivo.</p>
     * <p>Si hay algún error el método devolverá null</p>
     * <p>Screen.Density = Densidad de la pantalla en int</p>
     * <p>Screen.Size = Tamaño de la pantalla en float</p>
     * <p>Screen.Orientation = Orientación de la pantalla en String</p>
     * @return
     */
    public HashMap<TSMLyticsEnum, Object> getAllWithEntities() {
        HashMap<TSMLyticsEnum, Object> hashTSMLytics = new HashMap<>();

        try {
            hashTSMLytics.put(TSMLyticsEnum.AppInfo, AppInfo.getAppInfo(mContext));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.DeviceInfo, DeviceInfo.getDeviceInfo(mContext));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.DiskInfo, DiskInfo.getDiskInfo());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.MemoryInfo, MemoryInfo.getMemoryInfo(mContext));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.NetworkInfo, NetworkInfo.getNetworkInfo(mContext));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.OSInfo, OSInfo.getOSInfo());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            hashTSMLytics.put(TSMLyticsEnum.ScreenInfo, ScreenInfo.getScreenInfo(mContext));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return hashTSMLytics;
    }
}
