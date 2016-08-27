package com.iitism.ritik.enkindle.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
        Intent intent = new Intent(this,AskActivity.class);
        startActivity(intent);
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

    @Override
    protected void onStart() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
        super.onStart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
