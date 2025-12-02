package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for a QuestionBox (a Scene that represents a single trivia question).
 */

public class QuestionBoxBuilder extends DialogueBuilder{

    private static final int OPTION_X = 50;
    private int optionY = 350;
    private static final int TEXT_X = 400;
    private static final int TEXT_Y = 350;

    private String questionText = "";
    private final List<QuestionOption> questionOptions = new ArrayList<>();

    public QuestionBoxBuilder(String image) {
        super(image);
    }

    public QuestionBoxBuilder setQuestionText(String text) {
        this.questionText = text.replaceAll("&quot;", "\"").replaceAll("&#309;", "'");
        return this;
    }

    public QuestionBoxBuilder addAnswerOption(String answerText,
                                              boolean correct,
                                              Scene nextScene) {
        if (answerText == null || answerText.isEmpty()) {
            throw new IllegalArgumentException("null or empty answerText");
        }
//        if (nextScene == null) {
//            throw new IllegalArgumentException("null nextScene");
//        }

        QuestionOption option = new QuestionOptionFactory()
                .create(answerText, OPTION_X, optionY, nextScene, correct);

        this.questionOptions.add(option);
        optionY += 50;
        return this;
    }

    @Override
    public QuestionBox build() {
        QuestionText qText = new QuestionTextFactory().create(questionText, TEXT_X, TEXT_Y);
        return new QuestionBox(qText, questionOptions, getImage());
    }
}
