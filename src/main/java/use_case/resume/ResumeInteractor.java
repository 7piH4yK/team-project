package use_case.resume;

public class ResumeInteractor implements ResumeInputBoundary {

    private final ResumeOutputBoundary presenter;

    public ResumeInteractor(ResumeOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        presenter.prepareGameView();
    }
}

