package use_case.game;

import entity.DialogueBox;
import entity.Player;
import entity.Scene;

import java.util.Map;

/**
 * Data access interface for the Switch to Game View use case.
 */
public interface GameDataAccessInterface {

    /**
     * Returns the current scene.
     * @return the current scene
     */
    // add these:
    boolean isDoorUnlocked(String doorName);
    void unlockDoor(String doorName);

    Scene getCurrentScene();

    /**
     * Returns all scenes.
     */
    Map<String, Scene> getScenes();

    /**
     * Set current scene
     */
    void setCurrentScene(Scene scene);

    /**
     * Get the player
     */
    Player getPlayer();
    void setPlayer(Player player);


}
