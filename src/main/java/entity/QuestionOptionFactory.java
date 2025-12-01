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
        final String cleanedText = text.replaceAll("&quot;", "\"");
        return new QuestionOption(cleanedText, coordinateX, coordinateY, to, correct);
    }
}
