package es.luispedraza.geoquiz;

/**
 * Created by luis on 11/2/16.
 */
public class Question {
    private String mQuestionText;
    private boolean mAnswerTrue;
    private boolean mUserCheated = false;


    public Question(boolean answerTrue, String questionText) {
        mAnswerTrue = answerTrue;
        mQuestionText = questionText;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public String getQuestionText() {
        return mQuestionText;
    }

    public void setQuestionText(String questionText) {
        mQuestionText = questionText;
    }

    public boolean isUserCheated() {
        return mUserCheated;
    }

    public void setUserCheated(boolean userCheated) {
        mUserCheated = userCheated;
    }
}
