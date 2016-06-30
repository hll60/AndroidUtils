package com.hll.android.utils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText inputEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HUtils.initialize(getApplication());

        setContentView(R.layout.activity_main);

        inputEdt = (EditText) findViewById(R.id.inputEdt);

        assert inputEdt != null;
        inputEdt.requestFocus();

    }

    public void close(View v){
        HUtils.hideInputMethod(this);
    }

}
