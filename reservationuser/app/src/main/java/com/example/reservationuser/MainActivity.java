package com.example.reservationuser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.QuickContactBadge;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView gridviewfood;
    private ArrayList<MonAn> mMonAn;
    private GridAdapter adapter;
    Button btnLichSu,btnDatBan;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    Toolbar toolbar;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private void Anhxa(){
        gridviewfood = (GridView)findViewById(R.id.gridviewfood);
        mMonAn = new ArrayList<>();
        adapter = new GridAdapter(MainActivity.this,R.layout.gridview_row,mMonAn);
        gridviewfood.setAdapter(adapter);
        btnLichSu = (Button)findViewById(R.id.btnLichSu);
        btnDatBan = (Button)findViewById(R.id.btnDatBan);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Anhxa();
        final MonAn monrong = new MonAn("","","",false,"FoodImages/Goicuhudua.png","");
        reference.child("MonAn").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mMonAn.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    MonAn monAn = dataSnapshot.getValue(MonAn.class);
                    mMonAn.add(monAn);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final XemLichSuFragment fragment = new XemLichSuFragment();
        final FragmentManager manager = getSupportFragmentManager();
        //CHUYEN TU TRANG CHU SANG FRAGMENT XEM LICH SU
        btnLichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction().add(R.id.main_container,fragment).addToBackStack(MainActivity.class.getName()).commit();

            }
        });

        btnDatBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentToDatBan();
            }
        });


    }
    private void intentToDatBan(){
        Intent intent = new Intent(MainActivity.this,DatBan.class);
        startActivity(intent);
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

