
/*
 * Decompiled with CFR 0.0.
 *
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.Toast
 *  androidx.fragment.app.Fragment
 *  androidx.fragment.app.FragmentActivity
 *  com.google.android.material.floatingactionbutton.FloatingActionButton
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.example.reservationuser;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.reservationuser.DatBan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SoDoBan
        extends Fragment
        implements View.OnClickListener {
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18, btnfinal;
    int btnid = 0;
    FloatingActionButton fab;
    Button[] listNormal;
    Button[] listVip;

    private void AnhXa(View view) {
        btn1 = (Button)view.findViewById(R.id.btnT1);btn1.setOnClickListener(this);
        btn2 = (Button)view.findViewById(R.id.btnT2);btn2.setOnClickListener(this);
        btn3 = (Button)view.findViewById(R.id.btnT3);btn3.setOnClickListener(this);
        btn4 = (Button)view.findViewById(R.id.btnT4);btn4.setOnClickListener(this);
        btn5 = (Button)view.findViewById(R.id.btnT5);btn5.setOnClickListener(this);
        btn6 = (Button)view.findViewById(R.id.btnT6);btn6.setOnClickListener(this);
        btn7 = (Button)view.findViewById(R.id.btnT7);btn7.setOnClickListener(this);
        btn8 = (Button)view.findViewById(R.id.btnT8);btn8.setOnClickListener(this);
        btn9 = (Button)view.findViewById(R.id.btnT9);btn9.setOnClickListener(this);
        btn10 = (Button)view.findViewById(R.id.btnT10);btn10.setOnClickListener(this);
        btn11 = (Button)view.findViewById(R.id.btnT11);btn11.setOnClickListener(this);
        btn12 = (Button)view.findViewById(R.id.btnT12);btn12.setOnClickListener(this);
        btn13 = (Button)view.findViewById(R.id.btnT13);btn13.setOnClickListener(this);
        btn14 = (Button)view.findViewById(R.id.btnT14);btn14.setOnClickListener(this);
        btn15 = (Button)view.findViewById(R.id.btnT15);btn15.setOnClickListener(this);
        btn16 = (Button)view.findViewById(R.id.btnT16);btn16.setOnClickListener(this);
        btn17 = (Button)view.findViewById(R.id.btnT17);btn17.setOnClickListener(this);
        btn18 = (Button)view.findViewById(R.id.btnT18);btn18.setOnClickListener(this);
        listNormal = new Button[]{this.btn1, this.btn2, this.btn4, this.btn5, this.btn7, this.btn9, this.btn10, this.btn11, this.btn13, this.btn14, this.btn15, this.btn17};

        listVip= new Button[]{this.btn3, this.btn6, this.btn8, this.btn12, this.btn16, this.btn18};

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
    }

    public SoDoBan() {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.fragment_so_do_ban, viewGroup, false);
        this.AnhXa(view);
        this.fab.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                SoDoBan soDoBan = SoDoBan.this;
                DatBan datBan = (DatBan) getActivity();
                datBan.getTableNum(btnfinal.getHint().toString());
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }
    private void buttonClick(Button button) {
        this.fab.setVisibility(View.VISIBLE);
        this.clearDraw();
        this.btnfinal = button;
        for (int i = 0; i < listNormal.length; i++) {
            if (listNormal[i] == button){
                listNormal[i].setBackground(getResources().getDrawable(R.drawable.custom_button_click));
            }
        }
        for (int i = 0; i < listVip.length; i++) {
            if (listVip[i].equals(button)) {
                listVip[i].setBackground(getResources().getDrawable(R.drawable.custom_button_vip_click));
            }
        }
    }

    private void clearDraw() {
        for (int i = 0; i < listNormal.length; i++) {
            listNormal[i].setBackground(this.getResources().getDrawable(R.drawable.custom_button));
        }
        for (int i = 0; i < listVip.length; i++) {
            listVip[i].setBackground(this.getResources().getDrawable(R.drawable.custom_button_vip));
        }
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnT1: {
                buttonClick(btn1);
                break;
            }
            case R.id.btnT2: {
                buttonClick(btn2);
                break;
            }
            case R.id.btnT3: {
                buttonClick(btn3);
                break;
            }
            case R.id.btnT4: {
                buttonClick(btn4);
                break;
            }
            case R.id.btnT5: {
                buttonClick(btn5);
                break;
            }
            case R.id.btnT6: {
                buttonClick(btn6);
                break;
            }
            case R.id.btnT7: {
                buttonClick(btn7);
                break;
            }
            case R.id.btnT8: {
                buttonClick(btn8);
                break;
            }
            case R.id.btnT9: {
                buttonClick(btn9);
                break;
            }
            case R.id.btnT10: {
                buttonClick(btn10);
                break;
            }
            case R.id.btnT11: {
                buttonClick(btn11);
                break;
            }
            case R.id.btnT12: {
                buttonClick(btn12);
                break;
            }
            case R.id.btnT13: {
                buttonClick(btn13);
                break;
            }
            case R.id.btnT14: {
                buttonClick(btn14);
                break;
            }
            case R.id.btnT15: {
                buttonClick(btn15);
                break;
            }
            case R.id.btnT16: {
                buttonClick(btn16);
                break;
            }
            case R.id.btnT17: {
                buttonClick(btn17);
                break;
            }
            case R.id.btnT18: {
                buttonClick(btn18);
                break;
            }
        }
    }
}


