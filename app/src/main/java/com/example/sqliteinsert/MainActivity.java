package com.example.sqliteinsert;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText Name, Pass;
    myDbAdapter helper;
    Context context;
    private myDbAdapter mDbHelper;
    private UserProvider userprovider;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name= (EditText) findViewById(R.id.editName);
        Pass= (EditText) findViewById(R.id.editPass);
        helper = new myDbAdapter(this);
        mDbHelper = new myDbAdapter(this);
        userprovider = new UserProvider();
        context = this;
    }

    private void insertPet() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = Name.getText().toString().trim();
        String passString = Pass.getText().toString().trim();

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(Contract.ContractEntry.COLUMN_NAME, nameString);
        values.put(Contract.ContractEntry.COLUMN_PASSWORD, passString);


        // Insert a new pet into the provider, returning the content URI for the new pet.
        Uri newUri = getContentResolver().insert(Contract.ContractEntry.CONTENT_URI, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, "Fout",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(this, "Goed",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void addUser(View view)
    {
        Toast.makeText(this,"Running", Toast.LENGTH_LONG).show();
        String t1 = Name.getText().toString();
        String t2 = Pass.getText().toString();
        long identity = helper.insertData(t1,t2);
        if(identity<0)
        {
            Message.message(context,"Unsuccessful");
        } else
        {
            Message.message(context,"Successful");
        }
    }

    public void delete(View view) {
        String t1 = Name.getText().toString();
        ContentValues values = new ContentValues();
        values.put(Contract.ContractEntry.COLUMN_NAME , t1);
       // Uri newUri = getContentResolver().delete(Contract.ContractEntry.CONTENT_URI, values);
    }
}
