package ru.startandriod.p0281intentextras;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by user on 31.03.2016.
 */
public class ViewActivity extends Activity{

    TextView tvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);

        TextView tvView = (TextView) findViewById(R.id.tvView);

        Intent intent = getIntent();

        String fname = intent.getStringExtra("fname");
        String lname = intent.getStringExtra("lname");

        tvView.setText("Your name is: " + fname + " " + lname);
    }
}
