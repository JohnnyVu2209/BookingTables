package com.example.reservation_manager.KhuyenMai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.reservation_manager.KhachHang.KhachHang;
import com.example.reservation_manager.KhachHang.XemDanhSachKhachHang;
import com.example.reservation_manager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class XemDanhSachKhuyenMai extends AppCompatActivity {
    ListView listKM;
    ArrayList Khuyenmai, keys;
    DatabaseReference databaseReference;
    Adapter adapter;

    class myAdater extends BaseAdapter
    {
        Context context;
        int mLayout;
        ArrayList<KhuyenMai> mKhuyenmai;

        public myAdater(Context context, int layout, ArrayList<KhuyenMai> KMsList) {
            this.context = context;
            this.mLayout = layout;
            this.mKhuyenmai = KMsList;
        }

        @Override
        public int getCount() {
            return mKhuyenmai.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    private void Anhxa()
    {
        listKM = (ListView)findViewById(R.id.lvDSKM);
        adapter = new myAdater(getApplicationContext(),R.layout.km_item_list,Khuyenmai);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_danh_sach_khuyen_mai);

        databaseReference.child("KhuyenMai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Khuyenmai.clear();
                keys.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                   KhuyenMai KhuyenMai = dataSnapshot.getValue(KhuyenMai.class);
                   Khuyenmai.add(KhuyenMai);
                   keys.add(snapshot.getKey());
                }
                adapter.notify();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}