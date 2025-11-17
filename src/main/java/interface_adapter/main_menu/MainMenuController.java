package interface_adapter.main_menu;

import use_case.load.LoadInputBoundary;
import use_case.switch_to_game.SwitchToGameViewInputBoundary;
import data_access.InMemoryGameDataAccessObject;

/**
 * Controller for the Main Menu.
 */
public class MainMenuController {

    private final SwitchToGameViewInputBoundary switchToGameViewInteractor;
    private final LoadInputBoundary loadInputBoundary;
    private final InMemoryGameDataAccessObject gameDAO;


    public MainMenuController(SwitchToGameViewInputBoundary switchToGameViewInteractor,
                              LoadInputBoundary loadInputBoundary,
                              InMemoryGameDataAccessObject gameDAO) {
        this.switchToGameViewInteractor = switchToGameViewInteractor;
        this.loadInputBoundary = loadInputBoundary;
        this.gameDAO = gameDAO;
    }

    /**
     * Executes the switch to game view use case.
     */
    public void switchToGameView() {
        gameDAO.resetGame();
        switchToGameViewInteractor.execute();
    }

    public void loadGame() {
        loadInputBoundary.execute();
    }
}
