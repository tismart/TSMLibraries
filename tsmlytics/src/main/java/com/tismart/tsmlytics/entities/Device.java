package com.tismart.tsmlytics.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luis.rios on 21/05/2015.
 */
@SuppressWarnings("WeakerAccess")
public class Device implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() {
        @Override
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };
    private float Battery;
    private boolean IsTablet;
    private String Model;
    private String ID;
    private boolean IsRooted;

    public Device() {
    }

    protected Device(Parcel in) {
        Battery = in.readFloat();
        IsTablet = in.readByte() != 0x00;
        Model = in.readString();
        ID = in.readString();
        IsRooted = in.readByte() != 0x00;
    }

    public float getBattery() {
        return Battery;
    }

    public void setBattery(float battery) {
        Battery = battery;
    }

    public boolean isTablet() {
        return IsTablet;
    }

    public void setIsTablet(boolean isTablet) {
        IsTablet = isTablet;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean isRooted() {
        return IsRooted;
    }

    public void setIsRooted(boolean isRooted) {
        IsRooted = isRooted;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(Battery);
        dest.writeByte((byte) (IsTablet ? 0x01 : 0x00));
        dest.writeString(Model);
        dest.writeString(ID);
        dest.writeByte((byte) (IsRooted ? 0x01 : 0x00));
    }
}