package interface_adapter.main_menu;

import interface_adapter.ViewManagerModel;
import interface_adapter.game.GameState;
import interface_adapter.game.GameViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.switch_to_game.SwitchToGameOutputData;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuPresenterTest {

    private ViewManagerModel viewManagerModel;
    private GameViewModel gameViewModel;
    private MainMenuPresenter presenter;

    @BeforeEach
    void setUp() {
        viewManagerModel = new ViewManagerModel();
        gameViewModel = new GameViewModel();
        presenter = new MainMenuPresenter(viewManagerModel, gameViewModel);
    }

    @Test
    void testSwitchToGameViewUpdatesViewManagerState() {
        SwitchToGameOutputData output = new SwitchToGameOutputData();
        output.setBackgroundImage("bg.png");
        output.setClickableObjects(Collections.emptyList());
        output.setCurrentDialogue(null);

        presenter.switchToGameView(output);

        assertEquals(gameViewModel.getViewName(), viewManagerModel.getState());
    }


    }
