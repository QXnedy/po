package com.example.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBOpenHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public DBOpenHelper(Context context) {
        super(context, "quinto", null, 1);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS user(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    // 增
    public boolean add(String name, String password){
        boolean isSuccess = false;
        try {
            db.execSQL("INSERT INTO user (name, password) VALUES(?, ?)",new Object[]{name, password});
            isSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    // 删
    public boolean delete(String name, String password){
        boolean isSuccess = false;
        try {
            db.execSQL("DELETE FROM user WHERE name = '" + name + "' AND password = '" + password + "'");
            isSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    // 改
    public boolean update(String password){
        boolean isSuccess = false;
        try {
            db.execSQL("UPDATE user SET password = ?",new Object[]{password});
            isSuccess = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    // 查
    public ArrayList<User> query(){
        ArrayList<User> list = new ArrayList<>();
        Cursor cursor = db.query("user",null,null,null,null,null,"name DESC");
        while(cursor.moveToNext()){
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
            list.add(new User(name,password));
        }
        cursor.close();
        return list;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // 禁用wal
        db.disableWriteAheadLogging();
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        // 禁用wal
        db.disableWriteAheadLogging();
    }
}
