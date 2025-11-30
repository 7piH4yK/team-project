package use_case.pause;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PauseInteractorTest {

    @Test
    void testExecuteCallsPresenter() {
        FakePausePresenter presenter = new FakePausePresenter();
        PauseInteractor interactor = new PauseInteractor(presenter);

        interactor.execute();   // IMPORTANT: your interactor uses execute()

        assertTrue(presenter.called);
    }

    private static class FakePausePresenter implements PauseOutputBoundary {
        boolean called = false;

        @Override
        public void preparePauseView() {
            called = true;
        }
    }
}
