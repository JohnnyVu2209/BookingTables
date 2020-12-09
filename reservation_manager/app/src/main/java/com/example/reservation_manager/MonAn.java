package com.example.reservation_manager;

import androidx.annotation.Nullable;

public class MonAn {
    private String tenmonan;
    private String gia;
    private String loaimonan;
    private Boolean goimon;
    private String idhinh;
    private @Nullable String mota;

    public MonAn(String tenmonan, String gia, String loaimonan, Boolean goimon, String idhinh, @Nullable String mota) {
        this.tenmonan = tenmonan;
        this.gia = gia;
        this.loaimonan = loaimonan;
        this.goimon = goimon;
        this.idhinh = idhinh;
        this.mota = mota;
    }

    public String getIdhinh() {
        return idhinh;
    }

    public void setIdhinh(String idhinh) {
        this.idhinh = idhinh;
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

    @Nullable
    public String getMota() {
        return mota;
    }

    public void setMota(@Nullable String mota) {
        this.mota = mota;
    }
}
