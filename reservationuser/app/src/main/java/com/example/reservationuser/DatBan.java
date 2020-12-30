package com.example.reservationuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.interfaces.DSAKey;
import java.util.Calendar;

public class DatBan extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView tvTenNgDat, tvSDTNgDat, tvChonNgay, tvChonBan, tvThemMon;
    EditText etNgay, etThang, etGio, etSoBan;
    RadioGroup rgGoiMon;
    RadioButton rbCo, rbKhong;
    LinearLayout llGoiMon;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference;
    private void AnhXa(){
        tvTenNgDat = (TextView)findViewById(R.id.tvTenNgDat);
        tvSDTNgDat = (TextView)findViewById(R.id.tvSDTNgDat);
        tvChonNgay = (TextView)findViewById(R.id.tvChonNgay);
        tvChonBan  = (TextView)findViewById(R.id.tvChonBan);
        tvThemMon  = (TextView)findViewById(R.id.tvThemMon);
        etNgay     = (EditText)findViewById(R.id.etNgay);
        etThang    = (EditText)findViewById(R.id.etThang);
        etGio      = (EditText)findViewById(R.id.etGio);
        etSoBan    = (EditText)findViewById(R.id.etSoBan);
        rgGoiMon   = (RadioGroup) findViewById(R.id.rgGoiMon);
        rbCo       = (RadioButton)findViewById(R.id.rbCo);
        rbKhong    = (RadioButton)findViewById(R.id.rbkhong);
        llGoiMon   = (LinearLayout)findViewById(R.id.llGoiMon);

        reference  = FirebaseDatabase.getInstance().getReference("KhachHang");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_ban);
        AnhXa();

        reference.orderByChild("sdt").equalTo(user.getPhoneNumber()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    KhachHang khachHang = dataSnapshot.getValue(KhachHang.class);
                    Log.d("VALUE", "onDataChange: " + dataSnapshot.getValue(KhachHang.class).getHoten());
                    tvTenNgDat.setText(khachHang.hoten);
                    tvSDTNgDat.setText(khachHang.sdt);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        tvChonNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH , month+1);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        etNgay.setText(String.valueOf(c.getTime().getDate()));
        int m =c.getTime().getMonth();
        if(m == 0){
            m = 12;
        }
        etThang.setText(String.valueOf(m));
    }

    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(DatBan.this,MainActivity.class);
        startActivity(homeIntent);
        super.onBackPressed();
    }
}