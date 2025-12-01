package use_case.load;
import entity.Scene;
import entity.ClickableObject;
import entity.Collectibles;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Fake DAO for the load test.
 */
class FakeLoadDAO implements LoadDataAccessInterface {
    boolean loadGameCalled = false;
    Scene currentScene;

    @Override
    public void loadGame(String filePath) {
        loadGameCalled = true;
    }

    @Override
    public Scene getCurrentScene() {
        return currentScene;
    }
}

/**
 * Fake presenter for the load test
 */
class FakeLoadPresenter implements LoadOutputBoundary {
    boolean switched = false;
    LoadOutputData capturedData = null;
    String errorMessage = null;

    @Override
    public void switchToLoadView(LoadOutputData data) {
        switched = true;
        capturedData = data;
    }

    @Override
    public void displayError(String message) {
        errorMessage = message;
    }
}

/**
 * Executes two tests, one for when load is called with existing save,
 * one for when load is called without existing save.
 */
class LoadInteractorTest {

    /**
     * Tests for the following:
     *      --> loadGame() is called
     *      --> presenter swaps views
     *      --> output data (save data) is passed to presenter
     *      --> correct scene and objects are presented
     */
    @Test
    void testExecuteWithExistingSave() {
        // set up
        FakeLoadDAO dao = new FakeLoadDAO();
        FakeLoadPresenter presenter = new FakeLoadPresenter();

        List<ClickableObject> objects = new ArrayList<>();
        objects.add(new Collectibles("Gem", 10, 20, "gem.png"));
        Scene fakeScene = new Scene("Forest", objects, "forest.png");
        dao.currentScene = fakeScene;

        File dummyFile = new File("save.json");
        try { dummyFile.createNewFile(); } catch (Exception ignored) {}

        LoadInteractor interactor = new LoadInteractor(dao, presenter);

        // start test
        interactor.execute();

        // assertions
        assertTrue(dao.loadGameCalled, "loadGame() should be called");
        assertTrue(presenter.switched, "Presenter should switch to load view");
        assertNotNull(presenter.capturedData, "LoadOutputData should be passed to presenter");
        assertEquals("forest.png", presenter.capturedData.getBackgroundImage());
        assertEquals(objects, presenter.capturedData.getClickableObjects());

        // file cleanup
        dummyFile.delete();
    }

    /**
     * Tests for the following:
     *      --> loadGame() is not called
     *      --> no output data is passed
     *      --> error message is presented
     */
    @Test
    void testExecuteWithoutSaveFile() {
        // set up
        FakeLoadDAO dao = new FakeLoadDAO();
        FakeLoadPresenter presenter = new FakeLoadPresenter();

        File file = new File("save.json");
        if (file.exists()) file.delete();

        LoadInteractor interactor = new LoadInteractor(dao, presenter);

        // start of test
        interactor.execute();

        // assertions
        assertFalse(dao.loadGameCalled, "loadGame() should NOT be called");
        assertNull(presenter.capturedData, "LoadOutputData should NOT be passed");
        assertEquals("No saved game found!", presenter.errorMessage);
    }
}
