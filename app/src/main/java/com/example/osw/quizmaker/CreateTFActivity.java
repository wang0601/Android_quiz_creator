package com.example.osw.quizmaker;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class CreateTFActivity extends Activity {
        EditText question;
        RadioButton trueButton, falseButton;
        Button submit;

        SQLiteDatabase db;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_tf);

            db = DataBase.getDatabase(this);


            submit = (Button) findViewById(R.id.submit);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    question = (EditText)findViewById(R.id.question);
                    trueButton = (RadioButton) findViewById(R.id.option_true);
                    falseButton = (RadioButton) findViewById(R.id.option_false);

                    ContentValues cv = new ContentValues();
                    cv.put("question", question.getText().toString());
                    cv.put("option_1", "");
                    cv.put("option_2", "true");
                    cv.put("option_4", "false");

                    if(trueButton.isChecked()){
                        cv.put("answer", "true");
                    }
                    else{
                        cv.put("answer", "false");
                    }

                    db.insert(DatabaseHelper.TABLE_NAME, null ,cv );

                    Toast toast = Toast.makeText(getApplicationContext(), "Question added", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }

        protected void onDestroy() {
            super.onDestroy();
            DataBase.close();
            db.close();
        }
}
