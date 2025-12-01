package use_case.save;

import entity.ClickableObject;
import entity.Collectibles;
import entity.Player;
import entity.Scene;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SaveInteractor test the sees if it correctly calls each part of the
 * use case.
 */
class SaveInteractorTest {

    /**
     * An implementation of a fake DAO to use during test.
     */
    static class FakeSaveDAO implements SaveDataAccessInterface {
        Player player;
        Scene currentScene;
        Map<String, Scene> scenes = new HashMap<>();

        SaveOutputData savedData = null; // to capture what was passed in

        @Override
        public Scene getCurrentScene() {
            return currentScene;
        }

        @Override
        public Map<String, Scene> getScenes() {
            return scenes;
        }

        @Override
        public Player getPlayer() {
            return player;
        }

        @Override
        public void saveGame(SaveOutputData outputData) {
            this.savedData = outputData; // capture for assertions
        }
    }

    /**
     * An implementation of a fake presenter.
     */
    static class FakeSavePresenter implements SaveOutputBoundary {
        boolean switched = false;

        @Override
        public void switchToSaveView() {
            switched = true;
        }
    }

    /**
     * Executes test that checks that:
     *      --> saveGame() is called
     *      --> output data is correct
     *      --> a correct call is made to the presenter
     */
    @Test
    void testExecute() {
        // set up
        FakeSaveDAO dao = new FakeSaveDAO();
        FakeSavePresenter presenter = new FakeSavePresenter();

        List<Collectibles> inventory = new ArrayList<>();
        List<ClickableObject> scene_objects = new ArrayList<>();

        inventory.add(new Collectibles("Sword", 100, 200, "..."));
        inventory.add(new Collectibles("Key", 300, 150, "..."));
        scene_objects.add(new Collectibles("Gem", 50, 60, "..."));

        dao.player = new Player(inventory);
        dao.currentScene = new Scene("ex", scene_objects, "...");
        dao.scenes.put("ex", dao.currentScene);

        SaveInteractor interactor = new SaveInteractor(dao, presenter);

        // actual test
        interactor.execute();

        // assert: saveGame() must have been called
        assertNotNull(dao.savedData, "saveGame() was not called");

        // assert: savedData fields match what DAO provided
        assertEquals(dao.player, dao.savedData.getPlayer());
        assertEquals(dao.currentScene, dao.savedData.getCurrentScene());
        assertEquals(dao.scenes, dao.savedData.getScenes());

        // assert: presenter was triggered
        assertTrue(presenter.switched, "Presenter did not switch to save view");
    }
}
