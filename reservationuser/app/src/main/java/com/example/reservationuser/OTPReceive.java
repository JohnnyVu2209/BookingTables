package com.example.reservationuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OTPReceive extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private String mVerificationId;
    private EditText motpText;
    private Button mVerifybtn;
    private static final String KEY_VERIFICATION_ID = "key_verification_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_receive);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mVerificationId = getIntent().getStringExtra("AuthCredentials");

        motpText = (EditText)findViewById(R.id.otp_text_view);
        mVerifybtn = (Button)findViewById(R.id.verify_btn);

        mVerifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = motpText.getText().toString();
                if(otp.isEmpty()){
                    Toast.makeText(OTPReceive.this, "Please fill in the form to continue", Toast.LENGTH_SHORT).show();
                }
                else {

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,otp);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendUsertoHome();
                            // ...
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(OTPReceive.this, "Lỗi code OTP", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(mCurrentUser != null)
        {
            sendUsertoHome();
        }
    }
    public void sendUsertoHome(){
        Intent homeIntent = new Intent(OTPReceive.this,InsertName.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }
}