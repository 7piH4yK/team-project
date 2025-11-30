package interface_adapter.game;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Game View.
 */
public class GameViewModel extends ViewModel<GameState> {

    public GameViewModel() {
        super("game");
        setState(new GameState());
    }
}
