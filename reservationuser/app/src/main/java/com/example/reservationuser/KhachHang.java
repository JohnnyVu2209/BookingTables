package com.example.reservationuser;

public class KhachHang {
    public String hoten;
    public String sdt;
    public String ngaysinh;
    public boolean gioitinh;
    public int sldat;
    public String idavatar;

    public KhachHang(){
    }

    public KhachHang(String hoten, String sdt, String ngaysinh, Boolean gioitinh, int sldat, String idavatar) {
        this.hoten = hoten;
        this.sdt = sdt;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
        this.sldat = sldat;
        this.idavatar = idavatar;
    }

    public KhachHang(String hoten, String sdt) {
        this.hoten = hoten;
        this.sdt = sdt;
        this.sldat = 0;
    }

    public String getSdt() {
        return sdt;
    }

    public String getHoten() {
        return hoten;
    }
}
