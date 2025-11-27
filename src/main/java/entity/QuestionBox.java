package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * An extension of DialogueBox that represents a trivia question with answer options.
 */

public class QuestionBox extends DialogueBox {
    private final QuestionText questionText;
    private final List<QuestionOption> answerOptions;

    public QuestionBox(QuestionText questionText,
                       List<QuestionOption> answerOptions,
                       String image) {
        super(buildObjects(questionText, answerOptions), image);
        this.questionText = questionText;
        this.answerOptions = new ArrayList<>(answerOptions);
    }

    private static List<ClickableObject> buildObjects(QuestionText questionText,
                                                      List<QuestionOption> answerOptions) {
        List<ClickableObject> objects = new ArrayList<>();
        objects.add(questionText);
        objects.addAll(answerOptions);
        return objects;
    }

    public QuestionText getQuestionText() {
        return questionText;
    }

    public List<QuestionOption> getAnswerOptions() {
        return new ArrayList<>(answerOptions);
    }

    public QuestionOption getCorrectOption() {
        for (QuestionOption option : answerOptions) {
            if (option.isCorrect()) {
                return option;
            }
        }
        return null;
    }
}
