package com.eventor.smartshopapp.Adapter;

import android.content.Context;
import android.database.Cursor;
import com.eventor.smartshopapp.DATA.BillContract.BillEntry;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eventor.smartshopapp.DATA.BillContract;
import com.eventor.smartshopapp.R;

import org.w3c.dom.Text;

/**
 * Created by lenovo on 23-05-2017.
 */

public class Bill_listAdapter extends CursorAdapter {
    public Bill_listAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
       return LayoutInflater.from(context).inflate(R.layout.bill_list,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView a=(TextView)view.findViewById(R.id.bno);
        TextView b=(TextView)view.findViewById(R.id.dt);
        TextView c=(TextView)view.findViewById(R.id.productnm);
        TextView d=(TextView)view.findViewById(R.id.qnt);
        TextView e=(TextView)view.findViewById(R.id.unt);
        TextView f=(TextView)view.findViewById(R.id.total);
        int p=(cursor.getColumnIndex(BillEntry.COLUMN_Bill_NO));
        int q=(cursor.getColumnIndex(BillEntry.COLUMN_Bill_Date));
        int x=(cursor.getColumnIndex(BillEntry.COLUMN_Bill_PRODUCT));
        int y=(cursor.getColumnIndex(BillEntry.COLUMN_Bill_QUANTITY));
        int z=(cursor.getColumnIndex(BillEntry.COLUMN_Bill_UNIT));
        String p1=cursor.getString(p);
        String q1=cursor.getString(q);
        String x1=cursor.getString(x);
        String y1=cursor.getString(y);
        String z1=cursor.getString(z);
        float number = Float.parseFloat(y1);
        float n2=Float.parseFloat(z1);
        float T=n2*number;
        String total=String.valueOf(T);
        a.setText(p1);
        b.setText(q1);
        c.setText(x1);
        d.setText(y1);
        e.setText(z1);
        f.setText(total);
    }
}
