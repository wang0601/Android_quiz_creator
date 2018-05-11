package com.example.osw.quizmaker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class StartActivity extends Activity {


    Button createMP;
    Button createTF;
    Button createNP;
    Button createFromXml;
    Button questionList;
    Button viewInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



        createMP = findViewById(R.id.createMP);
        createTF = findViewById(R.id.createTF);
        createNP = findViewById(R.id.createNP);
        createFromXml = findViewById(R.id.createFromXml);
        questionList = findViewById(R.id.questionList);
        viewInfo = findViewById(R.id.viewInfo);



        createMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, CreateMPActivity.class);
                startActivity(intent);
            }
        });

        createTF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, CreateTFActivity.class);
                startActivity(intent);
            }
        });

        createNP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, CreateNPActivity.class);
                startActivity(intent);
            }
        });

        createFromXml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, CreateFromXmlActivity.class);
                startActivity(intent);
            }
        });

        questionList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, QuestionListActivity.class);
                startActivity(intent);
            }
        });

        viewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, ViewInfoActivity.class);
                startActivity(intent);
            }
        });
    }



}
