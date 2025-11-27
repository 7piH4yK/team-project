package interface_adapter.game;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Game View.
 */
public class GameViewModel extends ViewModel<Object> {

    public GameViewModel() {
        super("game");
        setState(new GameState());
    }
}
