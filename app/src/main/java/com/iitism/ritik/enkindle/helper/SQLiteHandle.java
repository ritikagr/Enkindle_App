package com.iitism.ritik.enkindle.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.HashMap;

/**
 * Created by ritik on 8/24/2016.
 */
public class SQLiteHandle extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandle.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "enkindle";

    private static final String TABLE_USER = "user";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_CLASS = "classes";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

    public SQLiteHandle(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "create table '"+TABLE_USER+"'('"+KEY_NAME+"' varchar(50),'"+
                KEY_EMAIL+"' varchar(50) unique,'"+
                KEY_CLASS+"' varchar(50),'"+
                KEY_UID+"' varchar(50),'"+
                KEY_CREATED_AT+"' varchar(30))";

        SQLiteStatement stmt = db.compileStatement(CREATE_LOGIN_TABLE);

        stmt.execute();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists"+TABLE_USER);
        onCreate(db);
    }

    public void addUser(String name,String email,String classes,String uid,String created_at)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,"'"+name+"'");
        values.put(KEY_EMAIL,"'"+email+"'");
        values.put(KEY_CLASS,"'"+classes+"'");
        values.put(KEY_UID,"'"+uid+"'");
        values.put(KEY_CREATED_AT,"'"+created_at+"'");

        long id = db.insert(TABLE_USER,null,values);
        db.close();
    }

    public HashMap<String,String> getUserDetails()
    {
        HashMap<String ,String> user = new HashMap<String, String>();
        String sql = "select * from "+TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(sql,null);

        c.moveToFirst();
        if(c.getCount()>0)
        {
            user.put(KEY_NAME,c.getString(1));
            user.put(KEY_EMAIL,c.getString(2));
            user.put(KEY_CLASS,c.getString(3));
            user.put(KEY_UID,c.getString(4));
            user.put(KEY_CREATED_AT,c.getString(5));
        }
        c.close();
        db.close();
        return user;
    }

    public void deleteUsers()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER,null,null);
        db.close();
    }
}
