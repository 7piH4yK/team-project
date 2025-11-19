package use_case.game;

import entity.DialogueBox;
import entity.Player;
import entity.Scene;

import java.util.Map;

/**
 * Data access interface for the Game use case.
 */
public interface GameDataAccessInterface {

    /**
     * Returns whether a door is unlocked.
     */
    boolean isDoorUnlocked(String doorName);

    /**
     * Unlocks a door.
     */
    void unlockDoor(String doorName);

    /**
     * Returns the current scene.
     * @return the current scene
     */
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

    /**
     * Set the player
     */
    void setPlayer(Player player);

    /**
     * Get the current dialogue box (null if no dialogue is active).
     */
    DialogueBox getCurrentDialogue();

    /**
     * Set the current dialogue box (null to close dialogue).
     */
    void setCurrentDialogue(DialogueBox dialogue);
}
