package use_case.save;

/**
 * Output boundary for the save use case.
 */
public interface SaveOutputBoundary {
    /**
     * Switches the current view to the view needed after the save.
     */
    void switchToSaveView();
}
