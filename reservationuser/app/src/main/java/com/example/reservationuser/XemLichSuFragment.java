package com.example.reservationuser;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;

public class XemLichSuFragment extends Fragment {
    MyAdapter adapter;
    DatabaseReference databaseReference;
    ListView listView;
    public View view;
    XemLichSuFragment context = this;
    ArrayList keys;
    ArrayList<DonDatBan> DDB;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private void AnhXa(View view) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        listView = (ListView) view.findViewById(R.id.lvLichSuDat);
        keys = new ArrayList();
        DDB = new ArrayList<DonDatBan>();
        adapter = new MyAdapter(getContext(),R.layout.lich_su_dat_list,DDB,keys);
        listView.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("Lich su dat", "LSĐ: onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("Lich su dat", "LSĐ: onCreateView");
        view = inflater.inflate(R.layout.fragment_xem_lich_su, container, false);

//        final String pos = String.valueOf(inflater);
        AnhXa(view);
        databaseReference.child("DonDatBan").orderByChild("SoDienThoai").equalTo(user.getPhoneNumber()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DDB.clear();
                keys.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DonDatBan Don = dataSnapshot.getValue(DonDatBan.class);
                    DDB.add(Don);
                    keys.add(dataSnapshot.getKey());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        Log.d("LichSuDat", "LichSuDat: onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("LichSuDat", "LichSuDat: onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("LichSuDat", "LichSuDat: onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("LichSuDat", "LichSuDat: onStop");
        super.onStop();
    }


    @Override
    public void onDestroyView() {
//        int a = 5;
        Log.d("LichSuDat", "LichSuDat: onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("LichSuDat", "LichSuDat: onDestroy");
        super.onDestroy();
    }
}

class MyAdapter extends BaseAdapter {
    Context context;
    int mlayout;
    ArrayList<DonDatBan> mDonDatBan;
    FirebaseController controller;
    ArrayList keyss;


    public MyAdapter(Context context, int layout, ArrayList<DonDatBan> DonDatBansList, ArrayList key) {
        this.context = context;
        this.mlayout = layout;
        this.mDonDatBan = DonDatBansList;
        this.keyss = key;

    }

    @Override
    public int getCount() {
        return mDonDatBan.size();
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(mlayout, null);

        TextView soban = (TextView) view.findViewById(R.id.tvSTTBan);
        TextView gio = (TextView) view.findViewById(R.id.tvGioDat);
        TextView ngay = (TextView) view.findViewById(R.id.tvNgayDat);
        TextView mondadat = (TextView) view.findViewById(R.id.tvMonDaDat);

        soban.setText("Bàn " + mDonDatBan.get(i).SoBan);
        gio.setText(mDonDatBan.get(i).GioNhanBan);
        ngay.setText(mDonDatBan.get(i).NgayNhanBan);
        mondadat.setText("Số món đã gọi: " + mDonDatBan.get(i).SoLuongMon);

        return view;
    }
}
