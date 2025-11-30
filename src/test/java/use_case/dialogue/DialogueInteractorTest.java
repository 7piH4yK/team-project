package use_case.dialogue;

import entity.*;
import org.junit.jupiter.api.Test;
import use_case.game.GameInputData;
import use_case.game.GameInteractor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DialogueInteractorTest {

    @Test
    void testDialogueInteractor() {
        assertDoesNotThrow(() -> new DialogueInteractor(null, null));
    }

    @Test
    void testExecuteDialogueOption() {
        Scene NextScene = new Scene("NextScene", new ArrayList<>(),"bag.png");
        DialogueOption optionScene = new DialogueOption("Go", 1, 2, NextScene);
        DialogueBox NextBox = new DialogueBox(new DialogueText("NextBox",1,2), new ArrayList<>(),"bag.png");
        DialogueOption optionDialogue = new DialogueOption("Go", 1, 2, NextBox);

        DialogueInteractor Interactor = new DialogueInteractor(null, null);

        assertThrows(NullPointerException.class, () -> Interactor.executeDialogueOption(optionScene));
        assertThrows(NullPointerException.class, () -> Interactor.executeDialogueOption(optionDialogue));
    }

    @Test
    void testUpdateView() {
        ClickableObject object = new ClickableObject("name", 1, 2, "bag.png");
        GameInputData gameInputData = new GameInputData(object);
        DialogueInteractor Interactor = new DialogueInteractor(null, null);
        assertThrows(NullPointerException.class,() -> Interactor.execute(gameInputData));
    }
}
