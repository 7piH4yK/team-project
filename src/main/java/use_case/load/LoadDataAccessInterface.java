package use_case.load;

import entity.Player;
import entity.Scene;

/**
 * Data access interface for the load use case.
 **/
public interface LoadDataAccessInterface {
    void loadGame(String filepath);
    Player getPlayer();

    Scene getCurrentScene();
}
