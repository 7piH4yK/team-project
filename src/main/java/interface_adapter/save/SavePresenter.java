package interface_adapter.save;

import interface_adapter.ViewManagerModel;
import interface_adapter.game.GameState;
import interface_adapter.game.GameViewModel;
import interface_adapter.main_menu.MainMenuViewModel;
import use_case.save.SaveOutputBoundary;

/**
 * The Presenter for the Save use case.
 */
public class SavePresenter implements SaveOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final MainMenuViewModel mainMenuViewModel;

    public SavePresenter(ViewManagerModel viewManagerModel, MainMenuViewModel mainMenuViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.mainMenuViewModel = mainMenuViewModel;
    }

    /**
     Swaps the view to the main menu.
     **/
    @Override
    public void switchToSaveView() {
        mainMenuViewModel.getState().setErrorMessage(null);
        mainMenuViewModel.firePropertyChange();

        viewManagerModel.setState("main menu");
        viewManagerModel.firePropertyChange();
    }
}
