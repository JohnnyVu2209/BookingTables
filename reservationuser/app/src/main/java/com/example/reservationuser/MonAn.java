package com.example.reservationuser;

import androidx.annotation.Nullable;

public class MonAn {
    public String tenmonan;
    public String gia;
    public String loaimonan;
    public Boolean goimon;
    public String idhinh;
    public  @Nullable String mota;

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
