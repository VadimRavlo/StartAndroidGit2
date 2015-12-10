package ru.startandriod.p0341simplesqlite;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    final String LOG_TAG = "myLogs";

    Button btnAdd, btnRead, btnClear, btnUpd, btnDel;
    EditText etName, etEmail, etID;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etID = (EditText) findViewById(R.id.etID);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        btnUpd = (Button) findViewById(R.id.btnUpd);
        btnUpd.setOnClickListener(this);

        btnDel = (Button) findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        // создаем объект ContentValues в виде <Map> для данных, в котором собираем нужные значения
        ContentValues contentValues = new ContentValues();

        // получаем данные из полей ввода
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String id = etID.getText().toString();

        //Идентифицируем переменную SQL базу даных - главный объект класса
        SQLiteDatabase database;

        // подключаемся к БД в режиме записи
        database = dbHelper.getWritableDatabase();

        switch(v.getId()){
            case R.id.btnAdd:
                Log.d(LOG_TAG, "--- Insert in my table: ---");

                // подготовим данные для вставки в виде пар: наименование столбца - значение
                contentValues.put("name", name);
                contentValues.put("email", email);

                // вставляем запись и получаем ее ID
                long rowID = database.insert("mytable", null, contentValues);

                //опциональный код для измерения времени операций
                /*for (int i=1; i<1000; i++){
                    rowID = database.insert("mytable", null, contentValues);
                }*/

                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                break;
            case R.id.btnRead:
                Log.d(LOG_TAG, "--- Rows in mytable ---");

                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                Cursor cursor = database.query("mytable", null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false, метод moveToFirst типа boolean
                if (cursor.moveToFirst()){
                    // определяем номера столбцов по имени в выборке
                    int idColumnIndex = cursor.getColumnIndex("id");
                    int nameColumnIndex = cursor.getColumnIndex("name");
                    int emailColumnIndex = cursor.getColumnIndex("email");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + idColumnIndex + "/" + cursor.getInt(idColumnIndex)+
                                ", name = " + nameColumnIndex + "/" + cursor.getString(nameColumnIndex)+
                                ", email = " + emailColumnIndex + "/" + cursor.getString(emailColumnIndex));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (cursor.moveToNext());
                } else {
                    Log.d(LOG_TAG, "0 rows");
                }
                cursor.close();
                break;
            case R.id.btnClear:
                Log.d(LOG_TAG, "--- Cleare mytable: ---");

                // удаляем все записи
                int clearCount = database.delete("mytable", null, null);

                Log.d(LOG_TAG, "deleted rows count = " + clearCount);
                break;
            case R.id.btnUpd:
                if (id.equalsIgnoreCase("")){
                    break;
                }
                Log.d(LOG_TAG, "--- Update mytable: ---");

                // подготовим значения для обновления
                contentValues.put("name", name);
                contentValues.put("email", email);

                // обновляем по id
                int updCount = database.update("mytable", contentValues, "id = " + id, null);
                //"id = ?", new String[] {id});
                Log.d(LOG_TAG, "update rows count = " + updCount);
                break;
            case R.id.btnDel:
                if (id.equalsIgnoreCase("")){
                    break;
                }
                Log.d(LOG_TAG, "--- Delete from mytable ---");
                //удаляем по ID
                int delCount = database.delete("mytable", "id = " + id, null);
                Log.d(LOG_TAG, "delete rows count = " + delCount);
                break;
        }
        // закрываем подключение к БД
        dbHelper.close();
    }

    class DBHelper extends SQLiteOpenHelper {
        // конструктор суперкласса
        public DBHelper(Context context){
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");

            // создаем таблицу с полями
            db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "email text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
