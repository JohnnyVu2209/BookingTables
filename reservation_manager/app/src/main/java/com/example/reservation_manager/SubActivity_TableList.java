package com.example.reservation_manager;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.reservation_manager.BanAn.tables;

public class SubActivity_TableList extends Fragment {

    ImageView image;
    // button dialog
    Button addtable, cancel;
    EditText number, amount;
    // button listview
    ListView listView;

    tables table;
    public View view;
    SubActivity_TableList context = this;
    FirebaseController controller;
    private final String TAG = "READ DATABASE";

    private void AnhXa() {
        image =(ImageView) view.findViewById(R.id.btnAddBan);
        controller = new FirebaseController(getActivity());
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

                number =(EditText) dialog.findViewById(R.id.etNumber);
                amount =(EditText)  dialog.findViewById(R.id.etAmount);

                addtable =(Button) dialog.findViewById(R.id.btnAdd);
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
