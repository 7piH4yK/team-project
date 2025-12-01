package interface_adapter.pause_menu;

import interface_adapter.ViewManagerModel;
import use_case.resume.ResumeOutputBoundary;

public class ResumePresenter implements ResumeOutputBoundary {

    private final ViewManagerModel viewManagerModel;

    public ResumePresenter(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareGameView() {
        viewManagerModel.setState("game");
        viewManagerModel.firePropertyChange();
    }
}
