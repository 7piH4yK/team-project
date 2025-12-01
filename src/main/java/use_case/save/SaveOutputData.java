package use_case.save;

import java.util.Map;

import entity.Player;
import entity.Scene;

/**
 * Output data for the save use case.
 */
public class SaveOutputData {

    private Scene currentScene;
    private Map<String, Scene> scenes;
    private Player player;

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }

    public Map<String, Scene> getScenes() {
        return scenes;
    }

    public void setScenes(Map<String, Scene> scenes) {
        this.scenes = scenes;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
