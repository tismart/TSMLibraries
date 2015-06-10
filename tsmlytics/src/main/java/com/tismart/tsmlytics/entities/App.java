package com.tismart.tsmlytics.entities;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luis.rios on 21/05/2015.
 */
@SuppressWarnings("WeakerAccess")
public class App implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<App> CREATOR = new Parcelable.Creator<App>() {
        @Override
        public App createFromParcel(Parcel in) {
            return new App(in);
        }

        @Override
        public App[] newArray(int size) {
            return new App[size];
        }
    };
    private String Name;
    private String Package;
    private Drawable Icon;

    public App() {
    }

    protected App(Parcel in) {
        Name = in.readString();
        Package = in.readString();
        Icon = (Drawable) in.readValue(Drawable.class.getClassLoader());
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPackage() {
        return Package;
    }

    public void setPackage(String aPackage) {
        Package = aPackage;
    }

    public Drawable getIcon() {
        return Icon;
    }

    public void setIcon(Drawable icon) {
        Icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(Package);
        dest.writeValue(Icon);
    }
}
