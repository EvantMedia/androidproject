package com.example.sqliteinsert;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Stefan on 9-11-2017.
 */

/* Content providers dienen als een soort laag tussen de database en de activity */
public class UserProvider extends ContentProvider {


    public static final String LOG_TAG = UserContract.class.getSimpleName();
    private static final int USERS = 100;
    private static final int USERS_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH_TABLE, USERS);
        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH_TABLE +"/#", USERS_ID);
    }

    private myDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new myDbHelper(getContext());
        return false;
    }

    //Uri = Wordt in de query method gegooit
    //Projection = gooit _Id en Name in de query method
    //Selection = de mee gegeven selectie bijvoorbeeld "+?"
    //SelectionArgs = hier komt het daad werkelijke id in te staat dus bijvoorbeeld { "5" }
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        //Cursor geeft read write access aan het resultaat van een database query.
        Cursor cursor;

        int match = sUriMatcher.match(uri);

        //Werkt het zelfde als een if statement maar gebruikt nu cases.
        switch (match){
            case USERS:
                cursor = database.query(UserContract.ContractEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case USERS_ID:
                selection = UserContract.ContractEntry._ID + "+?";

                //Zet het id in de Arg
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(UserContract.ContractEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Kan de gebruiker met id " + uri + " niet verwijderen.");
        }
        //Hier staat de query in.
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert( Uri uri, ContentValues contentValues) {

        //Maak een final int van de urimatcher.match
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case USERS:
                return insertUser(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertUser(Uri uri, ContentValues values) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        //Insert op de database met de values naam en password
        long id = database.insert(UserContract.ContractEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        //Geef het id weer terug in de uri
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        //Maak een final int van de urimatcher.match
        final int match = sUriMatcher.match(uri);

        //Kijk welke uri matched.
        switch (match) {
            case USERS:
                // Delete all rows that match the selection and selection args
                return database.delete(UserContract.ContractEntry.TABLE_NAME, selection, selectionArgs);
            case USERS_ID:
                // Delete a single row given by the ID in the URI
                selection = UserContract.ContractEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return database.delete(UserContract.ContractEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Kan id: " + uri + "Niet verwijderen.");
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}

