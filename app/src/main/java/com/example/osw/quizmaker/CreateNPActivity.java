package com.example.osw.quizmaker;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateNPActivity extends Activity {
    EditText question, answer, accuracy;
    Button submit;

    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_np);

        db = DataBase.getDatabase(this);

        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question = (EditText)findViewById(R.id.question);
                answer = (EditText)findViewById(R.id.answer);
                accuracy = (EditText)findViewById(R.id.accuracy);

                ContentValues cv = new ContentValues();
                cv.put("question", question.getText().toString());
                cv.put("option_1", answer.getText().toString());
                cv.put("answer", accuracy.getText().toString());

                db.insert(DatabaseHelper.TABLE_NAME, null ,cv );

                //QuestionListActivity.adapter.notifyDataSetChanged();

                Toast toast = Toast.makeText(getApplicationContext(), "Question added", Toast.LENGTH_LONG);
                toast.show();

/*                Intent intent = new Intent(CreateMPActivity.this, QuestionListActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("question", question.getText().toString());
                bundle.putString("option_1", option_1.getText().toString());
                bundle.putString("option_2", option_2.getText().toString());
                bundle.putString("option_3", option_3.getText().toString());
                bundle.putString("option_4", option_4.getText().toString());
                bundle.putString("answer", answer.getText().toString());

                bundle.putInt("request_code", REQUEST_CODE);

                intent.putExtras(bundle);

                startActivityForResult(intent, REQUEST_CODE);*/

            }
        });
    }

    protected void onDestroy(){
        super.onDestroy();
        DataBase.close();
        db.close();
    }
}
