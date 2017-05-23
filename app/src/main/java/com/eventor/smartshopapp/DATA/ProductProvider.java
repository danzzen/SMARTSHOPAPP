package com.eventor.smartshopapp.DATA;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;


/**
 * Created by lenovo on 22-05-2017.
 */

public class ProductProvider extends ContentProvider{
    public static final int PRODUCT=100;
    public static final int PRODUCT_KEY=101;
    public static final int BILL=102;
    public static final int BILL_ID=103;
    public static final UriMatcher sUriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(ProductContract.CONTENT_AUTHORITY,ProductContract.PATH_PRODUCT,PRODUCT);
        sUriMatcher.addURI(ProductContract.CONTENT_AUTHORITY,ProductContract.PATH_PRODUCT+"/#",PRODUCT_KEY);
        sUriMatcher.addURI(BillContract.CONTENT_AUTHORITY1,BillContract.PATH_PRODUCT1,BILL);
        sUriMatcher.addURI(BillContract.CONTENT_AUTHORITY1,BillContract.PATH_PRODUCT1+"/#",BILL_ID);

    }
    private DbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase sq = mDbHelper.getWritableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor = null;
        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCT:
                cursor = sq.query(ProductContract.ProductEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case PRODUCT_KEY:
                selection = ProductContract.ProductEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = sq.query(ProductContract.ProductEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case BILL:
                cursor = sq.query(BillContract.BillEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case BILL_ID:
                selection = BillContract.BillEntry._ID + "=?";
                projection=
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = sq.query(BillContract.BillEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;

    }

    @Nullable
    @Override
    public String getType( Uri uri) {
        return null;
    }
    public Uri insertProduct(Uri uri,ContentValues values)
    {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(ProductContract.ProductEntry.TABLE_NAME, null, values);
        if (id == -1) {
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri, id);
    }
    public Uri insertBill(Uri uri,ContentValues contentValues){
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(BillContract.BillEntry.TABLE_NAME, null, contentValues);
        if (id == -1) {
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri, id);
    }
    @Nullable
    @Override
    public Uri insert(Uri uri,  ContentValues contentValues) {
        final int match=sUriMatcher.match(uri);
        switch(match){
            case PRODUCT:
            {
                return insertProduct(uri,contentValues);
            }
            case BILL:
                return insertBill(uri,contentValues);
            default:
                throw new IllegalArgumentException("chgv"+uri);
        }
    }
private int deleteProduct(Uri uri, String selection, String[] sArgs){
    SQLiteDatabase database = mDbHelper.getWritableDatabase();
    getContext().getContentResolver().notifyChange(uri,null);
    return database.delete(ProductContract.ProductEntry.TABLE_NAME, selection,sArgs );
}
    private int deleteBill(Uri uri, String selection, String[] sArgs){
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        getContext().getContentResolver().notifyChange(uri,null);
        return database.delete(BillContract.BillEntry.TABLE_NAME, selection,sArgs );
    }
    @Override
    public int delete(Uri uri, String s, String[] strings) {
        final int match=sUriMatcher.match(uri);
        switch(match)
        {
            case PRODUCT:
                return deleteProduct(uri,s,strings);
            case PRODUCT_KEY:
                s = ProductContract.ProductEntry._ID + "=?";
                strings = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return deleteProduct(uri,s,strings);
            case BILL:
                return deleteBill(uri,s,strings);
            case BILL_ID:
                s = BillContract.BillEntry._ID + "=?";
                strings = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return deleteBill(uri,s,strings);
            default:
                throw new IllegalArgumentException();
        }
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCT:
               return updatePet(uri, values, selection, selectionArgs);

            case PRODUCT_KEY:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                Toast.makeText(getContext(),ProductContract.ProductEntry._ID,Toast.LENGTH_SHORT).show();
                selection = ProductContract.ProductEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updatePet(uri,values, selection, selectionArgs);
            case BILL:
                return updateBill(uri, values, selection, selectionArgs);
            case BILL_ID:
                selection = BillContract.BillEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateBill(uri,values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }
    private int updateBill(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        if (values.containsKey(BillContract.BillEntry.COLUMN_Bill_NO)) {
            String name = values.getAsString(BillContract.BillEntry.COLUMN_Bill_NO);
            if (name == null) {
                throw new IllegalArgumentException("Pet requires a name");
            }
        }
        // If the {@link ProductContract.ProductEntry#COLUMN_PET_GENDER} key is present,
        // check that the gender value is valid.

        // If the {@link ProductContract.ProductEntry#COLUMN_PET_WEIGHT} key is present,
        // No need to check the breed, any value is valid (including null).

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        getContext().getContentResolver().notifyChange(uri,null);
        return database.update(BillContract.BillEntry.TABLE_NAME,values, selection,selectionArgs );
    }

    /**
     * Update pets in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
     * Return the number of rows that were successfully updated.
     */
    private int updatePet(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        // If the {@link ProductContract.ProductEntry#COLUMN_PET_NAME} key is present,
        // check that the name value is not null.
        if (values.containsKey(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME)) {
            String name = values.getAsString(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Pet requires a name");
            }
        }
        // If the {@link ProductContract.ProductEntry#COLUMN_PET_GENDER} key is present,
        // check that the gender value is valid.

        // If the {@link ProductContract.ProductEntry#COLUMN_PET_WEIGHT} key is present,
        // No need to check the breed, any value is valid (including null).

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        getContext().getContentResolver().notifyChange(uri,null);
        return database.update(ProductContract.ProductEntry.TABLE_NAME,values, selection,selectionArgs );
    }

}
