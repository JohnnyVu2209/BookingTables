package com.example.reservation_manager.KhachHang;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reservation_manager.FirebaseController;
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
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class SuaKhachHang extends AppCompatActivity {
    ImageView avatar;
    EditText ht,sdt,ns,sld;
    RadioButton nam,nu;
    Button save;
    private String uid;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    KhachHang khachHang;
    FirebaseController controller;
    Uri imguri;
    String idavatar;
    StorageTask uploadTask;

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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(SuaKhachHang.this, "Đang tiến hành upload hình ảnh", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        UpdateKH();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                controller.UpdateData("KhachHang",uid,khachHang);
                intentToListKH();
            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 1);
                Filechooser();
            }
        });
    }

    private void intentToListKH(){
        Intent ListKH_intent = new Intent(SuaKhachHang.this,XemDanhSachKhachHang.class);
        ListKH_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ListKH_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(ListKH_intent);
        finish();
    }

    private void UpdateKH() {
        Fireuploader();
        String name = ht.getText().toString();
        String phone = sdt.getText().toString();
        String birthday = ns.getText().toString();
        Boolean sex = true;
        if (nam.isSelected() == true) {
            sex = true;
        } else {
            sex = false;
        }
        int amount = Integer.parseInt(sld.getText().toString());
        khachHang = new KhachHang(name,phone,birthday,sex,amount,idavatar);
    }

    private void Fireuploader() {
        idavatar = "KhachImages/"+removeAccent(ht.getText().toString()).replaceAll("\\s","")+"."+"png";
        StorageReference ref = storageReference.child(idavatar);
        uploadTask=ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }
    private String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    private void Filechooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            imguri = data.getData();
            avatar.setImageURI(imguri);
        }
    }
}
