package data_access;

import entity.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import use_case.game.GameDataAccessInterface;
import use_case.save.SaveOutputData;
import use_case.switch_to_game.SwitchToGameViewDataAccessInterface;
import use_case.save.SaveDataAccessInterface;
import use_case.load.LoadDataAccessInterface;

import java.util.*;

/**
 * In-memory implementation of game data access.
 */
public class InMemoryGameDataAccessObject implements SwitchToGameViewDataAccessInterface, GameDataAccessInterface,
    SaveDataAccessInterface, LoadDataAccessInterface {

    private Scene currentScene;
    private Player player;
    private Map<String, Scene> scenes = new HashMap<>();


    public InMemoryGameDataAccessObject() {
        this.resetGame();
    }

    @Override
    public Scene getCurrentScene() {
        return currentScene;
    }

    public Map<String, Scene> getScenes() {
        return scenes;
    }

    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
    }

    public Player getPlayer() {
        return player;
    }

    public void saveGame(SaveOutputData outputData) {
        JSONObject gameState = new JSONObject();
        List<Scene> scenesData = new ArrayList<>(outputData.getScenes().values());
        JSONArray scenes = new JSONArray();
        for (Scene s : scenesData) {
            scenes.put(s.toJson());
        }
        gameState.put("scenes", scenes);
        gameState.put("player", outputData.getPlayer().toJson());
        gameState.put("currentScene", outputData.getCurrentScene().toJson());

        // Save to file
        try (FileWriter file = new FileWriter("save.json")) {
            file.write(gameState.toString(4)); // pretty print indent
        } catch (IOException e) {
            System.out.println("Error writing JSON: " + e.getMessage());
        }
    }

    public void loadGame(String filePath) {
        StringBuilder jsonText = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonText.append(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading save file: " + e.getMessage());
            this.currentScene = null;
            this.player = null;
            this.scenes = new  HashMap<>();
            return;
        }
        JSONObject gameState = new JSONObject(jsonText.toString());

        JSONObject playerJson = gameState.getJSONObject("player");
        this.player = Player.fromJson(playerJson);

        JSONArray scenesArray = gameState.getJSONArray("scenes");
        Map<String, Scene> loadedScenes = new HashMap<>();
        for (int i = 0; i < scenesArray.length(); i++) {
            Scene s = Scene.fromJson(scenesArray.getJSONObject(i));
            loadedScenes.put(s.getName(), s);
        }
        this.scenes = loadedScenes;

        JSONObject currentSceneJson = gameState.getJSONObject("currentScene");
        this.currentScene = Scene.fromJson(currentSceneJson);
    }

    public void resetGame() {

        ClickableObject object1 = new ClickableObjectFactory().create("Object1", 0, 0, "object1.png",false);
        ClickableObject object2 = new ClickableObjectFactory().create("Object2", 600, 300, "object2.png", false);
        ClickableObject object3 = new ClickableObjectFactory().create("Object3", 200, 200, "object2.png", true);

        this.player = new PlayerFactory().create();

        Scene scene1 = new SceneFactory().create("Scene1", new ArrayList<>(List.of(object1, object2, object3)), "scene1.png");
        Scene scene2 = new SceneFactory().create("Scene2", new ArrayList<>(List.of(object2, object1)), "scene2.png");

        DialogueBox dialogBoxOptionOutcome1 = new DialogueBuilder("db1.png")
                .setText("OUTCOME1")
                .addOption("OK", scene2)
                .build();

        DialogueBox dialogBoxOptionOutcome2 = new DialogueBuilder("db1.png")
                .setText("OUTCOME2")
                .addOption("OK", scene2)
                .build();

        DialogueBox dialogueBox = new DialogueBuilder("db1.png")
                .setText("At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis " +
                        "praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, " +
                        "similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et " +
                        "expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat " +
                        "facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum " +
                        "necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente " +
                        "delectus, ut aut reiciendis.")
                .addOption("What do you think of me?", dialogBoxOptionOutcome1)
                .addOption("Option 2", dialogBoxOptionOutcome2)
                .build();

        NonPlayableCharacter npc1 = new NonPlayableCharacterFactory().create("NPC1", 300, 300, "npc1.png", dialogueBox);
        scene2.addObject(npc1);

        scenes.put("Scene1", scene1);
        scenes.put("Scene2", scene2);
        currentScene = scenes.get("Scene1");

    }

    public void setPlayer(Player player) {
        this.player = player;
    }
      
    private final java.util.Set<String> unlockedDoors = new java.util.HashSet<>();

    @Override
    public boolean isDoorUnlocked(String doorName) {
        return unlockedDoors.contains(doorName);
    }

    @Override
    public void unlockDoor(String doorName) {
        unlockedDoors.add(doorName);
    }

}
