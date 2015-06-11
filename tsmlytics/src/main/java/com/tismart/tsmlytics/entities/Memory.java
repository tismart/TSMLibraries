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
    private long Free;
    private long Used;
    private long Total;

    public Memory() {
    }

    protected Memory(Parcel in) {
        Free = in.readLong();
        Used = in.readLong();
        Total = in.readLong();
    }

    public long getFree() {
        return Free;
    }

    public void setFree(long free) {
        Free = free;
    }

    public long getUsed() {
        return Used;
    }

    public void setUsed(long used) {
        Used = used;
    }

    public long getTotal() {
        return Total;
    }

    public void setTotal(long total) {
        Total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(Free);
        dest.writeLong(Used);
        dest.writeLong(Total);
    }
}
