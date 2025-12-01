package interface_adapter.main_menu;

import data_access.InMemoryGameDataAccessObject;
import use_case.load.LoadInputBoundary;
import use_case.switch_to_game.SwitchToGameViewInputBoundary;

/**
 * Controller for the Main Menu.
 */
public class MainMenuController {

    private final SwitchToGameViewInputBoundary switchToGameViewInteractor;
    private final LoadInputBoundary loadInputBoundary;

    public MainMenuController(SwitchToGameViewInputBoundary switchToGameViewInteractor,
                              LoadInputBoundary loadInputBoundary,
                              InMemoryGameDataAccessObject gameDAO) {
        this.switchToGameViewInteractor = switchToGameViewInteractor;
        this.loadInputBoundary = loadInputBoundary;
    }

    /**
     * Executes the switch to game view use case.
     */
    public void switchToGameView() {
        switchToGameViewInteractor.execute();
    }

    /**
     * Requests that the application load a previously saved game.
     */
    public void loadGame() {
        loadInputBoundary.execute();
    }
}
