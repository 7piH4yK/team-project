package use_case.game;

/**
 * Input boundary for the Game use case.
 */
public interface GameInputBoundary {

    /**
     * Executes the click use case for clickable objects.
     *
     * @param gameInputData the input data
     */
    void execute(GameInputData gameInputData);
}
