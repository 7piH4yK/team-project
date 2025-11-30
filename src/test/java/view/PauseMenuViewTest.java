package view;

import interface_adapter.pause_menu.ResumeController;
import interface_adapter.save.SaveController;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class PauseMenuViewTest {

    @Test
    void testButtonsCallControllers() {

        MockResumeController rc = new MockResumeController();
        MockSaveController sc = new MockSaveController();

        PauseMenuView view = new PauseMenuView(rc, sc);

        JPanel modal = (JPanel) ((JPanel) view.getComponent(0)).getComponent(0);

        JButton resume = (JButton) modal.getComponent(2);
        JButton save = (JButton) modal.getComponent(4);

        resume.doClick();
        save.doClick();

        assertTrue(rc.called);
        assertTrue(sc.called);
    }

    @Test
    void testViewName() {
        PauseMenuView v = new PauseMenuView(null, null);
        assertEquals("pause_menu", v.getViewName());
    }

    private static class MockResumeController extends ResumeController {
        boolean called = false;
        public MockResumeController() { super(null); }
        @Override public void resume() { called = true; }
    }

    private static class MockSaveController extends SaveController {
        boolean called = false;
        public MockSaveController() { super(null); }
        @Override public void save() { called = true; }
    }
}

