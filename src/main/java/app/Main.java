package app;

import javax.swing.*;

import interface_adapter.gateways.trivia.TriviaApiDataAccessObject;
import use_case.question.QuestionDataAccessInterface;

/**
 * Main method for Escape Bahen.
 */
public class Main {
    public static void main(String[] args) {
        final QuestionDataAccessInterface triviaDAO = new TriviaApiDataAccessObject();

        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                .addMainMenuView()
                .addGameView()
                .addSwitchToGameUseCase()
                .addClickButtonUseCase()
                .addSaveUseCase()
                .addQuestionUseCase(triviaDAO)
                .addPauseMenu()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}