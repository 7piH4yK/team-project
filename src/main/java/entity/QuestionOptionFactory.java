package entity;

/**
 * Factory for creating QuestionOption objects.
 */

public class QuestionOptionFactory {
    public QuestionOption create(String text,
                                 int coordinateX,
                                 int coordinateY,
                                 Scene to,
                                 boolean correct) {
        return new QuestionOption(text, coordinateX, coordinateY, to, correct);
    }
}
