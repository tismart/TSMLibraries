package com.tismart.myapplication.entities;

/**
 * Created by luis.burgos on 07/05/2015.
 *
 */
@SuppressWarnings("unused")
@com.tismart.tsmlibrary.database.annotations.Entidad
public class Entidad {
    @Elemento(columnName = "Id", elementType = TipoElemento.LONG, isPrimary = true)
    private long id;

    @Elemento(columnName = "AppsOpen", elementType = TipoElemento.INTEGER)
    private int appsOpen;

    @Elemento(columnName = "AppsName")
    private String appsName;

    @Elemento(columnName = "DeviceBattery", elementType = TipoElemento.DOUBLE)
    private double deviceBattery;

    @Elemento(columnName = "DeviceType", elementType = TipoElemento.BOOLEAN)
    private boolean deviceType;

    @Elemento(columnName = "DeviceModel")
    private String deviceModel;

    @Elemento(columnName = "DeviceID")
    private String deviceID;

    @Elemento(columnName = "DeviceRooted", elementType = TipoElemento.BOOLEAN)
    private boolean deviceRooted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAppsOpen() {
        return appsOpen;
    }

    public void setAppsOpen(int appsOpen) {
        this.appsOpen = appsOpen;
    }

    public String getAppsName() {
        return appsName;
    }

    public void setAppsName(String appsName) {
        this.appsName = appsName;
    }

    public double getDeviceBattery() {
        return deviceBattery;
    }

    public void setDeviceBattery(double deviceBattery) {
        this.deviceBattery = deviceBattery;
    }

    public boolean isDeviceType() {
        return deviceType;
    }

    public void setDeviceType(boolean deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public boolean isDeviceRooted() {
        return deviceRooted;
    }

    public void setDeviceRooted(boolean deviceRooted) {
        this.deviceRooted = deviceRooted;
    }
}
