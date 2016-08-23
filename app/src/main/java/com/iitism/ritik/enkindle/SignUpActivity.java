package com.iitism.ritik.enkindle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.class_array);
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(SignUpActivity.this,R.layout.activity_sign_up,getResources().getStringArray(R.array.class_array));

        autoCompleteTextView.setAdapter(mArrayAdapter);
    }

    public void Register(View view)
    {
        Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
