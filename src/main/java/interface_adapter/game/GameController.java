package interface_adapter.game;

import entity.ClickableObject;
import entity.Collectibles;
import entity.DialogueOption;
import use_case.game.GameInputBoundary;
import use_case.game.GameInputData;

/**
 * Controller for the Game View.
 */
public class GameController {

    private final GameInputBoundary gameInputBoundary;

    public GameController(GameInputBoundary gameInputBoundary) {
        this.gameInputBoundary = gameInputBoundary;
    }

    /**
     * Click a clickable object
     */
    public void click(ClickableObject clickables) {
        gameInputBoundary.execute(new GameInputData(clickables));
    }

    /**
     * Click a dialogue option
     */
    public void clickDialogueOption(DialogueOption dialogueOption) {
        gameInputBoundary.executeDialogueOption(dialogueOption);
    }
}
