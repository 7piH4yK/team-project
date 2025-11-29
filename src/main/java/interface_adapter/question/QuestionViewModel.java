package interface_adapter.question;

import interface_adapter.ViewModel;
import interface_adapter.game.GameState;

public class QuestionViewModel extends ViewModel<GameState> {
    public QuestionViewModel() {
        super("question");
        setState(new GameState());
    }
}
