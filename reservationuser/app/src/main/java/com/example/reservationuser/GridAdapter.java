package com.example.reservationuser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class GridAdapter extends BaseAdapter{
    Context context;
    int glayout;
    ArrayList<MonAn> mMonan;
    StorageReference storageReference;

    public GridAdapter(Context context, int glayout, ArrayList<MonAn> mMonan) {
        this.context = context;
        this.glayout = glayout;
        this.mMonan = mMonan;
    }

    @Override
    public int getCount() {
        return mMonan.size();
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
        convertView = inflater.inflate(glayout,null);



        try{
            final ImageView imgfood = (ImageView)convertView.findViewById(R.id.imgFood);
            TextView tvFoodname = (TextView)convertView.findViewById(R.id.tvFoodname);
            TextView tvFoodprice = (TextView)convertView.findViewById(R.id.tvFoodprice);
            final File file = File.createTempFile("image","png");
            storageReference = FirebaseStorage.getInstance().getReference();
            storageReference.child(mMonan.get(position).idhinh).getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    imgfood.setImageBitmap(bitmap);
                }
            });
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
            String vnd = "";
            if(mMonan.get(position).gia != ""){
                vnd = currencyVN.format(Long.parseLong(mMonan.get(position).gia));
            }

            tvFoodname.setText(mMonan.get(position).tenmonan);
            tvFoodprice.setText(vnd);

        }catch (Exception e){
            e.printStackTrace();
        }

        return convertView;
    }
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 12;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
    public void filteredList(ArrayList<MonAn> filterlist) {
        mMonan = filterlist;
        notifyDataSetChanged();
    }
}

