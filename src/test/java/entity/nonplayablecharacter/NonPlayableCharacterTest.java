package entity.nonplayablecharacter;

import entity.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NonPlayableCharacterTest {

    @Test
    void testNonPlayableCharacter() {
        Scene TargetScene = new Scene("SceneA", new ArrayList<>(),"bag.png");
        DialogueOption opt = new DialogueOption("X", 0, 0, TargetScene);
        DialogueText text = new DialogueText("Text",1,2);
        DialogueBox box = new DialogueBox(text, List.of(opt), "bag.png");

        assertDoesNotThrow(() -> new NonPlayableCharacter("NPC", 1, 2, "bag.png", box));
    }

    @Test
    void testNonPlayableCharacterErrors() {
        Scene TargetScene = new Scene("SceneA", new ArrayList<>(),"bag.png");
        DialogueOption opt = new DialogueOption("X", 0, 0, TargetScene);
        DialogueText text = new DialogueText("Text",1,2);
        DialogueBox box = new DialogueBox(text, List.of(opt), "bag.png");

        assertThrows(IllegalArgumentException.class, () -> new NonPlayableCharacter("", 1, 2, "bag.png", box));
        assertThrows(IllegalArgumentException.class, () -> new NonPlayableCharacter("NPC", 1, 2, "", box));
    }

    @Test
    void testGetters() {
        Scene TargetScene = new Scene("SceneA", new ArrayList<>(),"bag.png");
        DialogueOption opt = new DialogueOption("X", 0, 0, TargetScene);
        DialogueText text = new DialogueText("Text",1,2);
        DialogueBox box = new DialogueBox(text, List.of(opt), "bag.png");
        NonPlayableCharacter npc = new NonPlayableCharacter("NPC", 10, 20, "bag.png", box);

        assertEquals("NPC", npc.getName());
        assertEquals(10, npc.getCoordinateX());
        assertEquals(20, npc.getCoordinateY());
        assertEquals("bag.png", npc.getImage());
        assertEquals(box, npc.getDB());
    }
}
