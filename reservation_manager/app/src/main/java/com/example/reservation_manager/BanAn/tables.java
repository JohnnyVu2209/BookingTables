package com.example.reservation_manager.BanAn;

public class tables {

    public int SoBan;
    public int SoLuongNguoi;
    public boolean LoaiBan;

    public tables() {
    }

    public tables(int So_ban, int Soluong_nguoi, boolean loai_ban) {
        SoBan = So_ban;
        SoLuongNguoi = Soluong_nguoi;
        LoaiBan = loai_ban;
    }

    public tables(int soBan, int soLuongNguoi) {
        SoBan = soBan;
        SoLuongNguoi = soLuongNguoi;
    }
}
