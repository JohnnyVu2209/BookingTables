package com.example.reservation_manager.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservation_manager.DonDatBan.DanhSachDonDat;
import com.example.reservation_manager.MonAn.MonAn;
import com.example.reservation_manager.MonAn.ThemMonAn;
import com.example.reservation_manager.MonAn.XemDanhSachMonAn;
import com.example.reservation_manager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    Button dangnhap;
    EditText etEmail,etPassword;
    FirebaseAuth mAuth;
    FirebaseUser user;
    private void AnhXa() {
        dangnhap = (Button)findViewById(R.id.btnLogin);
        etEmail = (EditText)findViewById(R.id.email_text);
        etPassword = (EditText)findViewById(R.id.password_text);
        user = mAuth.getCurrentUser();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        AnhXa();

        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DangNhap();
            }
        });
        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handle = false;
                if(i == EditorInfo.IME_ACTION_DONE){
                    DangNhap();
                    handle = true;
                }
                return handle;
            }
        });
    }
    private void DangNhap(){
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Authentication", "createUserWithEmail:success");
                            user = mAuth.getCurrentUser();
                            Log.d("TAG", "onComplete: " + user);
                            Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Authentication", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
    private void updateUI(FirebaseUser user){
        if (user != null){
            Intent goHome = new Intent(Login.this, DanhSachDonDat.class);
            startActivity(goHome);
            finish();
        }
    }

    @Override
    protected void onStart() {
        updateUI(user);
        super.onStart();
    }
}