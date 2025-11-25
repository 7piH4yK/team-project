package dataaccess;

import java.util.*;

import entity.*;
import use_case.collect_item.CollectItemDataAccessInterface;
import use_case.dialogue.DialogueDataAccessInterface;
import use_case.game.GameDataAccessInterface;
import use_case.load.LoadDataAccessInterface;
import use_case.save.SaveDataAccessInterface;
import use_case.save.SaveOutputData;
import use_case.switch_to_game.SwitchToGameViewDataAccessInterface;

/**
 * In-memory implementation of game data access.
 */
public class InMemoryGameDataAccessObject implements SwitchToGameViewDataAccessInterface, GameDataAccessInterface,
    SaveDataAccessInterface, LoadDataAccessInterface, CollectItemDataAccessInterface, DialogueDataAccessInterface {

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
        Scene scene1 = sceneMap.get("Scene Stairs");
        Scene scene2 = sceneMap.get("Scene Exit");
        Scene scene3 = sceneMap.get("Scene Table");
        Scene scene4 = sceneMap.get("Scene Classroom");

        loadGameConstants(scene1, scene2, scene3, scene4);
    }

    public void resetGame() {
        Collectibles objectKeyClassroom = new ClickableObjectFactory().createCollectibles("Key Classroom", 200, 200, "key1.png");
        Collectibles objectKeyExit = new ClickableObjectFactory().createCollectibles("Key Exit", 200, 200, "key1.png");
        ClickableObject objectDoorExit = new ClickableObjectFactory().create("Door Exit", 260, 310, "door_exit.png");
        ClickableObject objectDoorClassroom = new ClickableObjectFactory().create("Door Classroom", 430, 155, "door_classroom.png");
        ClickableObject objectGoExit = new  ClickableObjectFactory().create("Go Exit", 250, 250, "right.png");
        ClickableObject objectGoTable = new ClickableObject("Go Table", 0, 300, "left.png");
        ClickableObject objectGoStairsFromExit = new ClickableObject("Go Stairs From Exit", 400, 500, "down.png");
        ClickableObject objectGoTableFromClassroom = new ClickableObject("Go Table From Classroom", 200, 500, "down.png");
        ClickableObject objectGoStairsFromTable = new ClickableObject("Go Stairs From Table", 500, 500, "down.png");

        this.player = new PlayerFactory().create();

        Scene sceneStairs = new SceneFactory().create("Scene Stairs", new ArrayList<>(List.of(objectGoExit, objectGoTable)), "stairs.jpg");
        Scene sceneExit = new SceneFactory().create("Scene Exit", new ArrayList<>(List.of(objectDoorExit, objectGoStairsFromExit)), "exit.jpg");
        Scene sceneTable = new SceneFactory().create("Scene Table", new ArrayList<>(List.of(objectDoorClassroom, objectKeyClassroom, objectGoStairsFromTable)), "table.jpg");
        Scene sceneClassroom = new SceneFactory().create("Scene Classroom", new ArrayList<>(List.of(objectGoTableFromClassroom)), "classroom.jpg");

        // This method loads and creates all the NPCs and their dialogues
        loadGameConstants(sceneStairs, sceneExit, sceneTable, sceneClassroom);

        scenes.put("Scene Stairs", sceneStairs);
        scenes.put("Scene Exit", sceneExit);
        scenes.put("Scene Table", sceneTable);
        scenes.put("Scene Classroom", sceneClassroom);
        currentScene = scenes.get("Scene Stairs");

        unlockedDoors.clear();
        currentDialogue = null;
    }

    /**
     Creates NPC and dialogue.
     **/
    public void loadGameConstants(Scene sceneStairs, Scene sceneExit, Scene sceneTable, Scene sceneClassroom) {
        DialogueBox dialogBoxOptionOutcome1 = new DialogueBuilder("db1.png")
                .setText("OUTCOME1")
                .addOption("OK", sceneExit)
                .build();

        DialogueBox dialogBoxOptionOutcome2 = new DialogueBuilder("db1.png")
                .setText("OUTCOME2")
                .addOption("OK", sceneExit)
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
                .addOption("Goodbye", sceneClassroom)
                .build();

        NonPlayableCharacter npc1 = new NonPlayableCharacterFactory().create("NPC1", 300, 300, "npc1.png", dialogueBox);
        sceneClassroom.addObject(npc1);
        NonPlayableCharacter npc2 = new NonPlayableCharacterFactory().create("NPC2", 700, 300, "npc2.png", dialogueBox2);
        sceneStairs.addObject(npc2);
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

    public Scene getCurrentScene(String name) {
        return currentScene;
    }
    public void savePlayer(Player p) { this.player = p; }

    public void saveScene(Scene s) { this.scenes.put(s.getName(), s); }

}
