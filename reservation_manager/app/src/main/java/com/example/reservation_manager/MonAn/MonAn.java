package com.example.reservation_manager.MonAn;

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

    public MonAn() {
    }

}
