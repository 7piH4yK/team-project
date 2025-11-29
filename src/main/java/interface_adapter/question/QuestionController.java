package interface_adapter.question;

import use_case.question.QuestionInputBoundary;

public class QuestionController {
    private final QuestionInputBoundary questionInteractor;

    public QuestionController(QuestionInputBoundary questionInteractor) {
        this.questionInteractor = questionInteractor;
    }

    public void loadQuestions() {
        questionInteractor.execute();
    }
}
