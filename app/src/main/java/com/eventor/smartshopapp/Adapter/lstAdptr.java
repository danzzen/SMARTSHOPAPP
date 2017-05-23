package com.eventor.smartshopapp.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.eventor.smartshopapp.DATA.ProductContract;
import com.eventor.smartshopapp.R;


/**
 * Created by lenovo on 21-05-2017.
 */

public class lstAdptr extends CursorAdapter {

    public lstAdptr(Context context, Cursor c) {
        super(context, c,0);
//        Toast.makeText(context, c.getString(c.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME)), Toast.LENGTH_SHORT).show();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list, parent, false);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvBody = (TextView) view.findViewById(R.id.name);
        TextView tvPriority = (TextView) view.findViewById(R.id.rate);
        int name_col=(cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME));
       int priority_col =(cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_RATE));
        String body=cursor.getString(name_col);
       String priority=cursor.getString(priority_col);
       tvBody.setText("NAME : "+body);
       tvPriority.setText("Quantity : "+priority);
    }
}
