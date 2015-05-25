package com.tismart.tsmlytics.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luis.rios on 21/05/2015.
 */
public class Disk implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Disk> CREATOR = new Parcelable.Creator<Disk>() {
        @Override
        public Disk createFromParcel(Parcel in) {
            return new Disk(in);
        }

        @Override
        public Disk[] newArray(int size) {
            return new Disk[size];
        }
    };
    private float InternalFree;
    private float InternalUsed;
    private float InternalTotal;
    private float ExternalFree;
    private float ExternalUsed;
    private float ExternalTotal;

    public Disk() {
    }

    protected Disk(Parcel in) {
        InternalFree = in.readFloat();
        InternalUsed = in.readFloat();
        InternalTotal = in.readFloat();
        ExternalFree = in.readFloat();
        ExternalUsed = in.readFloat();
        ExternalTotal = in.readFloat();
    }

    public float getInternalFree() {
        return InternalFree;
    }

    public void setInternalFree(float internalFree) {
        InternalFree = internalFree;
    }

    public float getInternalUsed() {
        return InternalUsed;
    }

    public void setInternalUsed(float internalUsed) {
        InternalUsed = internalUsed;
    }

    public float getInternalTotal() {
        return InternalTotal;
    }

    public void setInternalTotal(float internalTotal) {
        InternalTotal = internalTotal;
    }

    public float getExternalFree() {
        return ExternalFree;
    }

    public void setExternalFree(float externalFree) {
        ExternalFree = externalFree;
    }

    public float getExternalUsed() {
        return ExternalUsed;
    }

    public void setExternalUsed(float externalUsed) {
        ExternalUsed = externalUsed;
    }

    public float getExternalTotal() {
        return ExternalTotal;
    }

    public void setExternalTotal(float externalTotal) {
        ExternalTotal = externalTotal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(InternalFree);
        dest.writeFloat(InternalUsed);
        dest.writeFloat(InternalTotal);
        dest.writeFloat(ExternalFree);
        dest.writeFloat(ExternalUsed);
        dest.writeFloat(ExternalTotal);
    }
}
