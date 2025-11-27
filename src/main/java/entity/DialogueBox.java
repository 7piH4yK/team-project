package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a dialogue box that displays text and options to the player.
 * This is rendered as an overlay on top of the current scene.
 */
public class DialogueBox {
    private final String image;
    private final DialogueText text;
    private final List<DialogueOption> options;

    public DialogueBox(DialogueText text, List<DialogueOption> options, String image) {
        this.text = text;
        this.options = new ArrayList<>(options);
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public DialogueText getText() {
        return text;
    }

    public List<DialogueOption> getOptions() {
        return new ArrayList<>(options);
    }

}
