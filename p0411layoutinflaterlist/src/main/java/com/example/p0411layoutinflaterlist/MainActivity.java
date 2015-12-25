package com.example.p0411layoutinflaterlist;

import android.graphics.Color;
import android.renderscript.Byte4;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String[] name = {"Іван", "Марія", "Петро", "Антон", "Даря", "Борис", "Костянтин", "Ігор"};
    String[] position = {"Програмер", "Бухгалтер", "Програмер", "Програмер", "Бухгалтер", "Директор", "Програмер", "Охоронник"};
    int[] salary = {25000, 8000, 25000, 25000, 8000, 35000, 25000, 5000};
    int[] colors = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colors[0] = Color.parseColor("#559966CC");
        colors[1] = Color.parseColor("#55336699");

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lin_layout);

        LayoutInflater layoutInflater = getLayoutInflater();

        for(int i=0; i<name.length; i++){
            Log.d("myLogs", "i = " + i);
            View item_view = layoutInflater.inflate(R.layout.item_element, linearLayout, false);

            TextView tvName = (TextView) item_view.findViewById(R.id.tv_name);
            tvName.setText(name[i]);

            TextView tvPosition = (TextView) item_view.findViewById(R.id.tv_position);
            tvPosition.setText("Посада: " + position[i]);

            TextView tvSalary = (TextView) item_view.findViewById(R.id.tv_salary);
            tvSalary.setText("Оклад: " + salary[i]);

            item_view.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            item_view.setBackgroundColor(colors[i % 2]);

            linearLayout.addView(item_view);
        }
    }
}
