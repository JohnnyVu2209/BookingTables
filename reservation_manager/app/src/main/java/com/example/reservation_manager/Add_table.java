//package com.example.reservation_manager;
//
//import android.content.ContentResolver;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.webkit.MimeTypeMap;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.auth.api.signin.internal.Storage;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.StorageTask;
//
//import java.text.Normalizer;
//import java.util.regex.Pattern;
//
//public class Add_table extends AppCompatActivity {
//
//    EditText number, amount;
//    Button add, cancel;
//
//    tables tabless;
//
//    StorageReference storage;
//    StorageTask uploadTask;
//
//    DatabaseReference reff = FirebaseDatabase.getInstance().getReference();
//    FirebaseController controller;
//    private final String TAG = "READ DATABASE";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.custom_dialog);
//
//        AnhXa();
//
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UpLoadData();
//                controller.WirteWithAutoIncreaseKey("Ban", tabless);
//                intentToListTable();
//            }
//        });
//    }
//
//    private void intentToListTable() {
//        Intent lisTable_intent = new Intent(Add_table.this, ViewTableList.class);
//        lisTable_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        lisTable_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(lisTable_intent);
//        finish();
//    }
//
//    private void UpLoadData() {
//            int SoBan, SoLuongNguoi;
//            SoBan = Integer.parseInt(number.getText().toString());
//            SoLuongNguoi = Integer.parseInt(amount.getText().toString());
//
////            tabless = new tables(SoBan, SoLuongNguoi);
//    }
//
//    private String getExtension(Uri uri) {
//        ContentResolver content = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(content.getType(uri));
//    }
//
////    private void Fireuploader() {
////        idhinh = "FoodImages/"+removeAccent(tenmonan.getText().toString()).replaceAll("\\s","")+"."+"png";
////        StorageReference ref = storage.child(idhinh);
////        uploadTask=ref.putFile(imguri)
////                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
////                    @Override
////                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
////                        // Get a URL to the uploaded content
////                        Uri downloadUrl = taskSnapshot.getUploadSessionUri();
////                    }
////                })
////                .addOnFailureListener(new OnFailureListener() {
////                    @Override
////                    public void onFailure(@NonNull Exception exception) {
////                        // Handle unsuccessful uploads
////                        // ...
////                    }
////                });
////    }
//
////    private String removeAccent(String str) {
////        String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
////        Pattern pattern = Pattern.compile("\\p{InCOMBINING_DIACRITICAL_MARKS}");
////        return pattern.matcher(temp).replaceAll("");
////    }
////
////    private void FileChooser() {
////        Intent intent = new Intent();
////        intent.setType("image/*");
////        intent.setAction(Intent.ACTION_GET_CONTENT);
////        startActivityForResult(intent, 1);
////    }
//
////    @Override
////    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
////        super.onActivityResult(resultCode, resultCode, data);
////        if(requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!=null) {
////            imguri = data.getData();
////            hinhdoan.setImageURI(imguri);
////        }
////    }
//
//    private void AnhXa() {
//        number =(EditText)findViewById(R.id.etNumber);
//        amount =(EditText)findViewById(R.id.etAmount);
//        add =(Button)findViewById(R.id.btnAdd);
//        cancel =(Button)findViewById(R.id.btnCancel);
//         controller = new FirebaseController(getApplicationContext());
//    }
//}
