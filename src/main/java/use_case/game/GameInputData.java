package use_case.game;

import entity.ClickableObject;

/**
 * Input data for the Click Button use case.
 */
public class GameInputData {

    private final ClickableObject clickable;

    public GameInputData(ClickableObject collectibles) {
        this.clickable = collectibles;
    }

    public ClickableObject getClickableObject() {
        return clickable;
    }
}
