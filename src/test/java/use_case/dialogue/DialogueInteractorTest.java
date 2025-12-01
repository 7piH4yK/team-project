package use_case.dialogue;

import entity.*;
import org.junit.jupiter.api.Test;
import use_case.game.GameInputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DialogueInteractorTest {

    class StubDAO implements DialogueDataAccessInterface {

        DialogueBox currentDialogue;
        Scene currentScene;
        Map<String, Scene> scenes = new java.util.HashMap<>();

        @Override
        public DialogueBox getCurrentDialogue() {
            return currentDialogue;
        }

        @Override
        public void setCurrentDialogue(DialogueBox dialogue) {
            this.currentDialogue = dialogue;
        }

        @Override
        public Scene getCurrentScene() {
            return currentScene;
        }

        @Override
        public void setCurrentScene(Scene scene) {
            this.currentScene = scene;
        }

        @Override
        public Map<String, Scene> getScenes() {
            return scenes;
        }
    }

    static class StubPresenter implements DialogueOutputBoundary {
        DialogueOutputData lastData;

        @Override
        public void prepareView(DialogueOutputData outputData) {
            this.lastData = outputData;
        }
    }

    @Test
    void testExecuteDialogueOption_leadsToScene() {
        StubDAO dao = new StubDAO();
        StubPresenter presenter = new StubPresenter();
        DialogueInteractor interactor = new DialogueInteractor(dao, presenter);

        Scene next = new Scene("NextScene", new ArrayList<>(), "img.png");
        DialogueOption option = new DialogueOption("Go", 1, 1, next);

        dao.currentDialogue = new DialogueBox(
                new DialogueText("hi", 1, 1),
                List.of(),
                "img.png"
        );

        interactor.executeDialogueOption(option);

        assertNull(dao.getCurrentDialogue());
        assertEquals(next, dao.currentScene);
        assertNotNull(presenter.lastData);
    }

    @Test
    void testExecuteDialogueOption_leadsToDialogue() {
        StubDAO dao = new StubDAO();
        StubPresenter presenter = new StubPresenter();
        DialogueInteractor interactor = new DialogueInteractor(dao, presenter);

        DialogueBox nextBox = new DialogueBox(
                new DialogueText("dialogue", 1, 1),
                List.of(),
                "img.png"
        );
        DialogueOption option = new DialogueOption("Talk", 1, 1, nextBox);

        interactor.executeDialogueOption(option);

        assertEquals(nextBox, dao.getCurrentDialogue());
        assertNotNull(presenter.lastData);
    }

    @Test
    void testExecute_withNPC() {
        StubDAO dao = new StubDAO();
        StubPresenter presenter = new StubPresenter();
        DialogueInteractor interactor = new DialogueInteractor(dao, presenter);

        DialogueBox db = new DialogueBox(
                new DialogueText("npc", 1, 1),
                List.of(),
                "img.png"
        );

        NonPlayableCharacter npc = new NonPlayableCharacter("npc", 1, 1, "npc.png", db);

        interactor.execute(new GameInputData(npc));

        assertEquals(db, dao.getCurrentDialogue());
        assertNotNull(presenter.lastData);
    }

    @Test
    void testExecute_withNonNPC() {
        StubDAO dao = new StubDAO();
        StubPresenter presenter = new StubPresenter();
        DialogueInteractor interactor = new DialogueInteractor(dao, presenter);

        ClickableObject obj = new ClickableObject("item", 1, 1, "img.png");

        interactor.execute(new GameInputData(obj));

        assertNull(dao.getCurrentDialogue());
        assertNotNull(presenter.lastData);
    }
}
