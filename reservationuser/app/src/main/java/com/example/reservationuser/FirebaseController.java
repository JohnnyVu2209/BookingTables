package com.example.reservationuser;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class FirebaseController {
    private FirebaseDatabase database;
    private DatabaseReference referencer;
    private Context context;
    ArrayList<Integer> key = new ArrayList();
    long maxid = 0;
    private final String TAG = "READ DATABASE";
    public FirebaseController(Context context){
        database = FirebaseDatabase.getInstance();
        referencer = database.getReference();
        this.context = context;
    }
    public<T> void Write(String child,T inputData){
        referencer.child(child).setValue(inputData, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if(error == null){
                    Toast.makeText(context, "Lưu thành công", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Lưu thất bại", Toast.LENGTH_SHORT).show();
                    Log.d("THONG BAO LOI:", error.getMessage());
                }
            }
        });
    }
    public<T> void WritePush(String child,T inputData){
        referencer.child(child).push().setValue(inputData, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if(error == null){
                    Toast.makeText(context, "Lưu thành công", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Lưu thất bại", Toast.LENGTH_SHORT).show();
                    Log.d("THONG BAO LOI:", error.getMessage());
                }
            }
        });
    }
    //Tạo key theo thứ tự tăng dần
    public<T> void WirteWithAutoIncreaseKey(final String child, final T inputData){
        referencer.child(child).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Log.d(TAG, "onDataChange: " + dataSnapshot.getKey());
                        key.add(Integer.valueOf(dataSnapshot.getKey()));
                    }
                    maxid = snapshot.getChildrenCount();
                    int lenght =Integer.parseInt(key.get(key.size() - 1).toString());
                    if (isMissing(key,lenght)){
                        UpdateData(child, String.valueOf(getMissingNumber(key,lenght).get(0)),inputData);
                    }
                    else {
                        UpdateData(child, String.valueOf(maxid + 1),inputData);
                    }
                }
                else {
                    UpdateData(child,String.valueOf(1),inputData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //Lấy số bị thiếu trong danh sách
    private ArrayList<Integer> getMissingNumber(ArrayList<Integer> key, int lenght) {
        int missingCount = lenght - key.size();
        BitSet bitSet = new BitSet(lenght);
        for (Integer number : key){
            bitSet.set(number - 1);
        }
        ArrayList<Integer> missingarray = new ArrayList();
        int lastMissingIndex = 0;
        for (int i = 0;i < missingCount; i++){
            lastMissingIndex = bitSet.nextClearBit(lastMissingIndex);
            missingarray.add(++lastMissingIndex);
        }
        return missingarray;
    }
    //kiểm tra xem danh sách có bị thiếu số không
    private Boolean isMissing(ArrayList<Integer> key, int lenght){
        Boolean missing = false;
        int missingCount = lenght - key.size();
        BitSet bitSet = new BitSet(lenght);
        for (Integer number : key){
            bitSet.set(number - 1);
        }
        int lastMissingIndex = 0;
        ArrayList missingarray = new ArrayList();
        for (int i = 0;i < missingCount; i++){
            lastMissingIndex = bitSet.nextClearBit(lastMissingIndex);
            missingarray.add(++lastMissingIndex);
        }
        if (lastMissingIndex != 0){
            missing = true;
        }
        return missing;
    }

    public<T> void UpdateData(String child, String child1, T inputData){
        referencer.child(child).child(child1).setValue(inputData, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if(error == null){
                    Toast.makeText(context, "Lưu thành công", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Lưu thất bại", Toast.LENGTH_SHORT).show();
                    Log.d("THONG BAO LOI:", error.getMessage());
                }
            }
        });
    }

       /* // Read from the database
       do việc đọc không lấy trực tiếp giá trị ra được nên chỉ có thể để vậy cho bây copy
       đọc xong thì những việc tiếp theo dựa theo prototype mà thiết kế
       dưới này là link hướng dẫn cơ bản cho việc đọc một class,có thể xem tham khảo
       https://www.youtube.com/watch?v=AkdeqoqcpLg&list=PLzrVYRai0riTLPLclyGuByHvZ8_tDZZIr&index=11
        referencer.child(Tên Tập Con muốn gọi).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TenClass tenclass = dataSnapshot.getValue(TenClass.class);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }

        });
        */


}
