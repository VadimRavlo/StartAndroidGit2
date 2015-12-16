package com.example.p0381sqlitetransaction;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(Constants.LOG_TAG, "--- onCreate Activity ---");

        dbHelper = new DBHelper(this);
        myActions();
    }

    void myActions(){
        try {
            db = dbHelper.getWritableDatabase();
            Utils.delete(db, Constants.MY_TABLE_NAME);
            db.beginTransactionNonExclusive();
            try {
                Utils.insert(db, Constants.MY_TABLE_NAME, "Wadyan");
                Utils.insert(db, Constants.MY_TABLE_NAME, "Lyudka");

                Log.d(Constants.LOG_TAG, "create second DBHelper");
                DBHelper dbHelper2 = new DBHelper(this);
                Log.d(Constants.LOG_TAG, "get db by dbHelper2");
                SQLiteDatabase db2 = dbHelper2.getReadableDatabase();
                Utils.read(db2, Constants.MY_TABLE_NAME);
                dbHelper2.close();
                Log.d(Constants.LOG_TAG, "Close dbHelper2");

                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
            Utils.insert(db, Constants.MY_TABLE_NAME, "Dianella");
            Utils.read(db, Constants.MY_TABLE_NAME);
            dbHelper.close();
        } catch (Exception e) {
            Log.d(Constants.LOG_TAG, e.getClass() + " error: " + e.getMessage());
        }
    }
}
