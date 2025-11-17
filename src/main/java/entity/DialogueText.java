package entity;

public class DialogueText extends ClickableObject
{
    private final String text;
    private final int coordinateX;
    private final int coordinateY;

    public DialogueText(String text, int coordinateX, int coordinateY)
    {
        super("qq", 0, 0, "qq", false);
        this.text = text;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public String getText() {return text;}

    public  int getCoordinateX() {return coordinateX;}

    public int getCoordinateY() {return coordinateY;}
}
