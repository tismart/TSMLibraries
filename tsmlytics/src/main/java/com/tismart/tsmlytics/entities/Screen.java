package com.tismart.tsmlytics.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luis.rios on 21/05/2015.
 */
public class Screen implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Screen> CREATOR = new Parcelable.Creator<Screen>() {
        @Override
        public Screen createFromParcel(Parcel in) {
            return new Screen(in);
        }

        @Override
        public Screen[] newArray(int size) {
            return new Screen[size];
        }
    };
    private double Size;
    private int Density;
    private String Orientation;

    public Screen() {
    }

    protected Screen(Parcel in) {
        Size = in.readDouble();
        Density = in.readInt();
        Orientation = in.readString();
    }

    public double getSize() {
        return Size;
    }

    public void setSize(double size) {
        Size = size;
    }

    public int getDensity() {
        return Density;
    }

    public void setDensity(int density) {
        Density = density;
    }

    public String getOrientation() {
        return Orientation;
    }

    public void setOrientation(String orientation) {
        Orientation = orientation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(Size);
        dest.writeInt(Density);
        dest.writeString(Orientation);
    }
}
