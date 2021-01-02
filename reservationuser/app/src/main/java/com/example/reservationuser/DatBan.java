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
import android.widget.CompoundButton;
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
import com.sanojpunchihewa.glowbutton.GlowButton;

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
    monDaChonAdapter monDaChonAdapter;
    ListView dsMonAn,dsMonAnDaChon;
    ArrayList<MonAn> listmonan,ChoosedFood;
    Button btnChon;
    GlowButton glowButton;
    FirebaseController controller;
    int nam = 0;

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
        dsMonAnDaChon = (ListView)findViewById(R.id.listMonGoiTruoc);
        ChoosedFood = new ArrayList<>();
        glowButton = (GlowButton)findViewById(R.id.gbDatBan);
        controller = new FirebaseController(this);
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

        rgGoiMon.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbCo){
                    glowButton.setVisibility(View.INVISIBLE);
                    llGoiMon.setVisibility(View.VISIBLE);
                }
                else {
                    llGoiMon.setVisibility(View.INVISIBLE);
                    glowButton.setVisibility(View.VISIBLE);
                }

            }
        });
        tvThemMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(v);
            }
        });
        Log.d("Food", "onCreate: " + ChoosedFood.size());
        glowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }
    public void getTableNum(String num){
        etSoBan.setText(num);
    }
    private void getData(){
        DonDatBan donDatBan;
        String tennguoidat = tvTenNgDat.getText().toString();
        String sdtngdat = tvSDTNgDat.getText().toString();
        String ngayden = etNgay.getText().toString() + "/" + etThang.getText().toString() + "/" + nam;
        String gioden = etGio.getText().toString() + " PM";
        int soban = Integer.parseInt(etSoBan.getText().toString());
        int goimon = rgGoiMon.getCheckedRadioButtonId();
        boolean goimontruoc = false;
        if (goimon == R.id.rbCo){
            goimontruoc = true;
        }
        if(goimontruoc){
            ArrayList<MonAn> mongoitruoc = monDaChonAdapter.getmMonAn();
            Log.d("TAG", "getData: "+ mongoitruoc.size());
            donDatBan = new DonDatBan(tennguoidat,sdtngdat,ngayden,gioden,soban,goimontruoc,mongoitruoc);
            controller.WirteWithAutoIncreaseKey("DonDatBan",donDatBan);
        }
        else {
            donDatBan = new DonDatBan(tennguoidat,sdtngdat,ngayden,gioden,soban,goimontruoc);
            controller.WirteWithAutoIncreaseKey("DonDatBan",donDatBan);
        }
    }
    public void openDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.chon_monan,null);
        builder.setView(row);
        final AlertDialog dialog = builder.create();
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
                Log.d("CHECK SIZE", "onDataChange: " + ChoosedFood.size());
                if(ChoosedFood.size() != 0){
                    for (int i = 0; i < listmonan.size(); i++) {
                        for (int j = 0; j < ChoosedFood.size(); j++) {
                            if (listmonan.get(i).tenmonan.contains(ChoosedFood.get(j).tenmonan)){
                                listmonan.get(i).setSelected(true);
                            }
                        }
                    }
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
                    Log.d("FOOD", "onItemClick: " + monAn.tenmonan);
                    monAn.setSelected(true);
                }
                listmonan.set(position,monAn);
                adapter.updateRecords(listmonan);
            }
        });

        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ChoosedFood = adapter.getmLayMon();
                monDaChonAdapter = new monDaChonAdapter(getApplicationContext(),R.layout.mon_da_chon_list,ChoosedFood);
                dsMonAnDaChon.setAdapter(monDaChonAdapter);
                monDaChonAdapter.notifyDataSetChanged();

                if (ChoosedFood.size() != 0){
                    glowButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH , month+1);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        nam = c.getTime().getYear();
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
        ArrayList<MonAn> mLayMon;

        public ChonMonAdapter(Context context, int mlayout, ArrayList<MonAn> mMonAn) {
            this.context = context;
            this.mlayout = mlayout;
            this.mMonAn = mMonAn;
            mLayMon  = new ArrayList<>();
            for (int i = 0; i <mMonAn.size() ; i++) {
                if (mMonAn.get(i).getSelected()){
                    mLayMon.add(mMonAn.get(i));
                }
            }

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

        public ArrayList<MonAn> getmLayMon() {
            return sortSame(mLayMon);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
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
                    mLayMon.add(mMonAn.get(position));
                }
                else {
                    checkBox.setChecked(false);
                }
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Log.d("CHECK", "onCheckedChanged: " + position+ " " + isChecked);
                        if(isChecked == true){
                            mLayMon.add(mMonAn.get(position));
                        }
                        else{
                            mLayMon.remove(mMonAn.get(position));
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            return convertView;
        }

        public void updateRecords(ArrayList<MonAn> listmonan) {
            this.mMonAn = listmonan;
            notifyDataSetChanged();
        }
        private ArrayList sortSame(ArrayList<MonAn> list){
            ArrayList<MonAn> list1 = new ArrayList<>();
            for (MonAn element: list) {
                if(!list1.contains(element)){
                    list1.add(element);
                }
            }
            return list1;
        }
    }
    class monDaChonAdapter extends BaseAdapter{
        Context context;
        int mlayout;
        ArrayList<MonAn> mMonAn;

        public monDaChonAdapter(Context context, int mlayout, ArrayList<MonAn> mMonAn) {
            this.context = context;
            this.mlayout = mlayout;
            this.mMonAn = mMonAn;
        }

        @Override
        public int getCount() {
            Log.d("SIZE", "getCount: " + mMonAn.size());
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mlayout,null);
            TextView tvTenMon = (TextView)convertView.findViewById(R.id.tvTenMonDaChon);
            ImageView imgRemove = (ImageView)convertView.findViewById(R.id.imgRemove);
            tvTenMon.setText(mMonAn.get(position).tenmonan);
            imgRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMonAn.remove(position);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        public ArrayList<MonAn> getmMonAn() {
            return mMonAn;
        }
    }
}