package com.example.p0401layoutinflater;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by administrator on 04.08.2016.
 */
public class Utils {

    static final String LOG_TAG = "myLog";

    static void inflateViewToLinearLayout(LayoutInflater layoutInflater, LinearLayout linearLayout) {
        View view1_of_text_view = layoutInflater.inflate(R.layout.layout_text_view, linearLayout, true);
//        linearLayout.addView(view1_of_text_view);
        ViewGroup.LayoutParams layoutParams1 = view1_of_text_view.getLayoutParams();

        Log.d(LOG_TAG, "Class of view1: " + view1_of_text_view.getClass().toString());
        Log.d(LOG_TAG, "Layout parameters of view1 is null: " + (layoutParams1 == null));
        Log.d(LOG_TAG, "Class of layoutParameters: " + layoutParams1.getClass().toString());
        try {
        Log.d(LOG_TAG, "Text of view1: " + ((TextView) view1_of_text_view).getText());
        } catch (Exception e){
            Log.d(LOG_TAG, e.toString());
        }
    }

    static void inflateViewToRelativeLayout(LayoutInflater layoutInflater, RelativeLayout relativeLayout){
        View view2_of_text_view = layoutInflater.inflate(R.layout.layout_text_view, relativeLayout, true);
//        relativeLayout.addView(view2_of_text_view);
        ViewGroup.LayoutParams layoutParams2 = view2_of_text_view.getLayoutParams();

        Log.d(LOG_TAG, "Class of view2: " + view2_of_text_view.getClass().toString());
        Log.d(LOG_TAG, "Layout parameters of view2 is null: " + (layoutParams2 == null));
        Log.d(LOG_TAG, "Class of layoutParameters: " + layoutParams2.getClass().toString());
        try {
            Log.d(LOG_TAG, "Text of view1: " + ((TextView) view2_of_text_view).getText());
        } catch (Exception e){
            Log.d(LOG_TAG, e.toString());
        }
        ;
    }
}
