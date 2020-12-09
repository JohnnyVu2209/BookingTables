package com.example.reservation_manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ThemMonAn extends AppCompatActivity {
    ImageView           hinhdoan;
    EditText            tenmonan,giamonan,mota;
    Spinner             loaima;
    Uri                 imguri;
    Boolean             isGoiMon = false;
    RadioGroup          group;
    RadioButton         can,not;
    Button              add;
    ArrayList<String>   TypeFood;
    ArrayAdapter<String>adapter;
    StorageReference    storage ;
    StorageTask         uploadTask;
    MonAn               monAn;
    String              idhinh;
    FirebaseController  controller;
    DatabaseReference   referencer = FirebaseDatabase.getInstance().getReference();
    private final String TAG = "READ DATABASE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_mon_an);

        AnhXa();
        //Lay hinh trong gallery
        hinhdoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Filechooser();
            }
        });
        //hien thi loai mon an
        referencer.child("LoaiMonAn").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                LoaiMonAn loaiMonAn = snapshot.getValue(LoaiMonAn.class);
                TypeFood.add(loaiMonAn.TenLoai);
                adapter.notifyDataSetChanged();
                Toast.makeText(ThemMonAn.this, ""+TypeFood.toArray(), Toast.LENGTH_SHORT).show();
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
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(ThemMonAn.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                }
                else {
                    String mt = "";
                    try {
                        mt = mota.getText().toString();
                        UploadData(mt);
                    }catch (Exception e){
                        mt = "";
                        UploadData(mt);
                    }


                    controller.WirteWithAutoIncreaseKey("MonAn",monAn);
                }
            }
        });

    }
    private void UploadData(String mt){
        String tenmon,gia,loai= "";
        int radioId =0;
        Fireuploader();
        tenmon = tenmonan.getText().toString();
        gia = giamonan.getText().toString();
        loai = loaima.getSelectedItem().toString();
        radioId = group.getCheckedRadioButtonId();
        if(can.getId() == radioId){
            isGoiMon = true;
        }
        monAn = new MonAn(tenmon,gia,loai,isGoiMon,idhinh,mt);
    }

    private String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
    private void Fireuploader() {
        idhinh = removeAccent(tenmonan.getText().toString()).replaceAll("\\s","")+"."+getExtension(imguri);
        StorageReference ref = storage.child(idhinh);
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
            hinhdoan.setImageURI(imguri);
        }
    }

    private void AnhXa() {
        hinhdoan   = (ImageView)findViewById(R.id.profile_image);
        tenmonan   = (EditText)findViewById(R.id.etTenDoAn);
        giamonan   = (EditText)findViewById(R.id.etGiaMonAn);
        loaima     = (Spinner)findViewById(R.id.ListLoaiMonAn);
        group      = (RadioGroup)findViewById(R.id.RbtnGoiMon);
        can        = (RadioButton)findViewById(R.id.RbtnDuoc);
        not        = (RadioButton)findViewById(R.id.RbtnKhong);
        add        = (Button)findViewById(R.id.btnThem);
        TypeFood   = new ArrayList<String>();
        adapter    = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,TypeFood);
        loaima.setAdapter(adapter);

        storage    = FirebaseStorage.getInstance().getReference("FoodImages");
        controller = new FirebaseController(getApplicationContext());

    }
}