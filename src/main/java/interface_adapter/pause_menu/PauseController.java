package interface_adapter.pause_menu;

import use_case.pause.PauseInputBoundary;

/**
 * The controller for the pause menu.
 */
public class PauseController {

    private final PauseInputBoundary interactor;

    /**
     * Constructs a {@code PauseController} with the given pause interactor.
     *
     * @param interactor the use case interactor responsible for handling pause requests
     */
    public PauseController(PauseInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Sends a pause request to the pause use case.
     */
    public void pause() {
        interactor.execute();
    }
}
