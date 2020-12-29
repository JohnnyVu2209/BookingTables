//package com.example.reservation_manager.BanAn;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.reservation_manager.R;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//
//import java.io.File;
//import java.util.ArrayList;
//
//public class DanhSachBan extends AppCompatActivity {
//    //ListView listView;
//    ArrayList banan ;
//    ImageView btneditkhach,btndeletekhach;
//    MyAdapter adapter;
//    DatabaseReference databaseReference;
//    StorageReference storageReference;
//    Context context = this;
//    Button yes, no;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.tabe_list);
//
//        AnhXa();
//        databaseReference.child("Ban").addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                banan.clear();
//                for(DataSnapshot dataSnapshot : snapshot.getChildren())
//                {
//                    BanAn banAn = dataSnapshot.getValue(BanAn.class);
//                    banan.add(banAn);
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        //LinearLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//        //    @Override
//        //    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//         //       Intent xemchitietintent = new Intent(XemDanhSachKhachHang.this, xemchitietkh.class);
//           //     xemchitietintent.putExtra("position",String.valueOf(position));
//             //   startActivity(xemchitietintent);
//            // }
//        // });
//    }
//
//    private void AnhXa() {
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//        storageReference = FirebaseStorage.getInstance().getReference();
//        //listView = (ListView)findViewById(R.id.lvDSB);
//        banan = new ArrayList<>();
//        adapter = new MyAdapter(getApplicationContext(),R.layout.table_list,banan);
//        //listView.setAdapter(adapter);
//        btneditkhach = (ImageView)findViewById(R.id.btnEditkhach);
//        btndeletekhach = (ImageView)findViewById(R.id.btnDeletekhach);
//
//    }
//    class MyAdapter extends BaseAdapter {
//        Context context;
//        int mlayout;
//        ArrayList<BanAn> mBanan;
//
//        public MyAdapter(Context context, int layout, ArrayList<BanAn> BAsList) {
//            this.context = context;
//            this.mlayout = layout;
//            this.mBanan = BAsList;
//        }
//
//        @Override
//        public int getCount() {
//            return mBanan.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.O)
//        @Override
//        public View getView(int i, View view, final ViewGroup viewGroup) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = inflater.inflate(mlayout, null);
//
//            final File file;
//            try {
//                TextView tvtable = (TextView) view.findViewById(R.id.tvTable);
//                TextView tvamount = (TextView) view.findViewById(R.id.tvAmount);
//                tvtable.setText("Tên bàn: " + mBanan.get(i).getTenban());
//                tvamount.setText("Số lượng người: " + mBanan.get(i).getSonguoi());
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            final String pos = String.valueOf(i);
//            ImageView edit = (ImageView) view.findViewById(R.id.btnEditkhach); //BẮT SỰ KIỆN CHO EDIT
//            edit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent editBAintent = new Intent(DanhSachBan.this, SuaBan.class);
//                    editBAintent.putExtra("position", pos);
//                    startActivity(editBAintent);
//                }
//            });
//
//
//            ImageView delete = (ImageView) view.findViewById(R.id.btnDeletekhach); //BẮT SỰ KIỆN CHO DELETE
//            delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    final Dialog mydialog = new Dialog(DanhSachBan.this);
//                    mydialog.setContentView(R.layout.activity_xoa_ban);
//                    mydialog.setCancelable(false);
//                    mydialog.show();
//
//                    yes = (Button)mydialog.findViewById(R.id.btnEdit);
//                    no = (Button)mydialog.findViewById(R.id.btnCancel);
//
//                    yes.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            FirebaseDatabase database =FirebaseDatabase.getInstance();
//                            DatabaseReference referencer = database.getReference("Ban");
//                            referencer.child(pos).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    Toast.makeText(DanhSachBan.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                            mydialog.dismiss();
//                        }
//                    });
//                    no.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(DanhSachBan.this,"Không xóa",Toast.LENGTH_LONG).show();
//                            mydialog.dismiss();
//                        }
//                    });
//
//                    /*AlertDialog.Builder mydialog = new AlertDialog.Builder(context);
//                    mydialog.setTitle("Xác nhận");
//                    mydialog.setMessage("Bạn có đồng ý xóa không?");
//                    mydialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            FirebaseDatabase database =FirebaseDatabase.getInstance();
//                            DatabaseReference referencer = database.getReference("KhachHang");
//                            referencer.child(pos).removeValue();
//                            finish();
//                        }
//                    });
//                    mydialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
//                    });
//                    AlertDialog alertDialog = mydialog.create();
//                    alertDialog.show();*/
//                }
//            });
//
//            return view;
//        }
//    }
//}