package interface_adapter.save;

import use_case.save.SaveInputBoundary;

/**
 * Controller for the Main Menu.
 */
public class SaveController {

    private final SaveInputBoundary saveInteractor;

    public SaveController(SaveInputBoundary saveInteractor) {
        this.saveInteractor = saveInteractor;
    }

    /**
     * Executes the switch to game view use case.
     */
    public void save() {
        saveInteractor.execute();
    }
}
