package com.example.reservation_manager.BanAn;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BanAn extends AppCompatActivity {
    private String tenban;
    private Boolean songuoi;


    public BanAn(String tenban, Boolean songuoi) {
        this.tenban = tenban;
        this.songuoi = songuoi;

    }

    public BanAn() {
    }

    public String getTenban() {
        return tenban;
    }

    public void setTenban(String tenban) {
        this.tenban = tenban;
    }

    public Boolean getSonguoi() {
        return songuoi;
    }

    public void setSonguoi(Boolean songuoi) {
        this.songuoi = songuoi;
    }
}



