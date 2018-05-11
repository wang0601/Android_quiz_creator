package com.example.osw.quizmaker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by osw on 2018-04-19.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context ctx, String DATABASE_NAME, int VERSION_NUM) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    public static final String TABLE_NAME = "QUESTION";
    public static final String KEY_ID = "_id";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_OPTION_1 = "option_1";
    public static final String KEY_OPTION_2 = "option_2";
    public static final String KEY_OPTION_3 = "option_3";
    public static final String KEY_OPTION_4 = "option_4";
    public static final String KEY_ANSWER = "answer";


    public static final String CREATE_TABLE = "create table if not exists " + TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_QUESTION + " not null,"
            + KEY_OPTION_1 + " not null,"
            + KEY_OPTION_2 + " ,"
            + KEY_OPTION_3 + " ,"
            + KEY_OPTION_4 + " ,"
            + KEY_ANSWER + " not null"
            + ")";

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

    }
}
