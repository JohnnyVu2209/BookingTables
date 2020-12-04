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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_name);
        ok = findViewById(R.id.btnOK);
        name = findViewById(R.id.tvName);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yourname = name.getText().toString();
                phone = mCurrentUser.getPhoneNumber();
                Intent home = new Intent(InsertName.this,MainActivity.class);
                home.putExtra("name",yourname);
                home.putExtra("phone",phone);
                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(home);
                finish();
            }
        });
    }
}