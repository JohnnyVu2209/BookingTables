package com.example.reservation_manager.KhuyenMai;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
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

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class XemDanhSachKhuyenMai extends AppCompatActivity {
    ListView listKM;
    ArrayList khuyenmai, keys;
    DatabaseReference databaseReference;
    myAdapter adapter;
    ImageView addkm;
    TextView addKM;
    Button L,H, luutt, de_co, de_khong;
    EditText code,program,start,end,percent, ed_code, ed_nameKM, ed_start, ed_exp, ed_percentage;
    KhuyenMai km;
    String c,p,s,e;
    int pe;
    String co,na,st,en;
    int perrrr;
    FirebaseController controller;
    KhuyenMai KM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_danh_sach_khuyen_mai);

        Anhxa();
        databaseReference.child("KhuyenMai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                khuyenmai.clear();
                keys.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                   KhuyenMai Khuyenmai = dataSnapshot.getValue(KhuyenMai.class);
                   khuyenmai.add(Khuyenmai);
                   keys.add(dataSnapshot.getKey());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

    private void Anhxa()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        listKM = (ListView)findViewById(R.id.lvDSKM);
        khuyenmai = new ArrayList<>();
        keys = new ArrayList<>();
        adapter = new myAdapter(getApplicationContext(), R.layout.km_item_list,khuyenmai);
        listKM.setAdapter(adapter);
        addkm = (ImageView)findViewById(R.id.btnAddKM);
        addKM = (TextView)findViewById(R.id.tvAddKM);
        controller = new FirebaseController(getApplicationContext());
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



    class myAdapter extends BaseAdapter
    {
        Context context;
        int mLayout;
        ArrayList<KhuyenMai> mKhuyenmai;

        public myAdapter(Context context, int layout, ArrayList<KhuyenMai> KMsList) {
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

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public View getView(final int position, View convertView, final ViewGroup parent)
        {
            final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mLayout, null);
            final String KEY = (String) keys.get(position);

            try
            {
                TextView code = (TextView)convertView.findViewById(R.id.tvcode);
                TextView namKM = (TextView)convertView.findViewById(R.id.tvnameKM);
                TextView start = (TextView)convertView.findViewById(R.id.tvstart);
                TextView exp = (TextView)convertView.findViewById(R.id.tvexp);
                TextView percentage = (TextView)convertView.findViewById(R.id.tvpercentage);

                code.setText("code: " + mKhuyenmai.get(position).code);
                namKM.setText("CT Khuyến mãi: " + mKhuyenmai.get(position).nameKM);
                start.setText("Ngày bắt đầu: " + mKhuyenmai.get(position).start);
                exp.setText("Ngày kết thúc: " + mKhuyenmai.get(position).exp);
                percentage.setText("Phần trăm: " + mKhuyenmai.get(position).percentage);

            }catch (Exception e)
            {
                e.printStackTrace();
            }

            //Bắt sự kiện cho Edit
            ImageView edit = (ImageView)convertView.findViewById(R.id.btnEditKM);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog mydialog = new Dialog(XemDanhSachKhuyenMai.this);
                    LayoutInflater inflater1 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View row = inflater1.inflate(R.layout.activity_sua_k_m,null);
                    mydialog.setContentView(row);
                    mydialog.show();

                    ed_code = (EditText)mydialog.findViewById(R.id.ecode);
                    ed_nameKM = (EditText)mydialog.findViewById(R.id.enameKM);
                    ed_start = (EditText)mydialog.findViewById(R.id.estart);
                    ed_exp = (EditText)mydialog.findViewById(R.id.eexp);
                    ed_percentage = (EditText)mydialog.findViewById(R.id.epercentage);
                    luutt = (Button)mydialog.findViewById(R.id.btnLuu);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference referencer = database.getReference("KhuyenMai");
                    referencer.child(KEY).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try
                            {
                                ed_code.setText(snapshot.getValue(KhuyenMai.class).code);
                                ed_nameKM.setText(snapshot.getValue(KhuyenMai.class).nameKM);
                                ed_start.setText(snapshot.getValue(KhuyenMai.class).start);
                                ed_exp.setText(snapshot.getValue(KhuyenMai.class).exp);
                                ed_percentage.setText(String.valueOf(snapshot.getValue(KhuyenMai.class).percentage));
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }



                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    luutt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            co = ed_code.getText().toString();
                            na = ed_nameKM.getText().toString();
                            st = ed_start.getText().toString();
                            en = ed_exp.getText().toString();
                            perrrr = Integer.parseInt(ed_percentage.getText().toString());
                            KM = new KhuyenMai(co,na,st,en,perrrr);
                            controller.UpdateData("KhuyenMai",KEY,KM);
                            mydialog.dismiss();
                        }
                    });

                }
            });

            //Bắt sự kiện cho Delete
            ImageView delete = (ImageView) convertView.findViewById(R.id.btnDeleteKM);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog mydialog = new Dialog(XemDanhSachKhuyenMai.this);
                    mydialog.setContentView(R.layout.delete_km);
                    mydialog.show();

                    de_co = (Button)mydialog.findViewById(R.id.btnC);
                    de_khong = (Button)mydialog.findViewById(R.id.btnK);

                    de_co.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase database =FirebaseDatabase.getInstance();
                            DatabaseReference referencer = database.getReference("KhuyenMai");
                            referencer.child(KEY).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(XemDanhSachKhuyenMai.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                }
                            });

                            mydialog.dismiss();
                        }
                    });
                    de_khong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(XemDanhSachKhuyenMai.this,"Không xóa",Toast.LENGTH_LONG).show();
                            mydialog.dismiss();
                        }
                    });
                }
            });

            return convertView;
        }
    }
}