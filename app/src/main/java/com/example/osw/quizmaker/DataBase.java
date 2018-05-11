package com.example.osw.quizmaker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by osw on 2018-04-19.
 */

public class DataBase {
    public static final String DATABASE_NAME = "Questions.db";
    public static final int VERSION_NUM = 20;
    public static SQLiteDatabase db;
    public static DatabaseHelper dbHelper;

    public  static SQLiteDatabase getDatabase(Context c){
        dbHelper = new DatabaseHelper(c, DATABASE_NAME, VERSION_NUM);
        db = dbHelper.getWritableDatabase();

        return db;
    }

    public static void close(){
        dbHelper.close();
    }
}
