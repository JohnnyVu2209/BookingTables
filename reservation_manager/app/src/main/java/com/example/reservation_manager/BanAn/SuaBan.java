package com.example.reservation_manager.BanAn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reservation_manager.FirebaseController;
import com.example.reservation_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.io.File;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class SuaBan extends AppCompatActivity {
    EditText tb,sln;
    Button sua;
    private String uid;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    BanAn banAn;
    FirebaseController controller;

    StorageTask uploadTask;

    private void AnhXa() {

        tb = (EditText) findViewById(R.id.etNumber);
        sln = (EditText) findViewById(R.id.etAmount);
        sua = (Button) findViewById(R.id.btnEdit);
        databaseReference = FirebaseDatabase.getInstance().getReference("KhachHang");
        storageReference = FirebaseStorage.getInstance().getReference();
        controller = new FirebaseController(getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_ban);

        AnhXa();

        uid = getIntent().getStringExtra("position");
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final File file;
                try {
                    tb.setText(snapshot.getValue(BanAn.class).getTenban());
                    sln.setText(snapshot.getValue(BanAn.class).getTenban());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateBA();
                controller.UpdateData("Ban",uid,banAn);
                intentToListBA();
            }
        });

    }

    private void intentToListBA(){
        Intent ListBA_intent = new Intent(SuaBan.this, DanhSachBan.class);
        ListBA_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ListBA_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(ListBA_intent);
        finish();
    }

    private void UpdateBA() {
        String number;
        int amount;
        number = tb.getText().toString();
        amount = Integer.parseInt(sln.getText().toString());

    }

    private String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

}