package interface_adapter.pause_menu;

import use_case.resume.ResumeInputBoundary;

/**
 * The controller for the Resume use case.
 */
public class ResumeController {

    /**
     * Constructs a ResumeController with the given interactor.
     *
     * @param interactor the use case interactor that handles resuming the game
     */
    private final ResumeInputBoundary interactor;

    public ResumeController(ResumeInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the Resume Use Case.
     */
    public void resume() {
        interactor.execute();
    }
}
