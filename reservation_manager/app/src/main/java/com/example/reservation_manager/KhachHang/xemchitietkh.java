package com.example.reservation_manager.KhachHang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservation_manager.FirebaseController;
import com.example.reservation_manager.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class xemchitietkh extends AppCompatActivity {
    ImageView avatar, ivhoten, ivsdt;
    TextView ht,sdt,ns,sld, gt;
    private String uid;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    FirebaseController controller;

    private void AnhXa() {
        avatar = (ImageView) findViewById(R.id.profile_image);
        ht = (TextView) findViewById(R.id.TvHT);
        sdt = (TextView) findViewById(R.id.TvSDT);
        ns = (TextView) findViewById(R.id.TvNS);
        sld = (TextView) findViewById(R.id.TvSLD);
        gt = (TextView)findViewById(R.id.Tvgt);
        databaseReference = FirebaseDatabase.getInstance().getReference("KhachHang");
        storageReference = FirebaseStorage.getInstance().getReference();
        controller = new FirebaseController(getApplicationContext());
        ivhoten = (ImageView)findViewById(R.id.edit_hoten);
        ivsdt = (ImageView)findViewById(R.id.edit_sdt);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemchitietkh);

        AnhXa();
        uid = getIntent().getStringExtra("key");
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final File file;
                try {
                    ht.setText(snapshot.getValue(KhachHang.class).hoten);
                    sdt.setText(snapshot.getValue(KhachHang.class).sdt);

                    if(snapshot.getValue(KhachHang.class).ngaysinh.isEmpty())
                    {
                        ns.setText("Chưa có");
                        ns.setTextColor(Color.RED);
                    } else {
                        ns.setText(snapshot.getValue(KhachHang.class).ngaysinh);
                    }
                    if (snapshot.getValue(KhachHang.class).gioitinh == true) {
                        gt.setText("Nam");
                    } else {
                        gt.setText("Nữ");
                    }
                    sld.setText(String.valueOf(snapshot.getValue(KhachHang.class).sldat));

                    file = File.createTempFile("image", "png");
                    storageReference.child(snapshot.getValue(KhachHang.class).idavatar).getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            avatar.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(xemchitietkh.this, "Không thể load ảnh, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ivhoten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edithotenIntent = new Intent(xemchitietkh.this, SuaKhachHang.class);
                edithotenIntent.putExtra("position",uid);
                startActivity(edithotenIntent);
            }
        });
        ivsdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editsdtIntent = new Intent(xemchitietkh.this, SuaKhachHang.class);
                editsdtIntent.putExtra("position",uid);
                startActivity(editsdtIntent);
            }
        });
    }
}