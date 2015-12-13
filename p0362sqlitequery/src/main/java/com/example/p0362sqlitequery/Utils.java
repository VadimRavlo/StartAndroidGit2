package com.example.p0362sqlitequery;

import android.database.Cursor;
import android.util.Log;

/**
 * Created by administrator on 08.06.2016.
 */
public class Utils {
    public static void printTable(Cursor cursor){
        String str;
        do {
            str = "";
            for (String columnName: cursor.getColumnNames()){
                str = str.concat(columnName + " = "
                        + cursor.getString(cursor.getColumnIndex(columnName)) + "; ");
            }
            Log.d(Constants.LOG_TAG, str);
        } while (cursor.moveToNext());
    }

}
