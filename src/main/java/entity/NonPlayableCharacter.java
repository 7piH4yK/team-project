package entity;

/**
 * Represents a clickable non-playable character (NPC) in the game that can be interacted with.
 */
public class NonPlayableCharacter extends ClickableObject {
    private final String name;
    private final int coordinateX;
    private final int coordinateY;
    private final String image;
    private final DialogueBox box;

    /**
     * Creates a new NPC.
     * @param name the name of the NPC
     * @param coordinateX the x coordinate of the NPC
     * @param coordinateY the y coordinate of the NPC
     * @param image the path to the image asset
     * @throws IllegalArgumentException if the name or image path is empty
     **/
    public NonPlayableCharacter(String name, int coordinateX, int coordinateY, String image, DialogueBox box) {
        super(name, coordinateX, coordinateY, image, false);
        if ("".equals(name)) {
            throw new IllegalArgumentException("Object name cannot be empty");
        }
        if ("".equals(image)) {
            throw new IllegalArgumentException("Image path cannot be empty");
        }
        this.name = name;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.image = image;
        this.box = box;
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
    @Override
    public String getImage() {return image;}

    public DialogueBox getDB() {return box;}
}
