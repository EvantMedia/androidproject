package com.example.sqliteinsert;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class myDbAdapter  {

    myDbHelper helper;

    public myDbAdapter(Context context)
    {
        helper = new myDbHelper(context);
    }

    public long insertData(String name, String password)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.PASSWORD, password);
        long id = db.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }
   static class myDbHelper extends SQLiteOpenHelper
   {
       private static final String DATABASE_NAME = "myDatabase";
       private static final String TABLE_NAME = "myTable";
       private static final int DATABASE_Version = 1;
       private static final String UID="_id";
       private static final String NAME = "Name";
       private static final String PASSWORD= "Password";
       private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME +
               "( "+UID+" INTEGER PRIMARY KEY AUTOINCREMENT ," +NAME+ " VARCHAR(225), " + PASSWORD+ " VARCHAR(225));";
       //private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
       private Context context;

       public myDbHelper(Context context) {
           super(context, DATABASE_NAME, null, DATABASE_Version);
           this.context=context;
           Message.message(context,"Started...");
       }

       @Override
       public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           try {
               Message.message(context,"OnUpgrade");
               db.execSQL("DROP_TABLE");
               onCreate(db);
           }catch (Exception e) {
               Message.message(context,""+e);
                          }
       }

       public void onCreate(SQLiteDatabase db) {

           String SQL_CREATE_WERKNEMER_TABLE =  "CREATE TABLE " + Contract.ContractEntry.TABLE_NAME + " ("
                   + Contract.ContractEntry.COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " //Wordt automaties een value aan gegeven.
                   + Contract.ContractEntry.COLUMN_NAME + " TEXT NOT NULL, "
                   + Contract.ContractEntry.COLUMN_PASSWORD + " TEXT, ";


           db.execSQL( SQL_CREATE_WERKNEMER_TABLE);
       }
   }
}
