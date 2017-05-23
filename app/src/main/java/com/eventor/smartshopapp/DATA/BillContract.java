package com.eventor.smartshopapp.DATA;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by lenovo on 23-05-2017.
 */

public class BillContract {
    public static final String CONTENT_AUTHORITY1= "com.eventor.smartshopapp";
    public static final Uri BASE_CONTENT_URI1= Uri.parse("content://" + CONTENT_AUTHORITY1);
    public static final String PATH_PRODUCT1 = "bills";

    private BillContract(){}

    public static final class BillEntry implements BaseColumns {
        public static final Uri CONTENT_URI1 = Uri.withAppendedPath(BASE_CONTENT_URI1, PATH_PRODUCT1);

        public final static String TABLE_NAME="bills";
        public final static String  _ID=BaseColumns._ID;
        public final static String  COLUMN_Bill_NO="no";
        public final static String  COLUMN_Bill_Date="date";
        public final static String  COLUMN_Bill_PRODUCT="product";
        public final static String  COLUMN_Bill_QUANTITY="quantity";
        public final static String  COLUMN_Bill_UNIT="unit";
    }
}
