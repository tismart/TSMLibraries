package com.tismart.tsmlytics.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luis.rios on 21/05/2015.
 */
public class Network implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Network> CREATOR = new Parcelable.Creator<Network>() {
        @Override
        public Network createFromParcel(Parcel in) {
            return new Network(in);
        }

        @Override
        public Network[] newArray(int size) {
            return new Network[size];
        }
    };
    private String Connection;
    private String Type;
    private String Strength;

    public Network() {
    }

    protected Network(Parcel in) {
        Connection = in.readString();
        Type = in.readString();
        Strength = in.readString();
    }

    public String getConnection() {
        return Connection;
    }

    public void setConnection(String connection) {
        Connection = connection;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getStrength() {
        return Strength;
    }

    public void setStrength(String strength) {
        Strength = strength;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Connection);
        dest.writeString(Type);
        dest.writeString(Strength);
    }
}
