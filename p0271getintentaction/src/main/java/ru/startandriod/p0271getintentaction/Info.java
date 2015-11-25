package ru.startandriod.p0271getintentaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by user on 31.03.2016.
 */
public class Info extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        Intent intent = getIntent();
        String action = intent.getAction();

        String format = "", textInfo = "";

        if (action.equals("ru.startandroid.intent.action.showtime")){
            format = "HH:mm:ss";
            textInfo = "Time: ";
        } else if (action.equals("ru.startandroid.intent.action.showdate")){
            format = "dd.MM.yyyy";
            textInfo = "Date: ";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String datetime = simpleDateFormat.format(new Date(System.currentTimeMillis()));

        TextView tvInfo = (TextView) findViewById(R.id.tvInfo);
        tvInfo.setText(textInfo + datetime);
    }
}
