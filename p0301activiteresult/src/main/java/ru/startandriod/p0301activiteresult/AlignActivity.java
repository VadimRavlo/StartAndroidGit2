package ru.startandriod.p0301activiteresult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

/**
 * Created by user on 01.04.2016.
 */
public class AlignActivity extends Activity implements View.OnClickListener {

    Button btnLeft, btnCenter, btnRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.align);

        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnLeft.setOnClickListener(this);

        btnCenter = (Button) findViewById(R.id.btnCenter);
        btnCenter.setOnClickListener(this);

        btnRight = (Button) findViewById(R.id.btnRight);
        btnRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.btnLeft:
                intent.putExtra("alignment", Gravity.LEFT);
                break;
            case R.id.btnCenter:
                intent.putExtra("alignment", Gravity.CENTER);
                break;
            case R.id.btnRight:
                intent.putExtra("alignment", Gravity.RIGHT);
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}
