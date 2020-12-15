package com.example.reservation_manager;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class TableS extends AppCompatActivity {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_b);

        AnhXa();

    }

    private void AnhXa() {
        list = (ListView) findViewById(R.id.lvTable);
    }
}
