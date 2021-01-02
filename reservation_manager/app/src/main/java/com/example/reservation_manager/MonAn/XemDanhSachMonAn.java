package com.example.reservation_manager.MonAn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservation_manager.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class XemDanhSachMonAn extends AppCompatActivity {
    ListView listView;
    ArrayList<MonAn> monan ;
    ImageView btnaddfood,btneditFood,btndeleteFood;
    TextView tvaddfood;
    MyAdapter adapter;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    //NAVIGATION VIEW
    Toolbar             toolbar;
    NavigationView      navview;
    DrawerLayout        drawerLayout;
    ActionBarDrawerToggle toggle;
    private void AnhXa() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        listView = (ListView)findViewById(R.id.lvDanhSachMonAn);
        monan = new ArrayList<MonAn>();
        adapter = new MyAdapter(getApplicationContext(),R.layout.item_list,monan);
        listView.setAdapter(adapter);
        btnaddfood = (ImageView) findViewById(R.id.btnAddFood);
        tvaddfood = (TextView)findViewById(R.id.tvAddFood);
        btneditFood = (ImageView)findViewById(R.id.btnEditFood);
        btndeleteFood = (ImageView)findViewById(R.id.btnDeleteFood);

        //SET UP NAVIGATION VIEW
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navview = (NavigationView)findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navview.bringToFront();
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navview.setCheckedItem(R.id.mnuFood);

        //THAY DOI HINH ACTIONBAR
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_danh_sach_mon_an);

        AnhXa();
        final MonAn monrong = new MonAn("","","",false,"FoodImages/Goicuhudua.png","");
        databaseReference.child("MonAn").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                monan.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MonAn monAn = dataSnapshot.getValue(MonAn.class);
                    monan.add(monAn);
                }
                monan.add(monrong);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //BAT SU KIEN NHAN NUT CUA NAVIGATION VIEW
        navview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mnuFood:
                        Toast.makeText(getApplicationContext(), "Food already opened", Toast.LENGTH_SHORT).show();
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
        AddAction();
       /* EditAction();
        DeleteAction();*/

    }
    // THÊM SỰ KIỆN CHO NÚT XÓA TẠI ĐÂY
    private void DeleteAction() {
        btndeleteFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    //THÊM SỰ KIỆN CHO NÚT SỬA TẠI ĐÂY
    private void EditAction() {
        btneditFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private  void AddAction(){
        btnaddfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentToCreateFood();
            }
        });
        tvaddfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentToCreateFood();
            }
        });
    }

    private void intentToCreateFood() {
        Intent Createintent = new Intent(XemDanhSachMonAn.this,ThemMonAn.class);
        startActivity(Createintent);
        finish();
    }


    class MyAdapter extends BaseAdapter {
        Context context;
        int mlayout;
        ArrayList<MonAn> mMonan;
        public MyAdapter(Context context,int layout,ArrayList<MonAn> monAnsList){
            this.context = context;
            this.mlayout = layout;
            this.mMonan = monAnsList;
        }

        @Override
        public int getCount() {
            return mMonan.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mlayout,null);

            final File file;
            try {
                final ImageView imgma = (ImageView)view.findViewById(R.id.imgMonAn);
                TextView  tvten = (TextView)view.findViewById(R.id.tvTenMon);
                TextView  tvloai = (TextView)view.findViewById(R.id.tvLoaiMon);
                TextView tvgia = (TextView)view.findViewById(R.id.tvGia);
                file = File.createTempFile("image","png");
                storageReference.child(mMonan.get(i).idhinh).getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        imgma.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Image Failed to load", Toast.LENGTH_SHORT).show();
                    }
                });
                Locale localeVN = new Locale("vi", "VN");
                NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                String vnd = "";
                if (mMonan.get(i).gia != ""){
                    vnd = currencyVN.format(Long.parseLong(mMonan.get(i).gia));
                }
                tvten.setText(mMonan.get(i).tenmonan);
                tvloai.setText(mMonan.get(i).loaimonan);
                tvgia.setText("Giá: "+ vnd);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return view;
        }
    }

}