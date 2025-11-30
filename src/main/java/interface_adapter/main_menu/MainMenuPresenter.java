package interface_adapter.main_menu;

import interface_adapter.ViewManagerModel;
import interface_adapter.game.GameState;
import interface_adapter.game.GameViewModel;
import use_case.switch_to_game.SwitchToGameOutputData;
import use_case.switch_to_game.SwitchToGameViewOutputBoundary;

/**
 * The Presenter for the Main Menu.
 */
public class MainMenuPresenter implements SwitchToGameViewOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final GameViewModel gameViewModel;

    public MainMenuPresenter(ViewManagerModel viewManagerModel, GameViewModel gameViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.gameViewModel = gameViewModel;
    }

    @Override
    public void switchToGameView(SwitchToGameOutputData outputData) {
        // switch to view
        viewManagerModel.setState(gameViewModel.getViewName());
        viewManagerModel.firePropertyChange();

        // prepare game view
        final GameState state = new GameState();
        state.setBackgroundImage(outputData.getBackgroundImage());
        state.setClickableObjects(outputData.getClickableObjects());
        gameViewModel.setState(state);
        gameViewModel.firePropertyChange();
    }
}
