package interface_adapter.dialogue;

import entity.ClickableObject;
import entity.DialogueOption;
import use_case.dialogue.DialogueInputBoundary;
import use_case.game.GameInputBoundary;
import use_case.game.GameInputData;

public class DialogueController {
    private final DialogueInputBoundary dialogueInputBoundary;

    public DialogueController(DialogueInputBoundary dialogueInputBoundary) {
        this.dialogueInputBoundary = dialogueInputBoundary;
    }

    /**
     * Click a dialogue option
     */
    public void clickDialogueOption(DialogueOption dialogueOption) {
        dialogueInputBoundary.executeDialogueOption(dialogueOption);
    }

    /**
     * Click a clickable object
     */
    public void click(ClickableObject clickable) {
        dialogueInputBoundary.execute(new GameInputData(clickable));
    }

}
