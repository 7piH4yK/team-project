package entity.dialogue;

import entity.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DialogueBoxFactoryTest {

    @Test
    void testCreateDialogueBox() {
        Scene TargetScene = new Scene("SceneA", new ArrayList<>(),"bag.png");
        DialogueOption option = new DialogueOption("X", 0, 0, TargetScene);
        DialogueText text = new DialogueText("Text", 1, 2);

        DialogueBoxFactory factory = new  DialogueBoxFactory();
        DialogueBox box = factory.create(text, List.of(option), "bag.png");

        assertEquals(text, box.getText());
        assertEquals("bag.png", box.getImage());
        assertEquals(List.of(option), box.getOptions());
    }
}