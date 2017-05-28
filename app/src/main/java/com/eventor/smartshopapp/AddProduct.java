package com.eventor.smartshopapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.eventor.smartshopapp.DATA.ProductContract;

import com.eventor.smartshopapp.DATA.ProductContract.ProductEntry;
import com.squareup.picasso.Picasso;

import java.io.IOException;


public class AddProduct extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private EditText mNameEditText, mBreedEditText;
    private ImageButton ibt;
    private ImageView imageView;
    private Uri mCurrentPetUri;
    private static final int x = 0;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri uri;
    private String imgStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        Intent intent = getIntent();
        mCurrentPetUri = intent.getData();

        if (mCurrentPetUri == null) {
            setTitle("Add a Product");
        } else {
            setTitle("Edit Product");
            getSupportLoaderManager().initLoader(x, null, this);
        }

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_pet_name);
        mBreedEditText = (EditText) findViewById(R.id.edit_pet_breed);
        imageView = (ImageView) findViewById(R.id.imageView);
        ibt = (ImageButton) findViewById(R.id.imageButton);
        ibt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRealPathFromURI();
            }
        });

    }

    private void getRealPathFromURI() {
        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
                insertProduct();

                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                deleteProduct();
                // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteProduct() {

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

    private void insertProduct() {

        String name = mNameEditText.getText().toString().trim();
        String rate = mBreedEditText.getText().toString().trim();
        String imageUrl;
        imageUrl = uri.toString();
        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME, name);
        values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_RATE, rate);
        values.put(ProductEntry.COLOUMN_PRODUCT_IMAGE, imageUrl);


        if (mCurrentPetUri == null) {
            Uri newUri = getContentResolver().insert(ProductContract.ProductEntry.CONTENT_URI, values);
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
                ProductEntry._ID,
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRODUCT_RATE,
                ProductEntry.COLOUMN_PRODUCT_IMAGE};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentPetUri,         // Query the content URI for the current pet
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order

    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        if (cursor.moveToFirst()) {
            // Find the columns of pet attributes that we're interested in
            int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
            int rateColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_RATE);
            int imgColumnIndex = cursor.getColumnIndex(ProductEntry.COLOUMN_PRODUCT_IMAGE);
            String name = cursor.getString(nameColumnIndex);
            String breed = cursor.getString(rateColumnIndex);
            String img = cursor.getString(imgColumnIndex);
            Picasso.with(getApplication()) // Context
                    .load(img)// URL or file
                    .into(imageView); // An ImageView object to show the loaded image
            mNameEditText.setText(name);
            mBreedEditText.setText(breed);
            uri = Uri.parse(img);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mNameEditText.setText("");
        mBreedEditText.setText("");
    }
}
