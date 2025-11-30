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

    /**
     * A stub controller so we can verify actions without using the real interactor.
     */
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
        // Arrange
        MainMenuViewModel vm = new MainMenuViewModel();
        MainMenuView view = new MainMenuView(vm);

        AtomicBoolean startPressed = new AtomicBoolean(false);
        AtomicBoolean loadPressed = new AtomicBoolean(false);

        view.setMainMenuController(new MainMenuControllerStub(startPressed, loadPressed));

        // Find the buttons panel (component index 4)
        Component c = view.getComponent(4);
        assertTrue(c instanceof JPanel, "Button panel should be a JPanel");
        JPanel buttonsPanel = (JPanel) c;

        // Find buttons by text (layout-safe)
        JButton startButton = findButton(buttonsPanel, "Start Game");
        JButton loadButton = findButton(buttonsPanel, "Load Game");
        JButton exitButton = findButton(buttonsPanel, "Exit Game");

        // Act
        startButton.doClick();
        loadButton.doClick();

        // Assert
        assertTrue(startPressed.get(), "Start should trigger switchToGameView()");
        assertTrue(loadPressed.get(), "Load should trigger loadGame()");
        assertEquals("Exit Game", exitButton.getText());
    }

    // ---------- Helper method (Java-8 safe) ----------
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
        return null; // unreachable
    }

    @Test
    void testErrorLabelUpdatesWhenStateChanges() {
        MainMenuViewModel vm = new MainMenuViewModel();
        MainMenuView view = new MainMenuView(vm);

        // The error label is component index 2
        JLabel errorLabel = (JLabel) view.getComponent(2);
        assertEquals("", errorLabel.getText());

        // Update state
        MainMenuState state = new MainMenuState();
        state.setErrorMessage("Something went wrong");
        vm.setState(state);

        // Fire update
        vm.firePropertyChange();

        // Assert label updated
        assertEquals("Something went wrong", errorLabel.getText());
    }

    @Test
    void testInitialLayoutHasCorrectComponents() {
        MainMenuViewModel vm = new MainMenuViewModel();
        MainMenuView view = new MainMenuView(vm);

        // Check that title, spacers, error label, and buttons exist
        assertEquals(5, view.getComponentCount());  // This matches your UI structure

        assertTrue(view.getComponent(0) instanceof JLabel); // Title label
        assertTrue(view.getComponent(2) instanceof JLabel); // Error label
        assertTrue(view.getComponent(4) instanceof JPanel); // Buttons panel
    }
}
