package com.example.reservation_manager.KhuyenMai;

import com.example.reservation_manager.KhachHang.KhachHang;

public class KhuyenMai {
    public String code;
    public String nameKM;
    public String start;
    public String exp;
    public String percentage;

    public KhuyenMai()
    {

    }

    public KhuyenMai(String code, String nameKM, String start, String exp, String percentage)
    {
        this.code = code;
        this.nameKM = nameKM;
        this.start = start;
        this.exp = exp;
        this.percentage = percentage;
    }
}
