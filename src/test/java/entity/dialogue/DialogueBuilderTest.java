package entity.dialogue;

import entity.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DialogueBuilderTest {

    @Test
    void testBuild() {
        assertDoesNotThrow(() -> new DialogueBuilder("bag.png").setText("Hello").build());
    }

    @Test
    void testGetImage() {
        DialogueBuilder builder = new DialogueBuilder("bag.png");

        assertEquals("bag.png", builder.getImage());
    }

    @Test
    void testAddOption() {
        DialogueBuilder builder = new DialogueBuilder("bag.png");

        Scene TargetScene = new Scene("SceneA", new ArrayList<>(),"bag.png");
        builder.addOption("Scene", TargetScene);

        DialogueBox NextBox = new DialogueBox(new DialogueText("NextBox",1,2), new ArrayList<>(),"bag.png");
        builder.addOption("Dialogue", NextBox);
    }

    @Test
    void testAddOptionError() {
        DialogueBuilder builder = new DialogueBuilder("bag.png");

        Scene TargetScene = new Scene("Scene", new ArrayList<>(),"bag.png");
        assertThrows(IllegalArgumentException.class, () -> builder.addOption("", TargetScene));
        assertThrows(IllegalArgumentException.class, () -> builder.addOption(null, TargetScene));

        DialogueBox NextBox = new DialogueBox(new DialogueText("Box",1,2), new ArrayList<>(),"bag.png");
        assertThrows(IllegalArgumentException.class, () -> builder.addOption("", NextBox));
        assertThrows(IllegalArgumentException.class, () -> builder.addOption(null, NextBox));
    }
}