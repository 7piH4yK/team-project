package use_case.question;

import data_access.api.TriviaResponse;
import interface_adapter.gateways.trivia.TriviaApiDataAccessObject;
import use_case.question.QuestionDataAccessInterface;

public class ApiCallTest {
    public static void main(String[] args) {

        QuestionDataAccessInterface triviaDAO = new TriviaApiDataAccessObject();

        System.out.println("Running Trivia API Test...\n");

        TriviaResponse response = triviaDAO.fetchRiddles();

        if (response == null) {
            System.out.println("❌ API returned null (network or parsing error).");
            return;
        }

        System.out.println("✔ Response Code: " + response.getResponseCode());
        System.out.println("✔ Total Results: " + response.getResults().size());

        System.out.println("\n--- First Question ---");
        System.out.println(response.getResults().get(0).getQuestion());

        System.out.println("\n--- Answers ---");
        System.out.println("Correct: " + response.getResults().get(0).getCorrectAnswer());
        System.out.println("Incorrect: " + response.getResults().get(0).getIncorrectAnswers());
    }
}
