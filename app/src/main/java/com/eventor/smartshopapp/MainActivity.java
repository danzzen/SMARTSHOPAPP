package com.eventor.smartshopapp;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.eventor.smartshopapp.Adapter.lstAdptr;
import com.eventor.smartshopapp.DATA.ProductContract;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor>{
  public static final int PET_LOADER=0;
    lstAdptr mCursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddProduct.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ListView lvItems = (ListView) findViewById(R.id.lvItems);
// Setup cursor adapter using cursor from last step
        View emptyView = findViewById(R.id.emppty);
        lvItems.setEmptyView(emptyView);
        mCursorAdapter = new lstAdptr(this, null);
        lvItems.setAdapter(mCursorAdapter);
        getLoaderManager().initLoader(PET_LOADER,null, this);
        lvItems.setAdapter(mCursorAdapter);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplication(),AddProduct.class);
             Uri curi= ContentUris.withAppendedId(ProductContract.ProductEntry.CONTENT_URI,l);
               intent.setData(curi);
              startActivity(intent);
            }
        });
    }

    protected void onStart() {
        super.onStart();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            moveTaskToBack(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharedPreferences sharedPrefs = getSharedPreferences("appData", MODE_PRIVATE);

            SharedPreferences.Editor prefsEditr = sharedPrefs.edit();
            prefsEditr.putString("logout"+"","yes");
            prefsEditr.commit();
            prefsEditr.apply();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
           // insertPet();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(MainActivity.this, Bill.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//    private void insertPet() {
//        ContentValues values = new ContentValues();
//
//        values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME, "mohit");
//        values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_RATE, "Rahul");
//
//
//// Insert the new row, returning the primary key value of the new row
//        //   long newRowId = db.insert(ProductContract.ProductEntry.TABLE_NAME, null, values); without content resolver
//        Uri newUri = getContentResolver().insert(ProductContract.ProductEntry.CONTENT_URI, values);
//    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] prejection = {ProductContract.ProductEntry._ID,
                ProductContract.ProductEntry.COLUMN_PRODUCT_NAME,
                ProductContract.ProductEntry.COLUMN_PRODUCT_RATE,
                ProductContract.ProductEntry.COLOUMN_PRODUCT_IMAGE
        };
        return new CursorLoader(this, ProductContract.ProductEntry.CONTENT_URI,prejection,null,null,null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);

    }

    // @Override
//    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
//        String[] prejection = {ProductContract.ProductEntry._ID,
//                ProductContract.ProductEntry.COLUMN_PRODUCT_NAME,
//                ProductContract.ProductEntry.COLUMN_PRODUCT_RATE
//        };
//        return new CursorLoader(this, ProductContract.ProductEntry.CONTENT_URI,prejection,null,null,null);
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
//        mCursorAdapter.swapCursor(cursor);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//        mCursorAdapter.swapCursor(null);
//    }
}
