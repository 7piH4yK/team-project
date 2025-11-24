package interface_adapter.game;

import use_case.game.GameOutputBoundary;
import use_case.game.GameOutputData;

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
        final GameState state = new GameState();
        state.setBackgroundImage(outputData.getBackgroundImage());
        state.setClickableObjects(outputData.getClickableObjects());
        state.setInventoryItems(outputData.getInventory());
        gameViewModel.setState(state);
        gameViewModel.firePropertyChange();
    }
}
