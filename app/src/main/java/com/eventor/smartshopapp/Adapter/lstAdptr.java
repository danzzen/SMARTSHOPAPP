package com.eventor.smartshopapp.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.CursorAdapter;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.eventor.smartshopapp.DATA.ProductContract;
import com.eventor.smartshopapp.R;
import com.squareup.picasso.Picasso;
public class lstAdptr extends CursorAdapter {

    public lstAdptr(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list, parent, false);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvBody = (TextView) view.findViewById(R.id.name);
        TextView tvPriority = (TextView) view.findViewById(R.id.rate);
        ImageView productImage=(ImageView) view.findViewById(R.id.imageView3);
        int name_col=(cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME));
        int priority_col =(cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_RATE));
        int img=(cursor.getColumnIndex(ProductContract.ProductEntry.COLOUMN_PRODUCT_IMAGE));
        String body=cursor.getString(name_col);
       String priority=cursor.getString(priority_col);
       String bt=(cursor.getString(img));
       tvBody.setText("NAME : "+body);
       tvPriority.setText("Quantity : "+priority);
        Picasso.with(context) // Context
                .load(bt)
                .resize(150,150)
                .centerCrop()// URL or file
                .into(productImage); // An ImageView object to show the loaded image
    }
}
