package com.eventor.smartshopapp.DATA;

import android.net.Uri;
import android.provider.BaseColumns;
public class ProductContract {

 
        public static final String CONTENT_AUTHORITY = "com.eventor.smartshopapp";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        public static final String PATH_PRODUCT = "products";

        private ProductContract(){}

        public static final class ProductEntry implements BaseColumns {
            public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCT);

            public final static String TABLE_NAME="products";
            public final static String  _ID=BaseColumns._ID;
            public final static String  COLUMN_PRODUCT_NAME="name";
            public final static String  COLUMN_PRODUCT_RATE="rate";
            public final static String  COLOUMN_PRODUCT_IMAGE="Image";

        }
    }

