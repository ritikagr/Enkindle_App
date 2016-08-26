package com.iitism.ritik.enkindle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.iitism.ritik.enkindle.R;
import com.iitism.ritik.enkindle.helper.SQLiteHandle;
import com.iitism.ritik.enkindle.helper.SessionManager;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private SQLiteHandle db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.enkindle_logo);

        db = new SQLiteHandle(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());

        if(!sessionManager.isLoggedIn())
        {
            logoutUser();
        }
    }

    public void logoutUser()
    {
        sessionManager.setLogin(false);
        db.deleteUsers();

        Intent intent = new Intent(MainActivity.this,LoginRegisterActivity.class);
        startActivity(intent);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {
            case R.id.action_settings:
                return true;
            case R.id.logout:
                logoutUser();
                break;
            default:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Consult(View view)
    {

    }

    public void Ask_Question(View view)
    {

    }

    public void Review(View view)
    {

    }

    public void Take_Test(View view)
    {

    }

    public void Chat(View view)
    {

    }

    public void Blog(View view)
    {

    }
}
