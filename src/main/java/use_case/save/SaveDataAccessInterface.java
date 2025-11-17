package use_case.save;

import entity.Scene;
import entity.Player;

import java.util.List;
import java.util.Map;

/**
 * Data access interface for the save use case.
 */
public interface SaveDataAccessInterface {

    /**
     * Returns the current scene.
     * @return the current scene
     */
    Scene getCurrentScene();
    Map<String, Scene> getScenes();
    Player getPlayer();
    void saveGame(SaveOutputData outputData);

}
