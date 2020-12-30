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
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SubActivity_TableList extends Fragment {

    ImageView image;
    // button dialog
    Button addtable, cancel;
    EditText number, amount;
    RadioButton vip, nor;

    tables table;
    public View view;
    SubActivity_TableList context = this;
    FirebaseController controller;
    private final String TAG = "READ DATABASE";

    private void AnhXa() {
        image =(ImageView) view.findViewById(R.id.imageViewAdd);
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

        AnhXa();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_them_ban);
                dialog.show();

                number =(EditText) dialog.findViewById(R.id.etNumber);
                amount =(EditText)  dialog.findViewById(R.id.etAmount);
                vip =(RadioButton) dialog.findViewById(R.id.rbtVip);
                nor =(RadioButton) dialog.findViewById(R.id.rbtNormal);

                addtable =(Button) dialog.findViewById(R.id.btnAdd);
                addtable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int soban, soluongnguoi;
                        boolean loaiban = true;
                        soban = Integer.parseInt(number.getText().toString());
                        soluongnguoi = Integer.parseInt(amount.getText().toString());
                        if(nor.isChecked()) {
                            loaiban = true;
                        } else if (vip.isChecked()) {
                            loaiban = false;
                        }
                        dialog.dismiss();

                        table = new tables(soban, soluongnguoi, loaiban);
                        controller.WirteWithAutoIncreaseKey("Ban", table);
                    }
                });
                cancel =(Button) dialog.findViewById(R.id.btnCancel);
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
