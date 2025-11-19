package entity;

/**
 * Represents the text content displayed in a dialogue box.
 */
public class DialogueText {
    private final String text;
    private final int coordinateX;
    private final int coordinateY;

    public DialogueText(String text, int coordinateX, int coordinateY) {
        this.text = text;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public String getText() {
        return text;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }
}
