package com.example.reservation_manager.MonAn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservation_manager.DonDatBan.DanhSachDonDat;
import com.example.reservation_manager.KhachHang.KhachHang;
import com.example.reservation_manager.KhachHang.XemDanhSachKhachHang;
import com.example.reservation_manager.KhuyenMai.XemDanhSachKhuyenMai;
import com.example.reservation_manager.MainActivity;
import com.example.reservation_manager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;

import com.google.android.gms.tasks.Task;
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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class XemDanhSachMonAn extends AppCompatActivity {
    ListView listView;
    ArrayList<MonAn> monan = new ArrayList<>();
    ImageView btnaddfood;
    TextView tvaddfood;
    MyAdapter adapter;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    EditText search_bar;
    //NAVIGATION VIEW
    Toolbar             toolbar;
    NavigationView      navview;
    DrawerLayout        drawerLayout;
    ActionBarDrawerToggle toggle;
    ArrayList keyArr = new ArrayList();
    private void AnhXa() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        listView = (ListView)findViewById(R.id.lvDanhSachMonAn);
        monan = new ArrayList<MonAn>();
        adapter = new MyAdapter(getApplicationContext(),R.layout.item_list,monan);
        listView.setAdapter(adapter);
        btnaddfood = (ImageView) findViewById(R.id.btnAddFood);
        tvaddfood = (TextView)findViewById(R.id.tvAddFood);

        //SET UP NAVIGATION VIEW
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String key;
        ArrayList keyArr = new ArrayList();
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

        search_bar = (EditText)findViewById(R.id.search_bar);

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
                    keyArr.add(dataSnapshot.getKey());

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
                        intentActivity(XemDanhSachKhachHang.class);
                        drawerLayout.closeDrawer(GravityCompat.START);break;
                    case R.id.mnuReservation:
                        Toast.makeText(getApplicationContext(), "Reservation are opening", Toast.LENGTH_SHORT).show();
                        intentActivity(DanhSachDonDat.class);
                        drawerLayout.closeDrawer(GravityCompat.START);break;
                    case R.id.mnuTables:
                        Toast.makeText(getApplicationContext(), "Tables are opening", Toast.LENGTH_SHORT).show();
                        intentActivity(MainActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);break;
                    case R.id.mnuPromotion:
                        Toast.makeText(getApplicationContext(), "Tables are opening", Toast.LENGTH_SHORT).show();
                        intentActivity(XemDanhSachKhuyenMai.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
        AddAction();
       /* EditAction();
        DeleteAction();*/

        databaseReference = FirebaseDatabase.getInstance().getReference().child("MonAn");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){
                    keyArr.add(child.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private<T> void intentActivity(Class<T> CClass) {
        Intent intent = new Intent(XemDanhSachMonAn.this,CClass);
        startActivity(intent);
        finish();
    }

    private void AddAction() {
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
        Intent Createintent = new Intent(XemDanhSachMonAn.this, ThemMonAn.class);
        startActivity(Createintent);
        finish();
    }
     class MyAdapter extends BaseAdapter {
        Context context;
        int mlayout;
        ArrayList<MonAn> mMonan;

        public MyAdapter(Context context, int layout, ArrayList<MonAn> monAnsList) {
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
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        public class Holder {
            ImageView btneditFood, btndeleteFood;
            ImageView imgma;
            TextView tvten;
            TextView tvloai;
            TextView tvgia;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final String KEY = (String) keyArr.get(i);
            Holder holder = null;
            if (view == null) {
                holder = new Holder();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(mlayout, null);
                holder.btneditFood = (ImageView) view.findViewById(R.id.btnEditFood);
                holder.btndeleteFood = (ImageView) view.findViewById(R.id.btnDeleteFood);
                holder.imgma = (ImageView) view.findViewById(R.id.imgMonAn);
                holder.tvten = (TextView) view.findViewById(R.id.tvTenMon);
                holder.tvloai = (TextView) view.findViewById(R.id.tvLoaiMon);
                holder.tvgia = (TextView) view.findViewById(R.id.tvGia);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }

            final File file;
            try {
                file = File.createTempFile("image", "png");
                final Holder finalHolder = holder;
                if(mMonan.get(i).idhinh != null){
                    storageReference.child(mMonan.get(i).idhinh).getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            finalHolder.imgma.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Image Failed to load", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                Locale localeVN = new Locale("vi", "VN");
                NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                String vnd = "";
                if (mMonan.get(i).gia != ""){
                    vnd = currencyVN.format(Long.parseLong(mMonan.get(i).gia));
                }

                holder.tvten.setText(mMonan.get(i).tenmonan);
                holder.tvloai.setText(mMonan.get(i).loaimonan);
                holder.tvgia.setText("Giá: " + vnd);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ImageView de_food = (ImageView) view.findViewById(R.id.btnDeleteFood); //BẮT SỰ KIỆN CHO DELETE
            de_food.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog mydialog = new Dialog(XemDanhSachMonAn.this);
                    LayoutInflater inflater1 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View row = inflater1.inflate(R.layout.delete_ma,null);
                    mydialog.setContentView(row);
                    mydialog.show();

                    Button food_Co = (Button) mydialog.findViewById(R.id.monan_btnco);
                    Button food_khong = (Button)mydialog.findViewById(R.id.monan_btnKhong);

                    food_Co.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase database =FirebaseDatabase.getInstance();
                            DatabaseReference referencer = database.getReference("MonAn");
                            referencer.child(KEY).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(XemDanhSachMonAn.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                }
                            });

                            mydialog.dismiss();
                        }
                    });
                    food_khong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(XemDanhSachMonAn.this,"Không xóa",Toast.LENGTH_LONG).show();
                            mydialog.dismiss();
                        }
                    });
                }
            });


            /*holder.btndeleteFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("MonAn");
                    myRef.child(keyArr.get(i).toString()).removeValue();
                    Toast.makeText(getApplicationContext(), keyArr.get(i).toString(), Toast.LENGTH_LONG).show();
                }
            });*/

            holder.btneditFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    Toast.makeText(context, "Cap nhat", Toast.LENGTH_LONG).show();
                    Intent updateIntent = new Intent(XemDanhSachMonAn.this, CapNhatMonAn.class);
                    updateIntent.putExtra("id", keyArr.get(i).toString());
                    updateIntent.putExtra("ten", mMonan.get(i).tenmonan);
                    updateIntent.putExtra("gia", mMonan.get(i).gia);
                    updateIntent.putExtra("loai", mMonan.get(i).loaimonan);
                    updateIntent.putExtra("mota", mMonan.get(i).mota);
                    updateIntent.putExtra("goimon", mMonan.get(i).goimon);
                    updateIntent.putExtra("hinh", mMonan.get(i).idhinh);
                    startActivity(updateIntent);
                    finish();
                }
            });

            return view;

        }



    }

}