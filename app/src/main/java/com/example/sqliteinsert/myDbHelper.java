package com.example.sqliteinsert;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Stefan on 9-11-2017.
 */

public class myDbHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "myDatabase";
    private static final String TABLE_NAME = "myTable";
    private static final int DATABASE_Version = 1;
    private static final String UID="_id";
    private static final String NAME = "Name";
    private static final String PASSWORD= "Password";
    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME +
            "( "+UID+" INTEGER PRIMARY KEY AUTOINCREMENT , " +NAME+ " VARCHAR(225), " + PASSWORD+" VARCHAR(225));";
    // private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
    private Context context;

    public myDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
        this.context=context;
        Message.message(context,"Started...");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_WERKNEMER_TABLE =  "CREATE TABLE " + UserContract.ContractEntry.TABLE_NAME + " ("
                + UserContract.ContractEntry.COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UserContract.ContractEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + UserContract.ContractEntry.COLUMN_PASSWORD + " TEXT NOT NULL, ";

        db.execSQL( CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          /* try {
               Message.message(context,"OnUpgrade");
               db.execSQL(DROP_TABLE);
               onCreate(db);
           }catch (Exception e) {
               Message.message(context,""+e);
                          }*/
    }

}

