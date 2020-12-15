package com.example.reservation_manager.KhachHang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XemDanhSachKhachHang extends AppCompatActivity {
    ListView listView;
    ArrayList<KhachHang> khachhang ;
    ImageView btneditKH,btndeleteKH;
    MyAdapter adapter;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemdskh);

        AnhXa();
        databaseReference.child("KhachHang").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                KhachHang khachHang = snapshot.getValue(KhachHang.class);
                khachhang.add(khachHang);
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
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int pos = parent.getPositionForView(view);

    }

    private void AnhXa() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        listView = (ListView)findViewById(R.id.lvDSKH);
        khachhang = new ArrayList<KhachHang>();
        adapter = new MyAdapter(getApplicationContext(),R.layout.user_item_list,khachhang);
        listView.setAdapter(adapter);
        btneditKH = (ImageView)findViewById(R.id.btnEditkhach);
        btndeleteKH = (ImageView)findViewById(R.id.btnDeletekhach);
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mlayout, null);

            final File file;
            try {
                final ImageView imgma = (ImageView) view.findViewById(R.id.imgProfile);
                TextView tvhoten = (TextView) view.findViewById(R.id.tvHoTen);
                TextView tvsdt = (TextView) view.findViewById(R.id.tvSdt);
                TextView tvngaysinh = (TextView) view.findViewById(R.id.tvNgaySinh);
                TextView tvgioitinh = (TextView) view.findViewById(R.id.tvGioiTinh);
                TextView tvsldat = (TextView) view.findViewById(R.id.tvSldat);
                file = File.createTempFile("image", "png");
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
                tvhoten.setText("Họ tên: " + mKhachhang.get(i).hoten);
                tvsdt.setText("Số điện thoại: " + mKhachhang.get(i).sdt);
                tvngaysinh.setText("Ngày sinh: " + mKhachhang.get(i).ngaysinh);
                if (mKhachhang.get(i).gioitinh == true)
                {
                    tvgioitinh.setText("Giới tính: Nam");
                }
                else {
                    tvgioitinh.setText("Giới tính: Nữ");
                }
                tvsldat.setText("Số lượng đặt: " + mKhachhang.get(i).sldat);
            } catch (IOException e) {
                e.printStackTrace();
            }

            final String pos = String.valueOf(i);
            ImageView edit = (ImageView) view.findViewById(R.id.btnEditkhach); //BẮT SỰ KIỆN CHO EDIT
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent editKHintent = new Intent(XemDanhSachKhachHang.this,SuaKhachHang.class);
                    editKHintent.putExtra("position", pos);
                    startActivity(editKHintent);
                }
            });

            ImageView delete = (ImageView) view.findViewById(R.id.btnDeletekhach); //BẮT SỰ KIỆN CHO DELETE
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            return view;
        }
    }
}
