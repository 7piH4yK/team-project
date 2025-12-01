package use_case.game;

/**
 * Output boundary for the Click Button use case.
 */
public interface GameOutputBoundary {

    /**
     * Prepares the view with the button click result.
     *
     * @param outputData the output data containing the message to display
     */
    void prepareView(GameOutputData outputData);
    void prepareDoorUnlockedView(String message);
    void prepareGameWonView();

    void prepareDoorLockedView(String s);
}
