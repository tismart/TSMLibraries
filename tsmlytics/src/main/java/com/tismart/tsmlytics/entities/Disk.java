package com.tismart.tsmlytics.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luis.rios on 21/05/2015.
 */
@SuppressWarnings("WeakerAccess")
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
    private long InternalFree;
    private long InternalUsed;
    private long InternalTotal;
    private long ExternalFree;
    private long ExternalUsed;
    private long ExternalTotal;

    public Disk() {
    }

    protected Disk(Parcel in) {
        InternalFree = in.readLong();
        InternalUsed = in.readLong();
        InternalTotal = in.readLong();
        ExternalFree = in.readLong();
        ExternalUsed = in.readLong();
        ExternalTotal = in.readLong();
    }

    public long getInternalFree() {
        return InternalFree;
    }

    public void setInternalFree(long internalFree) {
        InternalFree = internalFree;
    }

    public long getInternalUsed() {
        return InternalUsed;
    }

    public void setInternalUsed(long internalUsed) {
        InternalUsed = internalUsed;
    }

    public long getInternalTotal() {
        return InternalTotal;
    }

    public void setInternalTotal(long internalTotal) {
        InternalTotal = internalTotal;
    }

    public long getExternalFree() {
        return ExternalFree;
    }

    public void setExternalFree(long externalFree) {
        ExternalFree = externalFree;
    }

    public long getExternalUsed() {
        return ExternalUsed;
    }

    public void setExternalUsed(long externalUsed) {
        ExternalUsed = externalUsed;
    }

    public long getExternalTotal() {
        return ExternalTotal;
    }

    public void setExternalTotal(long externalTotal) {
        ExternalTotal = externalTotal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(InternalFree);
        dest.writeLong(InternalUsed);
        dest.writeLong(InternalTotal);
        dest.writeLong(ExternalFree);
        dest.writeLong(ExternalUsed);
        dest.writeLong(ExternalTotal);
    }
}
