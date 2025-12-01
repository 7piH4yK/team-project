package data_access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.ClickableObject;
import entity.ClickableObjectFactory;
import entity.Collectibles;
import entity.DialogueBox;
import entity.DialogueBuilder;
import entity.NonPlayableCharacter;
import entity.Player;
import entity.Scene;
import interface_adapter.factories.ClickableObjectFactoryInterface;
import interface_adapter.factories.NonPlayableCharacterFactoryInterface;
import interface_adapter.factories.PlayerFactoryInterface;
import interface_adapter.factories.SceneFactoryInterface;
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
        SaveDataAccessInterface, LoadDataAccessInterface, DialogueDataAccessInterface {

    private final java.util.Set<String> unlockedDoors = new java.util.HashSet<>();
    private Scene currentScene;
    private Player player;
    private Map<String, Scene> scenes = new HashMap<>();
    private DialogueBox currentDialogue;
    private final SceneFactoryInterface sceneFactory;
    private final ClickableObjectFactoryInterface clickableObjectFactory;
    private final NonPlayableCharacterFactoryInterface npcFactory;
    private final PlayerFactoryInterface playerFactory;

    public InMemoryGameDataAccessObject(SceneFactoryInterface sceneFactory,
                                        ClickableObjectFactory clickableObjectFactory,
                                        NonPlayableCharacterFactoryInterface npcFactory,
                                        PlayerFactoryInterface playerFactory) {
        this.sceneFactory = sceneFactory;
        this.clickableObjectFactory = clickableObjectFactory;
        this.npcFactory = npcFactory;
        this.playerFactory = playerFactory;
    }

    @Override
    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
    }

    public Map<String, Scene> getScenes() {
        return scenes;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Creates a FileAccessObject to handle the save.
     * @param outputData contains all the data needed to save current game state.
     **/
    public void saveGame(SaveOutputData outputData) {
        final FileAccessObject fileAccessObject = new FileAccessObject();
        fileAccessObject.saveGame(outputData);
    }

    /**
     * Creates a FileAccessObject to handle the load.
     * @param filePath is a String that is the path to the file save in game_saves folder
     **/
    public void loadGame(String filePath) {
        final FileAccessObject fileAccessObject = new FileAccessObject(filePath);
        this.scenes = fileAccessObject.loadScenes();
        this.player = fileAccessObject.loadPlayer();
        this.currentScene = this.scenes.get(fileAccessObject.loadCurrentScene());

        final Map<String, Scene> sceneMap = getScenes();
        final Scene scene1 = sceneMap.get("Scene Stairs");
        final Scene scene2 = sceneMap.get("Scene Exit");
        final Scene scene3 = sceneMap.get("Scene Table");
        final Scene scene4 = sceneMap.get("Scene Classroom");

        loadGameConstants(scene1, scene2, scene3, scene4);
    }

    /**
     * Resets the game back to the initial state.
     **/
    public void resetGame() {
        final Collectibles objectKeyClassroom = clickableObjectFactory.createCollectibles("Key Classroom", 200, 200,
                "key1.png");
        final Collectibles objectKeyExit = clickableObjectFactory.createCollectibles("Key Exit", 200, 200, "key1.png");
        final ClickableObject objectDoorExit = clickableObjectFactory.create("Door Exit", 260, 310, "door.png");
        final ClickableObject objectDoorClassroom = clickableObjectFactory.create("Door Classroom", 430, 155,
                "door_classroom.png");
        final ClickableObject objectGoExit = clickableObjectFactory.create("Go Exit", 250, 250, "right.png");
        final ClickableObject objectGoTable = new ClickableObject("Go Table", 0, 300, "left.png");
        final ClickableObject objectGoStairsFromExit = new ClickableObject("Go Stairs From Exit", 130, 460,
                "down.png");
        final ClickableObject objectGoTableFromClassroom = new ClickableObject("Go Table From Classroom", 200, 465,
                "down.png");
        final ClickableObject objectGoStairsFromTable = new ClickableObject("Go Stairs From Table", 500, 465,
                "down.png");

        this.player = playerFactory.create();

        final Scene sceneStairs = sceneFactory.create("Scene Stairs",
                new ArrayList<>(List.of(objectGoExit, objectGoTable)), "scene1.png");
        final Scene sceneExit = sceneFactory.create("Scene Exit",
                new ArrayList<>(List.of(objectDoorExit, objectGoStairsFromExit)), "scene2.png");
        final Scene sceneTable = sceneFactory.create("Scene Table",
                new ArrayList<>(List.of(objectDoorClassroom, objectKeyClassroom, objectGoStairsFromTable)),
                "scene3.png");
        final Scene sceneClassroom = sceneFactory.create("Scene Classroom",
                new ArrayList<>(List.of(objectGoTableFromClassroom)), "scene4.png");

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
     * Creates NPC and dialogue.
     * @param sceneStairs Scene representing the stair scene.
     * @param sceneExit Scene representing the exit scene.
     * @param sceneTable Scene representing the table scene.
     * @param sceneClassroom Scene representing the classroom scene.
     **/
    public void loadGameConstants(Scene sceneStairs, Scene sceneExit, Scene sceneTable, Scene sceneClassroom) {
        final DialogueBox dialogBoxOptionOutcome1 = new DialogueBuilder("robber_db.png")
                .setText("Well, maybe if you answer a riddle, that is!")
                .addOption("Answer riddle", sceneExit)
                .build();

        final DialogueBox dialogBoxOptionOutcome2 = new DialogueBuilder("janitor_db.png")
                .setText("I would've helped you, but somebody stole my keys and ran down the hallway!"
                        + " If you find them and bring back my keys, I will get you out!")
                .addOption("I'll see what I can do.", sceneExit)
                .build();

//        QuestionBox dialogBoxOptionOutcome4 = new QuestionBoxBuilder("laptop_db.png")
//                .setText("(Insert riddle here)")
//                .addOption("OK", sceneExit)
//                .build();

        final DialogueBox dialogBoxOptionOutcome3 = new DialogueBuilder("laptop_db.png")
                .setText("If you want to get past that door to the right, you must answer my question first!")
                .addOption("Answer riddle", sceneExit)
                .build();

        final DialogueBox dialogueBox = new DialogueBuilder("robber_db.png")
                .setText("I stole the keys and you will never get them back!!!")
                .addOption("Really, never?", dialogBoxOptionOutcome1)
                .addOption("OK, fine.", sceneClassroom)
                .build();

        final DialogueBox dialogueBox2 = new DialogueBuilder("janitor_db.png")
                .setText("Oh, what are you doing here? I thought the building was already closed!")
                .addOption("Yeah, I'm stuck here.", dialogBoxOptionOutcome2)
                .addOption("*Run away*", sceneExit)
                .build();

        final DialogueBox dialogueBox3 = new DialogueBuilder("laptop_db.png")
                .setText("Somebody seems to have forgotten their laptop here.")
                .addOption("Investigate.", dialogBoxOptionOutcome3)
                .addOption("*Step Away*", sceneExit)
                .build();

        final NonPlayableCharacter npc1 = npcFactory.create("NPC1", 300, 200, "robber.png", dialogueBox);
        sceneClassroom.addObject(npc1);
        final NonPlayableCharacter npc2 = npcFactory.create("NPC2", 500, 200, "janitor1.png", dialogueBox2);
        sceneStairs.addObject(npc2);
        final NonPlayableCharacter npc3 = npcFactory.create("NPC3", 80, 330, "laptop.png", dialogueBox3);
        sceneTable.addObject(npc3);
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
