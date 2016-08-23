package com.iitism.ritik.enkindle;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginRegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
    }

    public void OpenLogin(View view)
    {
        Intent intent = new Intent(LoginRegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void OpenRegister(View view)
    {
        Intent intent = new Intent(LoginRegisterActivity.this,SignUpActivity.class);
        startActivity(intent);
    }
}
