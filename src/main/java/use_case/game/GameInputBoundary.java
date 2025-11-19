package use_case.game;

import entity.DialogueOption;

/**
 * Input boundary for the Game use case.
 */
public interface GameInputBoundary {

    /**
     * Executes the click use case for clickable objects.
     * @param gameInputData the input data
     */
    void execute(GameInputData gameInputData);

    /**
     * Executes the click use case for dialogue options.
     * @param dialogueOption the clicked dialogue option
     */
    void executeDialogueOption(DialogueOption dialogueOption);
}
