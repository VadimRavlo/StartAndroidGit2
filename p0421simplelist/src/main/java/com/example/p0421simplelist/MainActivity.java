package com.example.p0421simplelist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    String[] names = {"Іван", "Марія", "Петро", "Антон", "Даря", "Борис", "Костянтин", "Ігор"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list_view_main);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.my_list_item, names);

        listView.setAdapter(arrayAdapter);
    }
}
