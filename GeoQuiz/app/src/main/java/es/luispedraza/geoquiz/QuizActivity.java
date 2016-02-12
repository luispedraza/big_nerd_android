package es.luispedraza.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private static String LOG_TAG = QuizActivity.class.getSimpleName();

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;

    private ArrayList<Question> mQuestionPool = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setTag(1);
        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setTag(-1);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        View.OnClickListener nextQuestionListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + (Integer)v.getTag()) % mQuestionPool.size();
                if (mCurrentIndex < 0) mCurrentIndex = mQuestionPool.size() - 1;
                updateView();
            }
        };

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton.setOnClickListener(nextQuestionListener);
        mPrevButton.setOnClickListener(nextQuestionListener);
        mQuestionTextView.setOnClickListener(nextQuestionListener);


        loadQuestionsJson();
        updateView();




//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateView() {
        Question currentQuestion = mQuestionPool.get(mCurrentIndex);
        mQuestionTextView.setText(currentQuestion.getQuestionText());

    }

    private void loadQuestionsJson() {
        mQuestionPool.clear();
        InputStream jsonStream = this.getResources().openRawResource(R.raw.questions);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int b;
        try {
            while ((b = jsonStream.read()) != -1) {
                byteArrayOutputStream.write(b);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.v(LOG_TAG, byteArrayOutputStream.toString());

        try {
            // parse data into json object
            JSONObject jsonObject = new JSONObject(byteArrayOutputStream.toString());
            JSONArray jsonQuestions = jsonObject.getJSONArray("questions");

            for (int i = 0; i < jsonQuestions.length(); i++) {
                JSONObject jsonQ = jsonQuestions.getJSONObject(i);
                Question question = new Question(jsonQ.getBoolean("isTrue"),
                        jsonQ.getString("question"));
                mQuestionPool.add(question);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.v(LOG_TAG, mQuestionPool.toString());
    }

    private void checkAnswer(boolean isTrue) {
        Toast.makeText(QuizActivity.this,
                (isTrue == mQuestionPool.get(mCurrentIndex).isAnswerTrue()) ? R.string.correct_toast : R.string.incorrect_toast,
                Toast.LENGTH_SHORT).show();
    }
}
