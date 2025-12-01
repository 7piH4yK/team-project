package use_case.save;

import java.util.Map;

import entity.Player;
import entity.Scene;

/**
 * Data access interface for the save use case.
 */
public interface SaveDataAccessInterface {

    /**
     * Gets the current scene.
     * @return the current scene.
     */
    Scene getCurrentScene();

    /**
     * Gets a map of the Scenes.
     * @return map of the Scenes.
     */
    Map<String, Scene> getScenes();

    /**
     * Gets the Player object.
     * @return Player object.
     */
    Player getPlayer();

    /**
     * Saves the game through the DAOs.
     * @param outputData is the information saved into the DAO.
     */
    void saveGame(SaveOutputData outputData);

}
