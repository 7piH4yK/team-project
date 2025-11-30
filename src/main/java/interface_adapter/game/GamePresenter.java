package interface_adapter.game;

import use_case.game.GameOutputBoundary;
import use_case.game.GameOutputData;

import java.util.ArrayList;

/**
 * The Presenter for the Game View.
 */
public class GamePresenter implements GameOutputBoundary {

    private final GameViewModel gameViewModel;

    public GamePresenter(GameViewModel gameViewModel) {
        this.gameViewModel = gameViewModel;
    }

    @Override
    public void prepareView(GameOutputData outputData) {
        GameState state = gameViewModel.getState();

        state.setBackgroundImage(outputData.getBackgroundImage());
        state.setClickableObjects(outputData.getClickableObjects());
        state.setInventoryItems(
                outputData.getInventory() == null ? new ArrayList<>() : outputData.getInventory()
        );
        state.setSceneName(outputData.getSceneName());

        gameViewModel.setState(state);
        gameViewModel.firePropertyChange();
    }
}
