package use_case.question;

import java.util.List;

import data_access.api.Question;

public class QuestionOutputData {
    private final int responseCode;
    private final List<Question> questions;

    public QuestionOutputData(int responseCode, List<Question> questions) {
        this.responseCode = responseCode;
        this.questions = questions;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
