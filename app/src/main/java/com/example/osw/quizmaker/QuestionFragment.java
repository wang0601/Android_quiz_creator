package com.example.osw.quizmaker;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



public class QuestionFragment extends Fragment {

    TextView question;

    Button deleteButton;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_question, null);
        question = view.findViewById(R.id.question);

        deleteButton = view.findViewById(R.id.deleteButton);

        final Bundle b = getArguments();

        final long id = b.getLong("id");

        question.setText(b.getString("question"));

        return view;
    }

}
