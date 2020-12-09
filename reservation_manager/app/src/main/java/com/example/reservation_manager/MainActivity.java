package com.example.reservation_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.lang.ref.SoftReference;

public class MainActivity extends AppCompatActivity {

    Button them;
    EditText tenloai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        them = findViewById(R.id.btnAdd);
        tenloai = findViewById(R.id.TenLoai);
        final FirebaseController controller = new FirebaseController(this);
/*        Tao loai mon an*/

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoaiMonAn loaiMonAn = new LoaiMonAn(tenloai.getText().toString());
                controller.WirteWithAutoIncreaseKey("LoaiMonAn",loaiMonAn);

            }
        });


    }
}