package com.iitism.ritik.enkindle.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.iitism.ritik.enkindle.R;
import com.iitism.ritik.enkindle.app.AppConfig;
import com.iitism.ritik.enkindle.app.AppController;
import com.iitism.ritik.enkindle.helper.SQLiteHandle;
import com.iitism.ritik.enkindle.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText mName,mEmail,mPassword;

    private SessionManager sessionManager;
    private SQLiteHandle db;
    private ProgressDialog pd;

    private TextView mClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mClass = (TextView) findViewById(R.id.classes);
        mName = (EditText) findViewById(R.id.register_name);
        mEmail = (EditText) findViewById(R.id.register_email);
        mPassword = (EditText) findViewById(R.id.register_password);

        sessionManager = new SessionManager(getApplicationContext());
        db = new SQLiteHandle(getApplicationContext());
        pd = new ProgressDialog(this);
        pd.setCancelable(false);

        if (sessionManager.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(SignUpActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }

        Spinner spinner = (Spinner) findViewById(R.id.class_spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.class_array,android.R.layout.simple_spinner_item);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void Register(View view)
    {
        String name = mName.getText().toString().trim();
        String classes = mClass.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if(!name.isEmpty() && !classes.isEmpty() && !email.isEmpty() && !password.isEmpty())
        {
            registerUser(name,classes,email,password);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please enter valid credentials!",Toast.LENGTH_LONG).show();
        }
    }

    public void registerUser(final String name, final String classes, final String email, final String password)
    {
        String str_req_tag = "register_req";

        pd.setMessage("Registering...");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    //response = response.replaceFirst("<font>.*?</font>", "");
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");

                    if(!error)
                    {
                        String uid = jsonObject.getString("uid");
                        String mname = jsonObject.getString("name");
                        String mclass =jsonObject.getString("class");
                        String memail = jsonObject.getString("email");
                        String mcreated_at = jsonObject.getString("created_at");

                        db.addUser(mname,memail,mclass,uid,mcreated_at);

                        Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        String err_msg = jsonObject.getString("error_msg");
                        Toast.makeText(getApplicationContext(),err_msg,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Json_error: "+e.getMessage(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                hideDialog();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("name",name);
                params.put("class",classes);
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest,str_req_tag);
    }

    public void showDialog()
    {
        if(!pd.isShowing())
            pd.show();
    }

    public void hideDialog()
    {
        if(pd.isShowing())
            pd.dismiss();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String clas = (String) parent.getItemAtPosition(position);
        mClass.setText(clas);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
