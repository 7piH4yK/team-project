package use_case.dialogue;

public interface DialogueOutputBoundary {

    /**
     * Prepares the view with the button click result.
     *
     * @param outputData the output data containing the message to display
     */
    void prepareView(DialogueOutputData outputData);
}
