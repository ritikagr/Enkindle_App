package com.iitism.ritik.enkindle.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ritik on 8/24/2016.
 */
public class SessionManager {

    public static final String TAG = SessionManager.class.getSimpleName();
    SharedPreferences pref;
    Context _context;
    SharedPreferences.Editor editor;

    private static final String PREF_NAME = "enkindle";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    int PRIVATE_MODE = 0;
    public SessionManager(Context context)
    {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn)
    {
        editor.putBoolean(KEY_IS_LOGGED_IN,isLoggedIn);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGED_IN,false);
    }
}
