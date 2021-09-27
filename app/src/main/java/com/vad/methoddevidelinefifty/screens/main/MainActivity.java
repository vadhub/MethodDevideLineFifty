package com.vad.methoddevidelinefifty.screens.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vad.methoddevidelinefifty.R;
import com.vad.methoddevidelinefifty.tools.parsemathexpression.ParseFunctions;

public class MainActivity extends AppCompatActivity implements ViewMain{

    private EditText editTextFunction;
    private EditText editTextA;
    private EditText editTextB;
    private EditText editTextEpsilon;
    private TextView textViewResult;
    private PresenterMain presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new PresenterMain(this);
        editTextFunction = (EditText) findViewById(R.id.editTextFunction);
        editTextA = (EditText) findViewById(R.id.editTextNumberA);
        editTextB = (EditText) findViewById(R.id.editTextNumberB);
        editTextEpsilon = (EditText) findViewById(R.id.editTextNumberEpsilon);
        textViewResult = (TextView) findViewById(R.id.textViewResult);
    }

    public void calculate(View view) {
        String expression = editTextFunction.getText().toString().trim().toLowerCase();

        presenter.calculateFunction(editTextA.getText().toString(), editTextB.getText().toString(), editTextEpsilon.getText().toString(), expression);
    }


    @Override
    public void showResult(String calculation) {
        textViewResult.setText(calculation);
    }

    @Override
    public void showError(String strErr) {
        Toast.makeText(this, strErr, Toast.LENGTH_SHORT).show();
    }
}