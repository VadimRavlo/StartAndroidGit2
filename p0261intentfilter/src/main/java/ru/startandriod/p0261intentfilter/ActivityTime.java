package ru.startandriod.p0261intentfilter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;


/**
 * Created by user on 31.03.2016.
 */
public class ActivityTime extends Activity {

    TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = simpleDateFormat.format(new Date(System.currentTimeMillis()));

        tvTime = (TextView) findViewById(R.id.tvTime);
        tvTime.setText(time);
    }
}
