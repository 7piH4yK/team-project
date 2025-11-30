package interface_adapter.pause_menu;

import interface_adapter.ViewManagerModel;
import use_case.pause.PauseOutputBoundary;

public class PauseMenuPresenter implements PauseOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final PauseMenuViewModel pauseMenuViewModel;

    public PauseMenuPresenter(ViewManagerModel viewManagerModel,
                              PauseMenuViewModel pauseMenuViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.pauseMenuViewModel = pauseMenuViewModel;
    }

    @Override
    public void preparePauseView() {
        viewManagerModel.setState("pause_menu");
        viewManagerModel.firePropertyChange();
    }
}
