package com.example.reservationuser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookButtonBase;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class Login extends AppCompatActivity {

    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private TextView mCountryCode,mPhoneNumber;
    private ImageButton mGenerateBtn;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;
    private LoginButton mlogin;

    private void AnhXa(){
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mCountryCode = (TextView)findViewById(R.id.country_code_text);
        mPhoneNumber = (TextView)findViewById(R.id.phone_number_text);
        mGenerateBtn = (ImageButton)findViewById(R.id.btnSigninbyphone);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AnhXa();
        //Phone login
        mGenerateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String country_code = mCountryCode.getText().toString();
                String phone_number = mPhoneNumber.getText().toString();
                String complete_phone = "+"+country_code + phone_number;

                if(country_code.isEmpty() || phone_number.isEmpty()){
                    Toast.makeText(Login.this, "please fill in form to continue", Toast.LENGTH_SHORT).show();
                }
                else {
                    mGenerateBtn.setEnabled(false);
                    mPhoneNumber.setEnabled(false);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            complete_phone,
                            60,
                            TimeUnit.SECONDS,
                            Login.this,
                            mCallBack
                    );
                }
            }
        });
        mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(Login.this, "Verification Failed, please try again.", Toast.LENGTH_SHORT).show();
                Log.d("Loi", e.getMessage());
                mPhoneNumber.setEnabled(true);
                mGenerateBtn.setEnabled(true);
            }

            @Override
            public void onCodeSent(@NonNull final String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                Intent otpIntent = new Intent(Login.this, OTPReceive.class);
                                otpIntent.putExtra("AuthCredentials", s);
                                startActivity(otpIntent);
                            }
                        },
                        10000);
            }
        };

        //fb login
        FacebookSdk.sdkInitialize(getApplicationContext());
        mlogin = findViewById(R.id.login_button);
        mlogin.setReadPermissions("email","public_profile");
        mCallbackManager = CallbackManager.Factory.create();
        mlogin.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("FacebookAuthentication", "onsuccess" + loginResult);
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("FacebookAuthentication", "onCancle");
            }

            @Override
            public void onError(FacebookException e) {
                Log.d("FacebookAuthentication", "onError"+ e);
            }
        });

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendUserToHome();
                            // ...
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(Login.this, "There was an error verifying OTP", Toast.LENGTH_SHORT).show();
                            }
                        }
                        mGenerateBtn.setEnabled(true);
                    }
                });
    }
    private void sendUserToHome() {
        Intent homeIntent = new Intent(Login.this, MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }
    private void handleFacebookToken(AccessToken token){
        Log.d("FacebookAuthentication", "handleFacebookToken" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("FacebookAuthentication", "sign in with credential: successful");
                    mCurrentUser = mAuth.getCurrentUser();
                    updateUI(mCurrentUser);
                }else {
                    Log.d("FacebookAuthentication", "sign in with credential: fail", task.getException());
                    updateUI(null);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private void updateUI(FirebaseUser user){
        if(user != null){
            Intent loginintent = new Intent(Login.this,MainActivity.class);
            loginintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            loginintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            loginintent.putExtra("fbname",user.getDisplayName());
            loginintent.putExtra("fbphone",user.getPhoneNumber());
            startActivity(loginintent);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI(mCurrentUser);
    }

}