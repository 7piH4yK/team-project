package interface_adapter.load;

import interface_adapter.ViewManagerModel;
import interface_adapter.game.GameState;
import interface_adapter.game.GameViewModel;
import interface_adapter.main_menu.MainMenuViewModel;
import use_case.load.LoadOutputData;
import use_case.load.LoadOutputBoundary;
import interface_adapter.main_menu.MainMenuState;

/**
 Presenter that handles the load use case.
 **/
public class LoadPresenter implements LoadOutputBoundary {
    GameViewModel gameViewModel;
    ViewManagerModel viewManagerModel;
    private final MainMenuViewModel mainMenuViewModel;

    public LoadPresenter(MainMenuViewModel mainMenuViewModel,ViewManagerModel viewManagerModel, GameViewModel gameViewModel) {
        this.mainMenuViewModel = mainMenuViewModel;
        this.viewManagerModel = viewManagerModel;
        this.gameViewModel = gameViewModel;
    }

    /**
     Similar to the game use case 'switchToGameView' but loads game first
     **/
    @Override
    public void switchToLoadView(LoadOutputData loadOutputData) {
        // switch to view
        viewManagerModel.setState(gameViewModel.getViewName());
        viewManagerModel.firePropertyChange();

        final GameState state = gameViewModel.getState();
        state.setBackgroundImage(loadOutputData.getBackgroundImage());
        state.setClickableObjects(loadOutputData.getClickableObjects());
        gameViewModel.setState(state);
        gameViewModel.firePropertyChange();
    }

    /**
     Displays errors involving load use case.
     **/
    public void displayError(String errorMessage) {
        MainMenuState state = mainMenuViewModel.getState();
        state.setErrorMessage(errorMessage);
        mainMenuViewModel.setState(state);
        mainMenuViewModel.firePropertyChange();
    }
}
