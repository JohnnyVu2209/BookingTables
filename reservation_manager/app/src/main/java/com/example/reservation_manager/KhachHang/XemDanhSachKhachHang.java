package com.example.reservation_manager.KhachHang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.reservation_manager.MainActivity;
import com.example.reservation_manager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.util.ArrayList;

public class XemDanhSachKhachHang extends AppCompatActivity {
    ListView listView;
    ArrayList khachhang ;
    ImageView btneditKH,btndeleteKH;
    MyAdapter adapter;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    Context context = this;
    Button yes, no;

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
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    KhachHang khachHang = dataSnapshot.getValue(KhachHang.class);
                    khachhang.add(khachHang);
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
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Intent xemchitietintent = new Intent(XemDanhSachKhachHang.this, xemchitietkh.class);
                xemchitietintent.putExtra("position",String.valueOf(position));
                startActivity(xemchitietintent);
            }
        });
    }

    private void AnhXa() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        listView = (ListView)findViewById(R.id.lvDSKH);
        khachhang = new ArrayList<>();
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
        public View getView(int i, View view, final ViewGroup viewGroup) {
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
                    final Dialog mydialog = new Dialog(XemDanhSachKhachHang.this);
                    mydialog.setContentView(R.layout.dialog);
                    mydialog.getWindow().setLayout(viewGroup.getLayoutParams().WRAP_CONTENT,viewGroup.getLayoutParams().WRAP_CONTENT);
                    mydialog.setCancelable(false);
                    mydialog.show();

                    yes = (Button)mydialog.findViewById(R.id.btnco);
                    no = (Button)mydialog.findViewById(R.id.btnkhong);

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase database =FirebaseDatabase.getInstance();
                            DatabaseReference referencer = database.getReference("KhachHang");
                            referencer.child(pos).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
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

                    /*AlertDialog.Builder mydialog = new AlertDialog.Builder(context);
                    mydialog.setTitle("Xác nhận");
                    mydialog.setMessage("Bạn có đồng ý xóa không?");
                    mydialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseDatabase database =FirebaseDatabase.getInstance();
                            DatabaseReference referencer = database.getReference("KhachHang");
                            referencer.child(pos).removeValue();
                            finish();
                        }
                    });
                    mydialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = mydialog.create();
                    alertDialog.show();*/
                }
            });

            return view;
        }
    }
}
