package com.example.reservation_manager;

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

    Button t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18;
    int a = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("fragmentA", "fragmentA: onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("fragmentA", "fragmentA: onCreateView với biến a = " + a);
        return inflater.inflate(R.layout.fragment_a, container, false);
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
