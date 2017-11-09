package com.example.sqliteinsert;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Stefan on 9-11-2017.
 */

public class UserContract {
    public static final String CONTENT_AUTHORITY = "com.example.sqliteinsert";
    //Geeft het pad van de uri mee
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //Table pad
    public static final String PATH_TABLE = "myTable";


    public static final class ContractEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TABLE);

        public static final String TABLE_NAME = "myTable";
        public static final String COLUMN_UID = BaseColumns._ID ; // Autoincrement
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_PASSWORD = "Password";

    }
}
