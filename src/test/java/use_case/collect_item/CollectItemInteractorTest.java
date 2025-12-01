package use_case.collect_item;

import data_access.InMemoryGameDataAccessObject;
import entity.*;
import interface_adapter.factories.PlayerFactoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CollectItemInteractorTest {

    // Simple presenter to capture output (NO mock, NO fake)
    static class TestPresenter implements CollectItemOutputBoundary {
        CollectItemOutputData successData;
        String failMessage;

        @Override
        public void prepareSuccessView(CollectItemOutputData data) {
            this.successData = data;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.failMessage = errorMessage;
        }
    }

    // Real DAO
    private InMemoryGameDataAccessObject dao;
    private TestPresenter presenter;
    private CollectItemInteractor interactor;

    @BeforeEach
    void setUp() {
        SceneFactory sceneFactory = new SceneFactory();
        ClickableObjectFactory clickFactory = new ClickableObjectFactory();
        NonPlayableCharacterFactory npcFactory = new NonPlayableCharacterFactory();
        PlayerFactory playerFactory = new PlayerFactory();
        dao = new InMemoryGameDataAccessObject(sceneFactory, clickFactory, npcFactory, playerFactory);
        dao.getScenes().clear();
        dao.setPlayer(new Player());
        dao.setCurrentScene(null);
        presenter = new TestPresenter();
        interactor = new CollectItemInteractor(dao, presenter);

        // Clear auto-loaded scenes from resetGame() and build our own test scene

    }

    // ----------------------------------------------------------
    // SUCCESS: Collect a valid Collectible
    // ----------------------------------------------------------
    @Test
    void testSuccessfulCollect() {

        Collectibles key = new Collectibles("Key", 10, 10, "key.png");
        Scene testScene = new Scene("TestScene",
                new ArrayList<>(List.of(key)),
                "test.png");

        dao.getScenes().put("TestScene", testScene);
        dao.setCurrentScene(testScene);

        CollectItemInputData input = new CollectItemInputData("Key", "TestScene");

        interactor.collect(input);

        // Inventory updated
        assertEquals(1, dao.getPlayer().getInventory().size());
        assertEquals("Key", dao.getPlayer().getInventory().get(0).getName());

        // Scene updated: object removed
        Scene updated = dao.getScenes().get("TestScene");
        assertEquals(0, updated.getObjects().size());

        // Current scene updated
        assertEquals(updated, dao.getCurrentScene());

        // Presenter received success
        assertNotNull(presenter.successData);
        assertNull(presenter.failMessage);
        assertEquals("Key", presenter.successData.getCollectedItemName());
    }

    // ----------------------------------------------------------
    // FAIL: Scene not found
    // ----------------------------------------------------------
    @Test
    void testSceneNotFound() {
        CollectItemInputData input = new CollectItemInputData("Key", "MissingScene");
        interactor.collect(input);

        assertEquals("Scene not found: MissingScene", presenter.failMessage);
        assertNull(presenter.successData);
    }

    // ----------------------------------------------------------
    // FAIL: Object exists but is NOT Collectibles
    // ----------------------------------------------------------
    @Test
    void testObjectNotCollectible() {
        ClickableObject rock = new ClickableObject("Rock", 0, 0, "rock.png");
        Scene testScene = new Scene("TestScene", List.of(rock), "test.png");

        dao.getScenes().put("TestScene", testScene);

        CollectItemInputData input = new CollectItemInputData("Rock", "TestScene");
        interactor.collect(input);

        assertEquals("This object cannot be collected.", presenter.failMessage);
        assertTrue(dao.getPlayer().getInventory().isEmpty());
    }

    // ----------------------------------------------------------
    // FAIL: Object not found in scene
    // ----------------------------------------------------------
    @Test
    void testObjectNotFoundInScene() {
        Scene testScene = new Scene("TestScene", List.of(), "test.png");
        dao.getScenes().put("TestScene", testScene);

        CollectItemInputData input = new CollectItemInputData("Key", "TestScene");
        interactor.collect(input);

        assertEquals("This object cannot be collected.", presenter.failMessage);
        assertNull(presenter.successData);
    }
}
