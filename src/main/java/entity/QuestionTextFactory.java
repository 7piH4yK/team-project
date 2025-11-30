package entity;

import java.util.List;

/**
 * Factory for creating QuestionText objects.
 */

public class QuestionTextFactory {
    public QuestionText create(String text, int coordinateX, int coordinateY) {
        return new QuestionText(text, coordinateX, coordinateY);
    }
}
