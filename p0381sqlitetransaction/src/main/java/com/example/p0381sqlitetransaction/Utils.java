package com.example.p0381sqlitetransaction;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by administrator on 03.08.2016.
 */
public class Utils {
    public static void insert(SQLiteDatabase db, String table, String value){
        Log.d(Constants.LOG_TAG, "Insert in table ``" + table + "`` value = ``" + value + "``");

        ContentValues contentValues = new ContentValues();
        contentValues.put("value", value);
        db.insert(table, null, contentValues);
    }

    public static void read(SQLiteDatabase db, String table){
        Log.d(Constants.LOG_TAG, "Read table ``" + table + "``");

        Cursor cursor = db.query(table, null, null, null, null, null, null);
        if (cursor != null){
            Log.d(Constants.LOG_TAG, "Record count = " + cursor.getCount());

            if (cursor.moveToFirst()){
                Utils.printTable(cursor);
            }
        }
    }

    public static void printTable(Cursor cursor){
        do{
            Log.d(Constants.LOG_TAG, cursor.getString(cursor.getColumnIndex("_id")) + " - " + cursor.getString(cursor.getColumnIndex("value")));
        } while (cursor.moveToNext());
    }

    public static void delete(SQLiteDatabase db, String table){
        Log.d(Constants.LOG_TAG, "Delete all from table " + table);

        db.delete(table, null, null);
    }
}
