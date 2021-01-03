package com.example.reservation_manager.MonAn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.reservation_manager.FirebaseController;
import com.example.reservation_manager.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class CapNhatMonAn extends AppCompatActivity {

    EditText ten, gia, mota;
    Boolean isGoiMon = false;
    ImageView hinhdoan;
    Spinner loaima;
    ArrayAdapter<String> adapter;
    ArrayList<String> TypeFood;
    RadioGroup group;
    RadioButton can, not;
    DatabaseReference referencer = FirebaseDatabase.getInstance().getReference();
    StorageReference storageReference;
    Button capnhat, thoat;
    StorageReference storage;
    StorageTask uploadTask;
    String idhinh;
    Uri imguri;
    MonAn monAn;
    String idavatartemp;

    FirebaseController controller;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_mon_an);
        AnhXa();

        //Lay hinh trong gallery
        hinhdoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Filechooser();
            }
        });
        ten.setText(intent.getStringExtra("ten"));
        gia.setText(intent.getStringExtra("gia"));
        mota.setText(intent.getStringExtra("mota"));
        hinhdoan.setImageURI(Uri.parse(intent.getStringExtra("hinh")));

        if (intent.getBooleanExtra("goimon", true) == true) {
            can.setChecked(true);
        } else {
            not.setChecked(false);
        }

        referencer.child("LoaiMonAn").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                LoaiMonAn loaiMonAn = snapshot.getValue(LoaiMonAn.class);
                TypeFood.add(loaiMonAn.TenLoai);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        try {
            final File file = File.createTempFile("image", "png");
            storageReference.child(intent.getStringExtra("hinh")).getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    hinhdoan.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CapNhatMonAn.this, "Image Failed to load", Toast.LENGTH_SHORT).show();
                }
            });
            idavatartemp = intent.getStringExtra("hinh");
        } catch (IOException e) {
            e.printStackTrace();
        }

        loaima.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent danhsach = new Intent(CapNhatMonAn.this, XemDanhSachMonAn.class);
                startActivity(danhsach);
            }
        });

        capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(getApplicationContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    String mt = "";
                    try {
                        mt = mota.getText().toString();
                        UploadData(mt);
                    } catch (Exception e) {
                        mt = "";
                        UploadData(mt);
                    }

                    controller.UpdateData("MonAn", intent.getStringExtra("id"), monAn);
                    intentToListFood();
                }
            }
        });
    }

    private void UploadData(String mt) {
        String tenmon, giama, loai = "";
        int radioId = 0;
        tenmon = ten.getText().toString();
        giama = gia.getText().toString();
        loai = loaima.getSelectedItem().toString();
        radioId = group.getCheckedRadioButtonId();
        if (can.getId() == radioId) {
            isGoiMon = true;
        }
        if (imguri != null) {
            Fireuploader();
            monAn = new MonAn(tenmon, giama, loai, isGoiMon, idhinh, mt);
        } else {
            monAn = new MonAn(tenmon, giama, loai, isGoiMon, idavatartemp, mt);
        }

    }

    private void Fireuploader() {
        idhinh = removeAccent(intent.getStringExtra("hinh")).replaceAll("\\s","");
        StorageReference ref = storage.child(idhinh);
        uploadTask = ref.putFile(imguri)
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

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
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
        startActivityForResult(intent, 1);
    }

    private void intentToListFood() {
        Intent ListFood_intent = new Intent(CapNhatMonAn.this, XemDanhSachMonAn.class);
        ListFood_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ListFood_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(ListFood_intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imguri = data.getData();
            hinhdoan.setImageURI(imguri);
        }
    }

    private void AnhXa() {
        hinhdoan = (ImageView) findViewById(R.id.profile_image);
        ten = (EditText) findViewById(R.id.etTenDoAn);
        gia = (EditText) findViewById(R.id.etGiaMonAn);
        mota = (EditText) findViewById(R.id.etMoTa);
        loaima = (Spinner) findViewById(R.id.ListLoaiMonAn);
        group = (RadioGroup) findViewById(R.id.RbtnGoiMon);
        can = (RadioButton) findViewById(R.id.RbtnDuoc);
        not = (RadioButton) findViewById(R.id.RbtnKhong);
        TypeFood = new ArrayList<String>();
        capnhat = (Button) findViewById(R.id.btnCapnhat);
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, TypeFood);
        storageReference = FirebaseStorage.getInstance().getReference();
        storage = FirebaseStorage.getInstance().getReference();
        thoat = (Button) findViewById(R.id.cancel);
        intent = getIntent();
        controller = new FirebaseController(getApplicationContext());

    }
}