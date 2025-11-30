package interface_adapter.pause_menu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PauseAndResumeControllerTest {

    @Test
    void testPauseControllerCallsInteractor() {
        MockPauseInteractor mock = new MockPauseInteractor();
        PauseController ctrl = new PauseController(mock);

        ctrl.pause();

        assertTrue(mock.called);
    }

    @Test
    void testResumeControllerCallsInteractor() {
        MockResumeInteractor mock = new MockResumeInteractor();
        ResumeController ctrl = new ResumeController(mock);

        ctrl.resume();

        assertTrue(mock.called);
    }

    private static class MockPauseInteractor implements use_case.pause.PauseInputBoundary {
        boolean called = false;
        @Override public void execute() { called = true; }
    }

    private static class MockResumeInteractor implements use_case.resume.ResumeInputBoundary {
        boolean called = false;
        @Override public void execute() { called = true; }
    }
}
