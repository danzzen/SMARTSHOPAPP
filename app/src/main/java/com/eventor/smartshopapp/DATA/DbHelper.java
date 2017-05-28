package com.eventor.smartshopapp.DATA;

import android.content.Context;
import com.eventor.smartshopapp.DATA.BillContract.BillEntry;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenovo on 21-05-2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Product.db";
    private static final String SQL_CREATE_ENTRIES =
                    "CREATE TABLE " + ProductContract.ProductEntry.TABLE_NAME + " (" +
                    ProductContract.ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ProductContract.ProductEntry.COLUMN_PRODUCT_NAME + " TEXT," +
                    ProductContract.ProductEntry.COLOUMN_PRODUCT_IMAGE+ " TEXT,"+
                    ProductContract.ProductEntry.COLUMN_PRODUCT_RATE + " TEXT)" ;
    public static final String SQL_CREATE_ENTRIES2=
            "CREATE TABLE "+BillEntry.TABLE_NAME+" ("+
                    BillEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    BillEntry.COLUMN_Bill_NO+" TEXT,"+
                    BillEntry.COLUMN_Bill_Date+" TEXT,"+
                    BillEntry.COLUMN_Bill_PRODUCT+" TEXT,"+
                    BillEntry.COLUMN_Bill_QUANTITY+" TEXT,"+
                    BillEntry.COLUMN_Bill_UNIT+" TEXT)";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ProductContract.ProductEntry.TABLE_NAME;
    private static final String  SQL_DELETE_ENTRIES2="DROP TABLE IF EXISTS " +BillEntry.TABLE_NAME;
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES2);
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ENTRIES2);
        onCreate(db);
    }
}
