package com.example.reservation_manager.KhachHang;

public class KhachHang {
    public String hoten;
    public String sdt;
    public String ngaysinh;
    public boolean gioitinh;
    public int sldat;
    public String idavatar;
    public boolean thanthiet;
    public boolean vip;

    public KhachHang(){
    }

    public KhachHang(String hoten, String sdt, String ngaysinh, Boolean gioitinh, int sldat, String idavatar, boolean tt, boolean vip) {
        this.hoten = hoten;
        this.sdt = sdt;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
        this.sldat = sldat;
        this.idavatar = idavatar;
        this.thanthiet = tt;
        this.vip = vip;
    }
}
