package entity.dialogue;

import entity.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DialogueOptionTest {

    @Test
    void testConstructorAndGetters() {
        Scene NextScene = new Scene("NextScene", new ArrayList<>(),"bag.png");
        DialogueOption option = new DialogueOption("Option1", 10, 20, NextScene);

        assertEquals("Option1", option.getName());
        assertEquals(10, option.getCoordinateX());
        assertEquals(20, option.getCoordinateY());
        assertEquals(NextScene, option.getTargetScene());
    }

    @Test
    void testTargetSceneTrue() {
        Scene NextScene = new Scene("NextScene", new ArrayList<>(),"bag.png");
        DialogueOption option = new DialogueOption("Go", 1, 2, NextScene);
        assertSame(NextScene,option.getTargetScene());
    }

    @Test
    void testTargetSceneFalse() {
        Scene NextScene = new Scene("NextScene", new ArrayList<>(),"bag.png");
        Scene NotNextScene = new Scene("NotNextScene", new ArrayList<>(),"bag.png");
        DialogueOption option = new DialogueOption("Go", 1, 2, NextScene);
        assertNotSame(NotNextScene,option.getTargetScene());
    }

    @Test
    void testTargetDialogueTrue() {
        DialogueBox NextBox = new DialogueBox(new DialogueText("NextBox",1,2), new ArrayList<>(),"bag.png");
        DialogueOption option = new DialogueOption("Go", 1, 2, NextBox);
        assertSame(NextBox,option.getTargetDialogue());
    }

    @Test
    void testTargetDialogueFalse() {
        DialogueBox NextBox = new DialogueBox(new DialogueText("NextBox",1,2), new ArrayList<>(),"bag.png");
        DialogueBox NotNextBox = new DialogueBox(new DialogueText("NotNextBox",1,2), new ArrayList<>(),"bag.png");
        DialogueOption option = new DialogueOption("Go", 1, 2, NextBox);
        assertNotSame(NotNextBox,option.getTargetDialogue());
    }

    @Test
    void testTargetGetName() {
        DialogueBox NextBox = new DialogueBox(new DialogueText("NextBox",1,2), new ArrayList<>(),"bag.png");
        DialogueOption option = new DialogueOption("Go", 1, 2, NextBox);
        assertEquals("Go", option.getName());
    }

    @Test
    void testTargetEmptyNameDialogueBox() {
        DialogueBox NextBox = new DialogueBox(new DialogueText("NextBox",1,2), new ArrayList<>(),"bag.png");
        assertThrows(IllegalArgumentException.class, () -> new DialogueOption("", 1, 2, NextBox));
    }

    @Test
    void testTargetEmptyNameScene() {
        Scene NextScene = new Scene("NextScene", new ArrayList<>(),"bag.png");
        assertThrows(IllegalArgumentException.class, () -> new DialogueOption("", 1, 2, NextScene));
    }

    @Test
    void testTargetGetCoordinateX() {
        DialogueBox NextBox = new DialogueBox(new DialogueText("NextBox",1,2), new ArrayList<>(),"bag.png");
        DialogueOption option = new DialogueOption("Go", 1, 2, NextBox);
        assertEquals(1, option.getCoordinateX());
    }

    @Test
    void testTargetGetCoordinateY() {
        DialogueBox NextBox = new DialogueBox(new DialogueText("NextBox",1,2), new ArrayList<>(),"bag.png");
        DialogueOption option = new DialogueOption("Go", 1, 2, NextBox);
        assertEquals(2, option.getCoordinateY());
    }

    @Test
    void testTargetLeadsToScene() {
        Scene NextScene = new Scene("NextScene", new ArrayList<>(),"bag.png");
        DialogueOption option = new DialogueOption("Go", 1, 2, NextScene);
        assertTrue(option.leadsToScene());
        assertFalse(option.leadsToDialogue());
    }

    @Test
    void testTargetLeadsToDialogue() {
        DialogueBox NextBox = new DialogueBox(new DialogueText("NextBox",1,2), new ArrayList<>(),"bag.png");
        DialogueOption option = new DialogueOption("Go", 1, 2, NextBox);
        assertFalse(option.leadsToScene());
        assertTrue(option.leadsToDialogue());
    }
}