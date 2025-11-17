package interface_adapter.save;

import interface_adapter.ViewManagerModel;
import interface_adapter.game.GameState;
import interface_adapter.game.GameViewModel;
import interface_adapter.main_menu.MainMenuViewModel;
import use_case.save.SaveOutputBoundary;

/**
 * The Presenter for the Main Menu.
 */
public class SavePresenter implements SaveOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final GameViewModel gameViewModel;
    private final MainMenuViewModel mainMenuViewModel;

    public SavePresenter(ViewManagerModel viewManagerModel, GameViewModel gameViewModel, MainMenuViewModel mainMenuViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.gameViewModel = gameViewModel;
        this.mainMenuViewModel = mainMenuViewModel;
    }

    @Override
    public void switchToSaveView() {
        mainMenuViewModel.getState().setErrorMessage(null);
        mainMenuViewModel.firePropertyChange();

        viewManagerModel.setState("main menu");
        viewManagerModel.firePropertyChange();
    }
}
