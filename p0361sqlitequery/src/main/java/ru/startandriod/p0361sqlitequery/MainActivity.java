package ru.startandriod.p0361sqlitequery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    final String LOG_TAG = "myLogs";

    String name[] = {"Китай", "США", "Бразилия", "Россия", "Япония", "Германия", "Египет",
            "Италия", "Франция", "Канада"};
    int people[] = {1400, 325, 208, 146, 128, 82, 89, 60, 66, 35};
    String region[] = {"Азия", "Америка", "Америка", "Европа", "Азия", "Европа", "Африка",
            "Европа", "Европа", "Америка"};

    Button btnAll, btnFunc, btnNumberOfPeople, btnGroup, btnHaving, btnSort;
    EditText etFunc, etNumberOfPeople, etRegionPeople;
    RadioGroup rgSort;

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAll = (Button) findViewById(R.id.btnAll);
        btnAll.setOnClickListener(this);

        btnFunc = (Button) findViewById(R.id.btnFunc);
        btnFunc.setOnClickListener(this);

        btnNumberOfPeople = (Button) findViewById(R.id.btnNumberOfPeople);
        btnNumberOfPeople.setOnClickListener(this);

        btnGroup = (Button) findViewById(R.id.btnGroup);
        btnGroup.setOnClickListener(this);

        btnHaving = (Button) findViewById(R.id.btnHaving);
        btnHaving.setOnClickListener(this);

        btnSort = (Button) findViewById(R.id.btnSort);
        btnSort.setOnClickListener(this);

        etFunc = (EditText) findViewById(R.id.etFunc);
        etNumberOfPeople = (EditText) findViewById(R.id.etNumberOfPeople);
        etRegionPeople = (EditText) findViewById(R.id.etRegionPeople);

        rgSort = (RadioGroup) findViewById(R.id.rgSort);

        dbHelper = new DBHelper(this);
        //connect to our MEGA database
        db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query("mytable", null, null, null, null, null, null);
        //проверка существования записей, если нету, то саполняем поля таблици mytable
        if (cursor.getCount() == 0){
            ContentValues contentValues = new ContentValues();
            Log.d(LOG_TAG, "--- Наполняем таблицу mytable ---");
            for (int i=0; i<10; i++){
                contentValues.put("name", name[i]);
                contentValues.put("people", people[i]);
                contentValues.put("region", region[i]);
                Log.d(LOG_TAG, "id = " + db.insert("mytable", null, contentValues));
            }
        }
        cursor.close();
        dbHelper.close();

        // эмулируем нажатие кнопки btnAll
        onClick(btnAll);
    }

    @Override
    public void onClick(View v) {
        // open database for edit
        db = dbHelper.getWritableDatabase();

        //get data from our smartphone screen
        String sFunc = etFunc.getText().toString();
        String sNumberOfPeople = etNumberOfPeople.getText().toString();
        String sRegionPeople = etRegionPeople.getText().toString();

        //variable for method query
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        //cursor
        Cursor cursor = null;

        switch (v.getId()) {
            case R.id.btnAll:
                Log.d(LOG_TAG, "--- All rows ---");
                cursor = db.query("mytable", null, null, null, null, null, null);
                break;
            case R.id.btnFunc:
                Log.d(LOG_TAG, "--- Function " + sFunc + " ---");
                columns = new String[] {sFunc};
                cursor = db.query("mytable", columns, null, null, null, null, null);
                break;
            case R.id.btnNumberOfPeople:
                Log.d(LOG_TAG, "--- Population more than " + sNumberOfPeople + " ---");
                selection = "people > ?";
                selectionArgs = new String[] {sNumberOfPeople};
                cursor = db.query("mytable", null, selection, selectionArgs, null, null, null);
                break;
            case R.id.btnGroup:
                Log.d(LOG_TAG, "--- Population by the region ---");
                columns = new String[] {"region", "sum(people) as people"};
                groupBy = "region";
                cursor = db.query("mytable", columns, null, null, groupBy, null, null);
                break;
            case R.id.btnHaving:
                Log.d(LOG_TAG, "--- Regions with population more than " + sRegionPeople + " ---");
                columns = new String[] {"region", "sum(people) as people"};
                groupBy = "region";
                having = "sum(people) > " + sRegionPeople;
                cursor = db.query("mytable", columns, null, null, groupBy, having, null);
                break;
            case R.id.btnSort:
                switch (rgSort.getCheckedRadioButtonId()){
                    case R.id.rName:
                        Log.d(LOG_TAG, "--- Сортировка по наименованию ---");
                        orderBy = "name";
                        break;
                    case R.id.rNumberOfPeople:
                        Log.d(LOG_TAG, "--- Сортировка по населению ---");
                        orderBy = "people";
                        break;
                    case R.id.rRegion:
                        Log.d(LOG_TAG, "--- Сортировка по региону ---");
                        orderBy = "region";
                        break;
                }
                cursor = db.query("mytable", null, null, null, null, null, orderBy);
                break;
        }

        if (cursor != null){
            if (cursor.moveToFirst()){
                String str;
                do {
                    str = "";
                    for (String columnName: cursor.getColumnNames()){
                        str = str.concat(columnName + " = "
                                + cursor.getString(cursor.getColumnIndex(columnName)) + "; ");
                    }
                    Log.d(LOG_TAG, str);
                } while (cursor.moveToNext());
            }
            cursor.close();
            Log.d(LOG_TAG, "cursor object close()");
        } else
            Log.d(LOG_TAG, "Cursor is null");
        dbHelper.close();
        Log.d(LOG_TAG, "dbHelper object close()");

    }

    public class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement, " + "name text, "
                    + "people integer, " + "region text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
