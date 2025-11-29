package app;

import javax.swing.*;

import interface_adapter.gateways.trivia.TriviaApiDataAccessObject;
import use_case.question.QuestionDataAccessInterface;

public class Main {
    public static void main(String[] args) {
        QuestionDataAccessInterface triviaDAO = new TriviaApiDataAccessObject();

        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addMainMenuView()
                .addGameView()
                .addSwitchToGameUseCase()
                .addClickButtonUseCase()
                .addSaveUseCase()
                .addQuestionUseCase(triviaDAO)
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}