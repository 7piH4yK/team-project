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
    private final java.util.Set<String> unlockedDoors = new java.util.HashSet<>();
    private DialogueBox currentDialogue;


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
        gameState.put("currentScene", outputData.getCurrentScene().getName());

        // Save to file
        try (FileWriter file = new FileWriter("save.json")) {
            file.write(gameState.toString(4)); // pretty print indent
        } catch (IOException e) {
            System.out.println("Error writing JSON: " + e.getMessage());
        }
    }

    public void loadGame(String filePath) {
        // read file and stuff
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

        // create JSONObject to read from
        JSONObject gameState = new JSONObject(jsonText.toString());

        // load player and scene object
        JSONObject playerJson = gameState.getJSONObject("player");
        this.player = Player.fromJson(playerJson);

        JSONArray scenesArray = gameState.getJSONArray("scenes");
        Map<String, Scene> loadedScenes = new HashMap<>();
        for (int i = 0; i < scenesArray.length(); i++) {
            Scene s = Scene.fromJson(scenesArray.getJSONObject(i));
            loadedScenes.put(s.getName(), s);
        }
        this.scenes = loadedScenes;

        // get scenes then add constant dialogue and NPCs
        Map<String, Scene> sceneMap = getScenes();
        Scene scene1 = sceneMap.get("Scene1");
        Scene scene2 = sceneMap.get("Scene2");
        Scene scene3 = sceneMap.get("Scene3");
        Scene scene4 = sceneMap.get("Scene4");

        loadGameConstants(scene1, scene2, scene3, scene4);

        String currentSceneName = gameState.getString("currentScene");
        this.currentScene = loadedScenes.get(currentSceneName);
    }

    public void resetGame() {

        ClickableObject object1 = new ClickableObjectFactory().create("Object1", 0, 0, "object1.png");
        ClickableObject object2 = new ClickableObjectFactory().create("Object2", 600, 300, "object2.png");
        ClickableObject object3 = new ClickableObjectFactory().create("Object3", 200, 200, "object3.png");
        Collectibles objectKey1 = new ClickableObjectFactory().createCollectibles("Key1", 200, 200, "key1.png");
        ClickableObject objectDoor1 = new ClickableObjectFactory().create("Door1", 200, 200, "door1.png");


        this.player = new PlayerFactory().create();

        Scene scene1 = new SceneFactory().create("Scene1", new ArrayList<>(List.of(object1, object2, objectKey1)), "scene1.png");
        Scene scene2 = new SceneFactory().create("Scene2", new ArrayList<>(List.of(object1, object2, object3)), "scene2.png");
        Scene scene3 = new SceneFactory().create("Scene3", new ArrayList<>(List.of(object1, objectDoor1)), "scene3.png");
        Scene scene4 = new SceneFactory().create("Scene4", new ArrayList<>(List.of(object1)), "scene4.png");

        // This method loads and creates all the NPCs and their dialogues
        loadGameConstants(scene1, scene2, scene3, scene4);

        scenes.put("Scene1", scene1);
        scenes.put("Scene2", scene2);
        scenes.put("Scene3", scene3);
        scenes.put("Scene4", scene4);
        currentScene = scenes.get("Scene1");

        unlockedDoors.clear();
        currentDialogue = null;
    }

    public void loadGameConstants(Scene scene1, Scene scene2, Scene scene3, Scene scene4) {
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


        DialogueBox dialogueBox2 = new DialogueBuilder("db1.png")
                .setText("Hello, world! I am NPC2")
                .addOption("Goodbye", scene4)
                .build();

        NonPlayableCharacter npc1 = new NonPlayableCharacterFactory().create("NPC1", 300, 300, "npc1.png", dialogueBox);
        scene2.addObject(npc1);
        NonPlayableCharacter npc2 = new NonPlayableCharacterFactory().create("NPC2", 700, 300, "npc2.png", dialogueBox2);
        scene4.addObject(npc2);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    @Override
    public boolean isDoorUnlocked(String doorName) {
        return unlockedDoors.contains(doorName);
    }

    @Override
    public void unlockDoor(String doorName) {
        unlockedDoors.add(doorName);
    }

    @Override
    public DialogueBox getCurrentDialogue() {
        return currentDialogue;
    }

    @Override
    public void setCurrentDialogue(DialogueBox dialogue) {
        this.currentDialogue = dialogue;
    }
}
