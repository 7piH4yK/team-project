package dataaccess;

import java.util.*;

import entity.*;
import use_case.collect_item.CollectItemDataAccessInterface;
import use_case.game.GameDataAccessInterface;
import use_case.load.LoadDataAccessInterface;
import use_case.save.SaveDataAccessInterface;
import use_case.save.SaveOutputData;
import use_case.switch_to_game.SwitchToGameViewDataAccessInterface;

/**
 * In-memory implementation of game data access.
 */
public class InMemoryGameDataAccessObject implements SwitchToGameViewDataAccessInterface, GameDataAccessInterface,
    SaveDataAccessInterface, LoadDataAccessInterface, CollectItemDataAccessInterface {

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

    /**
     Creates a FileAccessObject to handle the save.
     **/
    public void saveGame(SaveOutputData outputData) {
        FileAccessObject fileAccessObject = new FileAccessObject();
        fileAccessObject.saveGame(outputData);
    }

    /**
     Creates a FileAccessObject to handle the load.
     **/
    public void loadGame(String filePath) {
        FileAccessObject fileAccessObject = new FileAccessObject(filePath);
        this.scenes = fileAccessObject.loadScenes();
        this.player = fileAccessObject.loadPlayer();
        this.currentScene = this.scenes.get(fileAccessObject.loadCurrentScene());

        Map<String, Scene> sceneMap = getScenes();
        Scene scene1 = sceneMap.get("Scene1");
        Scene scene2 = sceneMap.get("Scene2");
        Scene scene3 = sceneMap.get("Scene3");
        Scene scene4 = sceneMap.get("Scene4");

        loadGameConstants(scene1, scene2, scene3, scene4);
    }

    public void resetGame() {

        ClickableObject object1 = new ClickableObjectFactory().create("Object1", 0, 0, "object1.png");
        ClickableObject object2 = new ClickableObjectFactory().create("Object2", 600, 300, "object2.png");
        ClickableObject object3 = new ClickableObjectFactory().create("Object3", 200, 200, "object3.png");
        Collectibles objectKey1 = new ClickableObjectFactory().createCollectibles("Key1", 200, 200, "key1.png");
        ClickableObject objectDoor1 = new ClickableObjectFactory().create("Door1", 200, 200, "door1.png");


        this.player = new PlayerFactory().create();

        Scene scene1 = new SceneFactory().create("Scene1", new ArrayList<>(List.of(object2, objectKey1)), "scene1.png");
        Scene scene2 = new SceneFactory().create("Scene2", new ArrayList<>(List.of(object1, object3)), "scene2.png");
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

    /**
     Creates NPC and dialogue.
     **/
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

    @Override
    public Scene getScene(String name) {
        return currentScene;
    }
    public void savePlayer(Player p) { this.player = p; }

    public void saveScene(Scene s) { this.scenes.put(s.getName(), s); }

}
