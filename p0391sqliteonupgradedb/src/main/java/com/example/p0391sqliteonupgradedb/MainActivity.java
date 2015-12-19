package com.example.p0391sqliteonupgradedb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.d(Constants.LOG_TAG, "--- Staff db v." + db.getVersion() + " ---");
        writeStaff(db);
        dbHelper.close();
    }

    private void writeStaff(SQLiteDatabase db){
        Cursor cursor = db.rawQuery("select * from " + Constants.TABLE_NAME_PEOPLE, null);
        logCursor(cursor, "Table ``" + Constants.TABLE_NAME_PEOPLE + "``");
        cursor.close();

        cursor = db.rawQuery("select * from " + Constants.TABLE_NAME_POSITION, null);
        logCursor(cursor, "Table ``" + Constants.TABLE_NAME_POSITION + "``");
        cursor.close();

        String sqlQuery = "select PEOPLE.name as Імя, POSITION.name as Посада, salary as Зарплата "
                + "from people as PEOPLE "
                + "inner join position as POSITION "
                + "on PEOPLE.posid = POSITION._id ";
        cursor = db.rawQuery(sqlQuery, null);
        logCursor(cursor, "inner join");
        cursor.close();
    }

    void logCursor(Cursor cursor, String title){
        if (cursor != null){
            if (cursor.moveToFirst()){
                printTable(cursor, title);
            }
        } else Log.d(Constants.LOG_TAG, title + ". Cursor is null");
    }

    void printTable(Cursor cursor, String title){
        Log.d(Constants.LOG_TAG, title + ". " + cursor.getCount() + " rows");
        StringBuilder stringBuilder = new StringBuilder();
        do{
            stringBuilder.setLength(0);
            for (String columnName: cursor.getColumnNames()){
                stringBuilder.append(columnName + " = " + cursor.getString(cursor.getColumnIndex(columnName)) + "; ");
            }
            Log.d(Constants.LOG_TAG, stringBuilder.toString());
        } while (cursor.moveToNext());
    }
}
