package interface_adapter.load;

import use_case.load.LoadInputBoundary;

/**
 * Controller that handles the load use case.
 **/
public class LoadController {
    private final LoadInputBoundary loadInteractor;

    public LoadController(LoadInputBoundary loadInteractor) {
        this.loadInteractor = loadInteractor;
    }

    /**
     * Executes the switch load use case.
     */
    public void switchToLoadView() {
        loadInteractor.execute();
    }
}
