package com.example.reservation_manager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.database.ValueEventListener;

public class SubActivity_TableList extends Fragment {

    ImageView image;
    // button dialog
    Button addtable, cancel;
    EditText number, amount;

    tables tabless;
    public View view;
    StorageReference storage;
    StorageTask uploadTask;
    SubActivity_TableList context = this;
    DatabaseReference reff;
    FirebaseController controller;
    private final String TAG = "READ DATABASE";

    private void AnhXa() {
        image =(ImageView) view.findViewById(R.id.imageViewAdd);
        number =(EditText) view.findViewById(R.id.etNumber);
        amount =(EditText)  view.findViewById(R.id.etAmount);
        reff = FirebaseDatabase.getInstance().getReference().child("tables");
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
        AnhXa();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.custom_dialog);
                dialog.show();

                addtable =(Button) view.findViewById(R.id.btnAdd);
                addtable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        insertDataTable();

                    }
                });
//                        int SoBan, SoLuongNguoi;
//                        SoBan = Integer.parseInt(number.getText().toString());
//                        SoLuongNguoi = Integer.parseInt(amount.getText().toString());
//                        tabless = new tables(SoBan, SoLuongNguoi);
//
//                        controller.WirteWithAutoIncreaseKey("Ban", tabless);
//                        intentToListTable();
            }
        });
        return view;
    }

//    private void intentToListTable() {
//        Intent listTable_intent = new Intent(String.valueOf(SubActivity_TableList.this));
//        listTable_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        listTable_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(listTable_intent);
//    }

    private void insertDataTable() {
        int soban, soluongnguoi;
        soban = Integer.parseInt(number.getText().toString());
        soluongnguoi = Integer.parseInt(amount.getText().toString());

        tables table = new tables(soban, soluongnguoi);
        controller.WirteWithAutoIncreaseKey("Ban", table);
        reff.push().setValue(table);
        Toast.makeText(getActivity(), "Lưu thành công", Toast.LENGTH_SHORT).show();
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
