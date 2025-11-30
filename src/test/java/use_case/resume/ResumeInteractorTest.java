package use_case.resume;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResumeInteractorTest {

    /**
     * A simple fake presenter used to verify the interactor's behavior
     * without relying on mocks or external libraries.
     */
    private static class FakeResumePresenter implements ResumeOutputBoundary {

        boolean prepareGameViewCalled = false;

        @Override
        public void prepareGameView() {
            prepareGameViewCalled = true;
        }
    }

    @Test
    void testResumeInteractorCallsPresenter() {
        // Arrange
        FakeResumePresenter fakePresenter = new FakeResumePresenter();
        ResumeInteractor interactor = new ResumeInteractor(fakePresenter);

        // Act
        interactor.execute();

        // Assert
        assertTrue(fakePresenter.prepareGameViewCalled,
                "ResumeInteractor should call presenter.prepareGameView()");
    }
}
