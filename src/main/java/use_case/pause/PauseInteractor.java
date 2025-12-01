package use_case.pause;

public class PauseInteractor implements PauseInputBoundary {

    private final PauseOutputBoundary presenter;

    public PauseInteractor(PauseOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        presenter.preparePauseView();
    }
}
