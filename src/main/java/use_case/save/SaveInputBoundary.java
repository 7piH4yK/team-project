package use_case.save;

/**
 * Input boundary for the save use case.
 */
public interface SaveInputBoundary {

    /**
     * Executes the save use case.
     * @param saveInputData contains scenes and current game states
     */
    void execute();
}
