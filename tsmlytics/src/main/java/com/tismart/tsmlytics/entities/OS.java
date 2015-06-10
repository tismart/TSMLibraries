package com.tismart.tsmlytics.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luis.rios on 21/05/2015.
 */
@SuppressWarnings({"WeakerAccess", "SameParameterValue"})
public class OS implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<OS> CREATOR = new Parcelable.Creator<OS>() {
        @Override
        public OS createFromParcel(Parcel in) {
            return new OS(in);
        }

        @Override
        public OS[] newArray(int size) {
            return new OS[size];
        }
    };
    private String Version;
    private String Name;

    public OS() {
    }

    protected OS(Parcel in) {
        Version = in.readString();
        Name = in.readString();
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Version);
        dest.writeString(Name);
    }
}
