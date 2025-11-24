package use_case.game;

import use_case.dialogue.DialogueOutputData;

/**
 * Output boundary for the Click Button use case.
 */
public interface GameOutputBoundary {

    /**
     * Prepares the view with the button click result.
     * @param outputData the output data containing the message to display
     */
    void prepareView(GameOutputData outputData);
}
