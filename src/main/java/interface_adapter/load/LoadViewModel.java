package interface_adapter.load;

import interface_adapter.ViewModel;
import interface_adapter.game.GameState;

/**
 * The ViewModel for the Game View.
 */
public class LoadViewModel extends ViewModel<GameState> {

    public LoadViewModel(GameState gameState) {
        super("game");
        setState(new GameState());
    }
}
