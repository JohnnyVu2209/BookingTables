package com.example.reservation_manager.BanAn;

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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.reservation_manager.FirebaseController;
import com.example.reservation_manager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SubActivity_TableList extends Fragment {

    ImageView btneditBan,btndeleteBan,btnAddBan;
    MyAdapter adapter;
    DatabaseReference databaseReference;
    // button dialog
    Button addtable, edit,cancel;
    EditText number, amount;
    RadioButton vip, nor;
    // button listview
    ListView listView;
    ArrayList<tables> banan ;
    tables table;
    public View view;
    SubActivity_TableList context = this;
    FirebaseController controller;
    ArrayList keyss;
    private final String TAG = "READ DATABASE";

    private void AnhXa(View view) {
        btnAddBan =(ImageView) view.findViewById(R.id.btnAddBan);
        controller = new FirebaseController(getActivity());
        databaseReference = FirebaseDatabase.getInstance().getReference();
        listView = (ListView) view.findViewById(R.id.lvTable);
        banan = new ArrayList<tables>();
        keyss = new ArrayList();
        adapter = new MyAdapter(getContext(),R.layout.tabe_list,banan,keyss);
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
        final tables banrong = new tables( 0,0, true);
        databaseReference.child("Ban").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                banan.clear();
                keyss.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    tables banAn = dataSnapshot.getValue(tables.class);
                    banan.add(banAn);
                    keyss.add(dataSnapshot.getKey());
                }
                banan.add(banrong);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnAddBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.custom_dialog);
                dialog.show();

                number = (EditText) dialog.findViewById(R.id.etNumber);
                amount = (EditText) dialog.findViewById(R.id.etAmount);
                vip =(RadioButton) dialog.findViewById(R.id.rbtVip);
                nor =(RadioButton) dialog.findViewById(R.id.rbtNormal);

                addtable = (Button) dialog.findViewById(R.id.btnAdd);
                addtable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int soban, soluongnguoi;
                        boolean loaiban = true;
                        soban = Integer.parseInt(number.getText().toString());
                        soluongnguoi = Integer.parseInt(amount.getText().toString());
                        if(nor.isChecked()) {
                            loaiban = true;
                        } else {
                            loaiban = false;
                        }
                        dialog.dismiss();

                        table = new tables(soban, soluongnguoi, loaiban);
                        controller.WirteWithAutoIncreaseKey("Ban", table);
                    }
                });

                cancel =(Button) dialog.findViewById(R.id.btnDat);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });
        return view;
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
    Button edit,cancel,delete;
    int mlayout;
    ImageView editBan,deleteBan;
    ArrayList<tables> mBanan;
    EditText number,amount,tenban;
    RadioButton vip, nor;
    tables updatetable;
    FirebaseController controller;
    ArrayList keys;


    public MyAdapter(Context context, int layout, ArrayList<tables> banAnsList,ArrayList key) {
        this.context = context;
        this.mlayout = layout;
        this.mBanan = banAnsList;
        this.keys = key;

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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(mlayout, null);

        TextView tvten = (TextView) view.findViewById(R.id.tvTenBan);
        TextView tvsoluong = (TextView) view.findViewById(R.id.tvSoNguoi);
        ImageView editBan = (ImageView) view.findViewById(R.id.btnEditTable);
        ImageView deleteBan = (ImageView) view.findViewById(R.id.btnDeleteTable);
        updatetable = new tables();
        controller = new FirebaseController(context);

        tvten.setText("Tên bàn:" + String.valueOf(mBanan.get(i).SoBan));
        tvsoluong.setText("Số người tối đa: " + String.valueOf(mBanan.get(i).SoLuongNguoi));


        editBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.activity_sua_ban);
                dialog.show();
                Log.d("CHECK", "onClick: " + keys.get(i).toString());

                number = (EditText) dialog.findViewById(R.id.etNumber);
                amount = (EditText) dialog.findViewById(R.id.etAmount);
                number.setText(String.valueOf(mBanan.get(i).SoBan));
                amount.setText(String.valueOf(mBanan.get(i).SoLuongNguoi));
                vip =(RadioButton) dialog.findViewById(R.id.rbtVip);
                nor =(RadioButton) dialog.findViewById(R.id.rbtNormal);
                edit = (Button) dialog.findViewById(R.id.btnEdit);
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int soban, slnguoi;
                        boolean loaiban = true;
                        soban = Integer.parseInt(number.getText().toString());
                        slnguoi = Integer.parseInt(amount.getText().toString());
                        if(nor.isChecked()) {
                            loaiban = true;
                        } else {
                            loaiban = false;
                        }
                        dialog.dismiss();
                        updatetable = new tables(soban,slnguoi,loaiban);
                        controller.UpdateData("Ban", keys.get(i).toString(), updatetable);
                    }
                });
            }
        });
        deleteBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.activity_xoa_ban);
                dialog.show();

                delete = (Button) dialog.findViewById(R.id.btnDelete);
                cancel = (Button) dialog.findViewById(R.id.btnDat);
                tenban = (EditText) dialog.findViewById(R.id.etTenban);
                tenban.setText("Số bàn:" + String.valueOf(mBanan.get(i).SoBan));
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference referencer = database.getReference("Ban");
                        referencer.child(keys.get(i).toString()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Không xóa", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });

            }
        });
        return view;

    }


}


