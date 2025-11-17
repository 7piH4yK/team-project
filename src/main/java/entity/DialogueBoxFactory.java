package entity;

import java.util.List;

/**
 * Factory for creating DialogueBox objects.
 */
public class DialogueBoxFactory {
    public DialogueBox create(List<ClickableObject> options, String image) {
        return new DialogueBox(options, image);
    }
}
