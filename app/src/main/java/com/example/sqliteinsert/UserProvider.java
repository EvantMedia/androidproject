package com.example.sqliteinsert;

/**
 * Created by Stefan on 8-11-2017.
 */

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import android.content.ContentProvider;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Switch;

import com.example.sqliteinsert.Contract.ContractEntry;

/**
 * Created by Stefan on 8-11-2017.
 */

public class UserProvider extends ContentProvider {

    public static final String LOG_TAG = UserProvider.class.getSimpleName();

    private static final int USERS = 100;

    private static final int USER_ID = 101;

    private myDbAdapter.myDbHelper dbHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(Contract.ContractEntry.CONTENT_AUTHORITY , Contract.ContractEntry.TABLE_NAME , USERS);
        sUriMatcher.addURI(Contract.ContractEntry.CONTENT_AUTHORITY , Contract.ContractEntry.TABLE_NAME + "/#" , USER_ID);
    }

    private myDbAdapter mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new myDbAdapter(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        SQLiteDatabase database = dbHelper.getReadableDatabase();


        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case 100:
                cursor = database.query(Contract.ContractEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case 101:
                selection = Contract.ContractEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(Contract.ContractEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case 100:
                return database.delete(Contract.ContractEntry.TABLE_NAME, selection, selectionArgs);
            case 101:
                selection = Contract.ContractEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return database.delete(Contract.ContractEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("De delete werkt niet voor: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}