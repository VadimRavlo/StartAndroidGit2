package com.example.p0362sqlitequery;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    String name[] = {"Китай", "США", "Бразилия", "Россия", "Япония", "Германия", "Египет",
            "Италия", "Франция", "Канада"};
    int people[] = {1400, 325, 208, 146, 128, 82, 89, 60, 66, 35};
    String region[] = {"Азия", "Америка", "Америка", "Европа", "Азия", "Европа", "Африка",
            "Европа", "Европа", "Америка"};

    @Bind(R.id.btn_all) Button btnAll;
    @Bind(R.id.btn_func) Button btnFunc;
    @Bind(R.id.btn_number_of_people) Button btnNumberOfPeople;
    @Bind(R.id.btn_group) Button btnGroup;
    @Bind(R.id.btn_having) Button btnHaving;
    @Bind(R.id.btn_sort) Button btnSort;

    @Bind(R.id.edit_func) EditText etFunc;
    @Bind(R.id.edit_number_of_people) EditText etNumberOfPeople;
    @Bind(R.id.edit_region_number_of_people) EditText etRegionPeople;

    @Bind(R.id.rg_sort) RadioGroup rgSort;

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        btnAll.setOnClickListener(this);
        btnFunc.setOnClickListener(this);
        btnNumberOfPeople.setOnClickListener(this);
        btnGroup.setOnClickListener(this);
        btnHaving.setOnClickListener(this);
        btnSort.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        //connect to our MEGA database
        db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query("mytable", null, null, null, null, null, null);

        //проверка существования записей, если нету, то саполняем поля таблици mytable
        if (cursor.getCount() == 0){
            ContentValues contentValues = new ContentValues();
            Log.d(Constants.LOG_TAG, "--- Наполняем таблицу mytable ---");
            for (int i=0; i<10; i++){
                contentValues.put("name", name[i]);
                contentValues.put("people", people[i]);
                contentValues.put("region", region[i]);
                Log.d(Constants.LOG_TAG, "id = " + db.insert("mytable", null, contentValues));
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
            case R.id.btn_all:
                Log.d(Constants.LOG_TAG, "--- All rows ---");
                cursor = db.query("mytable", null, null, null, null, null, null);
                break;
            case R.id.btn_func:
                Log.d(Constants.LOG_TAG, "--- Function " + sFunc + " ---");
                columns = new String[] {sFunc};
                cursor = db.query("mytable", columns, null, null, null, null, null);
                break;
            case R.id.btn_number_of_people:
                Log.d(Constants.LOG_TAG, "--- Population more than " + sNumberOfPeople + " ---");
                selection = "people > ?";
                selectionArgs = new String[] {sNumberOfPeople};
                cursor = db.query("mytable", null, selection, selectionArgs, null, null, null);
                break;
            case R.id.btn_group:
                Log.d(Constants.LOG_TAG, "--- Population by the region ---");
                columns = new String[] {"region", "sum(people) as people"};
                groupBy = "region";
                cursor = db.query("mytable", columns, null, null, groupBy, null, null);
                break;
            case R.id.btn_having:
                Log.d(Constants.LOG_TAG, "--- Regions with population more than " + sRegionPeople + " ---");
                columns = new String[] {"region", "sum(people) as people"};
                groupBy = "region";
                having = "sum(people) > " + sRegionPeople;
                cursor = db.query("mytable", columns, null, null, groupBy, having, null);
                break;
            case R.id.btn_sort:
                switch (rgSort.getCheckedRadioButtonId()){
                    case R.id.r_name:
                        Log.d(Constants.LOG_TAG, "--- Сортировка по наименованию ---");
                        orderBy = "name";
                        break;
                    case R.id.r_number_of_people:
                        Log.d(Constants.LOG_TAG, "--- Сортировка по населению ---");
                        orderBy = "people";
                        break;
                    case R.id.r_region:
                        Log.d(Constants.LOG_TAG, "--- Сортировка по региону ---");
                        orderBy = "region";
                        break;
                }
                cursor = db.query("mytable", null, null, null, null, null, orderBy);
                break;
        }

        if (cursor != null){
            if (cursor.moveToFirst()){
                Utils.printTable(cursor);
            }
            cursor.close();
            Log.d(Constants.LOG_TAG, "cursor object close()");
        } else
            Log.d(Constants.LOG_TAG, "Cursor is null");

        dbHelper.close();
        Log.d(Constants.LOG_TAG, "dbHelper object close()");
    }
}
