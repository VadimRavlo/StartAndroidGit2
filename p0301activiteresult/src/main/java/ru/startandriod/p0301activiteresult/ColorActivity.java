package ru.startandriod.p0301activiteresult;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by user on 01.04.2016.
 */
public class ColorActivity extends Activity implements View.OnClickListener{

    Button btnRed, btnGreen, btnBlue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color);

        btnRed = (Button) findViewById(R.id.btnRed);
        btnRed.setOnClickListener(this);

        btnGreen = (Button) findViewById(R.id.btnGreen);
        btnGreen.setOnClickListener(this);

        btnBlue = (Button) findViewById(R.id.btnBlue);
        btnBlue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.btnRed:
                intent.putExtra("color", Color.RED);
                break;
            case R.id.btnGreen:
                intent.putExtra("color", Color.GREEN);
                break;
            case R.id.btnBlue:
                intent.putExtra("color", Color.BLUE);
                break;
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}
