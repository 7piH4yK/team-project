package entity;

public class QuestionOption extends DialogueOption {

    private final boolean correct;

    /**
     * A dialogue option that represents an answer in a trivia question.
     *
     * @param text       the text shown on the button
     * @param coordinateX x position
     * @param coordinateY y position
     * @param to          the Scene to go to after answering (could be result scene)
     * @param correct     whether this option is the correct answer
     */

    public QuestionOption(String text,
                          int coordinateX,
                          int coordinateY,
                          Scene to,
                          boolean correct) {
        super(text, coordinateX, coordinateY, to);
        this.correct = correct;
    }

    public boolean isCorrect() {
        return correct;
    }
}
