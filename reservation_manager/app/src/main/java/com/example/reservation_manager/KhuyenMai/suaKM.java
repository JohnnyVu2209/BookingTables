package com.example.reservation_manager.KhuyenMai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.reservation_manager.FirebaseController;
import com.example.reservation_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class suaKM extends AppCompatActivity {
    EditText ed_code, ed_nameKM, ed_start, ed_exp, ed_percentage;
    private String uid;
    Button save;
    DatabaseReference databaseReference;
    KhuyenMai Khuyenmai;
    FirebaseController controller;

    private void anhxa(){
        ed_code = (EditText)findViewById(R.id.ecode);
        ed_nameKM = (EditText)findViewById(R.id.enameKM);
        ed_start = (EditText)findViewById(R.id.estart);
        ed_exp = (EditText)findViewById(R.id.eexp);
        ed_percentage = (EditText)findViewById(R.id.epercentage);
        save = (Button)findViewById(R.id.btnLuu);
        databaseReference = FirebaseDatabase.getInstance().getReference("KhuyenMai");
        controller = new FirebaseController(getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_k_m);

        anhxa();
        uid = getIntent().getStringExtra("key");
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try
                {
                    ed_code.setText(snapshot.getValue(KhuyenMai.class).code);
                    ed_nameKM.setText(snapshot.getValue(KhuyenMai.class).nameKM);
                    ed_start.setText(snapshot.getValue(KhuyenMai.class).start);
                    ed_exp.setText(snapshot.getValue(KhuyenMai.class).exp);
                    ed_percentage.setText(String.valueOf(snapshot.getValue(KhuyenMai.class).percentage));
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateKM();
                controller.UpdateData("KhuyenMai", uid, Khuyenmai);
                intentToListKM();
            }
        });
    }
    private void intentToListKM() {
        Intent ListKM_intent = new Intent(suaKM.this, XemDanhSachKhuyenMai.class);
        startActivity(ListKM_intent);
    }

    private void UpdateKM() {
        String c,n,s,e,p;
        c = ed_code.getText().toString();
        n = ed_nameKM.getText().toString();
        s = ed_start.getText().toString();
        e = ed_exp.getText().toString();
        p = ed_percentage.getText().toString();

    }
}