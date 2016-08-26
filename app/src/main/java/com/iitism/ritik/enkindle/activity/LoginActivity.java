package com.iitism.ritik.enkindle.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.EGLDisplay;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText email_text;
    private EditText password_text;
    private SessionManager sessionManager;
    private SQLiteHandle db;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_text = (EditText) findViewById(R.id.login_email);
        password_text = (EditText) findViewById(R.id.login_password);

        sessionManager = new SessionManager(getApplicationContext());
        db = new SQLiteHandle(getApplicationContext());

        pd = new ProgressDialog(this);

        pd.setCancelable(false);

        if(sessionManager.isLoggedIn())
        {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void Login(View view)
    {
        String email = email_text.getText().toString().trim();
        String password = password_text.getText().toString().trim();

        if(!email.isEmpty() && !password.isEmpty())
        {
            checkLogin(email,password);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please enter the credentials!",Toast.LENGTH_LONG).show();
        }
    }

    public void checkLogin(final String email, final String password)
    {
        final String str_req_tag = "login_req";

        pd.setMessage("Logging in...");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                hideDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");

                    if(!error)
                    {
                        sessionManager.setLogin(true);
                        String uid = jsonObject.getString("uid");
                        String name = jsonObject.getString("name");
                        String classes = jsonObject.getString("class");
                        String email = jsonObject.getString("email");
                        String created_at = jsonObject.getString("created_at");

                        db.addUser(name,email,classes,uid,created_at);

                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        String err_msg = jsonObject.getString("error_msg");
                        Toast.makeText(getApplicationContext(),err_msg,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Json Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
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
}
