package use_case.game;

import data_access.InMemoryGameDataAccessObject;
import entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.SwingUtilities;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameInteractorTest {

    /**
     * Test presenter to capture output data.
     */
    static class TestPresenter implements GameOutputBoundary {
        GameOutputData outputData;
        int prepareViewCallCount = 0;

        @Override
        public void prepareView(GameOutputData outputData) {
            this.outputData = outputData;
            this.prepareViewCallCount++;
        }
    }

    private InMemoryGameDataAccessObject dao;
    private TestPresenter presenter;
    private GameInteractor interactor;

    @BeforeEach
    void setUp() {
        SceneFactory sceneFactory = new SceneFactory();
        ClickableObjectFactory clickFactory = new ClickableObjectFactory();
        NonPlayableCharacterFactory npcFactory = new NonPlayableCharacterFactory();
        PlayerFactory playerFactory = new PlayerFactory();
        dao = new InMemoryGameDataAccessObject(sceneFactory, clickFactory, npcFactory, playerFactory);
        dao.getScenes().clear();
        dao.setPlayer(new Player());
        presenter = new TestPresenter();
        interactor = new GameInteractor(dao, presenter);
    }

    // ----------------------------------------------------------
    // Tests for execute() - Navigation between scenes
    // ----------------------------------------------------------

    @Test
    void testExecuteGoExit() {
        Scene sceneStairs = new Scene("Scene Stairs", new ArrayList<>(), "stairs.png");
        Scene sceneExit = new Scene("Scene Exit", new ArrayList<>(), "exit.png");

        dao.getScenes().put("Scene Stairs", sceneStairs);
        dao.getScenes().put("Scene Exit", sceneExit);
        dao.setCurrentScene(sceneStairs);

        ClickableObject goExit = new ClickableObject("Go Exit", 100, 100, "exit_button.png");
        GameInputData inputData = new GameInputData(goExit);

        interactor.execute(inputData);

        assertEquals(sceneExit, dao.getCurrentScene());
        assertEquals(1, presenter.prepareViewCallCount);
        assertEquals("Scene Exit", presenter.outputData.getSceneName());
    }

    @Test
    void testExecuteGoTable() {
        Scene sceneStairs = new Scene("Scene Stairs", new ArrayList<>(), "stairs.png");
        Scene sceneTable = new Scene("Scene Table", new ArrayList<>(), "table.png");

        dao.getScenes().put("Scene Stairs", sceneStairs);
        dao.getScenes().put("Scene Table", sceneTable);
        dao.setCurrentScene(sceneStairs);

        ClickableObject goTable = new ClickableObject("Go Table", 100, 100, "table_button.png");
        GameInputData inputData = new GameInputData(goTable);

        interactor.execute(inputData);

        assertEquals(sceneTable, dao.getCurrentScene());
        assertEquals("Scene Table", presenter.outputData.getSceneName());
    }

    @Test
    void testExecuteGoTableFromClassroom() {
        Scene sceneClassroom = new Scene("Scene Classroom", new ArrayList<>(), "classroom.png");
        Scene sceneTable = new Scene("Scene Table", new ArrayList<>(), "table.png");

        dao.getScenes().put("Scene Classroom", sceneClassroom);
        dao.getScenes().put("Scene Table", sceneTable);
        dao.setCurrentScene(sceneClassroom);

        ClickableObject goTableFromClassroom = new ClickableObject("Go Table From Classroom", 100, 100, "button.png");
        GameInputData inputData = new GameInputData(goTableFromClassroom);

        interactor.execute(inputData);

        assertEquals(sceneTable, dao.getCurrentScene());
        assertEquals("Scene Table", presenter.outputData.getSceneName());
    }

    @Test
    void testExecuteGoStairsFromExit() {
        Scene sceneExit = new Scene("Scene Exit", new ArrayList<>(), "exit.png");
        Scene sceneStairs = new Scene("Scene Stairs", new ArrayList<>(), "stairs.png");

        dao.getScenes().put("Scene Exit", sceneExit);
        dao.getScenes().put("Scene Stairs", sceneStairs);
        dao.setCurrentScene(sceneExit);

        ClickableObject goStairs = new ClickableObject("Go Stairs From Exit", 100, 100, "stairs_button.png");
        GameInputData inputData = new GameInputData(goStairs);

        interactor.execute(inputData);

        assertEquals(sceneStairs, dao.getCurrentScene());
        assertEquals("Scene Stairs", presenter.outputData.getSceneName());
    }

    @Test
    void testExecuteGoStairsFromTable() {
        Scene sceneTable = new Scene("Scene Table", new ArrayList<>(), "table.png");
        Scene sceneStairs = new Scene("Scene Stairs", new ArrayList<>(), "stairs.png");

        dao.getScenes().put("Scene Table", sceneTable);
        dao.getScenes().put("Scene Stairs", sceneStairs);
        dao.setCurrentScene(sceneTable);

        ClickableObject goStairs = new ClickableObject("Go Stairs From Table", 100, 100, "stairs_button.png");
        GameInputData inputData = new GameInputData(goStairs);

        interactor.execute(inputData);

        assertEquals(sceneStairs, dao.getCurrentScene());
        assertEquals("Scene Stairs", presenter.outputData.getSceneName());
    }

    @Test
    void testExecuteObject3() {
        Scene currentScene = new Scene("CurrentScene", new ArrayList<>(), "current.png");
        Scene scene3 = new Scene("Scene3", new ArrayList<>(), "scene3.png");

        dao.getScenes().put("CurrentScene", currentScene);
        dao.getScenes().put("Scene3", scene3);
        dao.setCurrentScene(currentScene);

        ClickableObject object3 = new ClickableObject("Object3", 100, 100, "object3.png");
        GameInputData inputData = new GameInputData(object3);

        interactor.execute(inputData);

        assertEquals(scene3, dao.getCurrentScene());
        assertEquals("Scene3", presenter.outputData.getSceneName());
    }

    // ----------------------------------------------------------
    // Tests for execute() - NPC interactions
    // ----------------------------------------------------------

    @Test
    void testExecuteNonPlayableCharacter() {
        Scene scene = new Scene("TestScene", new ArrayList<>(), "test.png");
        dao.getScenes().put("TestScene", scene);
        dao.setCurrentScene(scene);

        DialogueText dialogueText = new DialogueText("Hello there!", 100, 100);
        DialogueBox dialogueBox = new DialogueBox(dialogueText, new ArrayList<>(), "dialogue.png");
        NonPlayableCharacter npc = new NonPlayableCharacter("NPC1", 200, 200, "npc.png", dialogueBox);

        GameInputData inputData = new GameInputData(npc);
        interactor.execute(inputData);

        assertEquals(dialogueBox, dao.getCurrentDialogue());
        assertEquals(1, presenter.prepareViewCallCount);
    }

    // ----------------------------------------------------------
    // Tests for execute() - Door interactions
    // ----------------------------------------------------------

    @Test
    void testExecuteDoorClassroomAlreadyUnlocked() {
        Scene sceneTable = new Scene("Scene Table", new ArrayList<>(), "table.png");
        Scene sceneClassroom = new Scene("Scene Classroom", new ArrayList<>(), "classroom.png");

        dao.getScenes().put("Scene Table", sceneTable);
        dao.getScenes().put("Scene Classroom", sceneClassroom);
        dao.setCurrentScene(sceneTable);
        dao.unlockDoor("Door Classroom");

        ClickableObject doorClassroom = new ClickableObject("Door Classroom", 100, 100, "door.png");
        GameInputData inputData = new GameInputData(doorClassroom);

        interactor.execute(inputData);

        assertEquals(sceneClassroom, dao.getCurrentScene());
        assertEquals("Scene Classroom", presenter.outputData.getSceneName());
    }

    @Test
    void testExecuteDoorClassroomWithKey() throws InvocationTargetException, InterruptedException {
        Scene sceneTable = new Scene("Scene Table", new ArrayList<>(), "table.png");
        Scene sceneClassroom = new Scene("Scene Classroom", new ArrayList<>(), "classroom.png");

        dao.getScenes().put("Scene Table", sceneTable);
        dao.getScenes().put("Scene Classroom", sceneClassroom);
        dao.setCurrentScene(sceneTable);

        Player player = new Player();
        Collectibles key = new Collectibles("Key Classroom", 0, 0, "key.png");
        player.addToInventory(key);
        dao.setPlayer(player);

        assertFalse(dao.isDoorUnlocked("Door Classroom"));
        assertTrue(dao.getPlayer().hasItemNamed("Key Classroom"));

        ClickableObject doorClassroom = new ClickableObject("Door Classroom", 100, 100, "door.png");
        GameInputData inputData = new GameInputData(doorClassroom);

        interactor.execute(inputData);

        // Wait for EDT to process the JOptionPane lambda
        SwingUtilities.invokeAndWait(() -> { });

        assertTrue(dao.isDoorUnlocked("Door Classroom"));
        assertFalse(dao.getPlayer().hasItemNamed("Key Classroom"));
        assertEquals(sceneClassroom, dao.getCurrentScene());
    }

    @Test
    void testExecuteDoorClassroomWithoutKey() throws InvocationTargetException, InterruptedException {
        Scene sceneTable = new Scene("Scene Table", new ArrayList<>(), "table.png");
        Scene sceneClassroom = new Scene("Scene Classroom", new ArrayList<>(), "classroom.png");

        dao.getScenes().put("Scene Table", sceneTable);
        dao.getScenes().put("Scene Classroom", sceneClassroom);
        dao.setCurrentScene(sceneTable);

        Player player = new Player();
        dao.setPlayer(player);

        assertFalse(dao.getPlayer().hasItemNamed("Key Classroom"));

        ClickableObject doorClassroom = new ClickableObject("Door Classroom", 100, 100, "door.png");
        GameInputData inputData = new GameInputData(doorClassroom);

        interactor.execute(inputData);

        // Wait for EDT to process the JOptionPane lambda (line 86 coverage)
        SwingUtilities.invokeAndWait(() -> { });

        assertFalse(dao.isDoorUnlocked("Door Classroom"));
        assertEquals(sceneTable, dao.getCurrentScene());
    }

    // ----------------------------------------------------------
    // Tests for execute() - Unknown clickable object
    // ----------------------------------------------------------

    @Test
    void testExecuteUnknownClickableObject() {
        Scene scene = new Scene("TestScene", new ArrayList<>(), "test.png");
        dao.getScenes().put("TestScene", scene);
        dao.setCurrentScene(scene);

        ClickableObject unknown = new ClickableObject("Unknown Object", 100, 100, "unknown.png");
        GameInputData inputData = new GameInputData(unknown);

        interactor.execute(inputData);

        assertEquals(scene, dao.getCurrentScene());
        assertEquals(1, presenter.prepareViewCallCount);
    }

    // ----------------------------------------------------------
    // Tests for executeDialogueOption()
    // ----------------------------------------------------------

    @Test
    void testExecuteDialogueOptionLeadsToScene() {
        Scene currentScene = new Scene("CurrentScene", new ArrayList<>(), "current.png");
        Scene targetScene = new Scene("TargetScene", new ArrayList<>(), "target.png");

        dao.getScenes().put("CurrentScene", currentScene);
        dao.getScenes().put("TargetScene", targetScene);
        dao.setCurrentScene(currentScene);

        DialogueText text = new DialogueText("Dialogue text", 100, 100);
        DialogueBox dialogueBox = new DialogueBox(text, new ArrayList<>(), "dialogue.png");
        dao.setCurrentDialogue(dialogueBox);

        DialogueOption option = new DialogueOption("Go to target", 100, 100, targetScene);

        interactor.executeDialogueOption(option);

        assertNull(dao.getCurrentDialogue());
        assertEquals(targetScene, dao.getCurrentScene());
        assertEquals(1, presenter.prepareViewCallCount);
    }

    @Test
    void testExecuteDialogueOptionLeadsToDialogue() {
        Scene scene = new Scene("TestScene", new ArrayList<>(), "test.png");
        dao.getScenes().put("TestScene", scene);
        dao.setCurrentScene(scene);

        DialogueText text1 = new DialogueText("First dialogue", 100, 100);
        DialogueBox dialogueBox1 = new DialogueBox(text1, new ArrayList<>(), "dialogue1.png");

        DialogueText text2 = new DialogueText("Second dialogue", 100, 100);
        DialogueBox dialogueBox2 = new DialogueBox(text2, new ArrayList<>(), "dialogue2.png");

        dao.setCurrentDialogue(dialogueBox1);

        DialogueOption option = new DialogueOption("Continue", 100, 100, dialogueBox2);

        interactor.executeDialogueOption(option);

        assertEquals(dialogueBox2, dao.getCurrentDialogue());
        assertEquals(scene, dao.getCurrentScene());
        assertEquals(1, presenter.prepareViewCallCount);
    }

    // ----------------------------------------------------------
    // Tests for updateView (indirectly through other methods)
    // ----------------------------------------------------------

    @Test
    void testUpdateViewSetsCorrectOutputData() {
        Collectibles item1 = new Collectibles("Item1", 10, 10, "item1.png");
        Collectibles item2 = new Collectibles("Item2", 20, 20, "item2.png");

        Player player = new Player();
        player.addToInventory(item1);
        player.addToInventory(item2);
        dao.setPlayer(player);

        ClickableObject obj1 = new ClickableObject("Obj1", 100, 100, "obj1.png");
        Scene scene = new Scene("TestScene", new ArrayList<>(List.of(obj1)), "background.png");
        dao.getScenes().put("TestScene", scene);
        dao.setCurrentScene(scene);

        ClickableObject unknown = new ClickableObject("Unknown", 50, 50, "unknown.png");
        interactor.execute(new GameInputData(unknown));

        assertNotNull(presenter.outputData);
        assertEquals("TestScene", presenter.outputData.getSceneName());
        assertEquals("background.png", presenter.outputData.getBackgroundImage());
        assertEquals(1, presenter.outputData.getClickableObjects().size());
        assertEquals("Obj1", presenter.outputData.getClickableObjects().get(0).getName());
        assertEquals(2, presenter.outputData.getInventory().size());
    }

    // ----------------------------------------------------------
    // Edge case tests
    // ----------------------------------------------------------

    @Test
    void testDoorUnlockedSceneNotFound() {
        Scene sceneTable = new Scene("Scene Table", new ArrayList<>(), "table.png");
        dao.getScenes().put("Scene Table", sceneTable);
        dao.setCurrentScene(sceneTable);
        dao.unlockDoor("Door Classroom");

        ClickableObject doorClassroom = new ClickableObject("Door Classroom", 100, 100, "door.png");
        GameInputData inputData = new GameInputData(doorClassroom);

        interactor.execute(inputData);

        assertEquals(sceneTable, dao.getCurrentScene());
    }

    @Test
    void testMultipleNavigations() {
        Scene scene1 = new Scene("Scene Stairs", new ArrayList<>(), "stairs.png");
        Scene scene2 = new Scene("Scene Exit", new ArrayList<>(), "exit.png");
        Scene scene3 = new Scene("Scene Table", new ArrayList<>(), "table.png");

        dao.getScenes().put("Scene Stairs", scene1);
        dao.getScenes().put("Scene Exit", scene2);
        dao.getScenes().put("Scene Table", scene3);
        dao.setCurrentScene(scene1);

        interactor.execute(new GameInputData(new ClickableObject("Go Exit", 0, 0, "btn.png")));
        assertEquals(scene2, dao.getCurrentScene());

        interactor.execute(new GameInputData(new ClickableObject("Go Stairs From Exit", 0, 0, "btn.png")));
        assertEquals(scene1, dao.getCurrentScene());

        interactor.execute(new GameInputData(new ClickableObject("Go Table", 0, 0, "btn.png")));
        assertEquals(scene3, dao.getCurrentScene());

        assertEquals(3, presenter.prepareViewCallCount);
    }
}
