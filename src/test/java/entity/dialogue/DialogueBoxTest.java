package entity.dialogue;

import entity.DialogueBox;
import entity.DialogueOption;
import entity.DialogueText;
import entity.Scene;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DialogueBoxTest {

    @Test
    void testConstructorAndGetters() {
        Scene TargetScene = new Scene("SceneA", new ArrayList<>(),"bag.png");
        Scene SceneA = new Scene("SceneA", new ArrayList<>(),"bag.png");
        Scene SceneB = new Scene("SceneB", new ArrayList<>(),"bag.png");
        DialogueOption opt = new DialogueOption("X", 0, 0, TargetScene);
        DialogueOption opt1 = new DialogueOption("A", 1, 2, SceneA);
        DialogueOption opt2 = new DialogueOption("B", 3, 4, SceneB);

        DialogueText text = new DialogueText("Text",1,2);
        DialogueBox box = new DialogueBox(text, List.of(opt, opt1, opt2), "bag.png");

        assertEquals(text, box.getText());
        assertEquals("bag.png", box.getImage());
        assertEquals(List.of(opt, opt1, opt2), box.getOptions());
        List<DialogueOption> options = box.getOptions();
        assertEquals(3, options.size());
        assertEquals(opt, options.get(0));
        assertEquals(opt1, options.get(1));
        assertEquals(opt2, options.get(2));
    }
}