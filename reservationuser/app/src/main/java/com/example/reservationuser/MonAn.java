package com.example.reservationuser;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class MonAn implements Parcelable {
    public String tenmonan;
    public String gia;
    public String loaimonan;
    public Boolean goimon;
    public String idhinh;
    public  @Nullable String mota;
    public Boolean isSelected = false;

    protected MonAn(Parcel in) {
        tenmonan = in.readString();
        gia = in.readString();
        loaimonan = in.readString();
        byte tmpGoimon = in.readByte();
        goimon = tmpGoimon == 0 ? null : tmpGoimon == 1;
        idhinh = in.readString();
        mota = in.readString();
        byte tmpIsSelected = in.readByte();
        isSelected = tmpIsSelected == 0 ? null : tmpIsSelected == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tenmonan);
        dest.writeString(gia);
        dest.writeString(loaimonan);
        dest.writeByte((byte) (goimon == null ? 0 : goimon ? 1 : 2));
        dest.writeString(idhinh);
        dest.writeString(mota);
        dest.writeByte((byte) (isSelected == null ? 0 : isSelected ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MonAn> CREATOR = new Creator<MonAn>() {
        @Override
        public MonAn createFromParcel(Parcel in) {
            return new MonAn(in);
        }

        @Override
        public MonAn[] newArray(int size) {
            return new MonAn[size];
        }
    };

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public MonAn(String tenmonan, String gia, String loaimonan, Boolean goimon, String idhinh, @Nullable String mota) {
        this.tenmonan = tenmonan;
        this.gia = gia;
        this.loaimonan = loaimonan;
        this.goimon = goimon;
        this.idhinh = idhinh;
        this.mota = mota;
    }

    public MonAn(String tenmonan) {
        this.tenmonan = tenmonan;
    }

    public MonAn() {
    }

    public String getTenmonan() {
        return tenmonan;
    }

    public void setTenmonan(String tenmonan) {
        this.tenmonan = tenmonan;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getLoaimonan() {
        return loaimonan;
    }

    public void setLoaimonan(String loaimonan) {
        this.loaimonan = loaimonan;
    }

    public Boolean getGoimon() {
        return goimon;
    }

    public void setGoimon(Boolean goimon) {
        this.goimon = goimon;
    }

    public String getIdhinh() {
        return idhinh;
    }

    public void setIdhinh(String idhinh) {
        this.idhinh = idhinh;
    }

    @Nullable
    public String getMota() {
        return mota;
    }

    public void setMota(@Nullable String mota) {
        this.mota = mota;
    }
}
