package entity;

import java.util.List;

/**
 * Factory for creating QuestionBox objects.
 */

public class QuestionBoxFactory {
    public QuestionBox create(QuestionText text, List<QuestionOption> options, String image) {
        return new QuestionBox(text, options, image);
    }
}
