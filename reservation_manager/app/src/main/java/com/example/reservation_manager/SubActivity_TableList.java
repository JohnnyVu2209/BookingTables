package com.example.reservation_manager;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.reservation_manager.BanAn.tables;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SubActivity_TableList extends Fragment {

    ImageView btneditBan,btndeleteBan,btnAddBan;
    MyAdapter adapter;
    DatabaseReference databaseReference;
    // button dialog
    Button addtable, edit,cancel;
    EditText number, amount;
    // button listview
    ListView listView;
    ArrayList<tables> banan ;

    tables table;
    public View view;
    SubActivity_TableList context = this;
    FirebaseController controller;
    private final String TAG = "READ DATABASE";

    private void AnhXa(View view) {
        btnAddBan =(ImageView) view.findViewById(R.id.btnAddBan);
        controller = new FirebaseController(getActivity());
        databaseReference = FirebaseDatabase.getInstance().getReference();
        listView = (ListView)view.findViewById(R.id.lvTable);
        banan = new ArrayList<tables>();
        adapter = new MyAdapter(getContext(),R.layout.tabe_list,banan);
        listView.setAdapter(adapter);
        btneditBan = (ImageView)view.findViewById(R.id.btnEditTable);
        btndeleteBan = (ImageView)view.findViewById(R.id.btnDeleteTable);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("fragmentB", "fragmentB: onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("fragmentB", "fragmentB: onCreateView");
        view = inflater.inflate(R.layout.fragment_b, container, false);

//        final String pos = String.valueOf(inflater);
        AnhXa(view);
        databaseReference.child("Ban").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                tables banAn = snapshot.getValue(tables.class);
                banan.add(banAn);
                adapter.notifyDataSetChanged();
                //Log.d("HHH", "onChildAdded: " + banan.size());
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

        btnAddBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.custom_dialog);
                dialog.show();

                number = (EditText) dialog.findViewById(R.id.etNumber);
                amount = (EditText) dialog.findViewById(R.id.etAmount);

                addtable = (Button) dialog.findViewById(R.id.btnAdd);
                addtable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int soban, soluongnguoi;
                        soban = Integer.parseInt(number.getText().toString());
                        soluongnguoi = Integer.parseInt(amount.getText().toString());

                        table = new tables(soban, soluongnguoi);
                        controller.WirteWithAutoIncreaseKey("Ban", table);
                    }
                });

            }
        });
//        btneditBan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Dialog dialog = new Dialog(getActivity());
//                dialog.setContentView(R.layout.activity_sua_ban);
//                dialog.show();
//            }
//        });
//        btndeleteBan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Dialog dialog = new Dialog(getActivity());
//                dialog.setContentView(R.layout.activity_xoa_ban);
//                dialog.show();
//
//                edit = (Button) dialog.findViewById(R.id.btnEdit);
//                cancel = (Button) dialog.findViewById(R.id.btnCancel);
//
//                edit.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            FirebaseDatabase database =FirebaseDatabase.getInstance();
//                            DatabaseReference referencer = database.getReference("Ban");
//                            referencer.child(pos).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    Toast.makeText(SubActivity_TableList.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                            dialog.dismiss();
//                        }
//                    });
//                    cancel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(SubActivity_TableList.this,"Không xóa",Toast.LENGTH_LONG).show();
//                            dialog.dismiss();
//                        }
//                    });

        return view;
    }
//    private void intentToListTable() {
//        Intent listTable_intent = new Intent(String.valueOf(SubActivity_TableList.this));
//        listTable_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        listTable_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(listTable_intent);
//    }

        private void insertDataTable () {
            int soban, soluongnguoi;
            soban = Integer.parseInt(number.getText().toString());
            soluongnguoi = Integer.parseInt(amount.getText().toString());

            table = new tables(soban, soluongnguoi);

            //Toast.makeText(getActivity(), "Lưu thành công", Toast.LENGTH_SHORT).show();
        }


    @Override
    public void onStart() {
        Log.d("fragmentB", "fragmentB: onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("fragmentB", "fragmentB: onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("fragmentB", "fragmentB: onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("fragmentB", "fragmentB: onStop");
        super.onStop();
    }


    @Override
    public void onDestroyView() {
//        int a = 5;
        Log.d("fragmentB", "fragmentB: onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("fragmentB", "fragmentB: onDestroy");
        super.onDestroy();
    }
}
    class MyAdapter extends BaseAdapter {
    Context context;
    Button edit,cancel;
    int mlayout;
    ImageView editBan,deleteBan;
    ArrayList<tables> mBanan;

    public MyAdapter(Context context, int layout, ArrayList<tables> banAnsList) {
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
        view = inflater.inflate(mlayout, null);

        TextView tvten = (TextView) view.findViewById(R.id.tvTenBan);
        TextView tvsoluong = (TextView) view.findViewById(R.id.tvSoNguoi);

        tvten.setText("Tên bàn:" + String.valueOf(mBanan.get(i).SoBan));
        tvsoluong.setText("Số người tối đa: " + String.valueOf(mBanan.get(i).SoLuongNguoi));

        final String pos = String.valueOf(i);
        editBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(SubActivity_TableList.this);
                dialog.setContentView(R.layout.activity_sua_ban);
                dialog.show();
            }
        });
        deleteBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(SubActivity_TableList.this);
                dialog.setContentView(R.layout.activity_xoa_ban);
                dialog.show();

                edit = (Button) dialog.findViewById(R.id.btnEdit);
                cancel = (Button) dialog.findViewById(R.id.btnCancel);

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase database =FirebaseDatabase.getInstance();
                        DatabaseReference referencer = database.getReference("Ban");
                        referencer.child(pos).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(SubActivity_TableList.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            }
                        });

                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(SubActivity_TableList.this,"Không xóa",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });


        return view;
    }
}



