package use_case.switch_to_game;

import entity.Scene;

/**
 * Data access interface for the Switch to Game View use case.
 */
public interface SwitchToGameViewDataAccessInterface {

    /**
     * Returns the current scene.
     * @return the current scene
     */
    Scene getCurrentScene();
    void resetGame();
}
