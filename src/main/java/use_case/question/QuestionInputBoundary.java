package use_case.question;

public interface QuestionInputBoundary {
    /**
     * Execute the riddle-loading use case.
     * (If later you need params like category/difficulty, you can add a RiddleInputData.)
     */
    void execute();
}
