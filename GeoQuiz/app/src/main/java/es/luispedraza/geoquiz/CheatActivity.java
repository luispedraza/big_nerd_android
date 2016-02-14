package es.luispedraza.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "es.luispedraza.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "es.luispedraza.geoquiz.answer_shown";

    boolean mAnswerisTrue;

    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    public static Intent createNewIntent(Context context, boolean answerIsTrue) {
        Intent i = new Intent(context, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    private void setAnswerShown(boolean isAnswerShown) {
        Intent i = new Intent();
        i.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, i);
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerisTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.cheat_text_view);

        mShowAnswerButton = (Button) findViewById(R.id.show_cheat_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswerTextView.setText(mAnswerisTrue ? R.string.true_button : R.string.false_button);
                setAnswerShown(true);
            }
        });
    }
}
