package data_access;

import entity.Player;
import entity.Scene;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.save.SaveOutputData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that handles JSON access.
 **/
public class FileAccessObject {
    final private JSONObject gameState;

    public FileAccessObject() {
        this.gameState = null;
    }

    public FileAccessObject(String filePath) {
        StringBuilder jsonText = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonText.append(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading save file: " + e.getMessage());
        }
        this.gameState = new JSONObject(jsonText.toString());
    }

    /**
     * Saves game into a save file that can be loaded later.
     **/
    public void saveGame(SaveOutputData outputData) {
        final int prettyPrint = 4;
        final JSONObject gameState = new JSONObject();
        final List<Scene> scenesData = new ArrayList<>(outputData.getScenes().values());
        final JSONArray scenes = new JSONArray();

        for (Scene s : scenesData) {
            scenes.put(s.toJson());
        }
        gameState.put("scenes", scenes);
        gameState.put("player", outputData.getPlayer().toJson());
        gameState.put("currentScene", outputData.getCurrentScene().getName());

        // Save to file
        try (FileWriter file = new FileWriter("save.json")) {
            file.write(gameState.toString(prettyPrint));
        } catch (IOException exception) {
            System.out.println("Error writing JSON: " + exception.getMessage());
        }
    }

    /**
     * Load all scenes through save file.
     **/
    public Map<String, Scene> loadScenes() {
        final JSONArray scenesArray = this.gameState.getJSONArray("scenes");
        Map<String, Scene> loadedScenes = new HashMap<>();
        for (int i = 0; i < scenesArray.length(); i++) {
            Scene s = Scene.fromJson(scenesArray.getJSONObject(i));
            loadedScenes.put(s.getName(), s);
        }
        return loadedScenes;
    }

    /**
     * Load current player state through save file.
     **/
    public Player loadPlayer() {
        JSONObject playerJson = this.gameState.getJSONObject("player");
        return Player.fromJson(playerJson);
    }

    /**
     * Load current scene through save file.
     **/
    public String loadCurrentScene() {
        return this.gameState.getString("currentScene");
    }
}
