package com.kordloo.hosein.ynwa.karin.model;

import android.os.Parcel;
import android.os.Parcelable;
import io.realm.RealmModel;

public class WareArchive implements RealmModel, Parcelable {

    private String wareName;
    private String warePrice;
    private int wareCount;

    public WareArchive() {
    }

    public WareArchive(String wareName, String warePrice, int wareCount) {
        this.wareName = wareName;
        this.warePrice = warePrice;
        this.wareCount = wareCount;
    }

    protected WareArchive(Parcel in) {
        wareName = in.readString();
        warePrice = in.readString();
        wareCount = in.readInt();
    }

    public static final Creator<WareArchive> CREATOR = new Creator<WareArchive>() {
        @Override
        public WareArchive createFromParcel(Parcel in) {
            return new WareArchive(in);
        }

        @Override
        public WareArchive[] newArray(int size) {
            return new WareArchive[size];
        }
    };

    public String getWareName() {
        return wareName;
    }

    public void setWareName(String wareName) {
        this.wareName = wareName;
    }

    public String getWarePrice() {
        return warePrice;
    }

    public void setWarePrice(String warePrice) {
        this.warePrice = warePrice;
    }

    public int getWareCount() {
        return wareCount;
    }

    public void setWareCount(int wareCount) {
        this.wareCount = wareCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(wareName);
        dest.writeString(warePrice);
        dest.writeInt(wareCount);
    }
}
