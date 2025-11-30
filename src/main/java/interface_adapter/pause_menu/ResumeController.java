package interface_adapter.pause_menu;

import use_case.resume.ResumeInputBoundary;

public class ResumeController {

    private final ResumeInputBoundary interactor;

    public ResumeController(ResumeInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void resume() {
        interactor.execute();
    }
}
