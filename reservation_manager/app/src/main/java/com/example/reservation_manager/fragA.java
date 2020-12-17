package com.example.reservation_manager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class fragA extends Fragment {

    private  Button t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18;
    int a = 0;
    public View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("fragmentA", "fragmentA: onCreate");
        super.onCreate(savedInstanceState);
    }

    private void AnhXa() {
        t1 = (Button) view.findViewById(R.id.btnT1);
        t2 = (Button) view.findViewById(R.id.btnT2);
        t3 = (Button) view.findViewById(R.id.btnT3);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("fragmentA", "fragmentA: onCreateView với biến a = " + a);
        view = inflater.inflate(R.layout.fragment_a, container, false);

        AnhXa();
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông tin bàn 1");
                builder.setMessage("Số lượng người: 4");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông tin bàn 3");
                builder.setMessage("Số lượng người: 8");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        return  view;

    }

    @Override
    public void onStart() {
        Log.d("fragmentA", "fragmentA: onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("fragmentA", "fragmentA: onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("fragmentA", "fragmentA: onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("fragmentA", "fragmentA: onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d("fragmentA", "fragmentA: onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("fragmentA", "fragmentA: onDestroy");
        super.onDestroy();
    }

}


