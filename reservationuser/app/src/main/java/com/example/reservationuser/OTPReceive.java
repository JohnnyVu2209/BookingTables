package com.example.reservationuser;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OTPReceive extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private String mVerificationId;
    private EditText motpText;
    private Button mVerifybtn;
    private ArrayList<String> khach;
    int i = 0;
    private static final String KEY_VERIFICATION_ID = "key_verification_id";
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("KhachHang");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_receive);

        khach = new ArrayList<String>();

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
                            Doc();
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
    private void Doc(){

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                /*for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String phonemoi = getIntent().getStringExtra("CP");
                    String khachHang = dataSnapshot.child("sdt").getValue(String.class);
                    i++;
                    if(phonemoi.equals(khachHang)){
                        sendUsertoHome();
                    }
                    else if(!phonemoi.equals(khachHang) && i == dataSnapshot.getChildrenCount()){
                        sendUsertoInsertName();
                    }
                }*/
                khach.add(snapshot.child("sdt").getValue().toString());
                i++;
                final String phonemoi = getIntent().getStringExtra("CP");
                Log.d("lenght", "onChildAdded: " + khach + phonemoi);
                snapshot.getRef().getParent().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long maxid = snapshot.getChildrenCount();
                        Log.d("SINGLE", "onDataChange: " + khach+" " + phonemoi);
                        if (khach.contains(phonemoi)){
                            sendUsertoHome();
                        }
                        else{
                            sendUsertoInsertName();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void sendUsertoHome(){
        Intent homeIntent = new Intent(OTPReceive.this,MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }
    public void sendUsertoInsertName(){
        Intent InsertIntent = new Intent(OTPReceive.this,InsertName.class);
        InsertIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        InsertIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(InsertIntent);
        finish();
    }
}