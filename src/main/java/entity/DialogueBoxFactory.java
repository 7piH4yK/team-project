package entity;

import java.util.List;

/**
 * Factory for creating DialogueBox objects.
 */
public class DialogueBoxFactory {
    public DialogueBox create(DialogueText text, List<DialogueOption> options, String image) {
        return new DialogueBox(text, options, image);
    }
}
