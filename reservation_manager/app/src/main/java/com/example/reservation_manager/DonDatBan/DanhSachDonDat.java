package com.example.reservation_manager.DonDatBan;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.reservation_manager.KhachHang.XemDanhSachKhachHang;
import com.example.reservation_manager.KhuyenMai.XemDanhSachKhuyenMai;
import com.example.reservation_manager.MainActivity;
import com.example.reservation_manager.MonAn.MonAn;
import com.example.reservation_manager.MonAn.XemDanhSachMonAn;
import com.example.reservation_manager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DanhSachDonDat extends AppCompatActivity {
    TextView tvAddReservation;
    ImageView btnAddReservation;
    ListView lvDonDat;
    ArrayList<DonDatBan> dsDonDat;
    ArrayList key;
    donDatAdapter donDatAdapter;
    DatabaseReference databaseReference;
    //NAVIGATION VIEW
    Toolbar toolbar;
    NavigationView      navview;
    DrawerLayout        drawerLayout;
    ActionBarDrawerToggle toggle;
    private void AnhXa(){
        tvAddReservation = (TextView)findViewById(R.id.tvAddReservation);
        btnAddReservation = (ImageView)findViewById(R.id.btnAddReservation);
        lvDonDat = (ListView)findViewById(R.id.lvDanhSachDonDat);
        dsDonDat = new ArrayList<>();
        key = new ArrayList();
        donDatAdapter = new donDatAdapter(getApplicationContext(),R.layout.don_list,dsDonDat);
        lvDonDat.setAdapter(donDatAdapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("DonDatBan");
        //SET UP NAVIGATION VIEW
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navview = (NavigationView)findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navview.bringToFront();
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navview.setCheckedItem(R.id.mnuReservation);

        //THAY DOI HINH ACTIONBAR
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_don);
        AnhXa();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DonDatBan donDatBan = dataSnapshot.getValue(DonDatBan.class);
                    dsDonDat.add(donDatBan);
                    key.add(dataSnapshot.getKey());
                }
                donDatAdapter.notifyDataSetChanged();
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
                        Toast.makeText(getApplicationContext(), "Food are opening", Toast.LENGTH_SHORT).show();
                        intentActivity(XemDanhSachMonAn.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.mnuGuest:
                        Toast.makeText(getApplicationContext(), "Guest are opening", Toast.LENGTH_SHORT).show();
                        intentActivity(XemDanhSachKhachHang.class);
                        drawerLayout.closeDrawer(GravityCompat.START);break;
                    case R.id.mnuReservation:
                        Toast.makeText(getApplicationContext(), "Reservation are opened", Toast.LENGTH_SHORT).show();
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
    }
    private<T> void intentActivity(Class<T> CClass) {
        Intent intent = new Intent(getApplicationContext(),CClass);
        startActivity(intent);
        finish();
    }
    class donDatAdapter extends BaseAdapter{
        Context context;
        int mlayout;
        ArrayList<DonDatBan> listDonDat;

        public donDatAdapter(Context context, int mlayout, ArrayList<DonDatBan> listDonDat) {
            this.context = context;
            this.mlayout = mlayout;
            this.listDonDat = listDonDat;
        }

        @Override
        public int getCount() {
            return listDonDat.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup parent) {
            final String KEY = (String)key.get(i);
            LayoutInflater inflater  = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mlayout,null);
            TextView tvsoBan = (TextView)convertView.findViewById(R.id.tvSoBanDat);
            TextView tvNgayDat = (TextView)convertView.findViewById(R.id.tvNgayDat);
            TextView tvGioDat = (TextView)convertView.findViewById(R.id.tvGioDat);
            final TextView tvSoLuong = (TextView)convertView.findViewById(R.id.tvSoMonDat);
            TextView tvKhachDat = (TextView)convertView.findViewById(R.id.tvTenKhachDat);
            TextView tvSoDienThoaiDat = (TextView)convertView.findViewById(R.id.tvSoDienThoaiDat);
            ImageView btnEditReservation = (ImageView)convertView.findViewById(R.id.btnEditReservation);
            ImageView btnDeletReservation = (ImageView)convertView.findViewById(R.id.btnDeleteReservation);

            tvsoBan.setText("Bàn: " +String.valueOf(listDonDat.get(i).SoBan));
            tvNgayDat.setText("Ngày: " +  listDonDat.get(i).NgayNhanBan);
            tvGioDat.setText("Thời gian: " + listDonDat.get(i).GioNhanBan);

            if (listDonDat.get(i).GoiMonTruoc){
                tvSoLuong.setText("Số món gọi trước: " + listDonDat.get(i).SoLuongMon);
            }
            else {
                tvSoLuong.setText("Số món gọi trước: Không" );
            }
            tvKhachDat.setText("Khách: "+ listDonDat.get(i).NguoiDat);
            tvSoDienThoaiDat.setText("Số liên lạc: " + listDonDat.get(i).SoDienThoai);

            btnEditReservation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Xin lỗi chúng tôi vẫn đang cập nhật", Toast.LENGTH_SHORT).show();
                }
            });
            btnDeletReservation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog mydialog = new Dialog(DanhSachDonDat.this);
                    LayoutInflater inflater1 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View row = inflater1.inflate(R.layout.delete_kh,null);
                    mydialog.setContentView(row);
                    mydialog.show();
                    TextView tvMessage = (TextView)mydialog.findViewById(R.id.tvmessage);
                    tvMessage.setText("Bạn thật sự muốn xóa đơn này?");
                    Button yes = (Button)mydialog.findViewById(R.id.btnCo);
                    Button no = (Button)mydialog.findViewById(R.id.btnKhong);

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            databaseReference.child(KEY).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                                }
                            });

                            mydialog.dismiss();
                        }
                    });
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mydialog.dismiss();
                        }
                    });
                }
            });
            return convertView;
        }
    }
}
