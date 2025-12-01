package use_case.load;

import entity.Scene;

/**
 * Data access interface for the load use case.
 **/
public interface LoadDataAccessInterface {
    /**
     * The call that accesses the DAO for the load use case.
     * @param filepath is the file path to the save file.
     **/
    void loadGame(String filepath);

    /**
     * Gets the current scene.
     * @return a Scene representing the current scene.
     **/
    Scene getCurrentScene();
}
