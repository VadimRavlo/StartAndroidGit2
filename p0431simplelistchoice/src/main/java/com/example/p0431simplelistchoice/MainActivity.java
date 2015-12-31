package com.example.p0431simplelistchoice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    final String LOG_TAG = "myLogs";

    ListView lvMain;
    String[] names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMain = (ListView) findViewById(R.id.lv_main);
        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.names, android.R.layout.simple_list_item_multiple_choice);
        lvMain.setAdapter(arrayAdapter);

        Button btnChecked = (Button) findViewById(R.id.btn_checked);
        btnChecked.setOnClickListener(this);

        names = getResources().getStringArray(R.array.names);
    }

    @Override
    public void onClick(View view) {
        Log.d(LOG_TAG, "checked: ");
        SparseBooleanArray sparseBooleanArray = lvMain.getCheckedItemPositions();
        for (int i=0; i<sparseBooleanArray.size(); i++){
            int key = sparseBooleanArray.keyAt(i);
            if(sparseBooleanArray.get(key))
                Log.d(LOG_TAG, names[key]);
        }
    }
}
