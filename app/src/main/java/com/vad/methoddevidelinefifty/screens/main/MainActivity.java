package com.vad.methoddevidelinefifty.screens.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.vad.methoddevidelinefifty.R;

public class MainActivity extends AppCompatActivity {

    private EditText editTextFunction;
    private EditText editTextA;
    private EditText editTextB;
    private EditText editTextEpsilon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextFunction = (EditText) findViewById(R.id.editTextFunction);
        editTextA = (EditText) findViewById(R.id.editTextNumberA);
        editTextB = (EditText) findViewById(R.id.editTextNumberB);
        editTextEpsilon = (EditText) findViewById(R.id.editTextNumberEpsilon);
    }
}