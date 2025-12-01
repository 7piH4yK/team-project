package view;

import interface_adapter.main_menu.MainMenuController;
import interface_adapter.main_menu.MainMenuState;
import interface_adapter.main_menu.MainMenuViewModel;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuViewTest {

    static class MainMenuControllerStub extends MainMenuController {

        AtomicBoolean startCalled;
        AtomicBoolean loadCalled;

        public MainMenuControllerStub(AtomicBoolean startCalled, AtomicBoolean loadCalled) {
            super(null, null, null);
            this.startCalled = startCalled;
            this.loadCalled = loadCalled;
        }

        @Override
        public void switchToGameView() {
            startCalled.set(true);
        }

        @Override
        public void loadGame() {
            loadCalled.set(true);
        }
    }

    @Test
    void testButtonsTriggerControllerMethods() {
        MainMenuViewModel vm = new MainMenuViewModel();
        MainMenuView view = new MainMenuView(vm);

        AtomicBoolean startPressed = new AtomicBoolean(false);
        AtomicBoolean loadPressed = new AtomicBoolean(false);

        view.setMainMenuController(new MainMenuControllerStub(startPressed, loadPressed));

        // Button panel is now at index 5, not 4
        Component c = view.getComponent(5);
        assertTrue(c instanceof JPanel, "Button panel should be a JPanel");
        JPanel buttonsPanel = (JPanel) c;

        JButton startButton = findButton(buttonsPanel, "Start Game");
        JButton loadButton = findButton(buttonsPanel, "Load Game");
        JButton exitButton = findButton(buttonsPanel, "Exit Game");

        startButton.doClick();
        loadButton.doClick();

        assertTrue(startPressed.get(), "Start should trigger switchToGameView()");
        assertTrue(loadPressed.get(), "Load should trigger loadGame()");
        assertEquals("Exit Game", exitButton.getText());
    }

    private JButton findButton(JPanel panel, String text) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                if (text.equals(btn.getText())) {
                    return btn;
                }
            }
        }
        fail("Button not found: " + text);
        return null;
    }

    @Test
    void testErrorLabelUpdatesWhenStateChanges() {
        MainMenuViewModel vm = new MainMenuViewModel();
        MainMenuView view = new MainMenuView(vm);

        // Error label is now at index 3
        JLabel errorLabel = (JLabel) view.getComponent(3);
        assertEquals("", errorLabel.getText());

        MainMenuState state = new MainMenuState();
        state.setErrorMessage("Something went wrong");
        vm.setState(state);

        vm.firePropertyChange();

        assertEquals("Something went wrong", errorLabel.getText());
    }

    @Test
    void testInitialLayoutHasCorrectComponents() {
        MainMenuViewModel vm = new MainMenuViewModel();
        MainMenuView view = new MainMenuView(vm);

        // 6 components: spacer, title, spacer, error label, spacer, buttons panel
        assertEquals(6, view.getComponentCount());

        assertTrue(view.getComponent(1) instanceof JLabel); // title
        assertTrue(view.getComponent(3) instanceof JLabel); // error label
        assertTrue(view.getComponent(5) instanceof JPanel); // buttons panel
    }
}
