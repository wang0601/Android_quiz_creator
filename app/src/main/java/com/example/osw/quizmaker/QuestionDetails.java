package com.example.osw.quizmaker;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class QuestionDetails extends Activity {
    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_details);


        Bundle b = getIntent().getExtras();

        fm = getFragmentManager();
        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(b);

        FragmentTransaction ft = fm.beginTransaction();
        //ft.add(R.id.frame, fragment);
        ft.commit();
    }
}
