package com.example.reservationuser;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class DonDatBan implements Parcelable {
    public String NguoiDat;
    public String SoDienThoai;
    public String NgayNhanBan;
    public String GioNhanBan;
    public int SoBan;
    public boolean GoiMonTruoc;
    public ArrayList<MonAn> MonGoiTruoc;

    public DonDatBan() {
    }

    public DonDatBan(String nguoiDat, String soDienThoai, String ngayNhanBan, String gioNhanBan, int soBan, boolean goiMonTruoc) {
        NguoiDat = nguoiDat;
        SoDienThoai = soDienThoai;
        NgayNhanBan = ngayNhanBan;
        GioNhanBan = gioNhanBan;
        SoBan = soBan;
        GoiMonTruoc = goiMonTruoc;
    }

    public DonDatBan(String nguoiDat, String soDienThoai, String ngayNhanBan, String gioNhanBan, int soBan, Boolean goiMonTruoc , ArrayList<MonAn> monGoiTruoc) {
        NguoiDat = nguoiDat;
        SoDienThoai = soDienThoai;
        NgayNhanBan = ngayNhanBan;
        GioNhanBan = gioNhanBan;
        SoBan = soBan;
        GoiMonTruoc = goiMonTruoc;
        MonGoiTruoc = monGoiTruoc;
    }

    protected DonDatBan(Parcel in) {
        NguoiDat = in.readString();
        SoDienThoai = in.readString();
        NgayNhanBan = in.readString();
        GioNhanBan = in.readString();
        SoBan = in.readInt();
        GoiMonTruoc = in.readByte() != 0;
    }

    public static final Creator<DonDatBan> CREATOR = new Creator<DonDatBan>() {
        @Override
        public DonDatBan createFromParcel(Parcel in) {
            return new DonDatBan(in);
        }

        @Override
        public DonDatBan[] newArray(int size) {
            return new DonDatBan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(NguoiDat);
        dest.writeString(SoDienThoai);
        dest.writeString(NgayNhanBan);
        dest.writeString(GioNhanBan);
        dest.writeInt(SoBan);
        dest.writeByte((byte) (GoiMonTruoc ? 1 : 0));
    }
}
