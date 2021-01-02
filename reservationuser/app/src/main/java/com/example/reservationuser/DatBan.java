package com.example.reservationuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.security.interfaces.DSAKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DatBan extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView tvTenNgDat, tvSDTNgDat, tvChonNgay, tvChonBan, tvThemMon;
    EditText etNgay, etThang, etGio, etSoBan;
    RadioGroup rgGoiMon;
    RadioButton rbCo, rbKhong;
    LinearLayout llGoiMon;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    StorageReference storageReference;
    DatabaseReference reference;
    ChonMonAdapter adapter;
    ListView dsMonAn;
    ArrayList<MonAn> listmonan;
    Button btnChon;
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
        storageReference = FirebaseStorage.getInstance().getReference();
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
        final SoDoBan soDoBan = new SoDoBan();
        final FragmentManager fragmentManager = this.getSupportFragmentManager();
        tvChonBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().add(R.id.customDatBan,soDoBan).addToBackStack(DatBan.class.getName()).commit();
            }
        });
        String soBan = getIntent().getStringExtra("NUMBER");
        etSoBan.setText(soBan);
        this.rgGoiMon.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbCo){
                    llGoiMon.setVisibility(View.VISIBLE);
                }
                else {
                    llGoiMon.setVisibility(View.GONE);
                }

            }
        });


    }
    public void openDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.chon_monan,null);
        builder.setView(row);
        AlertDialog dialog = builder.create();
        dialog.show();
        btnChon = (Button)row.findViewById(R.id.btnChon);
        dsMonAn = (ListView)row.findViewById(R.id.listMonAn);
        listmonan = new ArrayList<>();
        adapter = new ChonMonAdapter(this,R.layout.chonmon_list,listmonan);
        dsMonAn.setAdapter(adapter);
        reference = FirebaseDatabase.getInstance().getReference("MonAn");
        reference.orderByChild("goimon").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MonAn monAn = dataSnapshot.getValue(MonAn.class);
                    listmonan.add(monAn);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        dsMonAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MonAn monAn = listmonan.get(position);
                if (monAn.getSelected()){
                    monAn.setSelected(false);
                }
                else {
                    monAn.setSelected(true);
                }
                listmonan.set(position,monAn);
                adapter.updateRecords(listmonan);
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
    class ChonMonAdapter extends BaseAdapter{
        Context context;
        int mlayout;
        ArrayList<MonAn> mMonAn;

        public ChonMonAdapter(Context context, int mlayout, ArrayList<MonAn> mMonAn) {
            this.context = context;
            this.mlayout = mlayout;
            this.mMonAn = mMonAn;
        }

        @Override
        public int getCount() {
            return mMonAn.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mlayout,null);

            final File file;
            try {
                final ImageView imgma = (ImageView)convertView.findViewById(R.id.imageMONAN);
                TextView  tvten = (TextView)convertView.findViewById(R.id.tvTenMONAN);
                CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.cbChon);
                file = File.createTempFile("image","png");
                storageReference.child(mMonAn.get(position).idhinh).getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        imgma.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Image Failed to load", Toast.LENGTH_SHORT).show();
                    }
                });
                tvten.setText(mMonAn.get(position).tenmonan);
                if (mMonAn.get(position).getSelected()){
                    checkBox.setChecked(true);
                }
                else {
                    checkBox.setChecked(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return convertView;
        }

        public void updateRecords(ArrayList<MonAn> listmonan) {
            this.mMonAn = listmonan;
            notifyDataSetChanged();
        }
    }
}