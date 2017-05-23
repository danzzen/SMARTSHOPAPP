package com.eventor.smartshopapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.eventor.smartshopapp.DATA.BillContract.BillEntry;
import com.eventor.smartshopapp.Adapter.Bill_listAdapter;
public class Bill extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    Bill_listAdapter cursorAdapter;
    private static final int LOADER_ID = 0;
    @Override
    
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        ListView billList = (ListView) findViewById(R.id.billItems);
// Setup cursor adapter using cursor from last step
        View emptyView = findViewById(R.id.emppty);
        billList.setEmptyView(emptyView);
        cursorAdapter = new Bill_listAdapter(this, null);
     //   billList.setAdapter(cursorAdapter);
        getLoaderManager().initLoader(LOADER_ID,null,this);
        billList.setAdapter(cursorAdapter);
        billList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplication(),AddBill.class);
                Uri curi= ContentUris.withAppendedId(BillEntry.CONTENT_URI1,l);
                intent.setData(curi);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
           Intent i=new Intent(Bill.this,AddBill.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                BillEntry._ID,
                BillEntry.COLUMN_Bill_NO,
                BillEntry.COLUMN_Bill_Date,
                BillEntry.COLUMN_Bill_PRODUCT,
                BillEntry.COLUMN_Bill_QUANTITY,
                BillEntry.COLUMN_Bill_UNIT};
        return new CursorLoader(this, BillEntry.CONTENT_URI1,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}
