package com.eventor.smartshopapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.eventor.smartshopapp.DATA.BillContract;
import com.eventor.smartshopapp.DATA.ProductContract;

import com.eventor.smartshopapp.DATA.ProductContract.ProductEntry;

/**
 * Created by lenovo on 21-05-2017.
 */

public class AddBill extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private EditText e1,e2,e3,e4,e5;
    private Uri mCurrentPetUri;
    private static final int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbill);
        Intent intent = getIntent();
        mCurrentPetUri = intent.getData();

        if (mCurrentPetUri == null) {
            setTitle("Add a Product");
        } else {
            setTitle("Edit Product");
            getSupportLoaderManager().initLoader(x, null, this);
        }

        // Find all relevant views that we will need to read user input from
     e1=(EditText)findViewById(R.id.billNo);
     e2=(EditText)findViewById(R.id.date);
        e3=(EditText)findViewById(R.id.product_name);
        e4=(EditText)findViewById(R.id.product_qun);
        e5=(EditText)findViewById(R.id.unit);


    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                insertbill();

                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                deleteBill();
                // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void deleteBill(){

        if (mCurrentPetUri == null) {


        } else {
            int row = getContentResolver().delete(mCurrentPetUri, null, null);
            if (row == 0) {
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void insertbill() {

        String n1 = e1.getText().toString().trim();
        String n2 = e2.getText().toString().trim();
        String n3 = e3.getText().toString().trim();
        String n4 = e4.getText().toString().trim();
        String n5 = e5.getText().toString().trim();
        ContentValues values = new ContentValues();
        values.put(BillContract.BillEntry.COLUMN_Bill_NO, n1);
        values.put(BillContract.BillEntry.COLUMN_Bill_Date, n2);
        values.put(BillContract.BillEntry.COLUMN_Bill_PRODUCT, n3);
        values.put(BillContract.BillEntry.COLUMN_Bill_QUANTITY, n4);
        values.put(BillContract.BillEntry.COLUMN_Bill_UNIT, n5);


        if (mCurrentPetUri == null) {
            Uri newUri = getContentResolver().insert(BillContract.BillEntry.CONTENT_URI1, values);
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, "failurekj",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, "Pet Saved",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            int row = getContentResolver().update(mCurrentPetUri, values, null, null);
            if (row == 0) {
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                BillContract.BillEntry._ID,
                BillContract.BillEntry.COLUMN_Bill_NO,
                BillContract.BillEntry.COLUMN_Bill_Date,
                BillContract.BillEntry.COLUMN_Bill_PRODUCT,
                BillContract.BillEntry.COLUMN_Bill_QUANTITY,
                BillContract.BillEntry.COLUMN_Bill_UNIT};
        return new CursorLoader(this, BillContract.BillEntry.CONTENT_URI1,projection,null,null,null);                  // Default sort order
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        if (cursor.moveToFirst()) {
            // Find the columns of pet attributes that we're interested in
            int p=(cursor.getColumnIndex(BillContract.BillEntry.COLUMN_Bill_NO));
            int q=(cursor.getColumnIndex(BillContract.BillEntry.COLUMN_Bill_Date));
            int x=(cursor.getColumnIndex(BillContract.BillEntry.COLUMN_Bill_PRODUCT));
            int y=(cursor.getColumnIndex(BillContract.BillEntry.COLUMN_Bill_QUANTITY));
            int z=(cursor.getColumnIndex(BillContract.BillEntry.COLUMN_Bill_UNIT));
           // Log.d("yo ",Integer.toString(breedColumnIndex));

            // Extract out the value from the Cursor for the given column index
            String n1 = cursor.getString(p);
            String n2 = cursor.getString(q);
            String n3 = cursor.getString(x);
            String n4 = cursor.getString(y);
            String n5= cursor.getString(z);
            // For each of the textViews I’ll set the proper text.

            // Update the views on the screen with the values from the database
            e1.setText(n1);
            e2.setText(n2);
            e3.setText(n3);
            e4.setText(n4);
            e5.setText(n5);
            // For the spinner, I’ll use a switch statement to set its state correctly using the setSelection method.

            // Gender is a dropdown spinner, so map the constant value from the database
            // into one of the dropdown options (0 is Unknown, 1 is Male, 2 is Female).
            // Then call setSelection() so that option is displayed on screen as the current selection.

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
       e1.setText("");
        e2.setText("");
        e3.setText("");
        e4.setText("");
        e5.setText("");
    }
}
