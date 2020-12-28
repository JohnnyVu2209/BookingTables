package com.example.reservationuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InsertName extends AppCompatActivity {

    private Button ok;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private EditText name;
    private String phone;
    private FirebaseController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_name);
        ok = findViewById(R.id.btnOK);
        name = findViewById(R.id.tvName);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        controller = new FirebaseController(getApplicationContext());
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yourname = name.getText().toString();
                Intent home = new Intent(InsertName.this,MainActivity.class);
                KhachHang khachmoi = new KhachHang(yourname,mCurrentUser.getPhoneNumber());
                controller.WirteWithAutoIncreaseKey("KhachHang",khachmoi);
                startActivity(home);
            }
        });
    }
}