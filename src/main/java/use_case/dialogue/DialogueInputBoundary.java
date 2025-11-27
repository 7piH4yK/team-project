package use_case.dialogue;

import entity.DialogueOption;
import use_case.game.GameInputData;

public interface DialogueInputBoundary {

    /**
     * Executes the click use case for dialogue options.
     *
     * @param dialogueOption the clicked dialogue option
     */
    void executeDialogueOption(DialogueOption dialogueOption);

    /**
     * Executes the click use case for clickable objects.
     *
     * @param gameInputData the input data
     */
    void execute(GameInputData gameInputData);
}
