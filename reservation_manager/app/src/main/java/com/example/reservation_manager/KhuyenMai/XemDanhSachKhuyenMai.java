package com.example.reservation_manager.KhuyenMai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservation_manager.FirebaseController;
import com.example.reservation_manager.KhachHang.KhachHang;
import com.example.reservation_manager.KhachHang.XemDanhSachKhachHang;
import com.example.reservation_manager.KhachHang.xemchitietkh;
import com.example.reservation_manager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class XemDanhSachKhuyenMai extends AppCompatActivity {
    ListView listKM;
    ArrayList Khuyenmai, keys;
    DatabaseReference databaseReference;
    Adapter adapter;
    ImageView addkm;
    TextView addKM;
    Button L,H;
    EditText code,program,start,end,percent;
    KhuyenMai km;
    String c,p,s,e;
    int pe;
    FirebaseController controller;

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
        addkm = (ImageView)findViewById(R.id.btnAddKM);
        addKM = (TextView)findViewById(R.id.tvAddKM);
        controller = new FirebaseController(getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_danh_sach_khuyen_mai);

        Anhxa();
//        databaseReference.child("KhuyenMai").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Khuyenmai.clear();
//                keys.clear();
//                for(DataSnapshot dataSnapshot : snapshot.getChildren())
//                {
//                   KhuyenMai KhuyenMai = dataSnapshot.getValue(KhuyenMai.class);
//                   Khuyenmai.add(KhuyenMai);
//                   keys.add(snapshot.getKey());
//                }
//                adapter.notify();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        // BẮT SỰ KIỆN THÊM KHUYẾN MÃI
        addkm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog mydialog = new Dialog(XemDanhSachKhuyenMai.this);
                mydialog.setContentView(R.layout.activity_them_k_m);
                mydialog.show();

                L = (Button)mydialog.findViewById(R.id.btnL);
                H = (Button)mydialog.findViewById(R.id.btnH);

                code = (EditText)mydialog.findViewById(R.id.edcode);
                program = (EditText)mydialog.findViewById(R.id.ednameKM);
                start = (EditText)mydialog.findViewById(R.id.edstart);
                end = (EditText)mydialog.findViewById(R.id.edexp);
                percent = (EditText)mydialog.findViewById(R.id.edpercentage);

                L.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            addKM();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mydialog.dismiss();
                    }
                });
                H.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mydialog.dismiss();
                    }
                });
            }
        });

        addKM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog mydialog = new Dialog(XemDanhSachKhuyenMai.this);
                mydialog.setContentView(R.layout.activity_them_k_m);
                mydialog.show();

                L = (Button) mydialog.findViewById(R.id.btnL);
                H = (Button) mydialog.findViewById(R.id.btnH);

                code = (EditText) mydialog.findViewById(R.id.edcode);
                program = (EditText) mydialog.findViewById(R.id.ednameKM);
                start = (EditText) mydialog.findViewById(R.id.edstart);
                end = (EditText) mydialog.findViewById(R.id.edexp);
                percent = (EditText) mydialog.findViewById(R.id.edpercentage);

                L.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            addKM();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mydialog.dismiss();
                    }
                });
                H.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mydialog.dismiss();
                    }
                });
            }
        });
    }

    private void addKM() {
        c = code.getText().toString();
        p = program.getText().toString();
        s = start.getText().toString();
        e = end.getText().toString();
        pe = Integer.parseInt(percent.getText().toString());

        km = new KhuyenMai(c,p,s,e,pe);
        controller.WirteWithAutoIncreaseKey("KhuyenMai",km);
    }
}