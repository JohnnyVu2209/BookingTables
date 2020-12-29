package com.example.reservation_manager.MonAn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservation_manager.CapNhatMonAn;
import com.example.reservation_manager.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class XemDanhSachMonAn extends AppCompatActivity {
    ListView listView;
    ArrayList<MonAn> monan = new ArrayList<>();
    ImageView btnaddfood;
    TextView tvaddfood;
    MyAdapter adapter;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String key;
    ArrayList keyArr = new ArrayList();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_danh_sach_mon_an);


        AnhXa();
        final MonAn monrong = new MonAn("","","",false,"FoodImages/Goicuhudua.png","");
        databaseReference.child("MonAn").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                monan.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MonAn monAn = dataSnapshot.getValue(MonAn.class);
                    monan.add(monAn);
                    keyArr.add(dataSnapshot.getKey());
                }
                monan.add(monrong);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        AddAction();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("MonAn");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){
                    keyArr.add(child.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void AddAction() {
        btnaddfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentToCreateFood();
            }
        });
        tvaddfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentToCreateFood();
            }
        });
    }


    private void intentToCreateFood() {
        Intent Createintent = new Intent(XemDanhSachMonAn.this, ThemMonAn.class);
        startActivity(Createintent);
        finish();
    }

    private void AnhXa() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        listView = (ListView) findViewById(R.id.lvDanhSachMonAn);
        monan = new ArrayList<MonAn>();
        adapter = new MyAdapter(getApplicationContext(), R.layout.item_list, monan);
        listView.setAdapter(adapter);
        btnaddfood = (ImageView) findViewById(R.id.btnAddFood);
        tvaddfood = (TextView) findViewById(R.id.tvAddFood);
    }

    public class MyAdapter extends BaseAdapter {
        Context context;
        int mlayout;
        ArrayList<MonAn> mMonan;

        public MyAdapter(Context context, int layout, ArrayList<MonAn> monAnsList) {
            this.context = context;
            this.mlayout = layout;
            this.mMonan = monAnsList;
        }

        @Override
        public int getCount() {
            return mMonan.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        public class Holder {
            ImageView btneditFood, btndeleteFood;
            ImageView imgma;
            TextView tvten;
            TextView tvloai;
            TextView tvgia;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            Holder holder = null;
            if (view == null) {
                holder = new Holder();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(mlayout, null);
                holder.btneditFood = (ImageView) view.findViewById(R.id.btnEditFood);
                holder.btndeleteFood = (ImageView) view.findViewById(R.id.btnDeleteFood);
                holder.imgma = (ImageView) view.findViewById(R.id.imgMonAn);
                holder.tvten = (TextView) view.findViewById(R.id.tvTenMon);
                holder.tvloai = (TextView) view.findViewById(R.id.tvLoaiMon);
                holder.tvgia = (TextView) view.findViewById(R.id.tvGia);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }

            final File file;
            try {
                file = File.createTempFile("image", "png");
                final Holder finalHolder = holder;
                if(mMonan.get(i).idhinh != null){
                    storageReference.child(mMonan.get(i).idhinh).getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            finalHolder.imgma.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Image Failed to load", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                Locale localeVN = new Locale("vi", "VN");
                NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                String vnd = "";
                if (mMonan.get(i).gia != ""){
                    vnd = currencyVN.format(Long.parseLong(mMonan.get(i).gia));
                }
                holder.tvten.setText(mMonan.get(i).tenmonan);
                holder.tvloai.setText(mMonan.get(i).loaimonan);
                holder.tvgia.setText("Giá: " + vnd);
            } catch (IOException e) {
                e.printStackTrace();
            }


            holder.btndeleteFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("MonAn");
                    myRef.child(keyArr.get(i).toString()).removeValue();
                    Toast.makeText(getApplicationContext(), keyArr.get(i).toString(), Toast.LENGTH_LONG).show();
                }
            });

            holder.btneditFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    Toast.makeText(context, "Cap nhat", Toast.LENGTH_LONG).show();
                    Intent updateIntent = new Intent(XemDanhSachMonAn.this, CapNhatMonAn.class);
                    updateIntent.putExtra("id", keyArr.get(i).toString());
                    updateIntent.putExtra("ten", mMonan.get(i).tenmonan);
                    updateIntent.putExtra("gia", mMonan.get(i).gia);
                    updateIntent.putExtra("loai", mMonan.get(i).loaimonan);
                    updateIntent.putExtra("mota", mMonan.get(i).mota);
                    updateIntent.putExtra("goimon", mMonan.get(i).goimon);
                    updateIntent.putExtra("hinh", mMonan.get(i).idhinh);
                    startActivity(updateIntent);
                    finish();
                }
            });

            return view;

        }



    }

}