package interface_adapter.pause_menu;

import use_case.pause.PauseInputBoundary;

public class PauseController {

    private final PauseInputBoundary interactor;

    public PauseController(PauseInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void pause() {
        interactor.execute();
    }
}
