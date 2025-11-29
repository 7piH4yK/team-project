package entity;

/**
 * Represents a clickable option in a dialogue box.
 */
public class DialogueOption {
    private final String name;
    private final int coordinateX;
    private final int coordinateY;
    private final Scene targetScene;
    private final DialogueBox targetDialogue;

    /**
     * Creates a dialogue option that leads to a scene.
     *
     * @param name        the text displayed for this option
     * @param coordinateX the x coordinate of the option
     * @param coordinateY the y coordinate of the option
     * @param targetScene the scene to navigate to when clicked
     */
    public DialogueOption(String name, int coordinateX, int coordinateY, Scene targetScene) {
        if ("".equals(name)) {
            throw new IllegalArgumentException("Object name cannot be empty");
        }
        this.name = name;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.targetScene = targetScene;
        this.targetDialogue = null;
    }

    /**
     * Creates a dialogue option that leads to another dialogue.
     *
     * @param name           the text displayed for this option
     * @param coordinateX    the x coordinate of the option
     * @param coordinateY    the y coordinate of the option
     * @param targetDialogue the dialogue box to show when clicked
     */
    public DialogueOption(String name, int coordinateX, int coordinateY, DialogueBox targetDialogue) {
        if ("".equals(name)) {
            throw new IllegalArgumentException("Object name cannot be empty");
        }
        this.name = name;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.targetScene = null;
        this.targetDialogue = targetDialogue;
    }

    public String getName() {
        return name;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    /**
     * Returns the target scene, or null if this option leads to a dialogue.
     */
    public Scene getTargetScene() {
        return targetScene;
    }

    /**
     * Returns the target dialogue, or null if this option leads to a scene.
     */
    public DialogueBox getTargetDialogue() {
        return targetDialogue;
    }

    /**
     * Returns true if this option leads to a scene (closes dialogue).
     */
    public boolean leadsToScene() {
        return targetScene != null;
    }

    /**
     * Returns true if this option leads to another dialogue.
     */
    public boolean leadsToDialogue() {
        return targetDialogue != null;
    }

}
