package use_case.load;

/**
 * Interface for output boundary for load use case.
 **/
public interface LoadOutputBoundary {
    /**
     * Switches the current view to the LoadView.
     * @param outputData contains the data required to swap to the correct game state.
     **/
    void switchToLoadView(LoadOutputData outputData);

    /**
     * Displays an error message if there is no save file.
     * @param message is the String containing the error message.
     **/
    void displayError(String message);
}
