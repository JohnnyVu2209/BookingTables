package com.example.reservation_manager.KhachHang;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.reservation_manager.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class SuaKhachHang extends AppCompatActivity {
    ImageView avatar;
    EditText ht,sdt,ns,sld;
    RadioButton nam,nu;
    Button save;
    private String uid;
    private String imguri;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    private void AnhXa() {
        avatar = (ImageView) findViewById(R.id.profile_image);
        ht = (EditText) findViewById(R.id.edHT);
        sdt = (EditText) findViewById(R.id.edSDT);
        ns = (EditText) findViewById(R.id.edNS);
        sld = (EditText) findViewById(R.id.edSLD);
        nam = (RadioButton) findViewById(R.id.rbtnNam);
        nu = (RadioButton) findViewById(R.id.rbtnNu);
        save = (Button) findViewById(R.id.btnS);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suakh);

        AnhXa();

        uid = getIntent().getStringExtra("position");
        databaseReference = FirebaseDatabase.getInstance().getReference("KhachHang");
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final File file;
                try {
                    ht.setText(snapshot.getValue(KhachHang.class).hoten);
                    sdt.setText(snapshot.getValue(KhachHang.class).sdt);
                    ns.setText(snapshot.getValue(KhachHang.class).ngaysinh);
                    if (snapshot.getValue(KhachHang.class).gioitinh == true) {
                        nam.setChecked(true);
                    } else {
                        nu.setChecked(true);
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
                            Toast.makeText(SuaKhachHang.this, "Không thể load ảnh, vui lòng thử lại", Toast.LENGTH_SHORT).show();
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

    }
}
