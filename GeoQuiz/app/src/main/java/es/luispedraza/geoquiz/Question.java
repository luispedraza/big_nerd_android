package es.luispedraza.geoquiz;

/**
 * Created by luis on 11/2/16.
 */
public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    public Question(boolean answerTrue, int textResId) {
        mAnswerTrue = answerTrue;
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }
}
