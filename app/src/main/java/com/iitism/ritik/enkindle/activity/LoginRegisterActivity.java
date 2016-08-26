package com.iitism.ritik.enkindle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.iitism.ritik.enkindle.R;
import com.iitism.ritik.enkindle.helper.SessionManager;

public class LoginRegisterActivity extends Activity {

    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        sessionManager = new SessionManager(getApplicationContext());

        if (sessionManager.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginRegisterActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void OpenLogin(View view)
    {
        Intent intent = new Intent(LoginRegisterActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void OpenRegister(View view)
    {
        Intent intent = new Intent(LoginRegisterActivity.this,SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }
}
