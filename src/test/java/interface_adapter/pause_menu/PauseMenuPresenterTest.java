package interface_adapter.pause_menu;

import interface_adapter.ViewManagerModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PauseMenuPresenterTest {

    @Test
    void testPresenterSwitchesState() {
        ViewManagerModel vm = new ViewManagerModel();
        PauseMenuPresenter presenter = new PauseMenuPresenter(vm, new PauseMenuViewModel());

        presenter.preparePauseView();

        assertEquals("pause_menu", vm.getState());
    }
}
