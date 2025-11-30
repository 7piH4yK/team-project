package entity.nonplayablecharacter;

import entity.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NonPlayableCharacterFactoryTest {

    @Test
    void testCreateNonPlayableCharacter() {
        Scene TargetScene = new Scene("SceneA", new ArrayList<>(),"bag.png");
        DialogueOption option = new DialogueOption("X", 0, 0, TargetScene);
        DialogueText text = new DialogueText("Text", 1, 2);
        DialogueBoxFactory factory = new  DialogueBoxFactory();
        DialogueBox box = factory.create(text, List.of(option), "bag.png");

        NonPlayableCharacterFactory npc_factory = new  NonPlayableCharacterFactory();
        NonPlayableCharacter npc = npc_factory.create("NPC", 10,20, "bag.png",box);

        assertEquals("NPC", npc.getName());
        assertEquals(10, npc.getCoordinateX());
        assertEquals(20, npc.getCoordinateY());
        assertEquals("bag.png", npc.getImage());
        assertEquals(box, npc.getDB());
    }

}
