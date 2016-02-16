package es.luispedraza.geoquiz;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private static final String LOG_TAG = CheatActivity.class.getSimpleName();

    private static final String EXTRA_ANSWER_IS_TRUE = "es.luispedraza.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "es.luispedraza.geoquiz.answer_shown";
    private static final String KEY_USER_CHEATED = "user_cheated";

    private boolean mAnswerisTrue;
    private boolean mUserCheated = false;

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

    /* Helper method */
    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if (savedInstanceState != null) {
            Log.v(LOG_TAG, "Recovering previous state");
            mUserCheated = savedInstanceState.getBoolean(KEY_USER_CHEATED);
            setAnswerShown(mUserCheated);
        }

        // Show current device api leve information:
        final int API_LEVEL = Build.VERSION.SDK_INT;
        ((TextView)findViewById(R.id.api_level_info)).setText("API level " + API_LEVEL);


        mAnswerisTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.cheat_text_view);

        mShowAnswerButton = (Button) findViewById(R.id.show_cheat_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswerTextView.setText(mAnswerisTrue ? R.string.true_button : R.string.false_button);
                mUserCheated = true;
                setAnswerShown(mUserCheated);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswerButton.getWidth() / 2;
                    int cy = mShowAnswerButton.getHeight() / 2;
                    float radius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswerButton,
                            cx, cy, radius, 0);
                    anim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mAnswerTextView.setVisibility(View.VISIBLE);
                            mShowAnswerButton.setVisibility(View.INVISIBLE);

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

                    anim.start();
                } else {
                    mAnswerTextView.setVisibility(View.VISIBLE);
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(LOG_TAG, "onSaveInstanceState()");
        outState.putBoolean(KEY_USER_CHEATED, mUserCheated);
    }
}
