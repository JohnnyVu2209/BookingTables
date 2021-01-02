package com.example.reservationuser;

import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;

public class DonDatBan {
    public String NguoiDat;
    public String SoDienThoai;
    public String NgayNhanBan;
    public String GioNhanBan;
    public int SoBan;
    public boolean GoiMonTruoc;
    public @Nullable ArrayList<MonAn> MonGoiTruoc;

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
}
