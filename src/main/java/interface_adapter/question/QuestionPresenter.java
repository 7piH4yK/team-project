package interface_adapter.question;

import dataaccess.api.Question;
import entity.QuestionBox;
import entity.QuestionBoxBuilder;

import entity.Scene;
import interface_adapter.game.GameState;
import interface_adapter.game.GameViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.main_menu.MainMenuState;
import interface_adapter.main_menu.MainMenuViewModel;
import use_case.question.QuestionOutputBoundary;
import use_case.question.QuestionOutputData;

import java.util.List;
import entity.ClickableObject;

public class QuestionPresenter implements QuestionOutputBoundary {
    private final MainMenuViewModel mainMenuViewModel;
    private final ViewManagerModel viewManagerModel;
    private final GameViewModel gameViewModel;
    private final InMemoryGameDataAccessObject gameDAO;

    public QuestionPresenter(MainMenuViewModel mainMenuViewModel, ViewManagerModel viewManagerModel, GameViewModel gameViewModel, InMemoryGameDataAccessObject gameDAO) {
        this.mainMenuViewModel = mainMenuViewModel;
        this.viewManagerModel = viewManagerModel;
        this.gameViewModel = gameViewModel;
        this.gameDAO = gameDAO;
    }

    @Override
    public void presentRiddles(QuestionOutputData outputData) {
        viewManagerModel.setState(gameViewModel.getViewName());
        viewManagerModel.firePropertyChange();

        List<Question> questions = outputData.getQuestions();
        if (questions.isEmpty()) {
            displayError("No riddles available.");
            return;
        }

        Question q = questions.get(0);

        if (q.getCorrectAnswer() == null || q.getCorrectAnswer().isEmpty()
                || q.getIncorrectAnswers() == null
                || q.getIncorrectAnswers().size() < 3) {
            displayError("Question from API is missing answers.");
            return;
        }

        class Answer {
            final String text;
            final boolean correct;

            Answer(String text, boolean correct) {
                this.text = text;
                this.correct = correct;
            }
        }

        Scene returnScene = gameDAO.getCurrentScene();

        java.util.List<Answer> answers = new java.util.ArrayList<>();
        answers.add(new Answer(q.getCorrectAnswer(), true));
        answers.add(new Answer(q.getIncorrectAnswers().get(0), false));
        answers.add(new Answer(q.getIncorrectAnswers().get(1), false));
        answers.add(new Answer(q.getIncorrectAnswers().get(2), false));

        java.util.Collections.shuffle(answers);

        QuestionBoxBuilder builder = new QuestionBoxBuilder("trivia_bg.png")
                .setQuestionText(q.getQuestion());

        for (Answer a : answers) {
            builder.addAnswerOption(a.text, a.correct, null);
        }

        QuestionBox questionBox = builder.build();

        java.util.List<ClickableObject> objects = questionBox.getObjects();

        GameState state = gameViewModel.getState();
        state.setBackgroundImage(questionBox.getImage());
        state.setClickableObjects(objects);
        gameViewModel.setState(state);
        gameViewModel.firePropertyChange();
    }

    @Override
    public void displayError(String message) {
        MainMenuState state = mainMenuViewModel.getState();
        state.setErrorMessage(message);
        mainMenuViewModel.setState(state);
        mainMenuViewModel.firePropertyChange();
    }
}
