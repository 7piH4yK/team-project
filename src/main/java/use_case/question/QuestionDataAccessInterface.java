package use_case.question;

import data_access.api.TriviaResponse;

public interface QuestionDataAccessInterface {
    /**
     * Fetch trivia / riddle data from the underlying source (API, file, etc.).
     *
     * @return a TriviaResponse containing the response_code and the list of Questions.
     */
    TriviaResponse fetchRiddles();
}
