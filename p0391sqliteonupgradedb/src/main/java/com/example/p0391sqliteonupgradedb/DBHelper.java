package com.example.p0391sqliteonupgradedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by administrator on 03.08.2016.
 */
public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context){
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(Constants.LOG_TAG, "--- OnCreate Database ---");
//        String[] people_position = {"Програмер", "Бухгалтер", "Програмер", "Програмер", "Бухгалтер",
//                "Директор", "Програмер", "Охоронник"};

        ContentValues contentValues = new ContentValues();

        createTablePosition(db, contentValues);
        createTablePeople(db, contentValues);
    }


    void createTablePosition(SQLiteDatabase db, ContentValues contentValues) {
        int[] position_id = {1, 2, 3, 4};
        String[] position_name = {"Директор", "Програмер", "Бухгалтер", "Охоронник"};
        int[] position_salary = {35000, 25000, 8000, 5000};

        db.execSQL("create table " + Constants.TABLE_NAME_POSITION
                + " (_id integer key, " + "name text, salary integer);");
        for (int i=0; i<position_id.length; i++){
            contentValues.clear();
            contentValues.put("_id", position_id[i]);
            contentValues.put("name", position_name[i]);
            contentValues.put("salary", position_salary[i]);
            db.insert(Constants.TABLE_NAME_POSITION, null, contentValues);
        }
    }

    void createTablePeople(SQLiteDatabase db, ContentValues contentValues) {
        String[] people_name = {"Іван", "Марія", "Петро", "Антон", "Дар'я", "Борис", "Костянтин", "Ігор"};
        int[] people_posid = {2, 3, 2, 2, 3, 1, 2, 4};

        db.execSQL("create table " + Constants.TABLE_NAME_PEOPLE
                + " (_id integer primary key autoincrement, "
                + "name text, posid text);");

        for (int i = 0; i < people_name.length; i++) {
            contentValues.clear();
            contentValues.put("name", people_name[i]);
            contentValues.put("posid", people_posid[i]);
            db.insert(Constants.TABLE_NAME_PEOPLE, null, contentValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(Constants.LOG_TAG, "--- onUpgrade database from " + oldVersion + " to " + newVersion + " version ---");
        if (oldVersion == 1 && newVersion == 2){
            ContentValues contentValues = new ContentValues();

            int[] position_id = {1, 2, 3, 4};
            String[] position_name = {"Директор", "Програмер", "Бухгалтер", "Охоронник"};
            int[] position_salary = {35000, 25000, 8000, 5000};

            db.beginTransaction();
            try {
                db.execSQL("create table " + Constants.TABLE_NAME_POSITION
                        + " (_id integer primary key, "
                        + "name text, salary integer);");

                for (int i=0; i<position_id.length; i++){
                    contentValues.clear();
                    contentValues.put("_id", position_id[i]);
                    contentValues.put("name", position_name[i]);
                    contentValues.put("salary", position_salary[i]);
                    db.insert(Constants.TABLE_NAME_POSITION, null, contentValues);
                }

                db.execSQL("alter table " + Constants.TABLE_NAME_PEOPLE + " add column posid integer;");

                for(int i=0; i<position_id.length; i++){
                    contentValues.clear();
                    contentValues.put("posid", position_id[i]);
                    db.update(Constants.TABLE_NAME_PEOPLE, contentValues, "position = ?", new String[] {position_name[i]});
                }

                db.execSQL("create temporary table " + Constants.TABLE_NAME_TMP
                        + " (_id integer, name text, position text, posid integer);");
                db.execSQL("insert into " + Constants.TABLE_NAME_TMP + " select _id, name, position, posid from " + Constants.TABLE_NAME_PEOPLE + ";");
                db.execSQL("drop table " + Constants.TABLE_NAME_PEOPLE + ";");
                db.execSQL("create table " + Constants.TABLE_NAME_PEOPLE
                        + " (_id integer primary key autoincrement, " + "name text, posid integer);");
                db.execSQL("insert into " + Constants.TABLE_NAME_PEOPLE + " select _id, name, posid from " + Constants.TABLE_NAME_TMP + ";");
                db.execSQL("drop table " + Constants.TABLE_NAME_TMP + ";");

                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();

            }
        }
    }
}
