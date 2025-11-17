package use_case.load;

import entity.Scene;

public interface LoadDataAccessInterface {
    void loadGame(String filepath);
    Scene getCurrentScene();
}
