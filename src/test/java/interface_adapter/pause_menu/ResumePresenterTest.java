package interface_adapter.pause_menu;

import interface_adapter.ViewManagerModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResumePresenterTest {

    @Test
    void testPresenterReturnsToGame() {
        ViewManagerModel vm = new ViewManagerModel();
        ResumePresenter presenter = new ResumePresenter(vm);

        presenter.prepareGameView();

        assertEquals("game", vm.getState());
    }
}
