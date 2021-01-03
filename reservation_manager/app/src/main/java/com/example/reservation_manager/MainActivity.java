package com.example.reservation_manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.reservation_manager.BanAn.SubActivity_TableList;
import com.example.reservation_manager.BanAn.SubActivity_TableMap;
import com.example.reservation_manager.DonDatBan.DanhSachDonDat;
import com.example.reservation_manager.KhachHang.XemDanhSachKhachHang;
import com.example.reservation_manager.KhuyenMai.XemDanhSachKhuyenMai;
import com.example.reservation_manager.MonAn.XemDanhSachMonAn;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button map, list;
    FragmentManager fragmentManager;
    //NAVIGATION VIEW
    Toolbar toolbar;
    NavigationView navview;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map = findViewById(R.id.btn_map);
        list = findViewById(R.id.btn_list);
        map.setOnClickListener(this);
        list.setOnClickListener(this);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new SubActivity_TableList(), null);
        //fragmentTransaction.replace(R.id.container, new FragmentA(), null);
        fragmentTransaction.commit();
        //SET UP NAVIGATION VIEW
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navview = (NavigationView)findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navview.bringToFront();
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navview.setCheckedItem(R.id.mnuTables);

        //THAY DOI HINH ACTIONBAR
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

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
                        Toast.makeText(getApplicationContext(), "Reservation are opening", Toast.LENGTH_SHORT).show();
                        intentActivity(DanhSachDonDat.class);
                        drawerLayout.closeDrawer(GravityCompat.START);break;
                    case R.id.mnuTables:
                        Toast.makeText(getApplicationContext(), "Tables are opened", Toast.LENGTH_SHORT).show();
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_list:
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container, new SubActivity_TableList());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.btn_map:
                FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                fragmentTransaction1.replace(R.id.container, new SubActivity_TableMap());
                fragmentTransaction1.addToBackStack(null);
                fragmentTransaction1.commit();
                break;
        }
    }
}
// test commit //