package com.example.reservationuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listfood;
    private ArrayList<String> mloai;
    private ListAdapter adapter;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    Toolbar toolbar;
    private void Anhxa(){
        listfood = (ListView)findViewById(R.id.listfood);
        mloai = new ArrayList<>();
        adapter = new ListAdapter(MainActivity.this,R.layout.item_list,mloai);
        listfood.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Anhxa();
        reference.child("MonAn").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    mloai.add(dataSnapshot.child("loaimonan").getValue().toString());
                }
                adapter.notifyDataSetChanged();
                Log.d("LLL", "onDataChange: " + sortSame(mloai));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private ArrayList sortSame(ArrayList<String> list){
        ArrayList<String> list1 = new ArrayList<>();
        for (String element: list) {
            if(!list1.contains(element)){
                list1.add(element);
            }
        }
        return list1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.mnuSignOut){
            FirebaseAuth.getInstance().signOut();
            Intent loginInten = new Intent(MainActivity.this,Login.class);
            startActivity(loginInten);
        }
        return super.onOptionsItemSelected(item);
    }
}

