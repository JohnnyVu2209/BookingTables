package com.example.reservation_manager;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class fragB extends Fragment {

    ImageView image;
    TextView add;
    public View view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("fragmentB", "fragmentB: onCreate");
        super.onCreate(savedInstanceState);
    }

    private void AnhXa() {
        image = (ImageView) view.findViewById(R.id.imageViewAdd);
        add = (TextView) view.findViewById(R.id.textviewAdd);
    }
    private void Dialog() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialog);
        dialog.show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("fragmentB", "fragmentB: onCreateView");
        view = inflater.inflate(R.layout.fragment_b, container, false);

        AnhXa();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog();
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
        int a = 5;
        Log.d("fragmentB", "fragmentB: onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("fragmentB", "fragmentB: onDestroy");
        super.onDestroy();
    }
}
