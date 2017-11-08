package com.example.sqliteinsert;

/**
 * Created by Stefan on 16-10-2017.
 */
import android.net.Uri;
import android.provider.BaseColumns;

public class Contract {

    private  Contract() {}

    //Columnen van de database
    public static final class ContractEntry implements BaseColumns {

        public static final String TABLE_NAME = "myTable";
        public static final String COLUMN_UID="_id";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_PASSWORD= "Password";
        public final static String CONTENT_AUTHORITY = "com.example.sqliteinsert";
        public final static Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        public final static Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME);

    }
}
