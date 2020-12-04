package com.example.reservationuser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String fbname = null;
    private String phonename = null;
    private String phone = null;
    private TextView name;
    private String fbphone = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.tvFbname);
        fbname = getIntent().getStringExtra("fbname");
        fbphone = getIntent().getStringExtra("fbphone");
        phonename = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        if(fbname != null){
            name.setText(fbname + "\n" + fbphone);
        }
        if(phone != null && phonename != null){
            name.setText(phonename + "\n" + phone);
        }
    }
}