package com.example.reservationuser;
import androidx.annotation.Nullable;

import java.util.Date;

public class KhachHang {
    private String hoten;
    private @Nullable String sdt;
    private @Nullable Date ngaysinh;
    private @Nullable boolean gioitinh;
    private @Nullable int sldat;
    private @Nullable String idavatar;

    public KhachHang(){

    }

    public KhachHang(String hoten, @Nullable String sdt, @Nullable Date ngaysinh, @Nullable Boolean gioitinh, @Nullable int sldat, @Nullable String idavatar) {
        this.hoten = hoten;
        this.sdt = sdt;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
        this.sldat = sldat;
        this.idavatar = idavatar;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    @Nullable
    public String getSdt() {
        return sdt;
    }

    public void setSdt(@Nullable String sdt) {
        this.sdt = sdt;
    }

    @Nullable
    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(@Nullable Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    @Nullable
    public Boolean getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(@Nullable Boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    @Nullable
    public int getSldat() {
        return sldat;
    }

    public void setSldat(@Nullable int sldat) {
        this.sldat = sldat;
    }

    @Nullable
    public String getIdavatar() {
        return idavatar;
    }

    public void setIdavatar(@Nullable String idavatar) {
        this.idavatar = idavatar;
    }

}
