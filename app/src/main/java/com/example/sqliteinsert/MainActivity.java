package com.example.sqliteinsert;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText Name, Pass, Delete;
    myDbHelper helper;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name= (EditText) findViewById(R.id.editName);
        Pass= (EditText) findViewById(R.id.editPass);
        Delete= (EditText) findViewById(R.id.editText6);
        helper = new myDbHelper(this);
        context = this;
    }

    public void addUser(View view)
    {
        ContentValues values = new ContentValues();
        values.put(UserContract.ContractEntry.COLUMN_NAME, Name.getText().toString().trim());
        values.put(UserContract.ContractEntry.COLUMN_PASSWORD, Pass.getText().toString().trim());


        Uri newUri = getContentResolver().insert(UserContract.ContractEntry.CONTENT_URI, values);

        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this,"Unsuccesful",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(this, "JA HIJ WERKT!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //Dit grapje hebben wij helemaal niet nodig.
    public void displayDatabaseInfo(){
        String[] projection = {
                UserContract.ContractEntry.COLUMN_NAME,
                UserContract.ContractEntry.COLUMN_PASSWORD };

        Cursor cursor = getContentResolver().query(UserContract.ContractEntry.CONTENT_URI, projection, null, null, null);
    }

    //delete functie
    public void DeleteUsers(View view) {
        //De contentresolver komt weer terug in de contentprovider
        ContentResolver cr =  getContentResolver();
        String Name = "Name=?";
        String[] args = new String[] { Delete.getText().toString().trim() };

        if(UserContract.ContractEntry.CONTENT_URI != null){
            int deletedRow = cr.delete(UserContract.ContractEntry.CONTENT_URI, Name, args );

            if(deletedRow == 0){

                Toast.makeText(this, "De database is leeg!",
                        Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(this, "Succesvolle delete aantal rows verwijderd: " + deletedRow,
                        Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void deleteUser(View view)
    {
        DeleteUsers(view);
    }
}