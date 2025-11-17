package use_case.load;

import entity.Scene;

import java.util.Map;

public interface LoadDataAccessInterface {
    void loadGame(String filepath);
    Scene getCurrentScene();
}
