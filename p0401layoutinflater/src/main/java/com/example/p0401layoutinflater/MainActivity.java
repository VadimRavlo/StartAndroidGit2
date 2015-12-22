package com.example.p0401layoutinflater;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LayoutInflater layoutInflater = getLayoutInflater();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lin_layout);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rel_layout);

        Utils.inflateViewToLinearLayout(layoutInflater, linearLayout);
        Utils.inflateViewToRelativeLayout(layoutInflater, relativeLayout);
    }
}