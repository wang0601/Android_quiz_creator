package com.example.osw.quizmaker;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionListActivity extends Activity {
    boolean isTab;

    public static QuizAdapter adapter;
    SQLiteDatabase db;
    Cursor cursor;


    ListView questionList;
    ArrayList<Question> questions;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        final FragmentManager fm = getFragmentManager();
        isTab = (findViewById(R.id.frame) != null);

        db = DataBase.getDatabase(this);

        // bundle = getIntent().getExtras();

        questionList = findViewById(R.id.questionList);
        questions = new ArrayList<>();
        adapter = new QuizAdapter(this, questions);

        questionList.setAdapter(adapter);


        cursor = db.query(DatabaseHelper.TABLE_NAME,
                new String[]{"_id", "question", "option_1", "option_2", "option_3", "option_4", "answer"},
                null, null, null, null, null);

        final int indexID = cursor.getColumnIndex("_id");
        final int indexQuestion = cursor.getColumnIndex("question");
        final int indexOption_1 = cursor.getColumnIndex("option_1");
        final int indexOption_2 = cursor.getColumnIndex("option_2");
        final int indexOption_3 = cursor.getColumnIndex("option_3");
        final int indexOption_4 = cursor.getColumnIndex("option_4");
        final int indexAnswer = cursor.getColumnIndex("answer");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(indexOption_2) == null && cursor.getString(indexOption_3) == null) {
                Question q = new Question();
                q.question = cursor.getString(indexQuestion);
                q.option_1 = cursor.getString(indexOption_1);
                q.answer = cursor.getString(indexAnswer);
                questions.add(q);
                cursor.moveToNext();
            } else if (cursor.getString(indexOption_2) != null && cursor.getString(indexOption_3) == null) {
                Question q = new Question();
                q.question = cursor.getString(indexQuestion);
                q.option_2 = cursor.getString(indexOption_2);
                q.option_4 = cursor.getString(indexOption_4);
                q.answer = cursor.getString(indexAnswer);
                questions.add(q);
                cursor.moveToNext();
            } else {
                Question q = new Question();
                q.question = cursor.getString(indexQuestion);
                q.option_1 = cursor.getString(indexOption_1);
                q.option_2 = cursor.getString(indexOption_2);
                q.option_3 = cursor.getString(indexOption_3);
                q.option_4 = cursor.getString(indexOption_4);
                questions.add(q);
                cursor.moveToNext();
            }

        }

        questionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cursor.moveToPosition(i);
                String question = cursor.getString(indexQuestion);
                long id = cursor.getLong(indexID);

                if (isTab) {
                    Bundle b = new Bundle();
                    b.putLong("id", id);
                    b.putString("question", question);
                    b.putBoolean("isTab", isTab);
                    QuestionFragment fragment = new QuestionFragment();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.add(R.id.frame, fragment);
                    fragment.setArguments(b);
                    ft.commit();
                } else {
                    Intent intent = new Intent(QuestionListActivity.this, QuestionDetails.class);
                    Bundle b = new Bundle();
                    b.putInt("position", i);
                    b.putLong("id", id);
                    b.putString("question", question);
                    b.putBoolean("isTab", isTab);
                    intent.putExtras(b);

                    startActivityForResult(intent, 1);

                }
            }
        });
    }
    protected void onDestroy(){
        super.onDestroy();
        DataBase.close();
        db.close();
    }

     public class QuizAdapter extends BaseAdapter {
            Context ctx;
            ArrayList<Question> questions;
            LayoutInflater inflater;

            public QuizAdapter(Context ctx, ArrayList<Question> ar) {
                // super(ctx, 0);
                this.ctx = ctx;
                questions = ar;
            }



            public int getCount() {
                return questions.size();
            }

            public Question getItem(int position) {
                return questions.get(position);
            }


            public long getItemId(int position) {


                return position;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                inflater = QuestionListActivity.this.getLayoutInflater();

                View result = null;
                Question currentQuestion = questions.get(position);

                if (currentQuestion.option_2 == null && currentQuestion.option_3 == null) {
                    result = inflater.inflate(R.layout.layout_numeric, null);
                    TextView question = (TextView) result.findViewById(R.id.question);
                    question.setText(currentQuestion.question);
                    EditText input = (EditText) findViewById(R.id.input);
                }
                else if(currentQuestion.option_2 != null && currentQuestion.option_3 == null){
                    result = inflater.inflate(R.layout.layout_true_false, null);
                    TextView question = (TextView) result.findViewById(R.id.question);
                    question.setText(currentQuestion.question);
                    RadioButton trueButton = (RadioButton) result.findViewById(R.id.trueButton);
                    trueButton.setText(currentQuestion.option_2);
                    RadioButton falseButton = (RadioButton) result.findViewById(R.id.falseButton);
                    falseButton.setText(currentQuestion.option_4);
                }

                else {
                    result = inflater.inflate(R.layout.layout_multiple_choice, null);

                    TextView question = (TextView) result.findViewById(R.id.question);
                    question.setText(currentQuestion.question);
                    RadioButton option_1 = (RadioButton) result.findViewById(R.id.option_1);
                    option_1.setText(currentQuestion.option_1);
                    RadioButton option_2 = (RadioButton) result.findViewById(R.id.option_2);
                    option_2.setText(currentQuestion.option_2);
                    RadioButton option_3 = (RadioButton) result.findViewById(R.id.option_3);
                    option_3.setText(currentQuestion.option_3);
                    RadioButton option_4 = (RadioButton) result.findViewById(R.id.option_4);
                    option_4.setText(currentQuestion.option_4);


                    // result = inflater.inflate(R.layout.layout_true_false, null);


                    //result = inflater.inflate(R.layout.layout_numeric, null);

                }

                return result;

            }


        }

}
