package data_access;

import entity.*;
import use_case.dialogue.DialogueDataAccessInterface;
import use_case.game.GameDataAccessInterface;
import use_case.load.LoadDataAccessInterface;
import use_case.save.SaveDataAccessInterface;
import use_case.save.SaveOutputData;
import use_case.switch_to_game.SwitchToGameViewDataAccessInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public InMemoryGameDataAccessObject() {
        this.resetGame();
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
     **/
    public void saveGame(SaveOutputData outputData) {
        FileAccessObject fileAccessObject = new FileAccessObject();
        fileAccessObject.saveGame(outputData);
    }

    /**
     * Creates a FileAccessObject to handle the load.
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
        ClickableObject objectDoorExit = new ClickableObjectFactory().create("Door Exit", 260, 310, "door.png");
        ClickableObject objectDoorClassroom = new ClickableObjectFactory().create("Door Classroom", 430, 155, "door_classroom.png");
        ClickableObject objectGoExit = new ClickableObjectFactory().create("Go Exit", 250, 250, "right.png");
        ClickableObject objectGoTable = new ClickableObject("Go Table", 0, 300, "left.png");
        ClickableObject objectGoStairsFromExit = new ClickableObject("Go Stairs From Exit", 130, 460, "down.png");
        ClickableObject objectGoTableFromClassroom = new ClickableObject("Go Table From Classroom", 200, 465, "down.png");
        ClickableObject objectGoStairsFromTable = new ClickableObject("Go Stairs From Table", 500, 465, "down.png");

        this.player = new PlayerFactory().create();

        Scene sceneStairs = new SceneFactory().create("Scene Stairs", new ArrayList<>(List.of(objectGoExit, objectGoTable)), "scene1.png");
        Scene sceneExit = new SceneFactory().create("Scene Exit", new ArrayList<>(List.of(objectDoorExit, objectGoStairsFromExit)), "scene2.png");
        Scene sceneTable = new SceneFactory().create("Scene Table", new ArrayList<>(List.of(objectDoorClassroom, objectKeyClassroom, objectGoStairsFromTable)), "scene3.png");
        Scene sceneClassroom = new SceneFactory().create("Scene Classroom", new ArrayList<>(List.of(objectGoTableFromClassroom)), "scene4.png");

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
     **/
    public void loadGameConstants(Scene sceneStairs, Scene sceneExit, Scene sceneTable, Scene sceneClassroom) {
        DialogueBox dialogBoxOptionOutcome1 = new DialogueBuilder("robber_db.png")
                .setText("Well, maybe if you answer a riddle, that is!")
                .addOption("Answer riddle", sceneExit)
                .build();

        DialogueBox dialogBoxOptionOutcome2 = new DialogueBuilder("janitor_db.png")
                .setText("I would've helped you, but somebody stole my keys and ran down the hallway!" +
                        " If you find them and bring back my keys, I will get you out!")
                .addOption("I'll see what I can do.", sceneExit)
                .build();

//        QuestionBox dialogBoxOptionOutcome4 = new QuestionBoxBuilder("laptop_db.png")
//                .setText("(Insert riddle here)")
//                .addOption("OK", sceneExit)
//                .build();

        DialogueBox dialogBoxOptionOutcome3 = new DialogueBuilder("laptop_db.png")
                .setText("If you want to get past that door to the right, you must answer my question first!")
                .addOption("Answer riddle", sceneExit)
                .build();

        DialogueBox dialogueBox = new DialogueBuilder("robber_db.png")
                .setText("I stole the keys and you will never get them back!!!")
                .addOption("Really, never?", dialogBoxOptionOutcome1)
                .addOption("OK, fine.", sceneClassroom)
                .build();


        DialogueBox dialogueBox2 = new DialogueBuilder("janitor_db.png")
                .setText("Oh, what are you doing here? I thought the building was already closed!")
                .addOption("Yeah, I'm stuck here.", dialogBoxOptionOutcome2)
                .addOption("*Run away*", sceneExit)
                .build();

        DialogueBox dialogueBox3 = new DialogueBuilder("laptop_db.png")
                .setText("Somebody seems to have forgotten their laptop here.")
                .addOption("Investigate.", dialogBoxOptionOutcome3)
                .addOption("*Step Away*", sceneExit)
                .build();

        NonPlayableCharacter npc1 = new NonPlayableCharacterFactory().create("NPC1", 300, 200, "robber.png", dialogueBox);
        sceneClassroom.addObject(npc1);
        NonPlayableCharacter npc2 = new NonPlayableCharacterFactory().create("NPC2", 500, 200, "janitor1.png", dialogueBox2);
        sceneStairs.addObject(npc2);
        NonPlayableCharacter npc3 = new NonPlayableCharacterFactory().create("NPC3", 80, 330, "laptop.png", dialogueBox3);
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
