package com.example.reservation_manager.BanAn;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reservation_manager.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class ViewTableList extends AppCompatActivity {
    ListView listView;
    ArrayList<tables> banan ;
    ImageView btnaddBan,btneditBan,btndeleteBan;
    TextView tvaddBan;
    MyAdapter adapter;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_b);

        AnhXa();
        databaseReference.child("Ban").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                tables banAn = snapshot.getValue(tables.class);
                banan.add(banAn);
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
//        AddAction();
        EditAction();
        DeleteAction();

    }
    // THÊM SỰ KIỆN CHO NÚT XÓA TẠI ĐÂY
    private void DeleteAction() {
        btndeleteBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



    //THÊM SỰ KIỆN CHO NÚT SỬA TẠI ĐÂY
    private void EditAction() {
        btneditBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }


//    private  void AddAction(){
//        btnaddBan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intentToCreateTable();
//            }
//        });
//        tvaddBan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intentToCreateTable();
//            }
//        });
//    }

//    private void intentToCreateTable() {
//        Intent Createintent = new Intent(ViewTableList.this,SuaBan.class);
//        startActivity(Createintent);
//        finish();
//    }

    private void AnhXa() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        listView = (ListView)findViewById(R.id.lvTable);
        banan = new ArrayList<tables>();
        adapter = new MyAdapter(getApplicationContext(),R.layout.tabe_list,banan);
        listView.setAdapter(adapter);
        btnaddBan = (ImageView) findViewById(R.id.btnAddBan);
        tvaddBan = (TextView)findViewById(R.id.tvAddBan);
        btneditBan = (ImageView)findViewById(R.id.btnEditBan);
        btndeleteBan = (ImageView)findViewById(R.id.btnDeleteBan);

    }
    class MyAdapter extends BaseAdapter {
        Context context;
        int mlayout;
        ArrayList<tables> mBanan;
        public MyAdapter(Context context,int layout,ArrayList<tables> banAnsList){
            this.context = context;
            this.mlayout = layout;
            this.mBanan = banAnsList;
        }

        @Override
        public int getCount() {
            return mBanan.size();
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mlayout,null);

            final File file;
            try {
                TextView  tvten = (TextView)view.findViewById(R.id.tvTable);
                TextView  tvsoluong = (TextView)view.findViewById(R.id.tvAmount);
                file = File.createTempFile("image","png");
                Locale localeVN = new Locale("vi", "VN");
//                tvten.setText(mBanan.get(i).getSo_ban());
//                tvsoluong.setText(mBanan.get(i).getSoluong_nguoi());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return view;
        }
    }

}