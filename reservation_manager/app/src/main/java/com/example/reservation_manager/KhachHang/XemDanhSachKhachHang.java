package com.example.reservation_manager.KhachHang;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservation_manager.DonDatBan.DanhSachDonDat;
import com.example.reservation_manager.KhuyenMai.XemDanhSachKhuyenMai;
import com.example.reservation_manager.MainActivity;
import com.example.reservation_manager.MonAn.XemDanhSachMonAn;
import com.example.reservation_manager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
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
import java.util.ArrayList;

public class XemDanhSachKhachHang extends AppCompatActivity {
    ListView listView;
    ArrayList khachhang, keys ;
    ImageView btnaddtypekh;
    MyAdapter adapter;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    Button yes, no, save, cancel;
    TextView addtypekh;

    Toolbar toolbar;
    NavigationView navview;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemdskh);

        AnhXa();
        //final KhachHang khachrong = new KhachHang("","","",false,0,"Demo.jpg");
        databaseReference.child("KhachHang").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                khachhang.clear();
                keys.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    KhachHang khachHang = dataSnapshot.getValue(KhachHang.class);
                    khachhang.add(khachHang);
                    keys.add(dataSnapshot.getKey());
                }
                //khachhang.add(khachrong);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int i, long id) {
                Intent xemchitietintent = new Intent(XemDanhSachKhachHang.this, xemchitietkh.class);
                String KEY = (String) keys.get(i);
                xemchitietintent.putExtra("key", KEY);
                startActivity(xemchitietintent);
            }
        });

        btnaddtypekh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog myadddialog = new Dialog(XemDanhSachKhachHang.this);
                myadddialog.setContentView(R.layout.activity_themloaikh);
                myadddialog.show();

                save = (Button)myadddialog.findViewById(R.id.btnLuu);
                cancel = (Button)myadddialog.findViewById(R.id.btnHuy);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myadddialog.dismiss();
                    }
                });
            }
        });

        addtypekh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog myadddialog1 = new Dialog(XemDanhSachKhachHang.this);
                myadddialog1.setContentView(R.layout.activity_themloaikh);
                myadddialog1.show();

                save = (Button)myadddialog1.findViewById(R.id.btnLuu);
                cancel = (Button)myadddialog1.findViewById(R.id.btnHuy);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myadddialog1.dismiss();
                    }
                });
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
                        Toast.makeText(getApplicationContext(), "Guest are opened", Toast.LENGTH_SHORT).show();
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
    }
    private<T> void intentActivity(Class<T> CClass) {
        Intent intent = new Intent(getApplicationContext(),CClass);
        startActivity(intent);
        finish();
    }
    private void AnhXa() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        listView = (ListView)findViewById(R.id.lvDSKH);
        khachhang = new ArrayList<>();
        keys = new ArrayList<>();
        adapter = new MyAdapter(getApplicationContext(),R.layout.user_item_list,khachhang);
        listView.setAdapter(adapter);
        btnaddtypekh = (ImageView)findViewById(R.id.btnAddTypeKH);
        addtypekh = (TextView)findViewById(R.id.tvAddTypeKH);
        //SET UP NAVIGATION VIEW
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navview = (NavigationView)findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navview.bringToFront();
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navview.setCheckedItem(R.id.mnuGuest);

        //THAY DOI HINH ACTIONBAR
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
    }
    class MyAdapter extends BaseAdapter {
        Context context;
        int mlayout;
        ArrayList<KhachHang> mKhachhang;

        public MyAdapter(Context context, int layout, ArrayList<KhachHang> KHsList) {
            this.context = context;
            this.mlayout = layout;
            this.mKhachhang = KHsList;
        }

        @Override
        public int getCount() {
            return mKhachhang.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public View getView(final int i, View view, final ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mlayout, null);
            final String KEY = (String) keys.get(i);

            final File file;
            try {
                final ImageView imgma = (ImageView) view.findViewById(R.id.imgProfile);
                TextView tvhoten = (TextView) view.findViewById(R.id.tvHoTen);
                TextView tvsdt = (TextView) view.findViewById(R.id.tvSdt);
                TextView tvloai = (TextView) view.findViewById(R.id.tvLKH);
                TextView tvvip = (TextView) view.findViewById(R.id.tvVipStatus);
                file = File.createTempFile("image", "png");
                if (mKhachhang.get(i).idavatar != null){
                    storageReference.child(mKhachhang.get(i).idavatar).getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            imgma.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Không thể load ảnh, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                tvhoten.setText("Họ tên: " + mKhachhang.get(i).hoten);
                tvsdt.setText("Số điện thoại: " + mKhachhang.get(i).sdt);
                if (mKhachhang.get(i).thanthiet == true) {
                    tvloai.setText("Loại khách hàng: khách hàng thân thiết");
                } else {
                    tvloai.setText("Loại khách hàng: khách hàng thường");
                }
                if (mKhachhang.get(i).vip == true) {
                    tvvip.setText("VIP MEMBER");
                    tvvip.setTextColor(Color.RED);
                } else {
                    tvvip.setText("NOT VIP");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            ImageView edit = (ImageView) view.findViewById(R.id.btnEditkhach); //BẮT SỰ KIỆN CHO EDIT
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent editKHintent = new Intent(XemDanhSachKhachHang.this,SuaKhachHang.class);
                    editKHintent.putExtra("key", KEY);
                    startActivity(editKHintent);
                }
            });


            ImageView delete = (ImageView) view.findViewById(R.id.btnDeletekhach); //BẮT SỰ KIỆN CHO DELETE
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog mydialog = new Dialog(XemDanhSachKhachHang.this);
                    LayoutInflater inflater1 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View row = inflater1.inflate(R.layout.delete_kh,null);
                    mydialog.setContentView(row);
                    mydialog.show();

                    yes = (Button)mydialog.findViewById(R.id.btnCo);
                    no = (Button)mydialog.findViewById(R.id.btnKhong);

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase database =FirebaseDatabase.getInstance();
                            DatabaseReference referencer = database.getReference("KhachHang");
                            referencer.child(KEY).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(XemDanhSachKhachHang.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                }
                            });
                          
                            mydialog.dismiss();
                        }
                    });
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(XemDanhSachKhachHang.this,"Không xóa",Toast.LENGTH_LONG).show();
                            mydialog.dismiss();
                        }
                    });
                }
            });

            return view;
        }
    }
}
