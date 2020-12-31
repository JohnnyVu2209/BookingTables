package com.example.reservation_manager;

public class tables {

    public int SoBan;
    public int SoLuongNguoi;
    public boolean BanThuong;

    public tables(int so_ban, int soluong_nguoi, boolean loai_ban) {
        SoBan = so_ban;
        SoLuongNguoi = soluong_nguoi;
        BanThuong = loai_ban;
    }
//
//    public int getSo_ban() {
//        return So_ban;
//    }
//
//    public int getSoluong_nguoi() {
//        return Soluong_nguoi;
//    }

    public tables() {

    }
}
