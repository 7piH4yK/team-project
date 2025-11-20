package use_case.game;

import entity.ClickableObject;

/**
 * Input data for the Click Button use case.
 */
public class GameInputData {

    private final ClickableObject clickables;

    public GameInputData(ClickableObject collectibles) {
        this.clickables = collectibles;
    }

    public ClickableObject getClickableObject() {
        return clickables;
    }
}
