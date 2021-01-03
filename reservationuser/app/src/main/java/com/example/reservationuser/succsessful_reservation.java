package com.example.reservationuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class succsessful_reservation extends AppCompatActivity implements Serializable {
    DonDatBan donDatBan;
    TextView tvTen,tvSo,tvNgay,tvGio,tvSoBan,tvGoiMon;
    ArrayList<MonAn> mMonan;
    Button btnBackToHome;
    ListView listMonGoiTruocThanhCong;
    LinearLayout llGoiMonThanhCong;
    monDaChonAdapter adapter;
    private void Anhxa(){
        donDatBan = getIntent().getParcelableExtra("DON");
        tvTen = (TextView)findViewById(R.id.tvTenNgDatThanhCong);
        tvSo = (TextView)findViewById(R.id.tvSDTNgDatThanhCong);
        tvNgay = (TextView)findViewById(R.id.tvNgayDatThanhCong);
        tvGio = (TextView)findViewById(R.id.tvGioDatThanhCong);
        tvSoBan = (TextView)findViewById(R.id.tvSoBanDatThanhCong);
        tvGoiMon = (TextView)findViewById(R.id.tvXacNhanGoiMon);
        btnBackToHome = (Button)findViewById(R.id.btnBackToHome);
        mMonan  = getIntent().getParcelableArrayListExtra("MONAN");
        llGoiMonThanhCong = (LinearLayout)findViewById(R.id.llGoiMonThanhCong);
        listMonGoiTruocThanhCong = (ListView)findViewById(R.id.listMonGoiTruocThanhCong);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succsessful_reservation);
        Anhxa();
        tvTen.setText(donDatBan.NguoiDat);
        tvSo.setText(donDatBan.SoDienThoai);
        tvNgay.setText(donDatBan.NgayNhanBan);
        tvGio.setText(donDatBan.GioNhanBan);
        tvSoBan.setText(String.valueOf(donDatBan.SoBan));
        if (donDatBan.GoiMonTruoc){
            tvGoiMon.setText("Có");
            llGoiMonThanhCong.setVisibility(View.VISIBLE);
            adapter = new monDaChonAdapter(this,R.layout.mon_da_chon_list,mMonan);
            listMonGoiTruocThanhCong.setAdapter(adapter);
        }
        else {
            tvGoiMon.setText("Không");
            llGoiMonThanhCong.setVisibility(View.INVISIBLE);
        }
        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentToHome();
            }
        });
    }
    private void intentToHome(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    class monDaChonAdapter extends BaseAdapter {
        Context context;
        int mlayout;
        ArrayList<MonAn> mMonAn;

        public monDaChonAdapter(Context context, int mlayout, ArrayList<MonAn> mMonAn) {
            this.context = context;
            this.mlayout = mlayout;
            this.mMonAn = mMonAn;
        }

        @Override
        public int getCount() {
            Log.d("SIZE", "getCount: " + mMonAn.size());
            return mMonAn.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mlayout,null);
            TextView tvTenMon = (TextView)convertView.findViewById(R.id.tvTenMonDaChon);
            ImageView imgRemove = (ImageView)convertView.findViewById(R.id.imgRemove);
            tvTenMon.setText(mMonAn.get(position).tenmonan);
            imgRemove.setVisibility(View.GONE);
            return convertView;
        }

        public ArrayList<MonAn> getmMonAn() {
            return mMonAn;
        }
    }
}