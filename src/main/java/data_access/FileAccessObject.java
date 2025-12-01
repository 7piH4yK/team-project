package data_access;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Player;
import entity.Scene;
import use_case.save.SaveOutputData;

/**
 * Class that handles JSON access.
 **/
public class FileAccessObject {
    private JSONObject gameState;

    public FileAccessObject() {
        this.gameState = null;
    }

    public FileAccessObject(String filePath) {
        final StringBuilder jsonText = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonText.append(line);
            }
        }
        catch (IOException exception) {
            System.out.println("Error reading save file: " + exception.getMessage());
        }
        this.gameState = new JSONObject(jsonText.toString());
    }

    /**
     * Saves game into a save file that can be loaded later.
     * @param outputData object that contains data for save.
     **/
    public void saveGame(SaveOutputData outputData) {
        final int prettyPrint = 4;
        this.gameState = new JSONObject();
        final List<Scene> scenesData = new ArrayList<>(outputData.getScenes().values());
        final JSONArray scenes = new JSONArray();

        for (Scene s : scenesData) {
            scenes.put(s.toJson());
        }
        gameState.put("scenes", scenes);
        gameState.put("player", outputData.getPlayer().toJson());
        gameState.put("currentScene", outputData.getCurrentScene().getName());

        // Save to file
        final String filePath = "src/main/java/data_access/game_saves/save.json";
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(gameState.toString(prettyPrint));
        }
        catch (IOException exception) {
            System.out.println("Error writing JSON: " + exception.getMessage());
        }
    }

    /**
     * Load all scenes through save file.
     * @return a map with all the current states of scenes.
     **/
    public Map<String, Scene> loadScenes() {
        final JSONArray scenesArray = this.gameState.getJSONArray("scenes");
        final Map<String, Scene> loadedScenes = new HashMap<>();
        for (int i = 0; i < scenesArray.length(); i++) {
            final Scene s = Scene.fromJson(scenesArray.getJSONObject(i));
            loadedScenes.put(s.getName(), s);
        }
        return loadedScenes;
    }

    /**
     * Load current player state through save file.
     * @return the current Player state.
     **/
    public Player loadPlayer() {
        final JSONObject playerJson = this.gameState.getJSONObject("player");
        return Player.fromJson(playerJson);
    }

    /**
     * Load current scene through save file.
     * @return the name of the current scene.
     **/
    public String loadCurrentScene() {
        return this.gameState.getString("currentScene");
    }
}
