package entity;

/**
 * Factory for creating DialogueOption objects.
 */
public class DialogueOptionFactory {
    /**
     * Creates a dialogue option that leads to a scene.
     */
    public DialogueOption createWithScene(String name, int coordinateX, int coordinateY, Scene scene) {
        return new DialogueOption(name, coordinateX, coordinateY, scene);
    }

    /**
     * Creates a dialogue option that leads to another dialogue.
     */
    public DialogueOption createWithDialogue(String name, int coordinateX, int coordinateY, DialogueBox dialogue) {
        return new DialogueOption(name, coordinateX, coordinateY, dialogue);
    }
}
