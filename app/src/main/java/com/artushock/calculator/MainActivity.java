package com.artushock.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_constraint_layout);

        log("onCreate");


    }

    private void log(String msg) {
        Log.d("MAIN_ACTIVITY_1", msg);
    }
}