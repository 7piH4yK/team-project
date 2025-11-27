package use_case.question;

import dataaccess.api.Question;
import dataaccess.api.TriviaResponse;

import java.util.List;

public class QuestionInteractor implements QuestionInputBoundary {
    private final QuestionDataAccessInterface riddleDataAccessObject;
    private final QuestionOutputBoundary presenter;

    public QuestionInteractor(QuestionDataAccessInterface riddleDataAccessObject,
                              QuestionOutputBoundary presenter) {
        this.riddleDataAccessObject = riddleDataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        TriviaResponse response = riddleDataAccessObject.fetchRiddles();

        if (response == null) {
            presenter.displayError("Failed to load riddles: no response from API.");
            return;
        }

        if (response.getResponseCode() != 0) {
            presenter.displayError("Riddle service returned error code: " + response.getResponseCode());
            return;
        }

        List<Question> questions = response.getResults();
        if (questions == null || questions.isEmpty()) {
            presenter.displayError("No riddles available.");
            return;
        }

        QuestionOutputData outputData = new QuestionOutputData(
                response.getResponseCode(),
                questions
        );

        presenter.presentRiddles(outputData);
    }
}
