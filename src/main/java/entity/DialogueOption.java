package entity;

public class DialogueOption extends ClickableObject{

    private final String name;
    private final int coordinateX;
    private final int coordinateY;
    private final Scene to;

    /**
     * Creates a new clickable object.
     * @param name the name of the object
     * @param coordinateX the x coordinate of the object
     * @param coordinateY the y coordinate of the object
     * @throws IllegalArgumentException if the name or image path is empty
     */
    public DialogueOption(String name, int coordinateX, int coordinateY,Scene to) {
        super(name, 0, 0, "qq", false);
        if ("".equals(name)) {
            throw new IllegalArgumentException("Object name cannot be empty");
        }
        this.name = name;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.to = to;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public int getCoordinateX() {
        return coordinateX;
    }
    @Override
    public int getCoordinateY() {
        return coordinateY;
    }

    public String getText() {
        return name;
    }

    public Scene getScene() {return to;}
}
