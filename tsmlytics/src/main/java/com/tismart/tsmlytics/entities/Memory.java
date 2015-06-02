package com.tismart.tsmlytics.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luis.rios on 21/05/2015.
 */
@SuppressWarnings("WeakerAccess")
public class Memory implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Memory> CREATOR = new Parcelable.Creator<Memory>() {
        @Override
        public Memory createFromParcel(Parcel in) {
            return new Memory(in);
        }

        @Override
        public Memory[] newArray(int size) {
            return new Memory[size];
        }
    };
    private float Free;
    private float Used;
    private float Total;

    public Memory() {
    }

    protected Memory(Parcel in) {
        Free = in.readFloat();
        Used = in.readFloat();
        Total = in.readFloat();
    }

    public float getFree() {
        return Free;
    }

    public void setFree(float free) {
        Free = free;
    }

    public float getUsed() {
        return Used;
    }

    public void setUsed(float used) {
        Used = used;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float total) {
        Total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(Free);
        dest.writeFloat(Used);
        dest.writeFloat(Total);
    }
}
