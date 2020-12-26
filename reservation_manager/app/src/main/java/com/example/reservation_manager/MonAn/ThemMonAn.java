package com.example.reservation_manager.MonAn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
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
import com.google.android.material.navigation.NavigationView;
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
    //CAC NUT TRONG GIAO DEIN
    ImageView           hinhdoan;
    EditText            tenmonan,giamonan,mota;
    Spinner             loaima;
    Uri                 imguri;
    Boolean             isGoiMon = false;
    RadioGroup          group;
    RadioButton         can,not;
    Button              add;
    //HIEN THI DROP LIST LOAI MON
    ArrayList<String>   TypeFood;
    ArrayAdapter<String>adapter;

    StorageTask         uploadTask;
    MonAn               monAn;
    String              idhinh;
    FirebaseController  controller;
    //NAVIGATION VIEW
    Toolbar             toolbar;
    NavigationView      navview;
    DrawerLayout        drawerLayout;
    ActionBarDrawerToggle toggle;
    //FIREBASE
    StorageReference    storage ;
    DatabaseReference   referencer = FirebaseDatabase.getInstance().getReference();
    private final String TAG = "READ DATABASE";

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

        storage    = FirebaseStorage.getInstance().getReference();
        controller = new FirebaseController(getApplicationContext());
        //SET UP NAVIGATION VIEW
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navview = (NavigationView)findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navview.bringToFront();
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //THAY DOI HINH ACTIONBAR
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);


    }

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
        //BAT SU KIEN THEM MON AN
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
                    intentToListFood();
                }
            }
        });
        //BAT SU KIEN NHAN NUT CUA NAVIGATION VIEW
        navview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mnuFood:
                        intentToListFood();
                        Toast.makeText(getApplicationContext(), "Food are opening", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.mnuGuest:
                        Toast.makeText(getApplicationContext(), "Guest are opening", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);break;
                    case R.id.mnuReservation:
                        Toast.makeText(getApplicationContext(), "Reservation are opening", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);break;
                    case R.id.mnuTables:
                        Toast.makeText(getApplicationContext(), "Tables are opening", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);break;
                }
                return true;
            }
        });
    }
    private void intentToListFood(){
        Intent ListFood_intent = new Intent(ThemMonAn.this,XemDanhSachMonAn.class);
        ListFood_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ListFood_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(ListFood_intent);
        finish();
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
    //HAM UPLOAD HINH LEN FIREBASE
    private void Fireuploader() {
        idhinh = "FoodImages/"+removeAccent(tenmonan.getText().toString()).replaceAll("\\s","")+"."+"png";
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
    //LOAI BO DAU TIENG VIET
    private String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
    //CHON HINH TU FILE MEDIA TRONG MAY
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

}