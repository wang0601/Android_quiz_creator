package com.example.osw.quizmaker;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CreateFromXmlActivity extends Activity {
    ProgressBar bar;
    ListView questionList;
    ArrayList<Question> questions= new ArrayList<>();
    Question q1, q2, q3;
    public static final String urlString = "http://torunski.ca/CST2335/QuizInstance.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_from_xml);

        bar = findViewById(R.id.progressBar);

        q1 = new Question();
        q2 = new Question();
        q3 = new Question();

        new XmlQuery().execute(urlString);

        questionList = findViewById(R.id.questions);

        QuizAdapter adapter = new QuizAdapter(CreateFromXmlActivity.this, questions);
        questionList.setAdapter(adapter);
    }

    private class XmlQuery extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... args) {
            URL url;
            InputStream in;

            try {
                url = new URL(args[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                in = conn.getInputStream();

                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                //parser.nextTag();

                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }

                    String name = parser.getName();

                    if (name.equals("MultipleChoiceQuestion")) {
                        q1.question = parser.getAttributeValue(null, "question");
                        q1.answer = parser.getAttributeValue(null, "correct");
                        publishProgress(25);

                    }

                    if (parser.getName().equals("Answer")) {
                        q1.option_1 = parser.getText();

                    }
                    if (parser.getName().equals("Answer")) {
                        q1.option_2 = parser.getText();

                    }
                    if (parser.getName().equals("Answer") ) {
                        q1.option_3 = parser.getText();

                    }
                    if (parser.getName().equals("Answer")) {
                        q1.option_4 = parser.getText();

                    }


                    if (name.equals("NumericQuestion")) {
                        q3.question = parser.getAttributeValue(null, "question");
                        q3.option_1 = parser.getAttributeValue(null, "answer");
                        q3.answer = parser.getAttributeValue(null, "accuracy");

                        publishProgress(50);

                        //questions.add(q3);
                    }

                    if (name.equals("TrueFalseQuestion")) {
                        q2.question = parser.getAttributeValue(null, "question");
                        q2.answer = parser.getAttributeValue(null, "answer");
                        q2.option_2 = "true";
                        q2.option_4 = "false";

                        publishProgress(75);

                        //questions.add(q2);

                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

            return "done";

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            bar.setProgress(values[0]);
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String result) {

            questions.add(q1);
            questions.add(q2);
            questions.add(q3);


            bar.setVisibility(View.INVISIBLE);
        }
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

        ;

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
            inflater = CreateFromXmlActivity.this.getLayoutInflater();

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

