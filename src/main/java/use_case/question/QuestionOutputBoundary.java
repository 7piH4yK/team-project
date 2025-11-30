package use_case.question;

public interface QuestionOutputBoundary {
    /**
     * Present riddles to the view layer.
     */
    void presentRiddles(QuestionOutputData outputData);

    /**
     * Present an error message (e.g., network error, empty results).
     */
    void displayError(String message);
}
